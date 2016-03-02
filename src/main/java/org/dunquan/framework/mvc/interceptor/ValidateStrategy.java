package org.dunquan.framework.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证数据的策略
 * @author Administrator
 *
 */
public interface ValidateStrategy {

	/**
	 * 验证注入数据
	 * @param action
	 * @param request
	 */
	public void validate(Object action, HttpServletRequest request);
}
