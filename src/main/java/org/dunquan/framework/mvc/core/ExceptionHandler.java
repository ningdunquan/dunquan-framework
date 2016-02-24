package org.dunquan.framework.mvc.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExceptionHandler {

	public void handleError(HttpServletRequest request,
			HttpServletResponse response, String msg, Exception exception);
}
