package com.feng.surveypark.schedule;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.feng.surveypark.service.LogService;
import com.feng.surveypark.util.LogUtil;

/**
 * spring使用quartz(石英调度)动态生成日志表
 * @author 冯思伟
 *
 */
public class GenerateLogsTableTask extends QuartzJobBean {
	private LogService logService ;	
	/**
	 * 执行任务
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		String sql = "create table if not exists "+LogUtil.generateLogTableName(1)+" like logs";
		logService.executeSQL(sql); 
		String sql2 = "create table if not exists "+LogUtil.generateLogTableName(2)+" like logs";
		logService.executeSQL(sql2); 
		System.err.println("搞定任务"+LogUtil.generateLogTableName(1));
	}	
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
