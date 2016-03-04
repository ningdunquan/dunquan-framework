package org.dunquan.framework.mvc.manager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.dunquan.framework.factory.InstanceFactory;
import org.dunquan.framework.mvc.annotation.Action;
import org.dunquan.framework.mvc.annotation.Out;
import org.dunquan.framework.mvc.annotation.RequestMapping;
import org.dunquan.framework.mvc.annotation.ResultValue;
import org.dunquan.framework.mvc.exception.ActionException;
import org.dunquan.framework.mvc.scanner.ClassScanner;
import org.dunquan.framework.mvc.sourse.ActionSource;
import org.dunquan.framework.mvc.sourse.MethodSource;
import org.dunquan.framework.mvc.sourse.Result;
import org.dunquan.framework.util.StringUtil;

public class AnnoActionManager implements ActionManager {

	private static final ClassScanner classScanner = InstanceFactory
			.getClassScanner();

	private Map<String, ActionSource> actionMap = new ConcurrentHashMap<String, ActionSource>();

	public Map<String, ActionSource> getActionMap() {
		return this.actionMap;
	}

	public ActionSource getActionSourse(String actionName) {
		return actionMap.get(actionName);
	}

	public void manager() {
		List<Class<?>> classList = classScanner.getClassListByAnnotation("", Action.class);

		for (Class<?> clazz : classList) {

			Set<Result> results = getAllResults(clazz);
			
			Set<String> outs = getAllOutsField(clazz);

			Set<MethodSource> methodSources = getAllMethodSource(clazz);
			
			ActionSource actionSource = new ActionSource();
			actionSource.setName(clazz.getName());
			actionSource.setMethodSources(methodSources);
			actionSource.setOut(outs);
			actionSource.setResults(results);
			
			actionMap.put(clazz.getName(), actionSource);
		}

	}

	/**
	 * 获取所有的methodSources
	 * @param clazz
	 * @return
	 */
	private Set<MethodSource> getAllMethodSource(Class<?> clazz) {
		Set<MethodSource> methodSources = new CopyOnWriteArraySet<MethodSource>();
		
		Method[] methods = clazz.getDeclaredMethods();
		addMethodSource(methodSources, methods);
		
		Class<?> superClazz = clazz.getSuperclass();
		if(superClazz != null) {
			methods = superClazz.getMethods();
			addMethodSource(methodSources, methods);
		}
		
		return methodSources;
	}

	/**
	 * 添加methodSource
	 * @param methodSources
	 * @param methods
	 */
	private void addMethodSource(Set<MethodSource> methodSources,
			Method[] methods) {
		for(Method method : methods) {
			if(method.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping mapping = method.getAnnotation(RequestMapping.class);
				
				String url = mapping.value();
				boolean isAjax = mapping.isAjax();
				String name = method.getName();
				MethodSource methodSource = new MethodSource(name, url, isAjax);
				methodSources.add(methodSource);
			}
		}
		
	}
	

	/**
	 * 获取所有的输出数据
	 * @param clazz
	 * @return
	 */
	private Set<String> getAllOutsField(Class<?> clazz) {
		Set<String> outs = new CopyOnWriteArraySet<String>();
		
		Field[] fields = clazz.getDeclaredFields();
		addFieldName(outs, fields);
		
		Class<?> superClazz = clazz.getSuperclass();
		if(superClazz != null) {
			fields = superClazz.getFields();
			addFieldName(outs, fields);
		}
		
		return outs;
	}

	/**
	 * 添加输出数据
	 * @param outs
	 * @param fields
	 */
	private void addFieldName(Set<String> outs, Field[] fields) {
		for(Field field : fields) {
			if(field.isAnnotationPresent(Out.class)) {
				outs.add(field.getName());
			}
		}
	}
	

	/**
	 * 获取action的results
	 * @param clazz
	 * @return
	 */
	private Set<Result> getAllResults(Class<?> clazz) {
		Set<Result> results = new CopyOnWriteArraySet<Result>();
		if (clazz.isAnnotationPresent(ResultValue.class)) {
			
			ResultValue rv = clazz.getAnnotation(ResultValue.class);
			String value = rv.value();
			if (StringUtil.isNotEmpty(value)) {
				String[] arr = value.split(";");
				for (String res : arr) {
					String[] arrRes = res.split(":");
					if (arrRes.length == 3) {
						Result result = new Result(arrRes[0], arrRes[1],
								arrRes[2]);
						results.add(result);
					} else {
						throw new ActionException("resultValue error");
					}
				}
			}
		}
		return results;
	}
	
	public static void main(String[] args) {
		AnnoActionManager actionManager = new AnnoActionManager();
		actionManager.manager();
		System.out.println(actionManager.actionMap.size());
	}
}
