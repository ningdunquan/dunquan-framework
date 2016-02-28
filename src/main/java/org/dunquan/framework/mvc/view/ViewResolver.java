package org.dunquan.framework.mvc.view;

import org.dunquan.framework.mvc.context.ExecuteContext;
import org.dunquan.framework.mvc.core.ActionHandler;
import org.dunquan.framework.mvc.exception.DispatcherException;

/**
 * 视图解析器
 * @author Administrator
 *
 */
public interface ViewResolver {

	/**
	 * 解析视图
	 * @param executeContext
	 * @param actionValue
	 * @param actionHandler
	 * @throws DispatcherException
	 */
	public void resolve(ExecuteContext executeContext, Object actionValue, ActionHandler actionHandler)
			throws DispatcherException;
}
