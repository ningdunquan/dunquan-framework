package org.dunquan.framework.mvc.core;

import org.dunquan.framework.mvc.exception.DispatcherException;
import org.dunquan.framework.sourse.ExecuteActionSource;

public interface ActionMapper {

	/**
	 * 根据映射找到执行中的actionSource
	 * @param requestBean
	 * @return
	 * @throws DispatcherException 
	 */
	public ExecuteActionSource findExecuteActionSource(RequestBean requestBean) throws DispatcherException;
}
