package org.dunquan.framework.mvc.core;

public class RequestBean {

	private String requestPath;
	
	private String requestMethodName;
	
	public String getRequestPath() {
		return requestPath;
	}
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	public String getRequestMethodName() {
		return requestMethodName;
	}
	public void setRequestMethodName(String requestMethodName) {
		this.requestMethodName = requestMethodName;
	}
	
}
