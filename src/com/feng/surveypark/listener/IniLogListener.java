package com.feng.surveypark.listener;

import javax.annotation.Resource;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.feng.surveypark.service.LogService;
import com.feng.surveypark.util.LogUtil;

/**
 * 这是一个初始化日志表的监听器
 * @author 冯思伟
 *
 */
@SuppressWarnings("rawtypes")
@Component
public class IniLogListener implements ApplicationListener {

	@Resource
	private LogService logService;
	
	
	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		// 判断是否是上下文刷新事件
		if (arg0 instanceof ContextRefreshedEvent) {
			String sql = "create table if not exists "+LogUtil.generateLogTableName(0)+" like logs";
			logService.executeSQL(sql);
			sql= "create table if not exists "+LogUtil.generateLogTableName(1)+" like logs";
			logService.executeSQL(sql);
			sql = "create table if not exists "+LogUtil.generateLogTableName(2)+" like logs";
			logService.executeSQL(sql);
			sql = "create table if not exists "+LogUtil.generateLogTableName(-1)+" like logs";
			logService.executeSQL(sql);
			System.out.println("日志表初始化完成");
		}

	}

}
