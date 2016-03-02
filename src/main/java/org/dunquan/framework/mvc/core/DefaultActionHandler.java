package org.dunquan.framework.mvc.core;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.dunquan.framework.factory.InstanceFactory;
import org.dunquan.framework.mvc.context.ActionInvocation;
import org.dunquan.framework.mvc.context.ExecuteContext;
import org.dunquan.framework.mvc.exception.DispatcherException;
import org.dunquan.framework.mvc.interceptor.Interceptor;
import org.dunquan.framework.mvc.interceptor.ParamValidateInterceptor;
import org.dunquan.framework.mvc.interceptor.ServletRefInterceptor;
import org.dunquan.framework.mvc.view.ViewResolver;
import org.dunquan.framework.sourse.ActionSourse;
import org.dunquan.framework.util.ReflectionUtil;

public class DefaultActionHandler implements ActionHandler {

	private static List<Interceptor> handlers = new CopyOnWriteArrayList<Interceptor>();
	private ViewResolver viewResolver = InstanceFactory.getViewResolver();
	
	static{
		handlers.add(new ServletRefInterceptor());
		handlers.add(new ParamValidateInterceptor());
	}
	
	/**
	 * 执行handler操作
	 * @param excuteContext
	 */
	public void handleAction(ExecuteContext excuteContext) throws Exception {
		ActionInvocation actionInvocation = excuteContext.getActionInvocation();
		for(Interceptor handler : handlers) {
			handler.beforeHandle(actionInvocation);
		}
		
		//执行action
		execute(excuteContext);
		
		for(Interceptor handler : handlers) {
			handler.afterHandle(actionInvocation);
		}
		
	}

	public void execute(ExecuteContext executeContext) throws DispatcherException {

		ActionSourse actionSourse = executeContext.getActionSourse();
		ActionInvocation actionInvocation = executeContext.getActionInvocation();
		if(actionInvocation == null) {
			return;
		}
		Object action = actionInvocation.getAction();
		String methodName = actionSourse.getActionMethod();
		
		Object actionValue = execute(action, methodName);
		
		if(actionValue == null) {
			throw new DispatcherException("no action result value");
		}
		
		//解析视图
		viewResolver.resolve(executeContext, actionValue, this);
		
	}


	private Object execute(Object action, String methodName) throws DispatcherException {
		Class<?> clazz = action.getClass();
		Object resultValue = null;
		try {
			Method method = ReflectionUtil.getMethod(clazz, methodName);
			resultValue = method.invoke(action);
		} catch (Exception e) {
			throw new DispatcherException("action execute error");
		}
		return resultValue;
	}
	
	
}
