package br.com.tcc.Exceptions;

import br.com.tcc.model.ServiceOrder;

/**
 * Created by hernaneb on 12/11/16.
 */
public class ServiceOrderException extends Exception {

	ServiceOrder serviceOrder;
	public ServiceOrderException(String msg){
		super(msg);
	}

	public ServiceOrderException(String msg, Throwable err){
		super(msg, err);
	}

	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}
}
