package org.dunquan.framework.mvc.core;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.dunquan.framework.context.ExecuteContext;
import org.dunquan.framework.mvc.interceptor.AbstractInterceptor;
import org.dunquan.framework.mvc.interceptor.ServletRefInterceptor;

public class DefaultActionHandler implements ActionHandler {

	private static List<AbstractInterceptor> handlers = new CopyOnWriteArrayList<AbstractInterceptor>();
	
	static{
		handlers.add(new ServletRefInterceptor());
	}
	
	/**
	 * 执行handler操作
	 * @param excuteContext
	 */
	public void handleAction(ExecuteContext excuteContext) throws Exception {
		for(AbstractInterceptor handler : handlers) {
			handler.beforeHanlde(excuteContext);
		}
		
		Object action = excuteContext.getAction();
		//执行action
		execute(action);
		
		for(AbstractInterceptor handler : handlers) {
			handler.afterHanlde(excuteContext);
		}
		
	}

	private void execute(Object action) {
		
	}
	
	
}
