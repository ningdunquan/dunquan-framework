package org.dunquan.framework.mvc.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

public class UploadUtils {

	/**
	 * 是否是文件上传
	 * @param request
	 * @return
	 */
	public static boolean isMultipartFormData(HttpServletRequest request) {
		
		return ServletFileUpload.isMultipartContent(request);
	}
	
	/**
	 * 获取文件名
	 * @param value
	 * @return
	 */
	public static String getFileName(String value) {
		
		return FilenameUtils.getName(value);
	}

	
}
