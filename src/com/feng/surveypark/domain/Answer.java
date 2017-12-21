package com.feng.surveypark.domain;

import java.util.Date;

/**
 * 答案实体
 * @author feng3
 *
 */
public class Answer extends BaseEntity {
	
	private static final long serialVersionUID = 2816611945284059768L;
//	选项索引
	private String answerIds;
	private String otherAnswer;
//	批次
	private String uuid;
	private Date answerTime;
//	关联字段,但不建立关联
	private Integer questionId;
	private Integer surveyId;
	
	
	public String getAnswerIds() {
		return answerIds;
	}
	public void setAnswerIds(String answerIds) {
		this.answerIds = answerIds;
	}
	public String getOtherAnswer() {
		return otherAnswer;
	}
	public void setOtherAnswer(String otherAnswer) {
		this.otherAnswer = otherAnswer;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public Integer getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}
}
