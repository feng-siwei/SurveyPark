package com.feng.surveypark.service;

import javax.jws.WebService;

import com.feng.surveypark.domain.statistics.QuestionStatisticsModel;

@WebService
public interface StatisticsService {
	public QuestionStatisticsModel statistics(Integer qid, Integer sid); 
}
