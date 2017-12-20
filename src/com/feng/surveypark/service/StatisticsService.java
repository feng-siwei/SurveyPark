package com.feng.surveypark.service;

import com.feng.surveypark.domain.statistics.QuestionStatisticsModel;

public interface StatisticsService {
	public QuestionStatisticsModel statistics(Integer qid); 
}
