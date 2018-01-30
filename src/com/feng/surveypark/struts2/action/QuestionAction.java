package com.feng.surveypark.struts2.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.Page;
import com.feng.surveypark.domain.Question;
import com.feng.surveypark.service.SurveyService;

/**
 * 问题的Action
 * @author feng3
 *
 */
@Controller
@Scope("prototype")
public class QuestionAction extends BaseAction<Question> {

	private static final long serialVersionUID = 4588038183068431329L;
	
	@Resource
	SurveyService surveyService ;
	
	private Integer sid;
	private Integer pid;
	private Integer qid;
	
	/**
	 * 选题界面
	 */
	public String toSelectQuestionType() {
		return"SelectQuestionTypePage";
	}
	
	/**
	 * 设计问题界面
	 */
	public String toDesignQuestionPage() {
		return model.getQuestionType()+"";
	}
	
	/**
	 * 保存或更新问题
	 * @return
	 */
	public String saveOrUpdateQuestion() {
		Page page = new Page();
		page.setId(pid);
		model.setPage(page);
		surveyService.saveOrUpdateQuestion(model);
		return "designSurveyAction";
	}
	
	/**
	 * 返回修改问题对应页面
	 */
	public String editQuestion() {
		this.model = surveyService.getQuestion(qid);
		return""+model.getQuestionType();
		
	}
	
	/**
	 * 删除问题
	 * @return
	 */
	public String deleteQuestion() {
		surveyService.deleteQuestion(qid);
		return "designSurveyAction";
	}
	

	
	
//	----------------------------------------------------------
	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}
	
}
