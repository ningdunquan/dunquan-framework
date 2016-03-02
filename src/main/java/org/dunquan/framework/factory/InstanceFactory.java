package org.dunquan.framework.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dunquan.framework.mvc.core.ActionHandler;
import org.dunquan.framework.mvc.core.DefaultActionHandler;
import org.dunquan.framework.mvc.core.DefaultExceptionHandler;
import org.dunquan.framework.mvc.core.ExceptionHandler;
import org.dunquan.framework.mvc.interceptor.DataValidateStrategy;
import org.dunquan.framework.mvc.interceptor.UploadValidateStrategy;
import org.dunquan.framework.mvc.interceptor.ValidateStrategy;
import org.dunquan.framework.mvc.view.DefaultViewResolver;
import org.dunquan.framework.mvc.view.FreeMarkerViewManager;
import org.dunquan.framework.mvc.view.JspViewManager;
import org.dunquan.framework.mvc.view.VelocityViewManager;
import org.dunquan.framework.mvc.view.ViewManager;
import org.dunquan.framework.mvc.view.ViewResolver;
import org.dunquan.framework.util.ReflectionUtil;


public class InstanceFactory {
	  /**
     * 用于缓存对应的实例
     */
    private static final Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    /**
     * ClassScanner
     */
    private static final String CLASS_SCANNER = "dunquan.framework.custom.class_scanner";

    /**
     * HandlerMapping
     */
    private static final String HANDLER_MAPPING = "dunquan.framework.custom.handler_mapping";

    /**
     * HandlerInvoker
     */
    private static final String ACTION_HANDLER = "dunquan.framework.mvc.action_handler";

    /**
     * HandlerExceptionResolver
     */
    private static final String HANDLER_EXCEPTION_RESOLVER = "dunquan.framework.mvc.exception_handler";

    /**
     * ViewResolver
     */
    private static final String VIEW_RESOLVER = "dunquan.framework.mvc.view_resolver";

    /**
     * freemarker视图管理器
     */
    private static final String FREEMARKER_VIEW_MANAGER = "dunquan.framework.mvc.freemarker_view_manager";

    /**
     * jsp视图管理器
     */
    private static final String JSP_VIEW_MANAGER = "dunquan.framework.mvc.jsp_view_manager";

    /**
     * velocity视图管理器
     */
    private static final String VELOCITY_VIEW_MANAGER = "dunquan.framework.mvc.velocity_view_manager";
  
    /**
     * 普通参数验证策略
     */
    private static final String DATA_VALIDATE_STRATEGY = "dunquan.framework.mvc.data_validate_strategy";

    /**
     * 上传数据验证策略
     */
    private static final String UPLOAD_VALIDATE_STRATEGY = "dunquan.framework.mvc.upload_validate_strategy";
    
    
    
    /**
     * 获取DataValidateStrategy
     * @return
     */
    public static ValidateStrategy getDataValidateStrategy() {
    	return getInstance(DATA_VALIDATE_STRATEGY, DataValidateStrategy.class);
    }
    
    /**
     * 获取UploadValidateStrategy
     * @return
     */
    public static ValidateStrategy getUploadvValidateStrategy() {
    	return getInstance(UPLOAD_VALIDATE_STRATEGY, UploadValidateStrategy.class);
    }
    
    /**
     * 获取 HandlerExceptionResolver
     */
    public static ExceptionHandler getExceptionHandler() {
        return getInstance(HANDLER_EXCEPTION_RESOLVER, DefaultExceptionHandler.class);
    }
    
    /**
     * 获取actionHandler
     * @return
     */
    public static ActionHandler getActionHandler() {
    	return getInstance(ACTION_HANDLER, DefaultActionHandler.class);
    }
    
    /**
     * 获取ViewResolver视图解析器
     * @return
     */
    public static ViewResolver getViewResolver() {
		return getInstance(VIEW_RESOLVER, DefaultViewResolver.class);
	}
    
    /**
     * 获取freemarker视图管理器
     * @return
     */
    public static ViewManager getFreeMarkerViewManager() {
    	return getInstance(FREEMARKER_VIEW_MANAGER, FreeMarkerViewManager.class);
    }
    
    /**
     * 获取jsp视图管理器
     * @return
     */
    public static ViewManager getJspViewManager() {
    	return getInstance(JSP_VIEW_MANAGER, JspViewManager.class);
    }
    
    /**
     * 获取velocity视图管理器
     * @return
     */
    public static ViewManager getVelocityViewManager() {
    	return getInstance(VELOCITY_VIEW_MANAGER, VelocityViewManager.class);
    }

    @SuppressWarnings("unchecked")
    public synchronized static <T> T getInstance(String cacheKey, Class<T> defaultImplClass) {
        // 若缓存中存在对应的实例，则返回该实例
        if (cache.containsKey(cacheKey)) {
            return (T) cache.get(cacheKey);
        }
        // 从配置文件中获取相应的接口实现类配置
        String implClassName = defaultImplClass.getName();
        // 通过反射创建该实现类对应的实例
        T instance = ReflectionUtil.newInstance(implClassName);
        // 若该实例不为空，则将其放入缓存
        if (instance != null) {
            cache.put(cacheKey, instance);
        }
        // 返回该实例
        return instance;
    }

}