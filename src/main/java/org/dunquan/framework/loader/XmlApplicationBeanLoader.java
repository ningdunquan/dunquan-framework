package org.dunquan.framework.loader;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.dunquan.framework.exception.MyException;
import org.dunquan.framework.manager.BeanManager;
import org.dunquan.framework.manager.XmlBeanManager;
import org.dunquan.framework.sourse.BeanSourse;
import org.dunquan.framework.util.BeanUtil;
import org.dunquan.framework.util.ReflectionUtil;
import org.dunquan.framework.util.StringUtil;

public class XmlApplicationBeanLoader implements ApplicationBeanLoader {

	private BeanManager beanManager;
	private Map<String, Object> beanMap = new HashMap<String, Object>();

	public XmlApplicationBeanLoader() {
		beanManager = new XmlBeanManager();
		setBeanMap(beanManager);
	}

	public XmlApplicationBeanLoader(String path) {
		beanManager = new XmlBeanManager(path);
		setBeanMap(beanManager);
	}

	/**
	 * 获取javaBean对象
	 * 
	 * @param beanName
	 * @return
	 */
	public Object getBean(String beanName) {
		return getSinglton(beanName);
	}

	/**
	 * 获取单例的bean对象
	 * 
	 * @param beanName
	 * @return
	 */
	private Object getSinglton(String beanName) {
		BeanSourse beanSourse = beanManager.getBean(beanName);
		if (beanSourse == null) {
			// throw new MyException("找不到对应的bean对象");
			return null;
		}
		boolean prototype = beanSourse.getPrototype();
		Object object = beanMap.get(beanName);
//		System.out.println("bean" + "--" + beanName + "--" + object);
		if (!prototype) {
			return object;
		}

		return getPrototype(beanSourse, object);
	}

	/**
	 * 获取原型的bean对象
	 * 
	 * @param object
	 * @return
	 */
	private Object getPrototype(BeanSourse beanSourse, Object object) {
		Class<?> clazz = object.getClass();
//		Method[] methods = clazz.getDeclaredMethods();
		Object object2 = null;
		try {
			object2 = clazz.newInstance();
		} catch (Exception e1) {
			throw new MyException("实例化类异常");
		}
//		String methodName = null;
//		Object objArg = null;
		
		setBeanRefs(beanSourse, object2);
		
		
//		for (Method method : methods) {
//			methodName = method.getName();
//			if (methodName.startsWith("set")) {
//				String name = StringUtil.changeMethodNameSetToGet(methodName);
//				try {
//					Method fieldMethod = clazz.getDeclaredMethod(name);
//					objArg = fieldMethod.invoke(object);
//					method.invoke(object2, objArg);
//				} catch (NoSuchMethodException | SecurityException
//						| IllegalAccessException | IllegalArgumentException
//						| InvocationTargetException e) {
//					e.printStackTrace();
//					throw new MyException("反射类异常");
//				}
//			}
//		}

		return object2;
	}

	/**
	 * 给beanMap赋值
	 * 
	 * @param beanManager
	 */
	private void setBeanMap(BeanManager beanManager) {
		Map<String, BeanSourse> map = beanManager.getBeanMap();

		BeanSourse beanSourse = null;
		String className = null;
		Map<String, String> fieldMap = new HashMap<String, String>();
//		Map<String, String> refMap = new HashMap<String, String>();

		for (Map.Entry<String, BeanSourse> entry : map.entrySet()) {
			beanSourse = entry.getValue();
			className = beanSourse.getClassName();
			Class<?> clazz = null;
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new MyException("类加载异常");
			}

			Object object = null;
			try {
				object = clazz.newInstance();
			} catch (Exception e) {
				throw new MyException("实例化类异常");
			}

			fieldMap = beanSourse.getFields();
			for (Map.Entry<String, String> fieldEntry : fieldMap.entrySet()) {
				String fieldName = fieldEntry.getKey();
				String fieldValue = fieldEntry.getValue();
				try {
					Method method = ReflectionUtil.getMethod(clazz, fieldName);

					setField(object, fieldValue, method);

				} catch (Exception e) {
					throw new MyException("没有setter，setter 方法");
				}
			}
//			System.out.println(object);
			beanMap.put(entry.getKey(), object);
		}
		
		for (Map.Entry<String, BeanSourse> entry : map.entrySet()) {
			beanSourse = entry.getValue();

			if (!beanSourse.getRefs().isEmpty()) {
				Object object = getBean(entry.getKey());

				object = setBeanRefs(beanSourse, object);
				beanMap.put(entry.getKey(), object);
			}
		}
	}

	/**
	 * 注入引用成员变量
	 * @param beanSourse
	 * @param object
	 * @return
	 */
	private Object setBeanRefs(BeanSourse beanSourse, Object object) {
		Map<String, String> refMap;
		refMap = beanSourse.getRefs();
		for (Map.Entry<String, String> refEntry : refMap.entrySet()) {
			String refName = refEntry.getKey();
			try {
				Method method = ReflectionUtil.getMethod(object.getClass(),
						refName);

				method.invoke(object, getBean(refName));
			} catch (Exception e) {
				e.printStackTrace();
				throw new MyException("注入引用变量异常");
			}
		}
		
		return object;
	}

	/**
	 * 注入成员变量
	 * 
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
			// 获取getter方法的形参的类型
			if (!"String".equals(simpleName)) {
				// 获取形参类型的全类名
				String realTypeName = StringUtil.getTypeName(simpleName);
				Class<?> ctype = null;
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

}
