package com.feng.surveypark.util;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.feng.surveypark.service.RightService;

/**
 * 生成所有权限的工具类
 * @author feng3
 *
 */
public class GenerateAllRightsUtil {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws URISyntaxException, ClassNotFoundException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		RightService rightService = (RightService) ac.getBean("rightService");
		
		URL url = GenerateAllRightsUtil.class.getClassLoader().getSystemResource("com/feng/surveypark/struts2/action");
		System.out.println(url);
		File dir = new File(url.toURI());
		File [] files = dir.listFiles();
		//正则表达式规则
		String regEx = "^.+(Action.class)$";
		// 编译正则表达式
	    Pattern pattern = Pattern.compile(regEx);
	    
		for (File file : files) {
			Matcher matcher = pattern.matcher(file.getName());	
			if (matcher.matches() && !"BaseAction.class".equals(file.getName())) {
//				System.out.println(file.getName());
				processClass(file.getName(),rightService);
			}
		}
	}
	
	/**
	 * 处理每个类
	 * @param rightService 
	 * @throws ClassNotFoundException 
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private static void processClass(String fname, RightService rightService) throws ClassNotFoundException {
		String pkgName = "com.feng.surveypark.struts2.action";
		String simpleClassName = fname.substring(0,fname.indexOf("."));
		Class clazz = Class.forName(pkgName+"."+simpleClassName);
		Method[] methods = clazz.getDeclaredMethods();
		
		//方法名
		String mName = "";
		//返回值
		Class retType = null;
		//参数
		Class [] paramType = null;
		String url = "";
		
		for (Method m : methods) {
			mName = m.getName();
			retType = m.getReturnType();
			paramType = m.getParameterTypes();
//			返回值为字符串,参数为空,修饰符为公开
			if ((String.class.equals(retType))
					&&(!ValidateUtil.isValid(paramType))
					&&(Modifier.isPublic(m.getModifiers()))  
					) {
				if ("execute".equals(mName)) {
					url = "/"+simpleClassName;
				}else {
					url = "/"+simpleClassName+"_"+mName;					
				}
				rightService.appendRightByURL(url);
				
			}
			
		}
	}

}
