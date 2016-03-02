package org.dunquan.framework.mvc.interceptor;

import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.dunquan.framework.mvc.exception.ValidateException;

public class DataValidateStrategy extends AbstractValidateStrategy {

	@Override
	public void validate(Object action, HttpServletRequest request)
			throws ValidateException {
		
		Map<String, String> paramMap = getAllDispatcherParameter(request);
		setActionField(action, paramMap);
		
	}

	/**
	 * 获取所有的参数，将其存在map中
	 * 
	 * @param request
	 */
	private Map<String, String> getAllDispatcherParameter(
			HttpServletRequest request) {
		Map<String, String> paramMap = new ConcurrentHashMap<String, String>();

		Enumeration<String> enumeration = request.getParameterNames();

		String name = null;
		String value = null;
		while (enumeration.hasMoreElements()) {
			name = enumeration.nextElement();
			value = request.getParameter(name);
			paramMap.put(name, value);
		}

		return paramMap;
	}

}
