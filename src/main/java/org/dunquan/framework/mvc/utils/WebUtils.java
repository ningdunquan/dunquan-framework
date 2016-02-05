package org.dunquan.framework.mvc.utils;

import javax.servlet.http.HttpServletRequest;

import org.dunquan.framework.mvc.core.RequestBean;


public class WebUtils {

	/**
     * 判断是否为 AJAX 请求
     */
    public static boolean isAJAX(HttpServletRequest request) {
        return request.getHeader("X-Requested-With") != null;
    }
    
    public static RequestBean getRequestBean(HttpServletRequest request) {
    	String method = request.getMethod();
    	String path = request.getServletPath();
    	RequestBean requestBean = new RequestBean();
    	requestBean.setRequestPath(path);
    	requestBean.setRequestMethodName(method);
    	
    	return requestBean;
    }
}
