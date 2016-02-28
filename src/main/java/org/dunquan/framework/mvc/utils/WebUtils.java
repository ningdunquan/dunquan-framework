package org.dunquan.framework.mvc.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dunquan.framework.mvc.core.RequestBean;
import org.dunquan.framework.mvc.exception.DispatcherException;
import org.dunquan.framework.util.JsonUtil;


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
    
    /**
     * 发送错误代码
     */
    public static void sendError(int code, String message, HttpServletResponse response) {
        try {
            response.sendError(code, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	public static void writeJSON(HttpServletResponse response, Object value) {
		response.setContentType("text/javascript; charset=UTF-8");
		String json;
		if(value instanceof String) {
			json = (String)value;
		}else {
			json = JsonUtil.toJSON(value);
		}
		PrintWriter writer;
		try {
			writer = response.getWriter();
			//写入json数据
			writer.write(json);
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 重定向 页面
	 * @param response
	 * @param path
	 * @throws DispatcherException 
	 */
	public static void sendRedirect(HttpServletResponse response, String path) throws DispatcherException {
		try {
			response.sendRedirect(path);
		} catch (IOException e) {
			throw new DispatcherException("redirect error");
		}
	}

	/**
	 * 页面转发
	 * @param request
	 * @param response
	 * @param path
	 * @throws DispatcherException
	 */
	public static void forwardDispatcher(HttpServletRequest request,
			HttpServletResponse response, String path) throws DispatcherException {
		try {
			request.getRequestDispatcher(path).forward(request, response);
		} catch (Exception e) {
			throw new DispatcherException("forward error", e);
		}
	}
}
