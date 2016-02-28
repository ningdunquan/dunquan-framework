package org.dunquan.framework.mvc.view;

import java.util.HashMap;
import java.util.Map;

public class LogicView {

	    private String path;              // 视图路径
	    private Map<String, Object> data; // 相关数据

	    public LogicView(String path) {
	        this.path = path;
	        data = new HashMap<String, Object>();
	    }
	    public LogicView(String path, Map<String, Object> data) {
	    	this.path = path;
	    	this.data = data;
	    }
	    public LogicView data(String path, String key, Object value) {
	        this.path = path;
	    	this.data = new HashMap<String, Object>();
	        data.put(key, value);
	        return this;
	    }

	    public String getPath() {
	        return path;
	    }

	    public void setPath(String path) {
	        this.path = path;
	    }

	    public Map<String, Object> getData() {
	        return data;
	    }

	    public void setData(Map<String, Object> data) {
	        this.data = data;
	    }
	}

