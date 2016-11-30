package br.com.tcc.controllers;

import br.com.tcc.model.HistoryStep;
import br.com.tcc.model.WorkflowInstance;
import br.com.tcc.repository.HistoryStepRepository;
import br.com.tcc.repository.WorkflowInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by hernaneb on 11/11/16.
 */
@RestController
public class HistoryStepController {

	@Autowired HistoryStepRepository historyStepRepository;
	@Autowired WorkflowInstanceRepository workflowInstanceRepository;

	@RequestMapping( value = "/historyStep/workflow/instace/{id}", method = RequestMethod.GET )
	public ResponseEntity<List<Resource<HistoryStep>>> findHistoryStepsByWorkflowInstanceId(@PathVariable("id") Long id) {
		WorkflowInstance workflowInstance = workflowInstanceRepository.findOne(id);
		List<Resource<HistoryStep>> historyStepResource = new ArrayList<>();

		if(workflowInstance == null){
			return new ResponseEntity<List<Resource<HistoryStep>>>(historyStepResource,
					HttpStatus.NOT_FOUND);
		}
		List<HistoryStep> historySteps = historyStepRepository.getHistoryStepByEntryId( workflowInstance.getIdExecution() );

		for( HistoryStep historyStep : historySteps ){
			historyStepResource.add( getHistoryStepResource(historyStep, workflowInstance) );
		}
		return new ResponseEntity<List<Resource<HistoryStep>>>(historyStepResource, HttpStatus.OK);
	}



	@RequestMapping( value = "/historyStep/{id}", method = RequestMethod.GET )
	public ResponseEntity<Resource<HistoryStep>> findHistoryStepById(@PathVariable("id") Long id) {


		HistoryStep historyStep = historyStepRepository.findOne(id);
		WorkflowInstance workflowInstance = workflowInstanceRepository.getWorkflowInstanceByIdExecutionId( historyStep.getEntry_id() );
		Resource<HistoryStep> historyStepResource = getHistoryStepResource(historyStep, workflowInstance);
		return new ResponseEntity<Resource<HistoryStep>>(historyStepResource, HttpStatus.OK);
	}

	private Resource<HistoryStep> getHistoryStepResource( HistoryStep historyStep, WorkflowInstance workflowInstance ){
		Resource<HistoryStep> historyStepResource = new Resource<>(historyStep);

		historyStepResource.add( linkTo(methodOn(HistoryStepController.class).findHistoryStepById( historyStep.getId() ) ).withSelfRel() );
		if( workflowInstance != null ){
			historyStepResource.add( linkTo(methodOn(WorkflowController.class).getWorkflowInstanceById( workflowInstance.getId() ) ).withRel("workflowInstance") );
		}
		return historyStepResource;
	}
}
