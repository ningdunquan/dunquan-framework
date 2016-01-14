package com.dunquan.framework.interceptor;

import com.dunquan.framework.api.ApplicationAble;
import com.dunquan.framework.api.HttpServletRequestAble;
import com.dunquan.framework.api.HttpServletResponseAble;
import com.dunquan.framework.api.HttpSessionAble;
import com.dunquan.framework.api.RequestAble;
import com.dunquan.framework.api.ServletContextAble;
import com.dunquan.framework.api.SessionAble;
import com.dunquan.framework.context.ActionContext;
import com.dunquan.framework.context.ExcuteContext;

public class ServletRefHandler extends AbstractHandler {

	@Override
	public void hanlder(ExcuteContext excuteContext) {
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

}
