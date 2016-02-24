package org.dunquan.framework.mvc.core;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.dunquan.framework.context.ExecuteContext;
import org.dunquan.framework.interceptor.AbstractHandler;
import org.dunquan.framework.interceptor.ServletRefHandler;

public class ActionHandler {

	private static List<AbstractHandler> handlers = new CopyOnWriteArrayList<AbstractHandler>();
	
	static{
		handlers.add(new ServletRefHandler());
	}
	
	/**
	 * 执行handler操作
	 * @param excuteContext
	 */
	public void handleAction(ExecuteContext excuteContext) {
		for(AbstractHandler handler : handlers) {
			handler.hanlder(excuteContext);
		}
	}
	
	
}
