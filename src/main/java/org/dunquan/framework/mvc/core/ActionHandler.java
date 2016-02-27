package org.dunquan.framework.mvc.core;

import org.dunquan.framework.mvc.context.ExecuteContext;

public interface ActionHandler {

	public void handleAction(ExecuteContext excuteContext) throws Exception;
}
