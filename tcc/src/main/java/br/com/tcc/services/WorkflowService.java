package br.com.tcc.services;

import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import br.com.tcc.Exceptions.ServiceOrderException;
import br.com.tcc.Exceptions.WorkflowInstanceNotFound;
import br.com.tcc.model.OsWorkflowdefs;
import br.com.tcc.model.ServiceOrder;
import br.com.tcc.model.WorkflowInstance;
import br.com.tcc.model.WorkflowModel;
import br.com.tcc.repository.OsWorkflowdefsRepository;
import br.com.tcc.repository.ServiceOrderRepository;
import br.com.tcc.repository.WorkflowInstanceRepository;
import br.com.tcc.repository.WorkflowModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by hernaneb on 18/10/16.
 */

@Service
public class WorkflowService {

	@Autowired
	private Workflow workflow;

	public static final int DEFAULT_INITIAL_ACTION = 0;

	@Autowired
	ServiceOrderRepository serviceOrderRepository;

	@Autowired
	WorkflowModelRepository workflowModelRepository;

	@Autowired
	WorkflowInstanceRepository workflowInstanceRepository;

	@Autowired OsWorkflowdefsRepository osWorkflowdefsRepository;

	public WorkflowService(){

	}

	public WorkflowInstance createWorkflow( ServiceOrder serviceOrder )
			throws  WorkflowException,
					ServiceOrderException {

		WorkflowInstance workflowInstance = null;
		try {

			if( serviceOrder.getId() == null ){
				serviceOrderRepository.saveAndFlush( serviceOrder );
				System.out.println( "New Service Order Created:" + serviceOrder.getId() );
			}else if (serviceOrderRepository.exists(serviceOrder.getId()))
				throw new ServiceOrderException("Ja existe uma service order cadastrata com o id " + serviceOrder.getId());
			else {
				throw new ServiceOrderException("Service Ordem possui id, remova o ID");
			}


//			ToDo: Validar salvar log

			WorkflowModel workflowModel = null;
			List<WorkflowModel> workflowModels = getWorkflowModelByServiceOrder( serviceOrder );

			if ( validWorkflowModels(serviceOrder, workflowModels) ) {
				return null;
			}
			workflowModel = workflowModels.get(0);
			System.out.println("Workflow found:" + workflowModel );
			workflowInstance = initializeNewWorkflow( serviceOrder, workflowModel );

		} catch (WorkflowException e) {
			e.printStackTrace();
			throw e;
		}catch ( ServiceOrderException e ){
			e.printStackTrace();
			throw e;
		}

		return workflowInstance;
	}

	private boolean validWorkflowModels(ServiceOrder serviceOrder, List<WorkflowModel> workflowModels) {
		if( workflowModels == null || workflowModels.size() <= 0 ){
			String msg = "No workflowModel Active found for name: " + serviceOrder.getWorkflowModelName() +
					" and Version: " + serviceOrder.getWorkflowModelVersion();
			System.out.println( msg );
			return true;
		}else if( workflowModels.size() > 1){
			String msg = "More than one workflowModel Active found for name: " + serviceOrder.getWorkflowModelName() +
					" and Version: " + serviceOrder.getWorkflowModelVersion();
			System.out.println( msg );
			return true;
		}
		return false;
	}

	public WorkflowInstance initializeNewWorkflow(ServiceOrder serviceOrder, WorkflowModel workflowModel) throws WorkflowException {
		WorkflowInstance workflowInstance = new WorkflowInstance();

		workflowInstance.setCreatedDate( new Date() );
		workflowInstance.setWorkflowModel( workflowModel );
		workflowInstance.setServiceOrder( serviceOrder );

		Map<String, Object> inputs = new HashMap();

		inputs.put("workflowInstance", workflowInstance );
		inputs.put("serviceOrder", workflowInstance.getServiceOrder() );

		System.out.println("Initialize Workflow name: " + workflowModel.getName() + " in version " + workflowModel.getVersion() );

		OsWorkflowdefs osWorkflowdefs = osWorkflowdefsRepository.findOne( workflowModel.getFkOsworkflowDef() );

		Long idExecution = workflow.initialize( osWorkflowdefs.getWfName(), DEFAULT_INITIAL_ACTION, inputs);

		workflowInstance.setIdExecution( idExecution );

		workflowInstanceRepository.saveAndFlush( workflowInstance );

		int[] actions = workflow.getAvailableActions( idExecution );

		validingWorkflow( workflowInstance.getServiceOrder(), workflowInstance, actions);

		return workflowInstance;
	}

