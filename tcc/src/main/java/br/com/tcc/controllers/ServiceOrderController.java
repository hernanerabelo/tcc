package br.com.tcc.controllers;

import br.com.tcc.model.ServiceOrder;
import br.com.tcc.repository.ServiceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by hernaneb on 09/11/16.
 */

@RestController
public class ServiceOrderController {

	@Autowired ServiceOrderRepository serviceOrderRepository;


	@RequestMapping( value = "/serviceOrder/{id}", method = RequestMethod.GET )
	public ResponseEntity<Resource<ServiceOrder>> findServiceOrderById(@PathVariable("id") Long id) {


		ServiceOrder serviceOrder = serviceOrderRepository.findOne(id);

		Resource<ServiceOrder> serviceOrderResource = getWorkflowResource(serviceOrder);
		return new ResponseEntity<Resource<ServiceOrder>>(serviceOrderResource, HttpStatus.OK);
	}

	private Resource<ServiceOrder> getWorkflowResource( ServiceOrder serviceOrder ){
		Resource<ServiceOrder> serviceOrderResourceResource = new Resource<>(serviceOrder);

		serviceOrderResourceResource.add( linkTo(methodOn(ServiceOrderController.class).findServiceOrderById( serviceOrder.getId() ) ).withSelfRel() );
		serviceOrderResourceResource.add( linkTo(methodOn(WorkflowController.class).getWorkflowInstanceById( serviceOrder.getWorkflowInstance().getId() ) ).withRel("workflowInstance") );

		return serviceOrderResourceResource;
	}
}
