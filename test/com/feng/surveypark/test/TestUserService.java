package com.feng.surveypark.test;


import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.feng.surveypark.domain.User;
import com.feng.surveypark.service.UserService;

public class TestUserService {
	private static ApplicationContext ac = null;
	
	@BeforeClass
	public static void iniAC() {
		ac=  new ClassPathXmlApplicationContext("beans.xml");
	}
	
	@Test
	public void insertUser() {
		UserService us = (UserService)ac.getBean("userService");
//		User user = new User();
//		user.setEmail("4916@qq.com");
//		user.setNickName("冯");
//		user.setPassword("123");
//		us.saveEntity(user);
		
		String hql = "from User u where u.email like ?";
		List<User> list = us.findEntityByHQL(hql, "%1%");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getEmail());
		}
	}
	
	
}
