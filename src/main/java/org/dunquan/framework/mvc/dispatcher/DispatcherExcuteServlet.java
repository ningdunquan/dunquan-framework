package org.dunquan.framework.mvc.dispatcher;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dunquan.framework.context.ActionContext;
import org.dunquan.framework.exception.DispatcherException;
import org.dunquan.framework.mvc.core.ActionCommand;
import org.dunquan.framework.mvc.core.RequestBean;
import org.dunquan.framework.mvc.handle.BeforePrepareHandle;
import org.dunquan.framework.mvc.handle.ManagerHandle;
import org.dunquan.framework.mvc.utils.WebUtils;
import org.dunquan.framework.sourse.ActionSourse;

/**
 * dispatcher分发的servlet，负责把请求交给真正的action来执行
 * 
 * @author dunquan
 *
 */
public class DispatcherExcuteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, ActionSourse> actionMap;
	
	private BeforePrepareHandle prepareHandle;
	private ManagerHandle managerHandle;
	
	
	/**
	 * 初始化servlet
	 */
	public void init(ServletConfig config) throws ServletException {
		
		prepareHandle = new BeforePrepareHandle();
		managerHandle = new ManagerHandle();
	}

	
	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		
		RequestBean requestBean = WebUtils.getRequestBean(request);
		String url = requestBean.getRequestPath();

		ActionContext actionContext = prepareHandle.createActionContext(request, response);
		
		Map<String, ActionSourse> actionMapSourse = getLocalActionMap();
		ActionSourse actionSourse = findActionSourse(url, actionMapSourse);
		
		if(actionSourse == null) {
			errorDispatcher(response, "actionSourse为空");
			return;
		}

		
		ActionCommand actionCommand = new ActionCommand(requestBean, actionContext, managerHandle.getApplicationBeanLoader(), actionSourse);
		
		actionCommand.execute();
	}


	/**
	 * 找不到请求的方法，返回404错误
	 * @param response
	 * @throws IOException
	 */
	private void errorDispatcher(HttpServletResponse response, String error)
			throws IOException {
		try {
			throw new DispatcherException("找不到对应的action, " + error);
		} catch (DispatcherException e) {
			e.printStackTrace();
		}
		response.sendError(404);
		return;
	}

	/**
	 * 寻找actionSourse对象
	 * @param url
	 * @param actionMapSourse
	 * @return
	 */
	private ActionSourse findActionSourse(String url,
			Map<String, ActionSourse> actionMapSourse) {
		ActionSourse actionSourse = null;
		for(Map.Entry<String, ActionSourse> entry : actionMapSourse.entrySet()) {
			String actionUrl = entry.getKey();
			if(actionUrl.contains("*")) {
				actionUrl = actionUrl.replace("*", "");

				if(url.contains(actionUrl)) {
					actionSourse = entry.getValue();
					return actionSourse;
				}
			} else {
				if(actionUrl.equals(url)) {
					actionSourse = entry.getValue();
					return actionSourse;
				}
			}
		}
		return null;
	}

	/**
	 * 获取当前的actionMap
	 * 
	 * @return
	 */
	private Map<String, ActionSourse> getLocalActionMap() {
		if (actionMap == null) {
			synchronized (this) {
				if (actionMap == null) {
					actionMap = managerHandle.getActionManager().getActionMap();
				}
			}
		}
		return actionMap;
	}

}
