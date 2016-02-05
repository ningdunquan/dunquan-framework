package org.dunquan.framework.interceptor;

import org.dunquan.framework.context.ExcuteContext;

public abstract class AbstractHandler {

	protected void init() {};
	
	public abstract void hanlder(ExcuteContext excuteContext);
}
