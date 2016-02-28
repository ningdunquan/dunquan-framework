package org.dunquan.framework.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dunquan.framework.mvc.exception.DispatcherException;

public interface ViewManager {

	public void handleView(HttpServletRequest request,
			HttpServletResponse response, LogicView logicView) throws DispatcherException ;
}
