package com.feng.surveypark.util;

import java.util.Calendar;

public class LogUtil {
	
	/**
	 * 动态生成表名
	 * @param offset 偏移量
	 * @return 表名
	 */
	public static String generateLogTableName(int offset){
		Calendar calendar = Calendar.getInstance(); 
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+ offset;
		int var = 0 ;
		if (month>11) {
			var =  (month / 12);
		}else if (month<0) {
			var =  (month / 12)-1;		
		}
		year = year + var;
		month = month - var *12 ;
		month++;
		return "logs_"+year+"_"+month;
	}
}
