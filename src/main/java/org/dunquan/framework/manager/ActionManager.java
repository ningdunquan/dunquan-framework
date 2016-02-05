package org.dunquan.framework.manager;

import java.util.Map;




import org.dunquan.framework.sourse.ActionSourse;

public interface ActionManager extends Manager {

	/**
	 * 获取actionMap对象
	 * @return
	 */
	Map<String, ActionSourse> getActionMap();
	
	/**
	 * 获取单个的actionSourse对象
	 * @param actionName
	 * @return
	 */
	ActionSourse getActionSourse(String actionName);
	
}