	private List<WorkflowModel> getWorkflowModelByServiceOrder( ServiceOrder serviceOrder ){
		if( serviceOrder.getWorkflowModelName() == null ||
			"".equals( serviceOrder.getWorkflowModelName() ) ||
			serviceOrder.getWorkflowModelVersion() == null ||
			"".equals( serviceOrder.getWorkflowModelVersion() ) ){
			System.out.println( "No workflowModelName or workflowModelVersion in serviceOrder" );

			return new ArrayList<>();
		}
		List<WorkflowModel> workflowModels = workflowModelRepository.getValidWorkflowModelByName(
				serviceOrder.getWorkflowModelName(), serviceOrder.getWorkflowModelVersion() );

		return workflowModels;
	}


	public WorkflowInstance resumeWorkflow( Long workflowInstanceId ) throws WorkflowInstanceNotFound {

		WorkflowInstance workflowInstance = workflowInstanceRepository.findOne(workflowInstanceId);

		if( workflowInstance == null ){
			throw new WorkflowInstanceNotFound("Não foi encontrato Workflow Instance para o id " + workflowInstanceId);
		}
		Map<String, Object> inputs = new HashMap();

		inputs.put("workflowInstance", workflowInstance );
		inputs.put("serviceOrder", workflowInstance.getServiceOrder() );

		int[] actions = getAvailableActions( workflowInstance.getIdExecution() );
		if( actions.length <= 0 ){
			return workflowInstance;
		}
		try {
			System.out.println("Start Workflow: " + workflowInstance.getIdExecution() +
				" for serviceOrder: " + workflowInstance.getServiceOrder().getId() );
			workflow.doAction( workflowInstance.getIdExecution(), actions[0], inputs );

			actions = getAvailableActions( workflowInstance.getIdExecution() );

			validingWorkflow(workflowInstance.getServiceOrder(), workflowInstance, actions);
		} catch (WorkflowException e) {
			e.printStackTrace();
		} catch ( Exception e ){
			e.printStackTrace();
		}

		return workflowInstance;
	}

	private void validingWorkflow(ServiceOrder serviceOrder, WorkflowInstance workflowInstance, int[] actions) {
		if( actions.length > 0 ){
			String workflowName = this.workflow.getWorkflowName( workflowInstance.getIdExecution() );
			System.out.println( "workflow name: " + workflowName );
			WorkflowDescriptor workflowDescriptor = this.workflow.getWorkflowDescriptor( workflowName );
			ActionDescriptor actionDescriptor = workflowDescriptor.getAction( actions[0] );

			workflowInstance.setStatus( actionDescriptor.getUnconditionalResult().getStatus() );
			serviceOrder.setLastUpdatedDate( new Date() );
			workflowInstance.setLastUpdatedDate( new Date() );

			workflowInstanceRepository.saveAndFlush( workflowInstance );

			System.out.println( "Update ServiceOrder: " + serviceOrder.getId() + " set status: " +
					actionDescriptor.getUnconditionalResult().getStatus() );
		}else{
			serviceOrder.setLastUpdatedDate( new Date() );
			serviceOrder.setClosedDate( new Date() );
			workflowInstance.setStatus( WorkflowInstance.CLOSED_STATUS );
			workflowInstance.setLastUpdatedDate( new Date() );
			workflowInstance.setClosedDate( new Date() );

			workflowInstanceRepository.saveAndFlush( workflowInstance );
		}
	}

	private int[] getAvailableActions( Long workflowInstanceId ){
		return this.workflow.getAvailableActions( workflowInstanceId );
	}

}
