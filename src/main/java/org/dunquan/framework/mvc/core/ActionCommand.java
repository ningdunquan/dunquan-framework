package org.dunquan.framework.mvc.core;


import org.dunquan.framework.factory.InstanceFactory;
import org.dunquan.framework.mvc.context.ActionContext;
import org.dunquan.framework.mvc.context.DefaultExecuteContext;
import org.dunquan.framework.mvc.context.ExecuteContext;
import org.dunquan.framework.mvc.exception.DispatcherException;
import org.dunquan.framework.sourse.ExecuteActionSource;

public class ActionCommand {

	private ExecuteActionSource actionSource;
	
	private ActionContext actionContext;
	
	public ActionCommand(ActionContext context, ExecuteActionSource source) {
		this.actionSource = source;
		this.actionContext = context;
	}
	
	public void execute() throws Exception {

		Object action = ActionCreater.createAction(actionSource.getName());
		
		if(action == null) {
			throw new DispatcherException("no action bean");
		}
		
		String methodName = actionSource.getMethodSource().getMethodName();
		
		if(methodName == null) {
			throw new DispatcherException("no action method");
		}
		
		ExecuteContext excuteContext = new DefaultExecuteContext(actionContext, action , actionSource);
		
		ActionHandler actionHandle = InstanceFactory.getActionHandler();
		actionHandle.handleAction(excuteContext);

	}

//	/**
//	 * 寻找action的methodName
//	 * @param url
//	 * @param flag
//	 * @param methodName
//	 */
//	private String findMethodName(String url, String actionUrl, String methodName) {
//		//actionUrl是否包含*符号
//		boolean flag = actionUrl.contains("*");
//		
//		if(methodName == null) {
//			if(flag) {
//				methodName = url.replace(url.replace("*", ""), "");
//				return methodName;
//			}
//		}
//		if(methodName.contains("*")) {
//			if(flag) {
//				methodName = methodName.replace("*", url.replace(actionUrl.replace("*", ""), ""));
//				return methodName;
//			}
//		}
//		return methodName;
//	}
//	
	
}
