package com.feng.surveypark.struts2.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.User;
import com.feng.surveypark.service.RightService;
import com.feng.surveypark.service.UserService;

/**
 * 登入Action
 * 接口SessionAware的实现意义是获得session的 map
 */
  
@Controller
@Scope("prototype")
public class LoginAction extends BaseAction<User> implements SessionAware {	
	private static final long serialVersionUID = -44954064920241948L;

	@Resource
	private UserService userService;
	@Resource
	private RightService rightService;
	
	//接收session 的 map
	private Map<String, Object> sessionMap;
	
	
	public String toLoginPage(){
		return "longinPage";
	}
	
	public String doLogin() {	
		return SUCCESS;	
	}
	
/*
 * validate拦截器
 * 方法一:
 * 直接使用validate做方法名
 * 用@SkipValidation注解加到不需要验证方法前,指定不用校验的方法
 * 方法二:
 * 使用validate(Do)方法名,针对该方法前运行.
 */
	
	public void validateDoDoLogin() {		
		User user = userService.validateLoginInfo(model.getEmail(),model.getPassword());
		if (user == null) {
			//失败
			addActionError("邮箱或密码错误~~~");
		} else {
			//计算用户授权
			int maxRightPos = rightService.getMaxRightPos();
			user.setRightSum(new long[maxRightPos+1]);
			//计算用户权限总和
			user.calculateRightSum();
			
			//将用户放入session
//			方法一:原生方法
//			HttpSession s = ServletActionContext.getRequest().getSession();
//			s.setAttribute("sessionName",Object);
//			方法二:实现SessionAware接口方法
			sessionMap.put("user", user);
		}
	}

//	注销
	public String logout() {
		sessionMap.put("user", null);
		return "index";	
	}
	
	//注入 session 的 map
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap ; 
		
	}
	

}
