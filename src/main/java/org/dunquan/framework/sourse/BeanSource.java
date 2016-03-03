package org.dunquan.framework.sourse;

import java.util.Map;

public class BeanSource {

	private String id;
	private String className;
	private Map<String, String> fields;
	private Map<String, String> refs;
	private boolean prototype;
	
	public BeanSource() {
	}
	public BeanSource(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Map<String, String> getFields() {
		return fields;
	}
	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}
	public Map<String, String> getRefs() {
		return refs;
	}
	public void setRefs(Map<String, String> refs) {
		this.refs = refs;
	}
	public boolean getPrototype() {
		return prototype;
	}
	public void setPrototype(boolean prototype) {
		this.prototype = prototype;
	}

	@Override
	public String toString() {
		return "BeanSourse [id=" + id + ", className=" + className + ", fields=" + fields + ", refs=" + refs
				+ ", prototype=" + prototype + "]";
	}
}
