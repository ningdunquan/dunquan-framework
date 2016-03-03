package org.dunquan.framework.mvc.context;

import org.dunquan.framework.sourse.ExecuteActionSource;


/**
 * 执行期间的context
 * @author Administrator
 *
 */
public interface ExecuteContext {

	public ActionInvocation getActionInvocation();
	
	public ExecuteActionSource getActionSource();
	
}
