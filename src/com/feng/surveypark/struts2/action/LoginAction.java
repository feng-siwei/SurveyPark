package com.feng.surveypark.struts2.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.User;
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

	//接收session 的 map
	private Map<String, Object> sessionMap;
	
	
	public String toLoginPage(){
		return "longinPage";
	}
	
	public String doLogin() {
		
		return SUCCESS;
		
	}
	
	//validate拦截器出来用@SkipValidation指定不用校验的也可以通过名字指定校验
	
	public void validateDoDoLogin() {		
		User user = userService.validateLoginInfo(model.getEmail(),model.getPassword());
		if (user == null) {
			//失败
			addActionError("邮箱或密码错误~~~");
		} else {
			//成功
//			方法一:原生方法
//			HttpSession s = ServletActionContext.getRequest().getSession();
//			s.setAttribute(arg0, arg1);
//			方法二:实现SessionAware接口方法
			sessionMap.put("user", user);
		}
	}

	//注入 session 的 map
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap ; 
		
	}
	

}
