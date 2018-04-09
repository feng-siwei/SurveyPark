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
	/**
	 * 在删除页面前检查是否是最后的一个页面,如果是禁止删除
	 * @return
	 */
	public void validateDoDeletePage() {
		long pagecount = surveyService.getPageCount(sid);
		boolean isOnePage = pagecount<2;   
		System.out.println("每次拦截结果"+isOnePage);
		if (isOnePage) {
			System.out.println("进入结果");
			//最后的页面
			addActionError("每个调查必须至少有一个页面");
		}
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
