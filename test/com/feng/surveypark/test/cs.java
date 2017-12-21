package com.feng.surveypark.test;


import com.feng.surveypark.domain.Survey;
import com.feng.surveypark.util.LogUtil;
import com.feng.surveypark.util.StringUtil;

public class cs {
	public static void main(String[] args) {
		String string  = "q12_3";
		System.out.println(string.substring(1, string.indexOf("_")));
		Survey survey = new Survey();
		System.out.println(survey);
		
		
		string = StringUtil.getDescString("User:{id:4, email:123, password:202CB962AC59075B964B07152D234B70, nickName:123, regDate:2015-11-07 20:03:28.0, superAdmin:true, }");
		System.out.println(string);
		
		System.out.println(LogUtil.generateLogTableName(0));
		System.out.println(LogUtil.generateLogTableName(1));
		System.out.println(LogUtil.generateLogTableName(12));
		System.out.println(LogUtil.generateLogTableName(13));
		System.out.println(LogUtil.generateLogTableName(14));
		System.out.println(LogUtil.generateLogTableName(-13));
		System.out.println(LogUtil.generateLogTableName(-12));
		System.out.println(LogUtil.generateLogTableName(-11));
		System.out.println(LogUtil.generateLogTableName(120));
		
		
	}
	private boolean other;
	public boolean isOther() {
		return other;
	}
	public void setOther(boolean other) {
		this.other = other;
	}
}
