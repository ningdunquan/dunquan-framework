package org.dunquan.framework.manager;

import java.util.Map;

import org.dunquan.framework.ioc.sourse.BeanSource;


public interface BeanManager {
	
	/**
	 * 获取存放配置文件数据的map
	 * @return
	 */
	Map<String, BeanSource> getBeanMap();
	
	/**
	 * 暴露在外面提供获取beanSource的方法
	 * @param bean
	 * @return
	 */
	BeanSource getBean(String bean);
	
}
