package br.com.tcc.controllers;

import br.com.tcc.model.Customer;
import br.com.tcc.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hernaneb on 18/10/16.
 */
@RestController
public class CustomerController {


	@Autowired CustomerRepository customerRepository;

	@RequestMapping( value = "/customer", method = RequestMethod.GET )
	public ResponseEntity<List<Customer>> findAllCustomers( ){

		List<Customer> customers = customerRepository.findAll();
		if( customers == null){
			return new ResponseEntity<List<Customer>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}

	@RequestMapping( value = "/customer/{id}", method = RequestMethod.GET )
	public ResponseEntity<Customer> findCustomerById( @PathVariable("id") Long id ){

		Customer customer = customerRepository.findOne( id );
		if( customer == null){
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>( customer, HttpStatus.OK );
	}

	@RequestMapping( value = "/customer", method = RequestMethod.PUT )
	public ResponseEntity<Customer> updateCustomer( @RequestBody Customer customer ){

		if( !customerRepository.exists( customer.getId() ) ){
			return new ResponseEntity<Customer>( HttpStatus.BAD_REQUEST );
		}
		Customer retorno = customerRepository.saveAndFlush( customer );

		return new ResponseEntity<Customer>(retorno, HttpStatus.ACCEPTED);
	}

	@RequestMapping( value = "/customer", method = RequestMethod.POST )
	public ResponseEntity<Customer> saveCustomer( @RequestBody Customer customer){

		if( customerRepository.exists( customer.getId() ) ){
			return new ResponseEntity<Customer>( HttpStatus.BAD_REQUEST );
		}

		Customer retorno = customerRepository.saveAndFlush(customer);

		return new ResponseEntity<Customer>(retorno, HttpStatus.CREATED);
	}

	@RequestMapping( value = "/customer/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Customer> deleteCustomerById(@PathVariable("id") String id ){
		Long id2 = null;
		try{
			id2 = new Long(id);
		}catch (Exception e){
			return new ResponseEntity<Customer>(HttpStatus.BAD_REQUEST);
		}
		if( customerRepository.exists(id2) ) {
			customerRepository.delete(id2);
		}else{
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
	}
}
