package com.feng.surveypark.test;



import javax.sql.DataSource;



import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;




public class TestDataSource {
	

	
	@Test
	public void getConn() throws Exception{
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		DataSource ds = (DataSource) ac.getBean("dataSource");
		System.out.println(ds.getConnection());	
	
	} 
	
//	@Test
//	public Integer add(int x , int y ) {
//		int i ;
//		i=x+y;
//		return i;
//	}
}
