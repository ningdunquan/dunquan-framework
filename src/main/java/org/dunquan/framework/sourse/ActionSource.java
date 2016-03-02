package org.dunquan.framework.sourse;

import java.io.Serializable;
import java.util.Set;

public class ActionSource implements Serializable {
	private static final long serialVersionUID = 3068896917560185677L;
	
	/**
	 * action的全类名
	 */
	private String name;
	
	/**
	 * action中的methodSource
	 * 其中包含类名，url，是否为ajax请求
	 */
	private Set<MethodSource> methodSources;
	
	/**
	 * action处理后的返回参数
	 */
	private Set<Result> results;
	
	/**
	 * action执行后需要传送到页面的值
	 */
	private Set<String> out;

	public ActionSource() {
		super();
	}

	public ActionSource(String name, Set<MethodSource> methodSources,
			Set<Result> results, Set<String> out) {
		super();
		this.name = name;
		this.methodSources = methodSources;
		this.results = results;
		this.out = out;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MethodSource> getMethodSources() {
		return methodSources;
	}

	public void setMethodSources(Set<MethodSource> methodSources) {
		this.methodSources = methodSources;
	}

	public Set<Result> getResults() {
		return results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}

	public Set<String> getOut() {
		return out;
	}

	public void setOut(Set<String> out) {
		this.out = out;
	}
	
}
