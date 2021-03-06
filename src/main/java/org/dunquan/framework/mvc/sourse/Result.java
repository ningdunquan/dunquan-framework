package org.dunquan.framework.mvc.sourse;

/**
 * Action返回对象的信息
 * @author ningdq
 *
 */
public class Result {
	
	/**
	 * 重定向
	 */
	public static final String RE_REDIRECT = "redirect";
	
	/**
	 * 转发
	 */
	public static final String RE_DISPATCHER = "dispatcher";
	
	/**
	 * ajax请求
	 */
	public static final String RE_AJAX = "ajax";
	
	/**
	 * ajax请求返回json数据
	 */
	public static final String RE_JSON = "json";
	
	/**
	 * 转发到另一个action处理
	 */
	public static final String RE_ACTION = "action";

	private String resultName;
	private String type;
	private String resultValue;
	
	public Result() {
		super();
	}
	public Result(String resultName, String type, String resultValue) {
		super();
		this.resultName = resultName;
		this.type = type;
		this.resultValue = resultValue;
	}
	
	public String getResultName() {
		return resultName;
	}
	public void setResultName(String resultName) {
		this.resultName = resultName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResultValue() {
		return resultValue;
	}
	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}
	
	@Override
	public String toString() {
		return "Result [resultName=" + resultName + ", type=" + type
				+ ", resultValue=" + resultValue + "]";
	}
	
}
