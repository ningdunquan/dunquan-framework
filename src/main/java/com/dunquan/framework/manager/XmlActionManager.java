package com.dunquan.framework.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dunquan.framework.exception.MyException;
import com.dunquan.framework.sourse.ActionSourse;
import com.dunquan.framework.sourse.Result;

public class XmlActionManager implements ActionManager {

	private String path = "action.xml";
	private Map<String, ActionSourse> actionMap = new HashMap<String, ActionSourse>();
	
	public XmlActionManager() {
		manager(path);
	}
	
	public XmlActionManager(String path) {
		this.path = path;
		manager(path);
	}
	
	public Map<String, ActionSourse> getActionMap() {
		return this.actionMap;
	}

	public ActionSourse getActionSourse(String actionName) {
		return actionMap.get(actionName);
	}
	

	public void manager(String path) {
		Document document = readDocFile(path);
		
		ActionSourse actionSourse = null;
		Node node = null;
		String actionUrl = null;
		String refClass = null;
		String actionMethod = null;
		
		NodeList beanList = document.getElementsByTagName("action");
	
		/**
		 * 遍历文件中的<action/>节点
		 */
		for (int i = 0; i < beanList.getLength(); i++) {
			//获取<bean/>节点的所有子节点
			NodeList nodeList = beanList.item(i).getChildNodes();
			actionSourse = new ActionSourse();

			List<Result> results = new ArrayList<Result>();
			
			//遍历<bean/>的子节点
			for (int j = 0; j < nodeList.getLength(); j++) {
				node = nodeList.item(j);
				String nodeName = node.getNodeName();
				
				if (!nodeName.equals("#text")) {
					
					if (nodeName.equals("url")) {
						actionUrl = node.getTextContent();
						actionSourse.setActionUrl(actionUrl);
						continue;
					}
					
					if (nodeName.equals("refClass")) {
						
						refClass = node.getTextContent();
						actionSourse.setRefClass(refClass);
						continue;
					}
					
					if (nodeName.equals("method")) {
						
						actionMethod = node.getTextContent();
						actionSourse.setActionMethod(actionMethod);
						continue;
					}
					
					setResultListByNode("result", node, results);
					
				}
			}
			actionSourse.setResults(results);;
			actionMap.put(actionUrl, actionSourse);
		}
	}
	
	/**
	 * 根据node设置result集合的值
	 * @param string
	 * @param node
	 * @param results
	 */
	private void setResultListByNode(String string, Node node, List<Result> results) {
		if(string != null && string.equals(node.getNodeName())) {
			Result result = new Result();
			
			result.setResultValue(node.getTextContent());
			// 如果元素有属性值
			if (node.hasAttributes()) {
				//获取所有的属性节点
				NamedNodeMap nodeMap = node.getAttributes();
				
				result.setResultName(nodeMap.item(0).getNodeValue());
				result.setType(nodeMap.item(1).getNodeValue());
			}
			results.add(result);
		}
	}
	

	public Document readDocFile(String path) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder documentBuilder = null;
		Document document = null;
		try {
			documentBuilder = builderFactory.newDocumentBuilder();

			document = documentBuilder.parse(this.getClass().getClassLoader().getResourceAsStream(path));

		} catch (Exception e) {
			throw new MyException("找不到文件");
		}
		return document;
	}

}
