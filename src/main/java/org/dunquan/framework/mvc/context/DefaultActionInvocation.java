package org.dunquan.framework.mvc.context;


public class DefaultActionInvocation implements ActionInvocation {

	private ActionContext actionContext;
	
	private Object action;
	
	public DefaultActionInvocation() {
		
	}
	public DefaultActionInvocation(ActionContext actionContext, Object action) {
		super();
		this.actionContext = actionContext;
		this.action = action;
	}
	
	public ActionContext getActionContext() {
		return actionContext;
	}
	public void setActionContext(ActionContext actionContext) {
		this.actionContext = actionContext;
	}
	public Object getAction() {
		return action;
	}
	public void setAction(Object action) {
		this.action = action;
	}
	
}
