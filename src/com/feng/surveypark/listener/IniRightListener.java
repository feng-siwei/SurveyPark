package com.feng.surveypark.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.feng.surveypark.domain.security.Right;
import com.feng.surveypark.service.RightService;

/**
 * 这是一个初始化权限map的监听器
 * @author 冯思伟
 *
 */
@SuppressWarnings("rawtypes")
@Component
public class IniRightListener implements ApplicationListener ,ServletContextAware {

	@Resource
	private RightService rightService;
	
	private ServletContext servletContext;
	
	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		// 判断是否是上下文刷新事件
		if (arg0 instanceof ContextRefreshedEvent) {
			List<Right> rights = rightService.findAllEntities();
			Map<String, Right> map = new HashMap<String,Right>();
			for (Right right : rights) {
				map.put(right.getRightUrl(), right);
			}
			//判断是否在web环境下
			if(servletContext != null){
				servletContext.setAttribute("all_rights_map", map);
				System.out.println("权限初始完成");
			}
		}

	}

	//注入servletContext 
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}

}
