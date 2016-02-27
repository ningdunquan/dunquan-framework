package org.dunquan.framework.util;

import java.lang.reflect.Method;

/**
 * 处理method的工具类
 * 
 * @author Administrator
 *
 */
public class MethodUtil {

	/**
	 * 通过getter，setter方法来获取具体的方法
	 * 
	 * @param clazz
	 * @param refName
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Method getMethodByGetSet(Class<?> clazz, String refName)
			throws NoSuchMethodException {
		Method method = null;
		try {
			method = getThisClassSetterMethod(clazz, refName);
		} catch (NoSuchMethodException e) {
		}
		if (method != null) {
			return method;
		}

		method = getSuperClassSetterMethod(clazz, refName);

		return method;
	}

	private static Method getSuperClassSetterMethod(Class<?> clazz, String refName)
			throws NoSuchMethodException {
		Class<?> superClass = clazz.getSuperclass();
		if (superClass == null) {
			return null;
		}

		String upperFieldName = StringUtil.getStringFieldUpperCase(refName);

		Class<?> parameterType = superClass.getMethod("get" + upperFieldName)
				.getReturnType();
		Method method = superClass.getMethod("set" + upperFieldName,
				parameterType);

		return method;
	}

	private static Method getThisClassSetterMethod(Class<?> clazz, String refName)
			throws NoSuchMethodException {
		String upperFieldName = StringUtil.getStringFieldUpperCase(refName);

		Class<?> parameterType = clazz
				.getDeclaredMethod("get" + upperFieldName).getReturnType();
		Method method = clazz.getDeclaredMethod("set" + upperFieldName,
				parameterType);

		return method;
	}

	public static Method getGetterMethod(Class<?> clazz, String actionField)
			throws NoSuchMethodException {
		Method method = null;
		try {
			method = getThisClassGetterMethod(clazz, actionField);
		} catch (Exception e) {
		}
		if (method != null) {
			return method;
		}

		method = getSuperClassGetterMethod(clazz, actionField);

		return method;
	}

	private static Method getSuperClassGetterMethod(Class<?> clazz,
			String actionField) throws NoSuchMethodException, SecurityException {
		return clazz.getDeclaredMethod("get"
				+ StringUtil.getStringFieldUpperCase(actionField));
	}

	private static Method getThisClassGetterMethod(Class<?> clazz,
			String actionField) throws NoSuchMethodException, SecurityException {
		Class<?> superClass = clazz.getSuperclass();
		if (superClass == null) {
			return null;
		}

		Method method = superClass.getMethod("get"
				+ StringUtil.getStringFieldUpperCase(actionField));
		return method;
	}

	public static Method getMethod(Class<?> clazz, String methodName) throws NoSuchMethodException, SecurityException {
		Method method = null;
		try {
			method = getThisClassMethod(clazz, methodName);
		} catch (Exception e) {
		}
		if (method != null) {
			return method;
		}

		method = getSuperClassMethod(clazz, methodName);

		return method;
	}

	private static Method getThisClassMethod(Class<?> clazz, String methodName) throws NoSuchMethodException, SecurityException {
		return clazz.getDeclaredMethod(methodName);
	}
	
	private static Method getSuperClassMethod(Class<?> clazz, String methodName) throws NoSuchMethodException, SecurityException {
		Class<?> superClass = clazz.getSuperclass();
		if (superClass == null) {
			return null;
		}

		Method method = superClass.getMethod(methodName);
		return method;
	}

}
