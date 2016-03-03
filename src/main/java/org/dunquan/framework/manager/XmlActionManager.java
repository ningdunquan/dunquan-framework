package org.dunquan.framework.manager;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dunquan.framework.exception.MyException;
import org.dunquan.framework.sourse.ActionSource;
import org.dunquan.framework.sourse.MethodSource;
import org.dunquan.framework.sourse.Result;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlActionManager implements ActionManager, XmlManager {

	private String path = "action.xml";
	private Map<String, ActionSource> actionMap = new ConcurrentHashMap<String, ActionSource>();
	
	public XmlActionManager() {
		manager(path);
	}
	
	public XmlActionManager(String path) {
		this.path = path;
		manager(path);
	}
	
	public Map<String, ActionSource> getActionMap() {
		return this.actionMap;
	}

	public ActionSource getActionSourse(String actionName) {
		return actionMap.get(actionName);
	}
	

	public void manager(String path) {
		Document document = readDocFile(path);
		
		ActionSource actionSource = null;
		Node node = null;
		String refClass = null;
		
		NodeList beanList = document.getElementsByTagName("action");
	
		/**
		 * 遍历文件中的<action/>节点
		 */
		for (int i = 0; i < beanList.getLength(); i++) {
			//获取<action/>节点的所有子节点
			NodeList nodeList = beanList.item(i).getChildNodes();
			actionSource = new ActionSource();

			Set<Result> results = new CopyOnWriteArraySet<Result>();
			Set<MethodSource> methodSources = new CopyOnWriteArraySet<MethodSource>();
			
			//遍历<action/>的子节点
			for (int j = 0; j < nodeList.getLength(); j++) {
				node = nodeList.item(j);
				String nodeName = node.getNodeName();
				
				if (!"#text".equals(nodeName)) {
					
					/**
					 * node为refClass
					 */
					if ("refClass".equals(nodeName)) {
						
						refClass = node.getTextContent();
						//设置action的name，即全类名
						actionSource.setName(refClass);
						continue;
					}
					
					/**
					 * node为out
					 */
					if ("out".equals(nodeName)) {
						String outValue = node.getTextContent();
						Set<String> outs = new CopyOnWriteArraySet<String>();
						
						if(outValue != null) {
							if(outValue.contains(",")) {
								addOuts(outValue, outs);
							}else {
								outs.add(outValue);
							}
						}
						
						actionSource.setOut(outs);
						continue;
					}
					
					/**
					 * node为method
					 */
					if ("method".equals(nodeName)) {
						
						setMethodSourceListByNode(node, methodSources);
						continue;
					}
					
					/**
					 * node为result
					 */
					if("result".equals(nodeName)) {
						setResultListByNode(node, results);
						continue;
					}
					
				}
			}
			actionSource.setResults(results);
			actionSource.setMethodSources(methodSources);
			actionMap.put(refClass, actionSource);
		}
	}
	
	/**
	 * 添加输出数据
	 * @param outValue
	 * @param outs
	 */
	private void addOuts(String outValue, Set<String> outs) {
		if(outValue == null) {
			return;
		}
		String[] arr = outValue.split(",");
		for(int i = 0; i < arr.length; i++) {
			outs.add(arr[i]);
		}
		
	}
	
	/**
	 * 根据node设置methodSource集合的值
	 * @param node
	 * @param methodSources
	 */
	private void setMethodSourceListByNode(Node node, Set<MethodSource> methodSources) {
		MethodSource methodSource = new MethodSource();
		
		methodSource.setMethodName(node.getTextContent());
		// 如果元素有属性值
		if (node.hasAttributes()) {
			//获取所有的属性节点
			NamedNodeMap nodeMap = node.getAttributes();
			
			int len = nodeMap.getLength();
			if(len >= 2) {
				methodSource.setUrl(nodeMap.item(0).getNodeValue());
				methodSource.setAjax(Boolean.parseBoolean(nodeMap.item(1).getNodeValue()));
			} else if(len == 1) {
				methodSource.setUrl(nodeMap.item(0).getNodeValue());
			}
		}
		//添加属性
		methodSources.add(methodSource);
	}

	/**
	 * 根据node设置result集合的值
	 * @param string
	 * @param node
	 * @param results
	 */
	private void setResultListByNode(Node node, Set<Result> results) {
		Result result = new Result();
		
		result.setResultValue(node.getTextContent());
		// 如果元素有属性值
		if (node.hasAttributes()) {
			//获取所有的属性节点
			NamedNodeMap nodeMap = node.getAttributes();
			
			int len = nodeMap.getLength();
			if(len >= 2) {
				result.setResultName(nodeMap.item(0).getNodeValue());
				result.setType(nodeMap.item(1).getNodeValue());
			}else if(len == 1) {
				result.setResultName(nodeMap.item(0).getNodeValue());
			}
		}
		//添加属性
		results.add(result);
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
