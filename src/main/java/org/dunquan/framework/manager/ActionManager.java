package org.dunquan.framework.manager;

import java.util.Map;





import org.dunquan.framework.sourse.ActionSource;

public interface ActionManager {

	/**
	 * 获取actionMap对象
	 * @return
	 */
	Map<String, ActionSource> getActionMap();
	
	/**
	 * 获取单个的actionSource对象
	 * @param actionName
	 * @return
	 */
	ActionSource getActionSourse(String actionName);
	
}
