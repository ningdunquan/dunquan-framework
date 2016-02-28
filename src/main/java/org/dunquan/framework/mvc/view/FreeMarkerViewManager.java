package org.dunquan.framework.mvc.view;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dunquan.framework.mvc.exception.DispatcherException;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerViewManager implements ViewManager {

	private static final Configuration cfg = new Configuration();
	
	private boolean flag = false;
	
	public void handleView(HttpServletRequest request,
			HttpServletResponse response, LogicView logicView) throws DispatcherException {
		if(flag) {
			cfg.setServletContextForTemplateLoading(request.getServletContext(), "");
			flag = true;
		}
		
		try {
		// 获取模板文件
		Template t = cfg.getTemplate(logicView.getPath());  
        
        // 使用text/html MIME-type  
        response.setContentType("text/html; charset=" + t.getEncoding());  
        Writer out = response.getWriter();  
        
        // 合并数据模型和模板，并将结果输出到out中  
        t.process(logicView.getData(), out); // 往模板里写数据  
        
		} catch (Exception e) {
			throw new DispatcherException("freemarker template error", e);
        }  
	}

}
