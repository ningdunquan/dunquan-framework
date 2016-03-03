package org.dunquan.framework.mvc.context;

import org.dunquan.framework.sourse.ExecuteActionSource;


public class DefaultExecuteContext implements ExecuteContext {

	private ActionInvocation actionInvocation;

	private ExecuteActionSource actionSource;

	public DefaultExecuteContext() {
		super();
	}
	public DefaultExecuteContext(ActionInvocation actionInvocation, ExecuteActionSource actionSource) {
		this.actionInvocation = actionInvocation;
		this.actionSource = actionSource;
	}
	public DefaultExecuteContext(ActionContext actionContext, Object action,
			ExecuteActionSource actionSource) {
		super();
		this.actionInvocation = new DefaultActionInvocation(actionContext, action);
		this.actionSource = actionSource;
	}

	public ActionInvocation getActionInvocation() {
		return actionInvocation;
	}

	public void setActionInvocation(ActionInvocation actionInvocation) {
		this.actionInvocation = actionInvocation;
	}

	public ExecuteActionSource getActionSource() {
		return actionSource;
	}

	public void setActionSource(ExecuteActionSource actionSource) {
		this.actionSource = actionSource;
	}

}
