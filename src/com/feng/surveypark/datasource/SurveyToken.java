package com.feng.surveypark.datasource;

import com.feng.surveypark.domain.Survey;

/**
 * 调查令牌,分库同步数据用
 * 需绑定到当前线程
 * @author 冯思伟
 *
 */
public class SurveyToken {
	private Survey currentSurvey;
	private static ThreadLocal<SurveyToken> threadLocal = new ThreadLocal<SurveyToken>();	
	/**
	 * 将令牌对象绑定到当前线程
	 * @param token 令牌
	 */
	public static void bindingToken(SurveyToken token) {
		threadLocal.set(token);
	}	
	/**
	 * 从当前线程中取出令牌
	 */
	public static SurveyToken getCurrenToken() {
		return threadLocal.get();
	}	
	/**
	 * 解除令牌绑定 
	 */
	public static void unbinadToken() {
		threadLocal.remove();
	}
	
	public Survey getCurrentSurvey() {
		return currentSurvey;
	}
	public void setCurrentSurvey(Survey currentSurvey) {
		this.currentSurvey = currentSurvey;
	} 
}
