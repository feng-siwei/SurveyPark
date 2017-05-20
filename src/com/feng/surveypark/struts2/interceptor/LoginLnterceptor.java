package com.feng.surveypark.struts2.interceptor;


import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.feng.surveypark.domain.User;
import com.feng.surveypark.struts2.action.BaseAction;
import com.feng.surveypark.struts2.action.LoginAction;
import com.feng.surveypark.struts2.action.RegAction;
import com.feng.surveypark.struts2.action.UserAware;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginLnterceptor implements Interceptor {



	/**
	 * 
	 */
	private static final long serialVersionUID = 4076442132177742773L;

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
			
	}

	@Override
	public void init() {
		// TODO 自动生成的方法存根

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		@SuppressWarnings("rawtypes")
		BaseAction action = (BaseAction) invocation.getAction();  
		//如果是登入和注册Action直接放行
		if (action instanceof LoginAction || action instanceof RegAction) {
			return invocation.invoke();
		}
		//否则验证是否登入
		else {
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user = (User) session.getAttribute("user");
			if (user==null) {
				return "login";
			}else {
				//登入成功处理
				//处理实现了UserAware接口的user注入问题(通过调用get方法,有趣)
				if (action instanceof UserAware) {
					((UserAware)action).setUser(user);
				} 
				return invocation.invoke();
			}
		}
			
//		return "login";
		
	}

}
