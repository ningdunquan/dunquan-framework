package org.dunquan.framework.mvc.context;

import org.dunquan.framework.sourse.ActionSourse;

public class DefaultExecuteContext implements ExecuteContext {

	private ActionInvocation actionInvocation;

	private ActionSourse actionSourse;

	public DefaultExecuteContext() {
		super();
	}
	public DefaultExecuteContext(ActionInvocation actionInvocation, ActionSourse actionSourse) {
		this.actionInvocation = actionInvocation;
		this.actionSourse = actionSourse;
	}
	public DefaultExecuteContext(ActionContext actionContext, Object action,
			ActionSourse actionSourse) {
		super();
		this.actionInvocation = new DefaultActionInvocation(actionContext, action);
		this.actionSourse = actionSourse;
	}

	public ActionInvocation getActionInvocation() {
		return actionInvocation;
	}

	public void setActionInvocation(ActionInvocation actionInvocation) {
		this.actionInvocation = actionInvocation;
	}

	public ActionSourse getActionSourse() {
		return actionSourse;
	}

	public void setActionSourse(ActionSourse actionSourse) {
		this.actionSourse = actionSourse;
	}

}
