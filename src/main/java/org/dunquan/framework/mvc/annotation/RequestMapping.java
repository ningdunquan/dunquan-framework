package org.dunquan.framework.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

	/**
	 * method对应的url
	 * @return
	 */
	public String value();
	
	/**
	 * 此请求是否为ajax请求
	 * @return
	 */
	public boolean isAjax() default false; 
}
