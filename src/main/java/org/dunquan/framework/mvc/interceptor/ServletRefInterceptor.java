package org.dunquan.framework.mvc.interceptor;

import org.dunquan.framework.api.ApplicationAble;
import org.dunquan.framework.api.HttpServletRequestAble;
import org.dunquan.framework.api.HttpServletResponseAble;
import org.dunquan.framework.api.HttpSessionAble;
import org.dunquan.framework.api.RequestAble;
import org.dunquan.framework.api.ServletContextAble;
import org.dunquan.framework.api.SessionAble;
import org.dunquan.framework.context.ActionContext;
import org.dunquan.framework.context.ExecuteContext;

public class ServletRefInterceptor extends AbstractInterceptor {

	@Override
	public void beforeHanlde(ExecuteContext excuteContext) {
		Object action = excuteContext.getAction();
		ActionContext actionContext = excuteContext.getActionContext();
		
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
	public void afterHanlde(ExecuteContext executeContext) {
		// TODO Auto-generated method stub
		
	}

}
