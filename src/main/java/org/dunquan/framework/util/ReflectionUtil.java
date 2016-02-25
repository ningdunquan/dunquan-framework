package org.dunquan.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import org.dunquan.framework.mvc.exception.ValidateException;

public class ReflectionUtil {

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String name) {
		T instance;
		
		try {
			Class<?> c = loadClass(name);
			
			instance = (T)c.newInstance();
		} catch (Exception e) {
            throw new RuntimeException(e);
		}
		
		return instance;
	}

	private static Class<?> loadClass(String name) {
		Class<?> c;
		
		try {
			c = Class.forName(name, true, getClassLoader());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return c;
	}

	private static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	
	public static Method getMethod(Class<?> clazz, String fieldName) {
		Method method = null;
		try {
			method = MethodUtil.getMethodByGetSet(clazz, fieldName);
		} catch (NoSuchMethodException e) {
			
		}
		
		if(method != null && isNonMethodAccessable(method)) {
			method.setAccessible(true);
		}
		
		return method;
	}

	public static void injectField(Object action, String name, String value) throws IllegalArgumentException, IllegalAccessException {
		//获取属性
		Field field = findField(action.getClass(), name);
		if(field == null) {
			return;
		}
		
		if(isNonMethodAccessable(field)) {
			field.setAccessible(true);
		}
		
		Object arg = getFiledValue(value, field);
		
		field.set(action, arg);
		
	}

	private static Object getFiledValue(String value, Field field)
			throws IllegalAccessException {
		Object arg = null;
		String simpleName = field.getType().getSimpleName();
		arg = value;
		if (!"String".equals(simpleName)) {
			// 获取形参类型的全类名
			String realTypeName = StringUtil.getTypeName(simpleName);
			Class<?> ctype = null;
			try {
				ctype = Class.forName(realTypeName);
			} catch (ClassNotFoundException e) {
				throw new ValidateException("类型转换异常");
			}

			try {
				arg = BeanUtil.getArg(value, simpleName, ctype);
			} catch (InvocationTargetException e) {
				throw new ValidateException("类型转换异常");
			}
		}
		return arg;
	}

	private static Field findField(Class<?> clazz, String name) {
		Field field = null;
		
		try {
			field = findThisClassField(clazz, name);
		} catch (NoSuchFieldException e) {
		}
		if(field != null) {
			return field;
		}
		
		try {
			field = findSuperClassField(clazz, name);
		} catch (NoSuchFieldException e) {
		}
		
		return field;
	}
	
	private static boolean isNonMethodAccessable(Member member) {
		return member.getModifiers() != 1;
	}
	
	private static Field findThisClassField(Class<?> clazz, String name) throws NoSuchFieldException {
		Field field  = clazz.getDeclaredField(name);
		
		return field;
	}
	
	private static Field findSuperClassField(Class<?> clazz, String name) throws NoSuchFieldException {
		Class<?> superClass = clazz.getSuperclass();
		if(superClass == null) {
			return null;
		}
		
		Field field = superClass.getField(name);
		return field;
	}
}
