package com.feng.surveypark.advice;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.ProceedingJoinPoint;

import com.feng.surveypark.domain.Log;
import com.feng.surveypark.domain.User;
import com.feng.surveypark.service.LogService;
import com.feng.surveypark.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 日志记录器
 * @author 冯思伟
 *
 */
public class Logger {

	private LogService logService;
	/**
	 * 记录日志
	 * @param pjp
	 * @return
	 */
	//注入
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	
	public Object record(ProceedingJoinPoint pjp) {
		Log log = new Log();
		try {
//			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
//			防止空指针异常
			ActionContext actionContext = ActionContext.getContext();
			if (actionContext != null) {
				HttpServletRequest request =  (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
				if (request != null) {
					User user = (User) request.getSession().getAttribute("user");
					if (user !=null) { 
						log.setOperator("id:"+user.getId()+" 名字:"+user.getNickName());
					}
					
				}
			}
			
//			方法名
			String methodName = pjp.getSignature().getName();
			log.setOperName(methodName);
			
//			方法参数列表
			Object[] args = pjp.getArgs();
			log.setOperParams(StringUtil.arr2Str(args));
			
//			调用目标对象方法,放行
			Object ret = pjp.proceed();
			
//			成功
			log.setOperResult("success");
			
//			结果
			if (ret != null) {
				log.setResultMsg(ret.toString());
			}
			
			return ret;
		}catch (Throwable e) {
//			失败
			log.setOperResult("failure");
			log.setResultMsg(e.getMessage());
		}finally{
			logService.saveEntity(log);
		}
		return null;
		
	}
}
