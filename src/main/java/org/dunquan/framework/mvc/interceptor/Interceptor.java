package org.dunquan.framework.mvc.interceptor;

import org.dunquan.framework.context.ExecuteContext;

public interface Interceptor {

	public void beforeHanlde(ExecuteContext excuteContext) throws Exception;
	
	public void afterHanlde(ExecuteContext executeContext) throws Exception;
}
