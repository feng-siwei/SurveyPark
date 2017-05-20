package com.feng.surveypark.test;


import java.lang.reflect.Method;



import javax.crypto.spec.DESedeKeySpec;



import org.junit.Test;

import antlr.BaseAST;

public class TestReflection {
	
	public TestReflection(){
		System.out.println("这是一个构造方法");
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getConn() throws Exception{
		
		System.out.println(DESedeKeySpec.class.getClassLoader());
		System.out.println(BaseAST.class.getClassLoader().getClass());
		System.out.println(BaseAST.class.getClassLoader().getClass().getName());
		
		System.out.println(this.getClass().getClassLoader());
		
		TestReflection.class.newInstance();
		
		String str = "com.feng.surveypark.test.TestDataSource";
		Class c = Class .forName(str);
		Object o = c.newInstance();
		Method [] methods = c.getMethods();
		//查看方法
		for (Method method : methods) {
			System.out.println("这是方法-"+method.getName());
		}
		//使用方法
		for (Method method : methods) {
			if (method.getName().equals("getConn")) {
				method.invoke(o);
			}
		}
		
		for (Method method : methods) {
			if (method.getName().equals("add")) {
				//获得方法参数类型
				for (Class paramType : method.getParameterTypes()) {
					System.out.println(paramType);
				}
				//获得返回值类型
				Class returnType  = method.getReturnType();
				System.out.println(returnType);
			}
		}
		
		
	} 
}
