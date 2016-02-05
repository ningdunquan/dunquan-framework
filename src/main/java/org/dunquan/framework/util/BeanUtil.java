package org.dunquan.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.dunquan.framework.exception.MyException;
import org.w3c.dom.Node;

/**
 * 处理bean的工具类
 * @author Administrator
 *
 */
public class BeanUtil implements Beanable {
	
	/**
	 * 获取参数
	 * @param args
	 * @param arg
	 * @param simpleName
	 * @param ctype
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getArg(String args, String simpleName, Class ctype)
			throws IllegalAccessException, InvocationTargetException {
		Object arg = args;
		try {
			if ("Character".equals(simpleName) || "char".equals(simpleName)) {
				arg = args.charAt(0);
			} else {
				String str = "parse" + StringUtil.getStringUpperCase(simpleName, 0, 1);
				if ("Integer".equals(simpleName)) {
					str = "parseInt";
				}

				Method mtype = ctype.getMethod(str, String.class);
				arg = mtype.invoke(null, args);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arg;
	}


	/**
	 * 获取bean对象
	 * 
	 * @param c
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getBeanObj(Class c) {
		Object obj = null;
		try {
			obj = c.newInstance();
		} catch (Exception e) {
			throw new MyException("没有无参的构造方法");
		}
		return obj;
	}

	/**
	 * 获取Bean的class
	 * 
	 * @param node
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class getBeanClass(Node node) {
		String clazz = node.getTextContent();
		System.out.println(clazz);
		Class c = null;
		try {
			 c = Class.forName(clazz);
		} catch (ClassNotFoundException e) {
			throw new MyException("找不到类");
		}
		return c;
	}
}
