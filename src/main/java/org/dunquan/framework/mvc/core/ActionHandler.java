package org.dunquan.framework.mvc.core;

import org.dunquan.framework.mvc.context.ExecuteContext;
import org.dunquan.framework.mvc.exception.DispatcherException;

public interface ActionHandler {

	public void handleAction(ExecuteContext excuteContext) throws Exception;
	
	public void execute(ExecuteContext executeContext) throws DispatcherException;
}
