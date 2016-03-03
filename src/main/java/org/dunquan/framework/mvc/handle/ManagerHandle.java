package org.dunquan.framework.mvc.handle;

import java.io.InputStream;

import org.dunquan.framework.exception.IOCException;
import org.dunquan.framework.loader.AnnoApplicationBeanLoader;
import org.dunquan.framework.loader.ApplicationBeanLoader;
import org.dunquan.framework.loader.XmlApplicationBeanLoader;
import org.dunquan.framework.manager.ActionManager;
import org.dunquan.framework.manager.AnnoActionManager;
import org.dunquan.framework.manager.XmlActionManager;
import org.dunquan.framework.mvc.exception.ActionException;

public class ManagerHandle {
	
	private ApplicationBeanLoader applicationBeanLoader;
	private ActionManager actionManager;

	private static ManagerHandle managerHandle = new ManagerHandle();
	private ManagerHandle() {
	}
	/**
	 * 单例设计
	 * @return
	 */
	public static ManagerHandle getInstance() {
		return managerHandle;
	}
	
	public void init() throws ActionException, IOCException {
		InputStream inputStream = null;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("action.xml");
			if(inputStream == null) {
				actionManager = new AnnoActionManager();
			}else {
				actionManager = new XmlActionManager();
			}
		} catch (Exception e) {
			throw new ActionException("no action manager", e);
		}
		
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("bean.xml");
			if(inputStream == null) {
				applicationBeanLoader = new AnnoApplicationBeanLoader();
			}else {
				applicationBeanLoader = new XmlApplicationBeanLoader();
			}
		} catch (Exception e) {
			throw new IOCException("no bean manager", e);
		}
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public ApplicationBeanLoader getApplicationBeanLoader() {
		return applicationBeanLoader;
	}
}
