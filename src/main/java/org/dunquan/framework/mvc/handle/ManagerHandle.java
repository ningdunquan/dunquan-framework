package org.dunquan.framework.mvc.handle;

import org.dunquan.framework.loader.ApplicationBeanLoader;
import org.dunquan.framework.loader.XmlApplicationBeanLoader;
import org.dunquan.framework.manager.ActionManager;
import org.dunquan.framework.manager.XmlActionManager;

public class ManagerHandle {

	private ApplicationBeanLoader applicationBeanLoader;
	private ActionManager actionManager;

	public ManagerHandle() {
		this.applicationBeanLoader = new XmlApplicationBeanLoader();

		this.actionManager = new XmlActionManager();
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public ApplicationBeanLoader getApplicationBeanLoader() {
		return applicationBeanLoader;
	}
}
