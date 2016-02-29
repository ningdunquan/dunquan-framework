package org.dunquan.framework.mvc.upload;

import java.io.InputStream;
import java.io.Serializable;

/**
 * 上传的文件类
 * @author Administrator
 *
 */
public class UploadFile implements Serializable {
	private static final long serialVersionUID = 5475665462960851942L;

	/**
	 * 文件名
	 */
	private String fileName;
	
	/**
	 * 文件类型
	 */
	private String fileContentType;
	
	/**
	 * 文件大小
	 */
	private long fileSize;
	
	/**
	 * 文件流
	 */
	private InputStream inputStream;

	public UploadFile() {
		super();
	}
	public UploadFile(String fileName, String fileContentType, long fileSize,
			InputStream inputStream) {
		super();
		this.fileName = fileName;
		this.fileContentType = fileContentType;
		this.fileSize = fileSize;
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
