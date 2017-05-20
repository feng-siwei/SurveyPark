 package com.feng.surveypark.struts2.action;

import java.util.List;


import javax.annotation.Resource;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.Survey;
import com.feng.surveypark.domain.User;
import com.feng.surveypark.service.SurveyService;


@Controller
@Scope("prototype")
public class SurveyAction extends BaseAction<Survey>implements UserAware {

	private static final long serialVersionUID = 7325567003597715623L;
	
//	//接收seesion
//	private Map<String, Object> sessionMap;
	//直接接收用户
	private User user;
	
	@Resource
	private SurveyService surveyService;
	
	private List<Survey> mySurveys ; 
	/**
	 * 新建调查
	 */
	public String newSurvey() {
		
		this.model = surveyService.newSurvey(user);
		return "designSurveyPage";
	}
	
	/**
	 * 查询我的调查
	 * 
	 */
	public String mySurvey() {  	
		mySurveys = surveyService.findMySurvey(user);
		return "mySurveyListPage";
	}

//	@Override
//	public void setSession(Map<String, Object> session) {
//		this.sessionMap = session;
//		
//	}

	public List<Survey> getMySurveys() {
		return mySurveys;
	}

	public void setMySurveys(List<Survey> mySurveys) {
		this.mySurveys = mySurveys;
	}

	//注入用户
	@Override
	public void setUser(User user) {
		this.user = user ;  
	}

	
}
