package org.dunquan.framework.mvc.sourse;

import java.io.Serializable;

public class MethodSource implements Serializable {
	private static final long serialVersionUID = 5171352467110604795L;

	private String methodName;
	private String url;
	private boolean isAjax;
	
	public MethodSource() {
		super();
	}
	public MethodSource(String methodName, String url, boolean isAjax) {
		super();
		this.methodName = methodName;
		this.url = url;
		this.isAjax = isAjax;
	}
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isAjax() {
		return isAjax;
	}
	public void setAjax(boolean isAjax) {
		this.isAjax = isAjax;
	}
	
}
