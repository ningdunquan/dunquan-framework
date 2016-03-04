package org.dunquan.framework.mvc.dispatcher;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dunquan.framework.factory.InstanceFactory;
import org.dunquan.framework.mvc.context.ActionContext;
import org.dunquan.framework.mvc.core.ActionCommand;
import org.dunquan.framework.mvc.core.ActionMapper;
import org.dunquan.framework.mvc.core.ExceptionHandler;
import org.dunquan.framework.mvc.core.RequestBean;
import org.dunquan.framework.mvc.handle.BeforePrepareHandle;
import org.dunquan.framework.mvc.handle.ManagerHandle;
import org.dunquan.framework.mvc.sourse.ExecuteActionSource;
import org.dunquan.framework.mvc.utils.WebUtils;

/**
 * dispatcher分发的servlet，负责把请求交给真正的action来执行
 * 
 * @author dunquan
 *
 */
public class DispatcherExecuteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ActionMapper actionMapper;

	private BeforePrepareHandle prepareHandle;

	private ExceptionHandler exceptionHandler;

	/**
	 * 初始化servlet
	 */
	public void init(ServletConfig config) throws ServletException {
		ServletContext servletContext = config.getServletContext();

		prepareHandle = new BeforePrepareHandle(servletContext);
		exceptionHandler = InstanceFactory.getExceptionHandler();
		actionMapper = InstanceFactory.getActionMapper();

		ManagerHandle managerHandle = ManagerHandle.getInstance();
		// 初始化
		managerHandle.init();
	}

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		RequestBean requestBean = WebUtils.getRequestBean(request);

		ActionContext actionContext = prepareHandle.createActionContext(
				request, response);

		try {
			ExecuteActionSource actionSource = actionMapper.findExecuteActionSource(requestBean);

			if (actionSource == null) {
				errorDispatcher(request, response, "can't find action", null);
				return;
			}

			ActionCommand actionCommand = new ActionCommand(actionContext, actionSource);

			actionCommand.execute();
		} catch (Exception e) {
			errorDispatcher(request, response, e.getMessage(), e);
		} finally {
			actionContext.destroy();
		}
	}

	/**
	 * 异常处理
	 * 
	 * @param request
	 * @param response
	 * @param string
	 * @param object
	 */
	private void errorDispatcher(HttpServletRequest request,
			HttpServletResponse response, String msg, Exception e) {
		exceptionHandler.handleError(request, response, msg, e);
		e.printStackTrace();
	}

}
