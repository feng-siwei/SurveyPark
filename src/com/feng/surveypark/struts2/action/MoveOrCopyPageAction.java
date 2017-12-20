package com.feng.surveypark.struts2.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.Page;
import com.feng.surveypark.domain.Survey;
import com.feng.surveypark.domain.User;
import com.feng.surveypark.service.SurveyService;
/**
 * 复制页面和复制功能的Action
 * 应该属于PageAction
 * @author feng3
 *
 */
@Controller
@Scope("prototype")
public class MoveOrCopyPageAction extends BaseAction<Page> implements UserAware {
	
	private static final long serialVersionUID = 4883172366874548678L;
	
	private Integer sid;
	private Integer srcPid;
	private Integer targPid;
	//0-之前 , 1-之后
	int pos; 
	private List<Survey> surveys; 
	
	@Resource
	private SurveyService surveyService;
	private User user;
	
	
	/**
	 * 进入页面调整页面
	 * @return
	 */
	public String toSelectTargetPage() {
		this.surveys = surveyService.findSurveysWithPage(user);
		return"moveOrCopyPageListPage";
	}

	/**
	 * 页面复制和操作
	 * @return
	 */
	public String doMoveOrCopyPage() {
		surveyService.MoveOrCopyPage(srcPid,targPid,pos);
		return "designSurveyAction";
	}
	
	
//	------------------------------------------------------------------
	public Integer getSrcPid() {
		return srcPid;
	}

	public void setSrcPid(Integer srcPid) {
		this.srcPid = srcPid;
	}

	public List<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
		
	}

	public Integer getTargPid() {
		return targPid;
	}

	public void setTargPid(Integer targPid) {
		this.targPid = targPid;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
	
}
