package org.dunquan.framework.mvc.interceptor;

import org.dunquan.framework.mvc.api.ApplicationAble;
import org.dunquan.framework.mvc.api.HttpServletRequestAble;
import org.dunquan.framework.mvc.api.HttpServletResponseAble;
import org.dunquan.framework.mvc.api.HttpSessionAble;
import org.dunquan.framework.mvc.api.RequestAble;
import org.dunquan.framework.mvc.api.ServletContextAble;
import org.dunquan.framework.mvc.api.SessionAble;
import org.dunquan.framework.mvc.context.ActionContext;
import org.dunquan.framework.mvc.context.ActionInvocation;

public class ServletRefInterceptor extends AbstractInterceptor {

	@Override
	public void beforeHandle(ActionInvocation actionInvocation) {
		Object action = actionInvocation.getAction();
		ActionContext actionContext = actionInvocation.getActionContext();
		
		if(action instanceof HttpServletRequestAble) {
			((HttpServletRequestAble)action).setHttpServletRequest(actionContext.getHttpServletRequest());
		}
		
		if(action instanceof HttpServletResponseAble) {
			((HttpServletResponseAble)action).setHttpServletResponse(actionContext.getHttpServletResponse());
		}
		
		if(action instanceof HttpSessionAble) {
			((HttpSessionAble)action).setHttpSession(actionContext.getHttpSession());
		}
		
		if(action instanceof ServletContextAble) {
			((ServletContextAble)action).setServletContext(actionContext.getServletContext());
		}

		if(action instanceof RequestAble) {
			((RequestAble)action).setRequest(actionContext.getHttpServletRequestMap());
		}
		
		if(action instanceof ApplicationAble) {
			((ApplicationAble)action).setApplication(actionContext.getApplicationMap());
		}
		
		if(action instanceof SessionAble) {
			((SessionAble)action).setSession(actionContext.getHttpSessionMap());
		}
	}

	@Override
	public void afterHandle(ActionInvocation actionInvocation) {
		// TODO Auto-generated method stub
		
	}

}
