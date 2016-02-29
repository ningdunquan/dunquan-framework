package org.dunquan.framework.mvc.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dunquan.framework.mvc.constant.MvcConstant;
import org.dunquan.framework.mvc.context.ActionContext;
import org.dunquan.framework.mvc.context.ActionInvocation;
import org.dunquan.framework.mvc.exception.ValidateException;
import org.dunquan.framework.mvc.upload.UploadField;
import org.dunquan.framework.mvc.upload.UploadFile;
import org.dunquan.framework.mvc.upload.UploadHelper;
import org.dunquan.framework.util.ReflectionUtil;

public class UpLoadInterceptor extends DataValidateInterceptor {

	public void beforeHandle(ActionInvocation actionInvocation)
			throws ValidateException {
		ActionContext actionContext = actionInvocation.getActionContext();
		HttpServletRequest request = actionContext.getHttpServletRequest();
		Object action = actionInvocation.getAction();
		
		if(!UploadHelper.isMultipartFormData(request)) {
			return;
		}
		
		UploadField uploadField = null;
		try {
			uploadField = UploadHelper.getUploadField(request);
		} catch (Exception e) {
			throw new ValidateException("upload error", e);
		}
		
		Map<String, String> param = uploadField.getParams();
		Map<String, UploadFile> uploads = uploadField.getUploads();
		
		setActionField(action, param);
		
		setActionUploadField(action, uploads);
	}


	private void setActionUploadField(Object action,
			Map<String, UploadFile> uploads) throws ValidateException {
		Class<?> clazz = action.getClass();
		
		String name = null;
		for (Map.Entry<String, UploadFile> entry : uploads.entrySet()) {
			name = entry.getKey();
			if(name == null) {
				continue;
			}
			Method method = null;
			// 如果是action中直接的属性
			if (!name.contains(MvcConstant.POINT)) {
				try {
					Field field = ReflectionUtil.findField(clazz, name);
					if(field == null) {
						continue;
					}
					method = ReflectionUtil.getSetterMethod(clazz, name);
					
					if(field.getType().isInstance(entry.getValue())) {
						//如果field属性是uploadFile
						if(method == null) {
							ReflectionUtil.setField(action, field, entry.getValue());
						
						}else {
							ReflectionUtil.invoke(action, method, entry.getValue());
						}
						
					}else if(field.getType().isInstance(entry.getValue().getInputStream())) {
						//如果field属性是inputStream
						if(method == null) {
							ReflectionUtil.setField(action, field, entry.getValue().getInputStream());
							
						}else {
							ReflectionUtil.invoke(action, method, entry.getValue().getInputStream());
						}
					}else {
						continue;
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

					Field actionField = getActionField(clazz, actionFieldName);
					if(fieldGetterMethod == null) {
						
						if(actionField != null)
							actionFieldObj = actionField.get(action);
					}else {
						actionFieldObj = fieldGetterMethod.invoke(action);
					}
					
					
					if(actionFieldObj == null) {
						if(actionField != null) {
							Class<?> actionFieldClass = actionField.getType();
							
							actionFieldObj = ReflectionUtil.newInstance(actionFieldClass);
							
							//注入属性
							injectField(action, actionField, actionFieldName, actionFieldObj);
						}
					}

					fieldMethod = ReflectionUtil.getSetterMethod(actionFieldObj.getClass(), innerFieldName);
					
					Field field = ReflectionUtil.findField(actionFieldObj.getClass(), innerFieldName);
					if(field == null) {
						continue;
					}
					
					if(field.getType().isInstance(entry.getValue())) {
						//如果field属性是uploadFile
						ReflectionUtil.invoke(actionFieldObj, fieldMethod, entry.getValue());
						
					}else if(field.getType().isInstance(entry.getValue().getInputStream())) {
						//如果field属性是inputStream
						ReflectionUtil.invoke(action, method, entry.getValue().getInputStream());
					}else {
						continue;
					}

				} catch (Exception e) {
					throw new ValidateException("validate data error");
				}

				// 这里可能还要添加方法
			}
		}
	}


	public void afterHandle(ActionInvocation actionInvocation) throws ValidateException {

	}

}
