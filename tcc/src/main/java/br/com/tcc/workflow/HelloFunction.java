package br.com.tcc.workflow;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;

import java.util.Map;

/**
 * Created by hernaneb on 21/10/16.
 */
public class HelloFunction implements FunctionProvider {

	@Override
	public void execute(Map transientVars, Map args, PropertySet ps) throws WorkflowException {
		System.out.println("Args = " + args);
	}
}
