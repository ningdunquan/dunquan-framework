package org.dunquan.framework.mvc.core;

import java.io.IOException;

import org.dunquan.framework.factory.InstanceFactory;
import org.dunquan.framework.loader.ApplicationBeanLoader;
import org.dunquan.framework.mvc.context.ActionContext;
import org.dunquan.framework.mvc.context.DefaultExecuteContext;
import org.dunquan.framework.mvc.context.ExecuteContext;
import org.dunquan.framework.mvc.exception.DispatcherException;
import org.dunquan.framework.sourse.ActionSourse;

public class ActionCommand {

	private ApplicationBeanLoader applicationBeanLoader;
	
	private ActionSourse actionSourse;
	
	private ActionContext actionContext;
	
	private RequestBean requestBean;
	
	public ActionCommand(RequestBean requestBean, ActionContext context, ApplicationBeanLoader beanLoader, ActionSourse sourse) {
		this.applicationBeanLoader = beanLoader;
		this.actionSourse = sourse;
		this.actionContext = context;
		this.requestBean = requestBean;
	}
	
	public void execute() throws Exception {

		Object action = createAction();
		
		if(action == null) {
			throw new DispatcherException("no action bean");
		}
		
		String methodName = actionSourse.getActionMethod();
		
		String actionUrl = actionSourse.getActionUrl();
		methodName = findMethodName(requestBean.getRequestPath(), actionUrl, methodName);
		
		if(methodName == null) {
			throw new DispatcherException("no action method");
		}
		
		ExecuteContext excuteContext = new DefaultExecuteContext(actionContext, action , actionSourse);
		
		ActionHandler actionHandle = InstanceFactory.getActionHandler();
		actionHandle.handleAction(excuteContext);

	}

	private Object createAction() throws IOException {
		Object object = applicationBeanLoader.getBean(actionSourse.getRefClass());
		
		return object;
	}
	
	
	/**
	 * 寻找action的methodName
	 * @param url
	 * @param flag
	 * @param methodName
	 */
	private String findMethodName(String url, String actionUrl, String methodName) {
		//actionUrl是否包含*符号
		boolean flag = actionUrl.contains("*");
		
		if(methodName == null) {
			if(flag) {
				methodName = url.replace(url.replace("*", ""), "");
				return methodName;
			}
		}
		if(methodName.contains("*")) {
			if(flag) {
				methodName = methodName.replace("*", url.replace(actionUrl.replace("*", ""), ""));
				return methodName;
			}
		}
		return methodName;
	}
	
	
}
