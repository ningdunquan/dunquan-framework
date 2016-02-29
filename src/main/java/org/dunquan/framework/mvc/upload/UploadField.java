package org.dunquan.framework.mvc.upload;

import java.util.Map;

public class UploadField {

	/**
	 * 普通参数
	 */
	private Map<String, String> params;
	
	/**
	 * 文件参数
	 */
	private Map<String, UploadFile> uploads;

	public UploadField() {
		super();
	}
	public UploadField(Map<String, String> params,
			Map<String, UploadFile> uploads) {
		super();
		this.params = params;
		this.uploads = uploads;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, UploadFile> getUploads() {
		return uploads;
	}

	public void setUploads(Map<String, UploadFile> uploads) {
		this.uploads = uploads;
	}
	
}
