package org.dunquan.framework.util;

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
}
