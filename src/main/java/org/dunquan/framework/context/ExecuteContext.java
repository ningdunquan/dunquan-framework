package org.dunquan.framework.context;

/**
 * 执行期间的context
 * @author Administrator
 *
 */
public class ExecuteContext {

	private ActionContext actionContext;
	
	private Object action;

	public ExecuteContext() {
		super();
	}
	public ExecuteContext(ActionContext actionContext, Object action) {
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
