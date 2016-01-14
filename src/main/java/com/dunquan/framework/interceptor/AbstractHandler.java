package com.dunquan.framework.interceptor;

import com.dunquan.framework.context.ExcuteContext;

public abstract class AbstractHandler {

	protected void init() {};
	
	public abstract void hanlder(ExcuteContext excuteContext);
}
