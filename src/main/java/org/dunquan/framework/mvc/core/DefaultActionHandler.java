package org.dunquan.framework.mvc.core;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dunquan.framework.factory.InstanceFactory;
import org.dunquan.framework.mvc.context.ActionContext;
import org.dunquan.framework.mvc.context.ActionInvocation;
import org.dunquan.framework.mvc.context.ExecuteContext;
import org.dunquan.framework.mvc.exception.DispatcherException;
import org.dunquan.framework.mvc.interceptor.DataValidateInterceptor;
import org.dunquan.framework.mvc.interceptor.Interceptor;
import org.dunquan.framework.mvc.interceptor.ServletRefInterceptor;
import org.dunquan.framework.mvc.view.LogicView;
import org.dunquan.framework.mvc.view.ViewResolver;
import org.dunquan.framework.sourse.ActionSourse;
import org.dunquan.framework.sourse.Result;
import org.dunquan.framework.util.ReflectionUtil;

public class DefaultActionHandler implements ActionHandler {

	private static List<Interceptor> handlers = new CopyOnWriteArrayList<Interceptor>();
	private ViewResolver viewResolver = InstanceFactory.getViewResolver();
	
	static{
		handlers.add(new ServletRefInterceptor());
		handlers.add(new DataValidateInterceptor());
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

	private void execute(ExecuteContext executeContext) throws DispatcherException {

		ActionSourse actionSourse = executeContext.getActionSourse();
		ActionInvocation actionInvocation = executeContext.getActionInvocation();
		if(actionInvocation == null) {
			return;
		}
		Object action = actionInvocation.getAction();
		ActionContext actionContext = actionInvocation.getActionContext();
		String methodName = actionSourse.getActionMethod();
		
		HttpServletRequest request = actionContext.getHttpServletRequest();
		HttpServletResponse response = actionContext.getHttpServletResponse();
		
		Object actionValue = execute(action, methodName);
		
		if(actionValue == null) {
			throw new DispatcherException("no action result value");
		}
		
		
		
		if(actionValue instanceof LogicView) {
			
		}else if(actionValue instanceof Result) {
			
		}else if(actionValue instanceof String) {
			String value = (String) actionValue;
			Result result = findResult(actionSourse, value);
			
			if(result == null) {
				throw new DispatcherException("no action result");
			}
			
			if(Result.RE_ACTION.equalsIgnoreCase(result.getType())) {
				actionSourse.setActionMethod(value);
				
				execute(executeContext);
				
				return;
			}
			
			
			
			if(Result.RE_REDIRECT.equalsIgnoreCase(result.getType())) {
				try {
					response.sendRedirect(result.getResultValue());
				} catch (IOException e) {
					throw new DispatcherException("redirect error");
				}
				return;
			}
			
			if(Result.RE_DISPATCHER.equalsIgnoreCase(result.getType())) {
				try {
					request.getRequestDispatcher(result.getResultValue()).forward(request, response);
				} catch (Exception e) {
					throw new DispatcherException("forward error");
				}
				return;
			}
			
			
		}else {
			return;
		}
		
		
		

		
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
	
	/**
	 * 寻找result对象
	 * @param actionSourse
	 * @param resultValue
	 * @return
	 */
	private Result findResult(ActionSourse actionSourse, String actionValue) {
		String resultName = null;
		for(Result result : actionSourse.getResults()) {
			resultName = result.getResultName();
			if(actionValue.equals(resultName)) {
				return result;
			}
		}
		return null;
	}
}
