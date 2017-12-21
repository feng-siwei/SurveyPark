package com.feng.surveypark.struts2.interceptor;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.feng.surveypark.service.RightService;
import com.feng.surveypark.util.ValidateUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 捕获url拦截器
 * 将经过的请求添加到权限数据库中
 * @author feng3
 *
 */
public class CatchUrlInterceptor implements Interceptor {

	private static final long serialVersionUID = -3828893909563384872L;

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
		//代理
		ActionProxy proxy = invocation.getProxy();
		//名字空间
		String namespace = proxy.getNamespace();
		//action名字
		String actionName = proxy.getActionName();
		if (!ValidateUtil.isValid(namespace) || "/".equals(namespace)) {
			namespace = "";
		}
		String url = namespace + "/" +actionName;
		
		ServletContext servletContext = ServletActionContext.getServletContext();
//		ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext); 
		RightService rightService = (RightService) applicationContext.getBean("rightService");
		rightService.appendRightByURL(url);
		
		return invocation.invoke();
	}

}
