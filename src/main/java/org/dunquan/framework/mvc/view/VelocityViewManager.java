package org.dunquan.framework.mvc.view;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class VelocityViewManager implements ViewManager {

	// private static final VelocityEngine velocityEngine = new
	// VelocityEngine();

	static {
//		velocityEngine
//				.setProperty("class.resource.loader.class",
//						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//
//		velocityEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "/");
//		velocityEngine.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
//		velocityEngine.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
//		// 进行初始化操作
//		try {
//			velocityEngine.init();
//		} catch (Exception e) {
//			throw new RuntimeException("not find velocity", e);
//		}
	}

	public void handleView(HttpServletRequest request,
			HttpServletResponse response, LogicView logicView) {
//		try {
//			// 加载模板，设定模板编码
//			Template t = velocityEngine.getTemplate(logicView.getPath(), "utf-8");
//			// 设置初始化数据
//			VelocityContext context = new VelocityContext();
//			
//			Map<String, Object> data = logicView.getData();
//			for(Map.Entry<String, Object> entry : data.entrySet()) {
//				context.put(entry.getKey(), entry.getValue());
//			}
//			
//			// 设置输出
//			StringWriter writer = new StringWriter();
//			// 将环境数据转化输出
//			t.merge(context, writer);
//			
//		} catch (Exception e) {
//			throw new RuntimeException("velocity error", e);
//		}
	}

}
