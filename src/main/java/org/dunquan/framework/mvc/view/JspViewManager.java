package org.dunquan.framework.mvc.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dunquan.framework.mvc.exception.DispatcherException;
import org.dunquan.framework.mvc.utils.WebUtils;

public class JspViewManager implements ViewManager {

	public void handleView(HttpServletRequest request,
			HttpServletResponse response, LogicView logicView) throws DispatcherException {
		String path = logicView.getPath();
		if(!path.endsWith(".jsp") && !path.contains(".")) {
			path = path + ".jsp";
		}
		
		Map<String, Object> outData = logicView.getData();
		for(Map.Entry<String, Object> entry : outData.entrySet()) {
			
			request.setAttribute(entry.getKey(), entry.getValue());
		}
		
		WebUtils.forwardDispatcher(request, response, path);
	}

	
}
