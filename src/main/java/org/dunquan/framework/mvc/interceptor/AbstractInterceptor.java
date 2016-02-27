package org.dunquan.framework.mvc.interceptor;

import org.dunquan.framework.mvc.context.ActionInvocation;

public abstract class AbstractInterceptor implements Interceptor {

	protected void init() {};
	
	public abstract void beforeHandle(ActionInvocation actionInvocation) throws Exception;
	
	public abstract void afterHandle(ActionInvocation actionInvocation) throws Exception;
}
