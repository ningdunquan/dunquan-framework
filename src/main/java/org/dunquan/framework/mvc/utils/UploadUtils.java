package org.dunquan.framework.mvc.utils;

import org.apache.commons.io.FilenameUtils;

public class UploadUtils {

	/**
	 * 获取文件名
	 * @param value
	 * @return
	 */
	public static String getFileName(String value) {
		
		return FilenameUtils.getName(value);
	}

	
}
