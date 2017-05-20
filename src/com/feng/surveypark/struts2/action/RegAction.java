package com.feng.surveypark.struts2.action;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.User;
import com.feng.surveypark.service.UserService;
import com.feng.surveypark.util.ValidateUtil;

@Controller
@Scope("prototype")
public class RegAction extends BaseAction<User> {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3967733843994178084L;

	
	
	//获得确认密码
	private String confirmPassword;	
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	//注入userService
	@Resource
	private UserService userService;
	
	
	
	/*
	 * 到达注册页面
	 */
	@SkipValidation
	public String toRegPage (){
		return "regPage";
	}
	
	/*
	 * 进行注册
	 */
	public String doReg (){
		userService.saveEntity(model);
		return SUCCESS;
	}

	/*
	 * 校验
	 */
	public void validate(){
		//非空
		if (!(ValidateUtil.isValid(model.getNickName()))) {
			addFieldError("nickName", "用户名为空");
		}
		if (!(ValidateUtil.isValid(model.getPassword()))) {
			addFieldError("password", "密码为空");
		}
		if (!(ValidateUtil.isValid(model.getEmail()))) {
			addFieldError("email", "E-mail为空");
		}
	
		if (this.hasErrors()) {
			return;
		}
		
		//密码一致
		if (!model.getPassword().equals(confirmPassword)) {
			addFieldError("password", "密码不一致");
			return;
		}
		
		//邮箱注册
		boolean b = userService.isRegisted(model.getEmail());
		if (b) {
			addFieldError("email", "邮箱已被注册");
		}
	}
	
	/*
	 * 
	 */
}
