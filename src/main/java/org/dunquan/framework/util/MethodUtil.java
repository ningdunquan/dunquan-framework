package org.dunquan.framework.util;

import java.lang.reflect.Method;

/**
 * 处理method的工具类
 * @author Administrator
 *
 */
public class MethodUtil {

	/**
	 * 通过getter，setter方法来获取具体的方法
	 * @param clazz
	 * @param refName
	 * @return
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getMethod(Class clazz, String refName) throws NoSuchMethodException {
		Class parameterType = clazz.getDeclaredMethod("get" + StringUtil.getStringFieldUpperCase(refName)).getReturnType();
		Method method = clazz.getDeclaredMethod("set" + StringUtil.getStringFieldUpperCase(refName), parameterType);
		return method;
	}
}
