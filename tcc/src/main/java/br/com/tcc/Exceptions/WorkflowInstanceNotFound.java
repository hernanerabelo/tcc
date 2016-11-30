package br.com.tcc.Exceptions;

/**
 * Created by hernaneb on 11/11/16.
 */
public class WorkflowInstanceNotFound extends WorkflowException {

	public WorkflowInstanceNotFound(Long id){
		super("Não foi encontrato Workflow Instance para id: " + id );
	}

	public WorkflowInstanceNotFound( String msg ){
		super(msg);
	}
	public WorkflowInstanceNotFound( String msg, Throwable err ){
		super(msg, err);
	}
}
