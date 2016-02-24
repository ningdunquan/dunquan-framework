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
import org.dunquan.framework.exception.DispatcherException;
import org.dunquan.framework.exception.MyException;
import org.dunquan.framework.loader.ApplicationBeanLoader;
import org.dunquan.framework.mvc.handle.ActionHandle;
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
	
	public void execute() throws ServletException, IOException {

		HttpServletRequest request = actionContext.getHttpServletRequest();
		Map<String, String> paramMap = new ConcurrentHashMap<String, String>();
		getAllDispatcherParameter(paramMap, request);
		
		String actionUrl = actionSourse.getActionUrl();
		//actionUrl是否包含*符号
		boolean flag = actionUrl.contains("*");
		
		String refClass = actionSourse.getRefClass();
		Object object = applicationBeanLoader.getBean(refClass);
		
		HttpServletResponse response = actionContext.getHttpServletResponse();
		
		if(object == null) {
			errorDispatcher(response, "action为空");
			return;
		}
		
		String methodName = actionSourse.getActionMethod();
		methodName = findMethodName(requestBean.getRequestPath(), actionUrl, flag, methodName);
		
		if(methodName == null) {
			errorDispatcher(response, "action的方法为空");
			return;
		}

		Class clazz = object.getClass();
		setActionField(object, clazz, paramMap);
		
		ExecuteContext excuteContext = new ExecuteContext(actionContext, object);
		
		ActionHandle actionHandle = new ActionHandle();
		actionHandle.handleAction(excuteContext);
		
		String resultValue = null;
		try {
			Method method = clazz.getDeclaredMethod(methodName);
			resultValue = (String) method.invoke(object);
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
			errorDispatcher(response, "action的返回值为空");
			return;
		}
		
		Result result = findResult(actionSourse, resultValue);

		if(result == null) {
			errorDispatcher(response, "action的result为空");
			return;
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
	
	/**
	 * 找不到请求的方法，返回404错误
	 * @param response
	 * @throws IOException
	 */
	private void errorDispatcher(HttpServletResponse response, String error) throws IOException {
		
		response.sendError(404);
		
		return;
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
	 * 根据前端提交的参数设置action的成员变量
	 * @param object
	 * @param paramMap2
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setActionField(Object object, Class clazz, Map<String, String> paramMap2) {
		String name = null;
		for(Map.Entry<String, String> entry : paramMap2.entrySet()) {
			name = entry.getKey();
			Method method = null;
			if(!name.contains(".")) {
				try {
					method = MethodUtil.getMethod(clazz, name);
					setField(object, entry.getValue(), method);
				} catch (NoSuchMethodException e) {
//					System.out.println(name + entry.getValue());
				}
				
			}else {
				String[] arr = name.split("\\.");
				
				Method fieldMethod;
				Object objField;
				try {
					fieldMethod = clazz.getDeclaredMethod("get" + StringUtil.getStringFieldUpperCase(arr[0]));
					
					objField = fieldMethod.invoke(object);
					
					fieldMethod = MethodUtil.getMethod(objField.getClass(), arr[1]);

					setField(objField, entry.getValue(), fieldMethod);
					
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
				
				
				//这里可能还要添加方法
			}
		}
	}
	

	

	/**
	 * 注入成员变量
	 * @param object
	 * @param entry2
	 * @param parameterType
	 * @param method
	 */
	private void setField(Object object, String fieldValue, Method method) {
		try {
			Object arg = null;
				String simpleName = method.getParameterTypes()[0].getSimpleName();
				arg = fieldValue;
				//获取getter方法的形参的类型
				if (!"String".equals(simpleName)) {
					//获取形参类型的全类名
					String realTypeName = StringUtil.getTypeName(simpleName);
					Class ctype = null;
					try {
						ctype = Class.forName(realTypeName);
					} catch (ClassNotFoundException e) {
						throw new MyException("类型转换异常");
					}

					arg = BeanUtil.getArg(fieldValue, simpleName, ctype);
				}
			
			method.invoke(object, arg);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("方法异常");
		}
	}

	/**
	 * 寻找action的methodName
	 * @param url
	 * @param flag
	 * @param methodName
	 */
	private String findMethodName(String url, String actionUrl, boolean flag, String methodName) {
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
	
	/**
	 * 获取所有的参数，将其存在map中
	 * 
	 * @param request
	 */
	private void getAllDispatcherParameter(Map<String, String> paramMap, HttpServletRequest request) {
		Enumeration<String> enumeration = request.getParameterNames();

		String name = null;
		String value = null;
		while (enumeration.hasMoreElements()) {
			name = enumeration.nextElement();
			value = request.getParameter(name);
			paramMap.put(name, value);
		}
	}
	
}
