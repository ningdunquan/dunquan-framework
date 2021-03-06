package org.dunquan.framework.mvc.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dunquan.framework.mvc.constant.MvcConstant;
import org.dunquan.framework.mvc.exception.ValidateException;
import org.dunquan.framework.util.BeanUtil;
import org.dunquan.framework.util.ReflectionUtil;
import org.dunquan.framework.util.StringUtil;

public abstract class AbstractValidateStrategy implements ValidateStrategy {

	public abstract void validate(Object action, HttpServletRequest request) throws ValidateException;
	
	/**
	 * 根据前端提交的参数设置action的成员变量
	 * 
	 * @param object
	 * @param paramMap2
	 */
	protected void setActionField(Object action, Map<String, String> paramMap) {
		Class<?> clazz = action.getClass();
		
		String name = null;
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			name = entry.getKey();
			if(name == null) {
				continue;
			}
			Method method = null;
			// 如果是action中直接的属性
			if (!name.contains(MvcConstant.POINT)) {
				try {
					method = ReflectionUtil.getSetterMethod(clazz, name);
					if (method != null) {
						setFieldByMethod(action, entry.getValue(), method);
					} else {
						setField(action, name, entry.getValue());
					}
				} catch (Exception e) {
					throw new ValidateException("validate data error");
				}

			} else {
				//暂时默认只能两层向action注入属性
				String[] arr = name.split(MvcConstant.REGEX_POINT);

				if(arr.length > 2) {
					continue;
				}
				Method fieldMethod;
				Object actionFieldObj = null;
				try {
					//example: user.name
					//but user不一定有setter，getter方法
					//ps：向user中注入属性时候需要新建user，并把user给action
					
					//当前action的属性，如：user
					String actionFieldName = arr[0];
					//action属性的属性，如：user中的name
					String innerFieldName = arr[1];
					
					Method fieldGetterMethod = getFieldGetterMethod(clazz, actionFieldName);
					
					if(fieldGetterMethod == null) {
						Field actionField = getActionField(clazz, actionFieldName);
						
						if(actionField != null)
							actionFieldObj = actionField.get(action);
					}else {
						actionFieldObj = fieldGetterMethod.invoke(action);
					}
					
					
					if(actionFieldObj == null) {
						Field actionField = getActionField(clazz, actionFieldName);
						
						if(actionField != null) {
							Class<?> actionFieldClass = actionField.getType();
							
							actionFieldObj = ReflectionUtil.newInstance(actionFieldClass);
							
							//注入属性
							injectField(action, actionField, actionFieldName, actionFieldObj);
						}
					}

					fieldMethod = ReflectionUtil.getSetterMethod(actionFieldObj.getClass(), innerFieldName);

					setFieldByMethod(actionFieldObj, entry.getValue(), fieldMethod);

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
	private void setField(Object action, String name, String value) {
		try {
			ReflectionUtil.injectField(action, name, value);
		} catch (Exception e) {
			throw new ValidateException("inject field error", e);
		}
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
			Object arg = resolveFieldValue(fieldValue, method);

			method.invoke(object, arg);

		} catch (Exception e) {
			throw new ValidateException("method error", e);
		}
	}

	/**
	 * 解析属性的值
	 * @param fieldValue
	 * @param method
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private Object resolveFieldValue(String fieldValue, Method method)
			throws IllegalAccessException, InvocationTargetException {
		Object arg = fieldValue;
		String simpleName = method.getParameterTypes()[0].getSimpleName();
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
		return arg;
	}

	/**
	 * 给action注入属性
	 * @param action
	 * @param actionField
	 * @param actionFieldName
	 * @param actionFieldObj
	 * @throws Exception
	 */
	protected void injectField(Object action, Field actionField, 
			String actionFieldName, Object actionFieldObj) throws Exception {
		Class<?> clazz = action.getClass();
		Method method = ReflectionUtil.getSetterMethod(clazz, actionFieldName);
		if(method != null) {
			method.invoke(action, actionFieldObj);
		}else {
			actionField.set(action, actionFieldObj);
		}
		
	}

	/**
	 * 根据name获取属性
	 * @param clazz
	 * @param actionFieldName
	 * @return
	 */
	protected Field getActionField(Class<?> clazz, String actionFieldName) {
		Field field = null;
		
		field = ReflectionUtil.getField(clazz, actionFieldName);
		
		return field;
	}


	/**
	 * 获取action属性的getter方法
	 * @param clazz
	 * @param actionField
	 * @return
	 */
	protected Method getFieldGetterMethod(Class<?> clazz, String actionField) {
		Method fieldGetterMethod = null;
		
		fieldGetterMethod = ReflectionUtil.getGetterMethod(clazz, actionField);
		
		return fieldGetterMethod;
	}
}
