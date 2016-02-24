package org.dunquan.framework.mvc.handle;

import java.util.ArrayList;
import java.util.List;

import org.dunquan.framework.context.ExecuteContext;
import org.dunquan.framework.interceptor.AbstractHandler;
import org.dunquan.framework.interceptor.ServletRefHandler;

public class ActionHandle {

	private static List<AbstractHandler> handlers = new ArrayList<AbstractHandler>();
	
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
