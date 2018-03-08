package com.feng.surveypark.struts2.interceptor;



import org.apache.struts2.ServletActionContext;

import com.feng.surveypark.struts2.action.BaseAction;
import com.feng.surveypark.util.ValidateUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 权限过滤拦截器
 * @author feng3
 *
 */
public class RightFilterInterceptor implements Interceptor {
	private static final long serialVersionUID = 4076442132177742773L;
	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
			
	}
	@Override
	public void init() {
		// TODO 自动生成的方法存根

	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		//acrion
		BaseAction action = (BaseAction) invocation.getAction();
		//URL
		ActionProxy proxy = invocation.getProxy();
		String nameSpace = proxy.getNamespace();
		String actionName = proxy.getActionName();
		if (ValidateUtil.hasRight(nameSpace, actionName,ServletActionContext.getRequest(), action)) {
			return invocation.invoke();
		}
		return "no_tight_error";
	}

}
