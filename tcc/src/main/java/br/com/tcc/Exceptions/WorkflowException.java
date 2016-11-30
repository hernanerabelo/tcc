package br.com.tcc.Exceptions;

/**
 * Created by hernaneb on 11/11/16.
 */
public class WorkflowException extends Exception {

	int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public WorkflowException( String msg){
		super(msg);
	}

	public WorkflowException( String msg, Throwable err){
		super(msg, err);
	}
}
