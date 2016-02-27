package org.dunquan.framework.mvc.interceptor;

import org.dunquan.framework.mvc.context.ActionInvocation;

public interface Interceptor {

	public void beforeHandle(ActionInvocation actionInvocation) throws Exception;
	
	public void afterHandle(ActionInvocation actionInvocation) throws Exception;
}
