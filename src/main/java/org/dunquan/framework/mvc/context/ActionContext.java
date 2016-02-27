package org.dunquan.framework.mvc.context;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ActionContext implements Serializable{
	private static final long serialVersionUID = 5009654757068720596L;

	private static ThreadLocal<ActionContext> threadLocal = new ThreadLocal<ActionContext>();
	
	public static final String HTTP_SERVLET_REQUEST = "http_servlet_request";
	
	public static final String HTTP_SERVLET_RESPONSE = "http_servlet_response";
	
	public static final String HTTP_SESSION = "http_session";
	
	public static final String SERVLET_CONTEXT = "servlet_context";
	
	public static final String HTTP_SERVLET_REQUEST_MAP = "http_servlet_request_map";
	
	public static final String HTTP_SESSION_MAP = "http_session_map";
	
	public static final String SERVLET_CONTEXT_MAP = "servlet_context_map";
	
	private Map<String, Object> contextMap;
	
	public ActionContext(Map<String, Object> contextMap){
		this.contextMap = contextMap;
	}
	
	/**
	 * 静态方法设置context
	 * @param actionContext
	 */
	public static void setContext(ActionContext actionContext) {
		threadLocal.set(actionContext);
	}
	
	/**
	 * 静态方法设置context
	 * @return
	 */
	public static ActionContext getContext() {
		return threadLocal.get();
	}
	
	/**
	 * 获取contextMap
	 * @return
	 */
	public Map<String, Object> getContextMap() {
		return contextMap;
	}

	/**
	 * 设置contextMap
	 * @param contextMap
	 */
	public void setContextMap(Map<String, Object> contextMap) {
		this.contextMap = contextMap;
	}

	
	public void setHttpServletRequestMap(Map<String, Object> requestMap) {
		contextPut(HTTP_SERVLET_REQUEST_MAP, requestMap);
	}
	
	public Map<String, Object> getHttpServletRequestMap() {
		return (Map<String, Object>) contextGet(HTTP_SERVLET_REQUEST_MAP);
	}
	
	public void setHttpSessionMap(Map<String, Object> sessionMap) {
		contextPut(HTTP_SESSION_MAP, sessionMap);
	}
	
	public Map<String, Object> getHttpSessionMap() {
		return (Map<String, Object>) contextGet(HTTP_SESSION_MAP);
	}
	
	public void setApplicationMap(Map<String, Object> applicationMap) {
        contextPut(SERVLET_CONTEXT_MAP, applicationMap);
    }
	

    /**
     * 
     */
    public Map<String, Object> getApplicationMap() {
        return (Map<String, Object>) contextGet(SERVLET_CONTEXT_MAP);
    }
	
	
	/**
	 * 设置request对象到map中
	 * @param request
	 */
	public void setHttpServletRequest(HttpServletRequest request) {
		contextPut(HTTP_SERVLET_REQUEST, request);
	}
	
	/**
	 * 获取当前的request对象
	 * @return
	 */
	public HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) contextGet(HTTP_SERVLET_REQUEST);
	}
	
	/**
	 * 设置response对象到map中
	 * @param response
	 */
	public void setHttpServletResponse(HttpServletResponse response) {
		contextPut(HTTP_SERVLET_RESPONSE, response);
	}
	
	/**
	 * 获取当前的response对象
	 * @return
	 */
	public HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) contextGet(HTTP_SERVLET_RESPONSE);
	}
	
	/**
	 * 设置session对象到map中
	 * @param session
	 */
	public void setHttpSession(HttpSession session) {
		contextPut(HTTP_SESSION, session);
	}
	
	/**
	 * 获取当前的session对象
	 * @return
	 */
	public HttpSession getHttpSession() {
		return (HttpSession) contextGet(HTTP_SESSION);
	}
	
	/**
	 * 设置servletContext对象到map中
	 * @param servletContext
	 */
	public void setServletContext(ServletContext servletContext) {
		contextPut(SERVLET_CONTEXT, servletContext);
	}
	
	/**
	 * 获取当前的servletContext对象
	 * @return
	 */
	public ServletContext getServletContext() {
		return (ServletContext) contextGet(SERVLET_CONTEXT);
	}
	
	
	/**
	 * 向map中添加数据
	 * @param key
	 * @param value
	 */
	public void contextPut(String key, Object value) {
		contextMap.put(key, value);
	}
	
	/**
	 * 获取map中的数据
	 * @param key
	 * @return
	 */
	public Object contextGet(String key) {
		return contextMap.get(key);
	}
	
	public void destroy() {
		threadLocal.remove();
	}
}
