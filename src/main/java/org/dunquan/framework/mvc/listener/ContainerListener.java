package org.dunquan.framework.mvc.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

/**
 * 容器监听器
 *
 */
@WebListener
public class ContainerListener implements ServletContextListener {
	
	/**
     * 当容器初始化时调用
     */
    public void contextInitialized(ServletContextEvent sce) {
        // 获取 ServletContext
        ServletContext servletContext = sce.getServletContext();
        // 添加 Servlet 映射
        addServletMapping(servletContext);
    }

    /**
     * 当容器销毁时调用
     */
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private void addServletMapping(ServletContext context) {
        // 用 DefaultServlet 映射所有静态资源
        registerDefaultServlet(context);
        // 用 JspServlet 映射所有 JSP 请求
        registerJspServlet(context);
    }

    private void registerDefaultServlet(ServletContext context) {
        ServletRegistration defaultServlet = context.getServletRegistration("default");
        defaultServlet.addMapping("/index.html");
        defaultServlet.addMapping("/favicon.ico");
    }

    private void registerJspServlet(ServletContext context) {
        ServletRegistration jspServlet = context.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp");
    }

}


