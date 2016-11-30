package br.com.tcc.workflow.spring;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.*;
import com.opensymphony.workflow.config.Configuration;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.query.WorkflowExpressionQuery;
import com.opensymphony.workflow.query.WorkflowQuery;

import java.util.List;
import java.util.Map;

public class SpringAbstractWorkflow implements Workflow {

	@Override public int[] getAvailableActions(long l) {
		return new int[0];
	}

	@Override public List getCurrentSteps(long l) {
		return null;
	}

	@Override public int getEntryState(long l) {
		return 0;
	}

	@Override public List getHistorySteps(long l) {
		return null;
	}

	@Override public PropertySet getPropertySet(long l) {
		return null;
	}

	@Override public List getSecurityPermissions(long l) {
		return null;
	}

	@Override public List getSecurityPermissions(long l, Map map) {
		return null;
	}

	@Override public WorkflowDescriptor getWorkflowDescriptor(String s) {
		return null;
	}

	@Override public String getWorkflowName(long l) {
		return null;
	}

	@Override public boolean canInitialize(String s, int i) {
		return false;
	}

	@Override public boolean canModifyEntryState(long l, int i) {
		return false;
	}

	@Override public void changeEntryState(long l, int i) throws WorkflowException {

	}

	@Override public void doAction(long l, int i, Map map) throws InvalidInputException, WorkflowException {

	}

	@Override public void executeTriggerFunction(long l, int i) throws WorkflowException {

	}

	@Override public long initialize(String s, int i, Map map)
			throws InvalidRoleException, InvalidInputException, WorkflowException, InvalidEntryStateException, InvalidActionException {
		return 0;
	}

	@Override public List query(WorkflowQuery workflowQuery) throws WorkflowException {
		return null;
	}

	@Override public List query(WorkflowExpressionQuery workflowExpressionQuery) throws WorkflowException {
		return null;
	}

	@Override public int[] getAvailableActions(long l, Map map) {
		return new int[0];
	}

	@Override public void setConfiguration(Configuration configuration) {

	}

	@Override public String[] getWorkflowNames() {
		return new String[0];
	}

	@Override public boolean canInitialize(String s, int i, Map map) {
		return false;
	}

	@Override public boolean removeWorkflowDescriptor(String s) throws FactoryException {
		return false;
	}

	@Override public boolean saveWorkflowDescriptor(String s, WorkflowDescriptor workflowDescriptor, boolean b) throws FactoryException {
		return false;
	}
}
