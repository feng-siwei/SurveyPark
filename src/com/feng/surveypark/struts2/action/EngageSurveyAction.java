package com.feng.surveypark.struts2.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.datasource.SurveyToken;
import com.feng.surveypark.domain.Answer;
import com.feng.surveypark.domain.Page;
import com.feng.surveypark.domain.Survey;
import com.feng.surveypark.service.SurveyService;
import com.feng.surveypark.util.StringUtil;
import com.feng.surveypark.util.ValidateUtil;

/**
 * 参与调查Action,可以归到SurveyAction
 * @author feng3
 *
 */
@Controller
@Scope("prototype")
public class EngageSurveyAction extends BaseAction<Survey> implements ServletContextAware ,
SessionAware ,ParameterAware{

	private static final long serialVersionUID = -3885197700506030276L;
	//当前问卷
	private static final String CURRENT_SURVEY = "current_survey" ;
	//回显数据
	private static final String ALL_PARAMS_MAP = "all_params_map" ;
	
	@Resource
	private SurveyService surveyService;
	private List<Survey> surveys ; 	
	private ServletContext servletContext;
	private Map<String, Object> sessionMap;
	private Map<String, String[]> paramsMap;
	
	private Integer sid;
	private Integer currPid;
	private Page currPage;

	
	

	
	/**
	 * 返回可以参与的问卷
	 */
	public String findAllAvailadleSurveys() {
		surveys = surveyService.findAllAvailadleSurveys();
		
		return "engageSurveyLisePage";		
	}
	
	/**
	 * 参与调查入口
	 */
	public String entry() {
		this.currPage = surveyService.getFirstPage(sid);
		//将Survey放入session中去
		sessionMap.put(CURRENT_SURVEY, currPage.getSurvey());
		//为了回显数据总体map
		sessionMap.put(ALL_PARAMS_MAP, new HashMap<Integer , Map<String, String[]>>());
		return "engageSurveyPage";
	}
	
	/**
	 * 调查中的操作,上一页,下一页,提交,退出
	 */
	public String doEngageSurvey() {
		String submitName =  getSubmitName();
		//上一步
		if (submitName.endsWith("pre")) {
			//存入回显数据
			mergeParamsIntoSession();
			currPage = surveyService.getPrePage(currPid);
			return "engageSurveyPage";
		}
		//下一步
		else if (submitName.endsWith("next")) {
			//存入回显数据
			mergeParamsIntoSession();
			currPage = surveyService.getNextPage(currPid);
			return "engageSurveyPage";
		}	
		//完成
		else if (submitName.endsWith("done")) {
			//存入回显数据
			mergeParamsIntoSession();
			
			//绑定令牌到当前线程中去
			SurveyToken token = new SurveyToken();
			token.setCurrentSurvey(getCurrentSurvey());
			SurveyToken.bindingToken(token);
			
			//保存
			surveyService.saveAnswers(processAnswer());
			clearSessionDate();
			return "engageSurveyAction";
		}
		//退出
		else if (submitName.endsWith("exit")) {
			clearSessionDate();
			return "engageSurveyAction";
		}
		
		return null;
	}
	/**
	 * 答案保存
	 * 内部方法
	 */
	private List<Answer> processAnswer() {
		List<Answer> answers = new ArrayList<>();
		Answer a = null;
		String key = null;
		String[] value = null;
		//矩阵单选处理
		Map<Integer, String> matrixRadioMap = new HashMap<Integer, String>();
		for (Map<String, String[]> map : getAllParamsMapInSession().values()) {
			for (Entry<String,String[]> entry : map.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				//包含q开头的参数名称
				if (key.startsWith("q")) {
					//去除其他项和矩阵单选
					if (!key.contains("other") && !key.contains("_")) {
						a = new Answer();
						//存入选项索引
						a.setAnswerIds(StringUtil.arr2Str(value));
						//存入问题ID
						a.setQuestionId(getQid(key));
						//调查ID
						a.setSurveyId(getCurrentSurvey().getId());
						
						//处理其他项
						String [] otherValue = map.get(key+"other");
						a.setOtherAnswer(StringUtil.arr2Str(otherValue));
						
						answers.add(a);
					}
					//矩阵单选框处理
					else if (key.contains("_")) {
						Integer qid = getMatrixRadioQid(key);
						String oldValue = matrixRadioMap.get(qid);
						//处理第一个逗号
						if (oldValue == null) {
							matrixRadioMap.put(qid,StringUtil.arr2Str(value));
						}
					}
				}
			}
		}
		//将矩形框单选答案合集存入答案实体
		processMatrixRadioAnswers(answers,matrixRadioMap);
		return answers;
		
	}
	
	
	/**
	 * 将矩形框单选答案合集存入答案实
	 */
	private void processMatrixRadioAnswers(List<Answer> answers,
			Map<Integer, String> matrixRadioMap) {
		Integer key = null;
		String value = null;
		Answer answer = null;
		for (Entry<Integer, String> entry : matrixRadioMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			answer = new Answer();
			answer.setAnswerIds(value);
			answer.setQuestionId(key);
			answer.setSurveyId(getCurrentSurvey().getId());
			answers.add(answer);
		}
	}

	/**
	 * 取出矩阵单选按钮
	 * @param key (示例数据:q12_0 -> 12)
	 * @return
	 */
	private Integer getMatrixRadioQid(String key) {
		
		return Integer.parseInt(key.substring(1, key.indexOf("_")));
	}

	/**
	 * 取得当前调查
	 * @return
	 */
	private Survey getCurrentSurvey() {
		return (Survey) sessionMap.get(CURRENT_SURVEY);
	}

	/**
	 * 获得问题ID
	 */
	private Integer getQid(String key) {
		return Integer.parseInt(key.substring(1));
	}

	/**
	 * 清除数据
	 * 内部方法
	 */
	private void clearSessionDate() {
		sessionMap.remove(CURRENT_SURVEY);
		sessionMap.remove(ALL_PARAMS_MAP);
	}

	/**
	 * 将这一步的调查结果合并到session中去
	 * 内部方法
	 */
	private void mergeParamsIntoSession() {
		Map<Integer, Map<String, String[]>> allParamsMap = getAllParamsMapInSession();
		allParamsMap.put(currPid,paramsMap);
		
	}

	/**
	 * 获得回显数据
	 * 内部方法
	 */
	@SuppressWarnings("unchecked")
	private Map<Integer, Map<String, String[]>> getAllParamsMapInSession() {
		return (Map<Integer, Map<String, String[]>>) sessionMap.get(ALL_PARAMS_MAP);
	}

	/**
	 * 提交按钮的名称
	 * 内部方法
	 */
	private String getSubmitName() {
		for (String name : paramsMap.keySet()) {
			if (name.startsWith("submit")) {
				return name;
			}
		}
		return null;
	}
	/**
	 * 得到logo图片的url地址
	 * 页面上使用方法
	 */
	public String getImageUrl(String logoPhotoPath) {
		if (ValidateUtil.isValid(logoPhotoPath)) {
			String realPath = servletContext.getRealPath(logoPhotoPath);
			if (new File(realPath).exists()) {
				return servletContext.getContextPath() + logoPhotoPath;
			}
		} 
		return servletContext.getContextPath() + "/upload/surveytu.png";
	}
	
	/**
	 * 回显数据方法 选项回显
	 * 页面使用
	 * @param name 题的编号
	 * @param value 查询的值
	 * @param tag	标识
	 * @return
	 */
	public String setTag(String name,String value,String tag ) {
		Integer pid = this.currPage.getId();
		Map<String, String[]> map = this.getAllParamsMapInSession().get(pid);
		String[] oldValues = map.get(name);
		if (StringUtil.contains(oldValues,value)) {
			return tag;
		}
		return null;
		
	}
	
	/**
	 * 文本框回显
	 */
	public String setText(String name ) {
		Integer pid = this.currPage.getId();
		Map<String, String[]> map = this.getAllParamsMapInSession().get(pid);
		String[] oldValues = map.get(name);
		if (ValidateUtil.isValid(oldValues)) {
			return "value='"+oldValues[0]+"'";
		}
		return null;
	}


//	--------------------------------------------------------------------
	public List<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Page getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Page currPage) {
		this.currPage = currPage;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.sessionMap = session;
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.paramsMap = parameters;
	}

	public Integer getCurrPid() {
		return currPid;
	}

	public void setCurrPid(Integer currPid) {
		this.currPid = currPid;
	}

	

	

	
}
