package org.dunquan.framework.manager;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dunquan.framework.exception.MyException;
import org.dunquan.framework.sourse.BeanSource;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlBeanManager implements BeanManager,XmlManager {

	private Map<String, BeanSource> map = new HashMap<String, BeanSource>();
	private String path = "bean.xml";

	public XmlBeanManager() {
		manager(path);
	}
	public XmlBeanManager(String path) {
		this.path = path;
		manager(path);
	}

	/**
	 * 获取存放配置文件数据的map
	 * @return
	 */
	public Map<String, BeanSource> getBeanMap() {
		return this.map;
	}
	
	/**
	 * 暴露在外面提供获取beanSourse的方法
	 * @param bean
	 * @return
	 */
	public BeanSource getBean(String bean) {
		return map.get(bean);
	}

	/**
	 * 读取xml文件，将bean节点的数据存入到map中
	 * @param path
	 */
	public void manager(String path) {
		String id = null;
		String className = null;
		boolean prototype = false;
		Node node = null;
		BeanSource beanSourse = null;
		
		Document document = readDocFile(path);
		
		NodeList beanList = document.getElementsByTagName("bean");
	
		/**
		 * 遍历文件中的<bean/>节点
		 */
		for (int i = 0; i < beanList.getLength(); i++) {
			//获取<bean/>节点的所有子节点
			NodeList nodeList = beanList.item(i).getChildNodes();
			beanSourse = new BeanSource();

			Map<String, String> fieldMap = new HashMap<String, String>();
			Map<String, String> refMap = new HashMap<String, String>();
			
			//遍历<bean/>的子节点
			for (int j = 0; j < nodeList.getLength(); j++) {
				node = nodeList.item(j);
				String nodeName = node.getNodeName();
				
				if (!nodeName.equals("#text")) {
					
					if (nodeName.equals("id")) {
						id = node.getTextContent();
						beanSourse.setId(id);
						continue;
					}
					
					if (nodeName.equals("class")) {
						
						className = node.getTextContent();
						beanSourse.setClassName(className);
						continue;
					}
					
					setFieldAndRefByNode("field", node, fieldMap);
					
					setFieldAndRefByNode("myref", node, refMap);
					
					if("prototype".equals(nodeName)) {
						try {
							prototype = Boolean.parseBoolean(node.getTextContent());
						} catch (DOMException e) {
							throw new MyException("类型转换异常");
						}
						beanSourse.setPrototype(prototype);
						continue;
					}
				}
				
			}
			beanSourse.setFields(fieldMap);
			beanSourse.setRefs(refMap);
			map.put(id, beanSourse);
		}
		
	}

	/**
	 * 加载xml配置文件
	 * @param path
	 * @return
	 */
	public Document readDocFile(String path) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder documentBuilder = null;
		Document document = null;
		try {
			documentBuilder = builderFactory.newDocumentBuilder();

			document = documentBuilder.parse(this.getClass().getClassLoader().getResourceAsStream(path));

		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("找不到文件");
		}
		return document;
	}

	/**
	 * 设置bean的属性
	 * @param str
	 * @param node
	 * @param map
	 */
	private void setFieldAndRefByNode(String str, Node node, Map<String, String> map) {
		if (str.equals(node.getNodeName())) {
			// 如果元素有属性值
			if (node.hasAttributes()) {
				
				//获取所有的属性节点
				NamedNodeMap nodeMap = node.getAttributes();
				
				map.put(nodeMap.item(0).getNodeValue(), nodeMap.item(1).getNodeValue());

			}
		}
	}

}
