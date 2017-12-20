package com.feng.surveypark.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.feng.surveypark.service.LogService;
import com.feng.surveypark.util.LogUtil;



public class TestLogService {
	private static ApplicationContext ac = null;
	
	@BeforeClass
	public static void iniAC() {
		ac=  new ClassPathXmlApplicationContext("beans.xml");
	}
	
	@Test
	public void statistics() {
		LogService  logService = (LogService)ac.getBean("logService");
		
		String sql = "create table if not exists "+LogUtil.generateLogTableName(1)+" like logs";
		logService.executeSQL(sql); 
		String sql2 = "create table if not exists "+LogUtil.generateLogTableName(2)+" like logs";
		logService.executeSQL(sql2); 
//		System.err.println("搞定任务"+LogUtil.generateLogTableName(0));
	}
	
}
