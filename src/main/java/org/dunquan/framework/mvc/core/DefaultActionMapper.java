package org.dunquan.framework.mvc.core;

import java.util.Map;
import java.util.Set;

import org.dunquan.framework.manager.ActionManager;
import org.dunquan.framework.mvc.exception.DispatcherException;
import org.dunquan.framework.mvc.handle.ManagerHandle;
import org.dunquan.framework.mvc.sourse.ActionSource;
import org.dunquan.framework.mvc.sourse.ExecuteActionSource;
import org.dunquan.framework.mvc.sourse.MethodSource;

public class DefaultActionMapper implements ActionMapper {

	private ActionManager actionManager;
	
	public ActionManager getActionManager() {
		if(actionManager == null) {
			actionManager = ManagerHandle.getInstance().getActionManager();
		}
		return actionManager;
	}
	
	public ExecuteActionSource findExecuteActionSource(RequestBean requestBean) throws DispatcherException {
		String url = requestBean.getRequestPath();
		String actionUrl = null;
		
		for(Map.Entry<String, ActionSource> entry : getActionManager().getActionMap().entrySet()) {
			Set<MethodSource> methodSources = entry.getValue().getMethodSources();
			for(MethodSource methodSource : methodSources) {
				//action对应方法的url
				actionUrl = methodSource.getUrl();
				if(actionUrl == null) {
					throw new DispatcherException("action error");
				}
				if(actionUrl.contains("*")) {
					if(actionUrl.indexOf("*") != actionUrl.lastIndexOf("*")) {
						throw new DispatcherException("action url has too many *");
					}
					//测试：*/test/phone
					//	/test/phone/nn*ndq
					//	/test/phone/ndq*
					String[] arr = actionUrl.split("\\*");
					if(arr.length == 2) {
						if(url.startsWith(arr[0]) && url.endsWith(arr[1])) {
							return new ExecuteActionSource(entry.getKey(), methodSource);
						}
					}
					if(actionUrl.endsWith("*")) {
						if(url.startsWith(arr[0])) {
							return new ExecuteActionSource(entry.getKey(), methodSource);
						}
					}
				} else {
					if(actionUrl.equals(url)) {
						return new ExecuteActionSource(entry.getKey(), methodSource);
					}
				}
			}
			
		}
		return null;
	}
	
}
