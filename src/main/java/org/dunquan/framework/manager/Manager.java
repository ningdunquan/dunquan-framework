package org.dunquan.framework.manager;

import org.w3c.dom.Document;

public interface Manager {

	/**
	 * 加载配置文件
	 * 
	 * @param path
	 * @return
	 */
	Document readDocFile(String path);

	/**
	 * 加载配置文件，读取配置文件的内容
	 * 
	 * @param path
	 */
	void manager(String path);
}
