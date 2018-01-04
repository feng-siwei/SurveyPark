package com.feng.surveypark.util;

public class StringUtil {
	
	
	/**
	 * 拆分字符串
	 * @param str 需要拆分的字符串
	 * @param tag 按什么拆分
	 * @return
	 */
	public static String[] strToArr(String str,String tag) {
		if(ValidateUtil.isValid(str)){
			return str.split(tag);
		}
		return null;
	}
	
	/**
	 * 查询数组中是否有值
	 * @param oldValues
	 * @param value
	 * @return
	 */
	public static boolean contains(String[] oldValues, String value) {
		if (ValidateUtil.isValid(oldValues)) {
			for (String s : oldValues) {
				if (s.equals(value)) {
					return true;
				}
				
			}
		}
		return false;
	}

	/**
	 * 将数组转换成对应字符串,使用","分隔
	 * @param value
	 * @return
	 */
	public static String arr2Str(Object[] value) {
		String temp = "";
		if (ValidateUtil.isValid(value)) {
			for (Object o : value) {
				temp = temp + o +",";
			}
			return temp.substring(0, temp.length()-1);
		}
//		Arrays.toString(value);
		return null;
	}

	/**
	 * 处理超长字符串
	 * @param str
	 * @return
	 */
	public static String getDescString(String str) {
		if (ValidateUtil.isValid(str) && str.length()>30) {
			return str.substring(0, 28)+"...";
		}
		return str;

	}
	

}
