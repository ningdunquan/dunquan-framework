package org.dunquan.framework.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dunquan.framework.mvc.core.ActionHandler;
import org.dunquan.framework.mvc.core.DefaultActionHandler;
import org.dunquan.framework.mvc.core.DefaultExceptionHandler;
import org.dunquan.framework.mvc.core.ExceptionHandler;
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
    private static final String VIEW_RESOLVER = "dunquan.framework.custom.view_resolver";

    /**
     * 获取 HandlerExceptionResolver
     */
    public static ExceptionHandler getExceptionHandler() {
        return getInstance(HANDLER_EXCEPTION_RESOLVER, DefaultExceptionHandler.class);
    }
    
    public static ActionHandler getActionHandler() {
    	return getInstance(ACTION_HANDLER, DefaultActionHandler.class);
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
