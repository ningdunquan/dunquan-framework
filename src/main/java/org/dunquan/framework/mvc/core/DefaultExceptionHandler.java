package org.dunquan.framework.mvc.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dunquan.framework.mvc.utils.WebUtils;

public class DefaultExceptionHandler implements ExceptionHandler {

	public void handleError(HttpServletRequest request,
			HttpServletResponse response, String msg, Exception exception) {
		// 判断异常原因
		if(exception == null) {
			// 跳转到 404 页面
			WebUtils.sendError(HttpServletResponse.SC_NOT_FOUND, msg, response);
		}
//		Throwable cause = exception.getCause();
		// 分两种情况进行处理
		if (WebUtils.isAJAX(request)) {
			// 跳转到 403 页面
			WebUtils.sendError(HttpServletResponse.SC_FORBIDDEN, msg, response);
		} else {
			// 跳转到 404 页面
			WebUtils.sendError(HttpServletResponse.SC_NOT_FOUND, msg, response);
		}
	}

}
