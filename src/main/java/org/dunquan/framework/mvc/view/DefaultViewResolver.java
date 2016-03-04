package org.dunquan.framework.mvc.view;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dunquan.framework.factory.InstanceFactory;
import org.dunquan.framework.mvc.context.ActionContext;
import org.dunquan.framework.mvc.context.ExecuteContext;
import org.dunquan.framework.mvc.core.ActionHandler;
import org.dunquan.framework.mvc.exception.DispatcherException;
import org.dunquan.framework.mvc.handle.ManagerHandle;
import org.dunquan.framework.mvc.sourse.ActionSource;
import org.dunquan.framework.mvc.sourse.ExecuteActionSource;
import org.dunquan.framework.mvc.sourse.Result;
import org.dunquan.framework.mvc.utils.WebUtils;
import org.dunquan.framework.util.ReflectionUtil;

public class DefaultViewResolver implements ViewResolver {

	public void resolve(ExecuteContext executeContext, Object actionValue, ActionHandler actionHandler)
			throws DispatcherException {
		ActionContext actionContext = executeContext.getActionInvocation().getActionContext();
		HttpServletRequest request = actionContext.getHttpServletRequest();
		HttpServletResponse response = actionContext.getHttpServletResponse();

		ExecuteActionSource actionSource = executeContext.getActionSource();
		Object action = executeContext.getActionInvocation().getAction();
		
		if(WebUtils.isAJAX(request) || actionSource.getMethodSource().isAjax()) {
			//写入json数据
			WebUtils.writeJSON(response, actionValue);
			return;
		}
		
		if(actionValue instanceof LogicView) {
			
			resolveLogicView(request, response, (LogicView)actionValue);
			
		}else if(actionValue instanceof Result) {
			Result result = (Result)actionValue;
			
			resolveResult(actionSource.getName(), action, request, response, result);
			
		}else if(actionValue instanceof String) {
			String value = (String) actionValue;
			
			Result result = findResult(actionSource.getName(), value);
			
			if(result == null) {
				result = new Result();
				if(value.startsWith("redirect:")) {
					result.setType(Result.RE_REDIRECT);
				}else if(value.startsWith("action:")) {
					result.setType(Result.RE_ACTION);
				}else {
					result.setType(Result.RE_DISPATCHER);
				}
				String resultValue = getResultValue(value);
				result.setResultValue(resultValue);
				
			}
			
			if(Result.RE_ACTION.equalsIgnoreCase(result.getType())) {
				actionSource.getMethodSource().setMethodName(result.getResultValue());
				
				actionHandler.execute(executeContext);
				
				return;
			}
			
			resolveResult(actionSource.getName(), action, request, response, result);
		}else {
			return;
		}
	}

	/**
	 * 获取resultValue
	 * @param value
	 * @return
	 */
	private String getResultValue(String value) {
		int index = value.indexOf(":") + 1;
		return value.substring(index);
	}

	/**
	 * 解析Result视图
	 * @param name
	 * @param action
	 * @param request
	 * @param response
	 * @param result
	 * @throws DispatcherException
	 */
	private void resolveResult(String name, Object action,
			HttpServletRequest request, HttpServletResponse response,
			Result result) throws DispatcherException {
		if(Result.RE_REDIRECT.equalsIgnoreCase(result.getType())) {
			WebUtils.sendRedirect(response, result.getResultValue());
			return;
		}
		
		if(Result.RE_DISPATCHER.equalsIgnoreCase(result.getType())) {
			Set<String> outs = getActionSource(name).getOut();
			Map<String, Object> data = new HashMap<String, Object>();
			for(String out : outs) {
				Field field = ReflectionUtil.getField(action, out);
				if(field != null) {
					Object outValue = null;
					try {
						outValue = field.get(action);
					} catch (Exception e) {
						throw new DispatcherException("field error", e);
					}
					data.put(out, outValue);
				}
			}
			LogicView logicView = new LogicView(result.getResultValue(), data);
			
			resolveLogicView(request, response, logicView);
			
			return;
		}
	}

	/**
	 * 获取actionSource
	 * @param name
	 * @return
	 */
	private ActionSource getActionSource(String name) {
		Map<String, ActionSource> map = ManagerHandle.getInstance().getActionManager().getActionMap();
		
		ActionSource actionSource = null;
		for(Map.Entry<String, ActionSource> entry : map.entrySet()) {
			if(name.equals(entry.getKey())) {
				actionSource = entry.getValue();
				return actionSource;
			}
		}
		return null;
	}

	/**
	 * 解析逻辑视图
	 * @param request
	 * @param response
	 * @param logicView
	 * @throws DispatcherException
	 */
	private void resolveLogicView(HttpServletRequest request,
			HttpServletResponse response, LogicView logicView)
			throws DispatcherException {
		String path = logicView.getPath();
		
		ViewManager viewManager = createViewManager(path);
		if(viewManager == null) {
			throw new RuntimeException("not find viewManager");
		}
		
		viewManager.handleView(request, response, logicView);
		
	}
	
	/**
	 * 创建ViewManager对象
	 * @param path
	 * @return
	 */
	private ViewManager createViewManager(String path) {
		ViewManager viewManager = null;
		if(path.endsWith(".ftl")) {
			//freemarker视图
			viewManager = InstanceFactory.getFreeMarkerViewManager();
		}else if(path.endsWith(".vm")) {
			//velocity的视图
			viewManager = InstanceFactory.getVelocityViewManager();
		}else {
			//jsp视图
			viewManager = InstanceFactory.getJspViewManager();
		}
		return viewManager;
	}

	/**
	 * 寻找result对象
	 * @param actionSourse
	 * @param resultValue
	 * @return
	 */
	private Result findResult(String name, String actionValue) {
		ActionSource actionSource = getActionSource(name);
		if(actionSource == null) {
			return null;
		}
		
		String resultName = null;
		for(Result result : actionSource.getResults()) {
			resultName = result.getResultName();
			if(actionValue.equals(resultName)) {
				return result;
			}
		}
		return null;
	}

}
