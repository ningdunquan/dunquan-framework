package com.dunquan.framework.handle;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dunquan.framework.context.ActionContext;
import com.dunquan.framework.wrapmap.ApplicationMap;
import com.dunquan.framework.wrapmap.RequestMap;
import com.dunquan.framework.wrapmap.SessionMap;

public class BeforePrepareHandle {

	/**
	 * 新建actionContext对象
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionContext createActionContext(HttpServletRequest request,
			HttpServletResponse response) {
		ActionContext actionContext = null;

		ActionContext context = ActionContext.getContext();

		if (context == null) {

			Map<String, Object> map = createContextMap(request, response);

			actionContext = new ActionContext(map);

		} else {
			actionContext = new ActionContext(new HashMap<String, Object>(
					context.getContextMap()));
		}

		return actionContext;
	}

	/**
	 * 新建contextMap对象
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private Map<String, Object> createContextMap(HttpServletRequest request,
			HttpServletResponse response) {
		ServletContext servletContext = request.getServletContext();

		HttpSession session = request.getSession(false);

		return initContextMap(request, response, session, servletContext);
	}

	/**
	 * 初始化contextMap对象
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param servletContext
	 * @return
	 */
	private Map<String, Object> initContextMap(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			ServletContext servletContext) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put(ActionContext.HTTP_SERVLET_REQUEST, request);
		map.put(ActionContext.HTTP_SERVLET_RESPONSE, response);
		map.put(ActionContext.HTTP_SESSION, session);
		map.put(ActionContext.SERVLET_CONTEXT, servletContext);
		map.put(ActionContext.HTTP_SERVLET_REQUEST_MAP, new RequestMap(request));
		map.put(ActionContext.HTTP_SESSION_MAP, new SessionMap<String, Object>(
				request));
		map.put(ActionContext.SERVLET_CONTEXT_MAP, new ApplicationMap(
				servletContext));

		return map;
	}
}
