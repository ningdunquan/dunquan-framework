package org.dunquan.framework.interceptor;

import org.dunquan.framework.context.ExecuteContext;

public abstract class AbstractHandler {

	protected void init() {};
	
	public abstract void hanlder(ExecuteContext excuteContext);
}
