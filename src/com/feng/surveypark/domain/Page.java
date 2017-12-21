package com.feng.surveypark.domain;

import java.util.HashSet;
import java.util.Set;
/**
 * 页面实体
 * @author 冯思伟
 *
 */
public class Page extends BaseEntity {
	private static final long serialVersionUID = -8025828543839877526L;
	
	private String title = "未命名";
	//描述
	private String description;
	//页序
	private float orderno;
	
	//调查 属性中的transient 属性,断开深度复制的链条
	private transient Survey survey;
	//问题
	private Set<Question> questions = new HashSet<Question>();
	
	//对setID进行重构以便初始化ID时可以将页序初始化
	@Override
	public void setId(Integer id) {
		this.id = id;
		if (id!=null) {
			this.orderno = id;
		}
	}
	

	public Set<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}
	public Survey getSurvey() {
		return survey;
	}
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getOrderno() {
		return orderno;
	}
	public void setOrderno(float orderno) {
		this.orderno = orderno;
	}
	
	
}
