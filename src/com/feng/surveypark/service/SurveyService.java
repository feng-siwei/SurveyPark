package com.feng.surveypark.service;

import java.util.List;

import com.feng.surveypark.domain.Survey;
import com.feng.surveypark.domain.User;

public interface SurveyService {
	/**
	 * 新建调查
	 * @param u
	 * @return
	 */
 	public Survey newSurvey(User user);

 	/**
 	 * 查询调查 
 	 * @param user
 	 * @return
 	 */
	public List<Survey> findMySurvey(User user);

}
