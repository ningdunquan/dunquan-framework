package org.dunquan.framework.mvc.core;

import org.dunquan.framework.mvc.handle.ManagerHandle;

public class ActionCreater {

	/**
	 * 新建action
	 * @param name
	 * @return
	 */
	public static Object createAction(String name) {
		Object action = ManagerHandle.getInstance().getApplicationBeanLoader().getBean(name);
		
		return action;
	}
}
