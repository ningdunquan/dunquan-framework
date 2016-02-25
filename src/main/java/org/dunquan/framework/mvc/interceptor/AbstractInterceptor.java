package org.dunquan.framework.mvc.interceptor;

import org.dunquan.framework.context.ExecuteContext;

public abstract class AbstractInterceptor implements Interceptor {

	protected void init() {};
	
	public abstract void beforeHanlde(ExecuteContext excuteContext) throws Exception;
	
	public abstract void afterHanlde(ExecuteContext executeContext) throws Exception;
}
