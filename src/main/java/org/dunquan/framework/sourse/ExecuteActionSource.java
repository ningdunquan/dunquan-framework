package org.dunquan.framework.sourse;

import java.io.Serializable;

/**
 * 执行中的action映射
 * @author Administrator
 *
 */
public class ExecuteActionSource implements Serializable {
	private static final long serialVersionUID = -4414881539502476390L;
	
	/**
	 * action的全类名
	 */
	private String name;
	
	/**
	 * action中的methodSource
	 * 其中包含类名，url，是否为ajax请求
	 */
	private MethodSource methodSource;

	public ExecuteActionSource() {
		super();
	}

	public ExecuteActionSource(String name, MethodSource methodSource) {
		super();
		this.name = name;
		this.methodSource = methodSource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MethodSource getMethodSource() {
		return methodSource;
	}

	public void setMethodSource(MethodSource methodSource) {
		this.methodSource = methodSource;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
