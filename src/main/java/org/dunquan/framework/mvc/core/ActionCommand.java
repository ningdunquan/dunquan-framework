package org.dunquan.framework.mvc.core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dunquan.framework.context.ActionContext;
import org.dunquan.framework.context.ExecuteContext;
import org.dunquan.framework.exception.MyException;
import org.dunquan.framework.factory.InstanceFactory;
import org.dunquan.framework.loader.ApplicationBeanLoader;
import org.dunquan.framework.mvc.exception.DispatcherException;
import org.dunquan.framework.sourse.ActionSourse;
import org.dunquan.framework.sourse.Result;
import org.dunquan.framework.util.BeanUtil;
import org.dunquan.framework.util.MethodUtil;
import org.dunquan.framework.util.StringUtil;

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

		HttpServletRequest request = actionContext.getHttpServletRequest();
		HttpServletResponse response = actionContext.getHttpServletResponse();
		
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

		Class<?> clazz = action.getClass();
		Map<String, String> paramMap = new ConcurrentHashMap<String, String>();
//		getAllDispatcherParameter(paramMap, request);
//		setActionField(action, clazz, paramMap);
		
		ExecuteContext excuteContext = new ExecuteContext(actionContext, action);
		
		ActionHandler actionHandle = InstanceFactory.getActionHandler();
		actionHandle.handleAction(excuteContext);
		
		String resultValue = null;
		try {
			Method method = clazz.getDeclaredMethod(methodName);
			resultValue = (String) method.invoke(action);
		} catch (NoSuchMethodException e) {

			e.printStackTrace();
		} catch (SecurityException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		}
		
		if(resultValue == null) {
			throw new DispatcherException("no action result value");
		}
		
		Result result = findResult(actionSourse, resultValue);

		if(result == null) {
			throw new DispatcherException("no action result");
		}
		
		if(Result.RE_REDIRECT.equals(result.getType())) {
			response.sendRedirect(result.getResultValue());
			return;
		}
		
		if(Result.RE_DISPATCHER.equals(result.getType())) {
			request.getRequestDispatcher(result.getResultValue()).forward(request, response);
			return;
		}
		
	}

	private Object createAction()
			throws IOException {
		Object object = applicationBeanLoader.getBean(actionSourse.getRefClass());
		
		return object;
	}
	
	
	/**
	 * 寻找result对象
	 * @param actionSourse
	 * @param resultValue
	 * @return
	 */
	private Result findResult(ActionSourse actionSourse, String resultValue) {
		String resultName = null;
		for(Result result : actionSourse.getResults()) {
			resultName = result.getResultName();
			if(resultValue.equals(resultName)) {
				return result;
			}
		}
		return null;
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
