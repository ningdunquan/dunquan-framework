package com.dunquan.framework.handle;

import java.util.ArrayList;
import java.util.List;

import com.dunquan.framework.context.ExcuteContext;
import com.dunquan.framework.interceptor.AbstractHandler;
import com.dunquan.framework.interceptor.ServletRefHandler;

public class ActionHandle {

	private static List<AbstractHandler> handlers = new ArrayList<AbstractHandler>();
	
	static{
		handlers.add(new ServletRefHandler());
	}
	
	/**
	 * 执行handler操作
	 * @param excuteContext
	 */
	public void handleAction(ExcuteContext excuteContext) {
		for(AbstractHandler handler : handlers) {
			handler.hanlder(excuteContext);
		}
	}
	
	
}
