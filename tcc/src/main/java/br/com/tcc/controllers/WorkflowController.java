package br.com.tcc.controllers;

import com.opensymphony.workflow.WorkflowException;
import br.com.tcc.Exceptions.ServiceOrderException;
import br.com.tcc.Exceptions.WorkflowInstanceNotFound;
import br.com.tcc.model.ServiceOrder;
import br.com.tcc.model.WorkflowInstance;
import br.com.tcc.model.WorkflowModel;
import br.com.tcc.repository.WorkflowInstanceRepository;
import br.com.tcc.repository.WorkflowModelRepository;
import br.com.tcc.services.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by hernaneb on 19/10/16.
 */
@RestController
public class WorkflowController {

	@Autowired
	WorkflowModelRepository workflowModelRepository;

	@Autowired
	WorkflowInstanceRepository workflowInstanceRepository;

	@Autowired
	WorkflowService workflowService;

	@RequestMapping( value = "/workflow/instance", method = RequestMethod.POST )
	public ResponseEntity createWorkflow( @RequestBody ServiceOrder serviceOrder ){

		WorkflowInstance workflowInstance = null;
		try {
			workflowInstance = workflowService.createWorkflow( serviceOrder );
		} catch (WorkflowException e) {
			e.printStackTrace();
		} catch (ServiceOrderException e) {
			e.setServiceOrder(serviceOrder);
			return new ResponseEntity<ServiceOrderException>(e, HttpStatus.BAD_REQUEST);
		}

		Resource<WorkflowInstance> workflowInstanceResource = getWorkflowInstanceResource(workflowInstance);
		if(workflowInstanceResource == null ){
			return ResponseEntity.notFound().build();
		}
		return new ResponseEntity<Resource<WorkflowInstance>>(workflowInstanceResource, HttpStatus.CREATED);
	}

	@RequestMapping( value = "/workflow/instance/{id}", method = RequestMethod.GET )
	public ResponseEntity<Resource<WorkflowInstance>> getWorkflowInstanceById( @PathVariable("id") Long id ){

		WorkflowInstance workflowInstance = workflowInstanceRepository.findOne(id);

		Resource<WorkflowInstance> workflowInstanceResource = getWorkflowInstanceResource(workflowInstance);
		if(workflowInstanceResource == null ){
			return new ResponseEntity<Resource<WorkflowInstance>>(workflowInstanceResource, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Resource<WorkflowInstance>>(workflowInstanceResource, HttpStatus.OK);
	}

	@RequestMapping( value = "/workflow/instance", method = RequestMethod.GET )
	public ResponseEntity getAllWorkflowInstance(  ){

		List<WorkflowInstance> workflowInstances = workflowInstanceRepository.findAll();

		List<Resource<WorkflowInstance>> workflowInstanceResources = new ArrayList<>();
		for( WorkflowInstance workflowInstance : workflowInstances ){
			Resource<WorkflowInstance> workflowInstanceResource = getWorkflowInstanceResource(workflowInstance);
			workflowInstanceResources.add(workflowInstanceResource);
		}

		return new ResponseEntity<List<Resource<WorkflowInstance>>>(workflowInstanceResources, HttpStatus.OK);
	}


	@RequestMapping( value = "/workflow/instance/{id}/resume", method = RequestMethod.GET )
	public ResponseEntity<Resource<WorkflowInstance>> resumeWorkflowByInstanceId( @PathVariable("id") Long id ) throws WorkflowException {

		WorkflowInstance workflowInstance = null;
		Resource<WorkflowInstance> workflowInstanceResource = null;
		try {
			workflowInstance = workflowService.resumeWorkflow(id);
		} catch (WorkflowInstanceNotFound e) {
			return new ResponseEntity<Resource<WorkflowInstance>>(workflowInstanceResource,
					HttpStatus.NOT_FOUND);
		}
		workflowInstanceResource = getWorkflowInstanceResource(workflowInstance);

		return new ResponseEntity<Resource<WorkflowInstance>>(workflowInstanceResource, HttpStatus.OK);

	}

	@RequestMapping( value = "/workflow", method = RequestMethod.GET )
	public ResponseEntity findAllWorkflows( ){
		List<Resource<WorkflowModel>> workflowModelResources = new ArrayList<>();

		List<WorkflowModel> workflowModels = workflowModelRepository.findAll();

		for( WorkflowModel workflowModel : workflowModels ){
			workflowModelResources.add(getWorkflowResource(workflowModel));
		}

		return ResponseEntity.ok(workflowModelResources);
	}

	@RequestMapping( value = "/workflow/{id}", method = RequestMethod.GET )
	public ResponseEntity<Resource<WorkflowModel>> findWorkflowById(@PathVariable("id") Long id) {


		WorkflowModel workflowModel = workflowModelRepository.findOne(id);

		return ResponseEntity.ok(getWorkflowResource(workflowModel));
	}

	private Resource<WorkflowModel> getWorkflowResource( WorkflowModel workflowModel ){
		if( workflowModel == null ){
			return null;
		}
		Resource<WorkflowModel> workflowModelResource = new Resource<>(workflowModel);

		workflowModelResource.add( linkTo(methodOn(WorkflowController.class).findWorkflowById( workflowModel.getId() ) ).withSelfRel() );

		return workflowModelResource;
	}

	private Resource<WorkflowInstance> getWorkflowInstanceResource( WorkflowInstance workflowInstance ){
		if(workflowInstance == null ){
			return null;
		}
		Resource<WorkflowInstance> workflowInstanceResource = new Resource<>(workflowInstance);

		workflowInstanceResource.add( linkTo(methodOn(WorkflowController.class).getWorkflowInstanceById( workflowInstance.getId() ) ).withSelfRel() );
		workflowInstanceResource.add( linkTo( methodOn(ServiceOrderController.class).
									findServiceOrderById(workflowInstance.getServiceOrder().getId() ) ).
									withRel("serviceOrder")
		);
		workflowInstanceResource.add( linkTo( methodOn(HistoryStepController.class).
									findHistoryStepsByWorkflowInstanceId(workflowInstance.getId() ) ).
									withRel("historySteps")
		);
		return workflowInstanceResource;
	}
}
