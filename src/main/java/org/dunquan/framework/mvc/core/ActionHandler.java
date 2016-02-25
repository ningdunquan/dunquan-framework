package org.dunquan.framework.mvc.core;

import org.dunquan.framework.context.ExecuteContext;

public interface ActionHandler {

	public void handleAction(ExecuteContext excuteContext) throws Exception;
}
