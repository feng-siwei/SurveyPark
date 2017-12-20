package com.feng.surveypark.struts2.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.Page;
import com.feng.surveypark.domain.Survey;
import com.feng.surveypark.service.SurveyService;

/**
 * 
 * 页面Action
 */
@Controller
@Scope("prototype")
public class PageAction extends BaseAction<Page> {

	
	private static final long serialVersionUID = 2253845028838732260L;
	
	@Resource
	private SurveyService surveyService;
	
	private Integer sid;
	private Integer pid;
	

	
	/**
	 * 添加新的页面
	 * 
	 */
	public String toAddPage() {
		this.model = new Page();
	
		return "addPagePage";
	}
	
	/**
	 * 保存/更新页面
	 * 
	 */
	public String saveOrUpdatePage() {
		
		Survey survey = new Survey();
		
		survey.setId(sid);
		
		model.setSurvey(survey);
		surveyService.saveOrUpdatePage(model);  
		
		return"designSurveyAction";
		
		
	}
	
	/**
	 * 修改页面标题
	 */
	public String editPage() {
		this.model = surveyService.getPage(pid);
		return"editPagePage";
	}
	
	/**
	 * 删除页面
	 */
	public String deletePage() {
		surveyService.deletePage(pid);
		return"designSurveyAction";
		
	}
	
//	get,set___________________________________________________________
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

	
	
	
	
}
