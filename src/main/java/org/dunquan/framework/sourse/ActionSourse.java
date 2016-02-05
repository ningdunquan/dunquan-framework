package org.dunquan.framework.sourse;

import java.util.List;

public class ActionSourse {
	private String actionUrl;
	private String refClass;
	private String actionMethod;
	private List<Result> results;
	
	public ActionSourse() {
		super();
	}
	public ActionSourse(String actionUrl, String refClass, String actionMethod,
			List<Result> results) {
		super();
		this.actionUrl = actionUrl;
		this.refClass = refClass;
		this.actionMethod = actionMethod;
		this.results = results;
	}
	
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public String getRefClass() {
		return refClass;
	}
	public void setRefClass(String refClass) {
		this.refClass = refClass;
	}
	public String getActionMethod() {
		return actionMethod;
	}
	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}
	public List<Result> getResults() {
		return results;
	}
	public void setResults(List<Result> results) {
		this.results = results;
	}
	
	@Override
	public String toString() {
		return "ActionSourse [actionUrl=" + actionUrl + ", refClass="
				+ refClass + ", actionMethod=" + actionMethod + ", results="
				+ results + "]";
	}
	
}
