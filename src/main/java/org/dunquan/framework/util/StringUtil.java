package org.dunquan.framework.util;

/**
 * 字符串工具类
 * @author ndq
 *
 */
public class StringUtil implements Beanable {


	/**
	 * 获取全类名
	 * 
	 * @param simpleName
	 * @return
	 */
	public static String getTypeName(String simpleName) {
		if (simpleName == null) {
			return null;
		}
		if ("int".equals(simpleName)) {
			return S_INTEGER;
		}
		if ("char".equals(simpleName)) {
			return S_CHARACTER;
		}

		String s = simpleName.substring(0, 1);
		return S_TYPE + simpleName.replaceFirst(s, s.toUpperCase());
	}
	
	
	/**
	 * 将setter方法名改为getter方法名
	 * @param methodName
	 * @return
	 */
	public static String changeMethodNameSetToGet(String methodName) {
		return methodName.replaceFirst("set", "get");
	}
	
	/**
	 * 将javabean的setter方法提取属性
	 * 
	 * @param methodName
	 * @return
	 */
	public static String getFiled(String methodName) {
		return getStringLowerCase(methodName, 3, 4);
	}

	/**
	 * 将字符串的第一个字母变成大写
	 * @param fieldName
	 * @return
	 */
	public static String getStringFieldUpperCase(String fieldName) {
		return getStringUpperCase(fieldName, 0, 1);
	}
	
	/**
	 * 将字符串某一位置的字母变成大写
	 * 
	 * @param str
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String getStringUpperCase(String str, int begin, int end) {
		String s = str.substring(begin, end);
		String filedName = str.substring(begin).replaceFirst(s, s.toUpperCase());
		return filedName;
	}

	/**
	 * 将字符串某一位置的字母变成小写
	 * 
	 * @param str
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String getStringLowerCase(String str, int begin, int end) {
		String s = str.substring(begin, end);
		String filed = str.substring(begin).replaceFirst(s, s.toLowerCase());
		return filed;
	}
}
