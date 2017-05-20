package com.feng.surveypark.util;

import java.util.Collection;

/*
 * 校验工具类
 */
public class ValidateUtil {
	
	/*
	 * 判断字符有效性
	 */
	public static boolean isValid(String str){
		if (str == null || "".equals(str.trim())) {
			return false ; 
		}
		return true;
	}
	
	/*
	 * 判断集合有效性
	 */
	public static boolean isValid(Collection<?> col){
		if (col == null || col.isEmpty()) {
			return false;	
		}
		return true;
	}
	
}
