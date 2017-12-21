package com.feng.surveypark.test;


import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.feng.surveypark.domain.Survey;
import com.feng.surveypark.domain.User;
import com.feng.surveypark.service.SurveyService;


public class TestSurveyService {
	private static ApplicationContext ac = null;
	
	@BeforeClass
	public static void iniAC() {
		ac=  new ClassPathXmlApplicationContext("beans.xml");
	}
	
	@Test
	public void insertUser() {
		SurveyService ss = (SurveyService)ac.getBean("surveyService");
		User user  = new User();
		user.setId(4);
		List<Survey> surveys = ss.findMySurvey(user);
		System.out.println(surveys.get(0));
//		ss.newSurvey(user);
	}
	
	@Test
	public void getSurvey() {
		SurveyService ss = (SurveyService)ac.getBean("surveyService");
		ss.getSurvey(12);
		System.out.println(ss.getSurvey(12).getMaxOrderno());
		
	}
	
}
