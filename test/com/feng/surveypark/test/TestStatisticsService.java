package com.feng.surveypark.test;




import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.feng.surveypark.service.StatisticsService;



public class TestStatisticsService {
	private static ApplicationContext ac = null;
	
	@BeforeClass
	public static void iniAC() {
		ac=  new ClassPathXmlApplicationContext("beans.xml");
	}
	
	@Test
	public void statistics() {
		StatisticsService  ss = (StatisticsService)ac.getBean("statisticsService");
		ss.statistics(19);
	}
	
}
