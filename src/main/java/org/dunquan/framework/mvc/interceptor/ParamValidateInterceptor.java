package org.dunquan.framework.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.dunquan.framework.factory.InstanceFactory;
import org.dunquan.framework.mvc.context.ActionContext;
import org.dunquan.framework.mvc.context.ActionInvocation;
import org.dunquan.framework.mvc.utils.UploadUtils;

public class ParamValidateInterceptor extends AbstractInterceptor {

	private ValidateStrategy validateStrategy;
	
	@Override
	public void beforeHandle(ActionInvocation actionInvocation)
			throws Exception {
		ActionContext actionContext = actionInvocation.getActionContext();
		HttpServletRequest request = actionContext.getHttpServletRequest();
		Object action = actionInvocation.getAction();
		
		validateStrategy = createValidateStrategy(request);

		validateStrategy.validate(action, request);
	}

	
	/**
	 * 选择创建哪种策略
	 * @param request
	 * @return
	 */
	private ValidateStrategy createValidateStrategy(HttpServletRequest request) {
		ValidateStrategy strategy = null;
		if(UploadUtils.isMultipartFormData(request)) {
			strategy = InstanceFactory.getDataValidateStrategy();
		}else {
			strategy = InstanceFactory.getUploadvValidateStrategy();
		}
		
		return strategy;
	}

	
	@Override
	public void afterHandle(ActionInvocation actionInvocation) throws Exception {

	}

}
