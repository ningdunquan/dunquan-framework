package com.ning.java.action;

import org.dunquan.framework.mvc.annotation.Action;
import org.dunquan.framework.mvc.annotation.Out;
import org.dunquan.framework.mvc.annotation.RequestMapping;
import org.dunquan.framework.mvc.annotation.ResultValue;

@Action
@ResultValue("success:action:show;hello:dispatcher:userinfo.jsp")
public class UserAction {

	@Out
	private City city;
	@Out
	private int id;
	
	@RequestMapping("/test/user/exec.action")
	public String execute() {
		id = 12;
		city = new City();
		
		return "hello";
	}
	
	@RequestMapping("/test/user/show.action")
	public String show() {
		id = 12;
		city = new City();
		
		return "userinfo.jsp";
	}
}
