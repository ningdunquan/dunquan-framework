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
			method = getThisClassMethod(clazz, refName);
		} catch (NoSuchMethodException e) {
		}
		if(method != null) {
			return method;
		}
		
		method = getSuperClassMethod(clazz, refName);
		
		return method;
	}

	private static Method getSuperClassMethod(Class<?> clazz, String refName) 
			throws NoSuchMethodException {
		Class<?> superClass = clazz.getSuperclass();
		if(superClass == null) {
			return null;
		}
		
		String upperFieldName = StringUtil.getStringFieldUpperCase(refName);
		
		Class<?> parameterType = superClass.getMethod(
				"get" + upperFieldName).getReturnType();
		Method method = superClass.getMethod(
				"set" + upperFieldName, parameterType);
		
		return method;
	}

	private static Method getThisClassMethod(Class<?> clazz, String refName)
			throws NoSuchMethodException {
		String upperFieldName = StringUtil.getStringFieldUpperCase(refName);
		
		Class<?> parameterType = clazz.getDeclaredMethod(
				"get" + upperFieldName).getReturnType();
		Method method = clazz.getDeclaredMethod(
				"set" + upperFieldName, parameterType);
		
		return method;
	}
	
}
