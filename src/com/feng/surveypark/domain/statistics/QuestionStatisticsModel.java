package com.feng.surveypark.domain.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.feng.surveypark.domain.Question;

/**
 * 问题统计模型
 * @author feng3
 *
 */
public class QuestionStatisticsModel implements Serializable{
	private static final long serialVersionUID = -97695544781453361L;
	//问题
	private Question question ;
	//人数
	private int count;
	//选项模型
	private List<OptionStatisticsModel> osms = new ArrayList<OptionStatisticsModel>();
	
	
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<OptionStatisticsModel> getOsms() {
		return osms;
	}
	public void setOsms(List<OptionStatisticsModel> osms) {
		this.osms = osms;
	}
}
