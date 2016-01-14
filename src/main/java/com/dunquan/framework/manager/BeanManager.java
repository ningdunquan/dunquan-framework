package com.dunquan.framework.manager;

import java.util.Map;

import com.dunquan.framework.sourse.BeanSourse;

public interface BeanManager extends Manager {
	
	/**
	 * 获取存放配置文件数据的map
	 * @return
	 */
	Map<String, BeanSourse> getBeanMap();
	
	/**
	 * 暴露在外面提供获取beanSourse的方法
	 * @param bean
	 * @return
	 */
	BeanSourse getBean(String bean);
	
}
