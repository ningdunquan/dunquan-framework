package org.dunquan.framework.mvc.upload;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.dunquan.framework.mvc.constant.MvcConstant;
import org.dunquan.framework.mvc.utils.UploadUtils;


public class UploadHelper {

	private static final String FILE_TEMP = "org_dunquan_file_temp_space";
	
	private static final int capacity = 10;
	
	private static ServletFileUpload fileUpload;

	public static void init(ServletContext servletContext) {
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();

		File file = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		File tempFile = new File(file, FILE_TEMP);
		if(!tempFile.exists()) {
			tempFile.mkdirs();
		}
		
		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		factory.setRepository(tempFile);
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(capacity * 1024 * 1024);
		
		// API文件上传处理
		fileUpload = new ServletFileUpload(factory);
	}
	
	/**
	 * 是否是文件上传
	 * @param request
	 * @return
	 */
	public static boolean isMultipartFormData(HttpServletRequest request) {
		
		return ServletFileUpload.isMultipartContent(request);
	}
	
	public static UploadField getUploadField(HttpServletRequest request) throws Exception {
		// 这意味着可以上传多个文件
		@SuppressWarnings("unchecked")
		List<FileItem> list = fileUpload.parseRequest(request);

		Map<String, String> params = new HashMap<String, String>();
		Map<String, UploadFile> uploads = new HashMap<String, UploadFile>();
		for(FileItem fileItem : list) {
			//字段名
            String fieldName = fileItem.getFieldName();
			if(fileItem.isFormField()) {
				//如果是普通数据
				String fieldValue = fileItem.getString(MvcConstant.UTF_8);
				params.put(fieldName, fieldValue);
			}else {
				//如果是文件数据
				String fileName = UploadUtils.getFileName(fileItem.getName());
				
				String contentType = fileItem.getContentType();
				long size = fileItem.getSize();
				UploadFile uploadFile = new UploadFile(fileName, contentType, size, fileItem.getInputStream());
				
				uploads.put(fieldName, uploadFile);
			}
		}
		
		return new UploadField(params, uploads);
	}
	
}
