package org.dunquan.framework.mvc.interceptor;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.dunquan.framework.context.ExecuteContext;
import org.dunquan.framework.mvc.exception.ValidateException;
import org.dunquan.framework.util.BeanUtil;
import org.dunquan.framework.util.ReflectionUtil;
import org.dunquan.framework.util.StringUtil;

public class DataValidateInterceptor extends AbstractInterceptor {

	@Override
	public void beforeHanlde(ExecuteContext excuteContext)
			throws ValidateException {
		HttpServletRequest request = excuteContext.getActionContext()
				.getHttpServletRequest();

		Object action = excuteContext.getAction();

		Map<String, String> paramMap = getAllDispatcherParameter(request);
		Class<?> clazz = action.getClass();
		setActionField(action, clazz, paramMap);

	}

	@Override
	public void afterHanlde(ExecuteContext executeContext)
			throws ValidateException {

	}

	/**
	 * 获取所有的参数，将其存在map中
	 * 
	 * @param request
	 */
	private Map<String, String> getAllDispatcherParameter(
			HttpServletRequest request) {
		Map<String, String> paramMap = new ConcurrentHashMap<String, String>();

		Enumeration<String> enumeration = request.getParameterNames();

		String name = null;
		String value = null;
		while (enumeration.hasMoreElements()) {
			name = enumeration.nextElement();
			value = request.getParameter(name);
			paramMap.put(name, value);
		}

		return paramMap;
	}

	/**
	 * 根据前端提交的参数设置action的成员变量
	 * 
	 * @param object
	 * @param paramMap2
	 */
	private void setActionField(Object action, Class<?> clazz,
			Map<String, String> paramMap) {
		String name = null;
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			name = entry.getKey();
			Method method = null;
			// 如果是action中直接的属性
			if (!name.contains(".")) {
				try {
					method = ReflectionUtil.getMethod(clazz, name);
					if (method != null) {
						setFieldByMethod(action, entry.getValue(), method);
					} else {
						setField(action, name, entry.getValue());
					}
				} catch (Exception e) {
					throw new ValidateException("validate data error");
				}

			} else {
				String[] arr = name.split("\\.");

				Method fieldMethod;
				Object objField;
				try {
					fieldMethod = clazz.getDeclaredMethod("get"
							+ StringUtil.getStringFieldUpperCase(arr[0]));

					objField = fieldMethod.invoke(action);

					fieldMethod = ReflectionUtil.getMethod(objField.getClass(),
							arr[1]);

					setFieldByMethod(objField, entry.getValue(), fieldMethod);

				} catch (Exception e) {
					throw new ValidateException("validate data error");
				}

				// 这里可能还要添加方法
			}
		}
	}

	/**
	 * 直接设置属性的值
	 * 
	 * @param action
	 * @param name
	 * @param value
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void setField(Object action, String name, String value)
			throws IllegalArgumentException, IllegalAccessException {
		ReflectionUtil.injectField(action, name, value);
	}

	/**
	 * 使用getter setter的方法注入成员变量
	 * 
	 * @param object
	 * @param fieldValue
	 * @param method
	 */
	private void setFieldByMethod(Object object, String fieldValue,
			Method method) {
		try {
			Object arg = null;
			String simpleName = method.getParameterTypes()[0].getSimpleName();
			arg = fieldValue;
			// 获取getter方法的形参的类型
			if (!"String".equals(simpleName)) {
				// 获取形参类型的全类名
				String realTypeName = StringUtil.getTypeName(simpleName);
				Class<?> ctype = null;
				try {
					ctype = Class.forName(realTypeName);
				} catch (ClassNotFoundException e) {
					throw new ValidateException("类型转换异常");
				}

				arg = BeanUtil.getArg(fieldValue, simpleName, ctype);
			}

			method.invoke(object, arg);

		} catch (Exception e) {
			throw new ValidateException("方法异常");
		}
	}

}
