package com.feng.surveypark.service;

import java.util.List;

import com.feng.surveypark.domain.Answer;
import com.feng.surveypark.domain.Page;
import com.feng.surveypark.domain.Question;
import com.feng.surveypark.domain.Survey;
import com.feng.surveypark.domain.User;
/**
 * SurveyService抽象层
 * @author feng3
 *
 */
public interface SurveyService {
	/**
	 * 新建调查
	 * @param u
	 * @return
	 */
 	public Survey newSurvey(User user);

 	/**
 	 * 查询自己调查 
 	 * @param user
 	 * @return
 	 */
	public List<Survey> findMySurvey(User user);

	/**
	 * 得到调查
	 * @param sid 调查id
	 * @return
	 */
	public Survey getSurvey(Integer sid);

	/**
	 * 得到调查,并带有所以子元素,以解决懒加载问题
	 * @param sid
	 * @return
	 */
	public Survey getSurveyChilden(Integer sid);

	/**
	 * 更新调查
	 * @param model
	 */
	public void updateSurvey(Survey model);

	/**
	 * 更新或修改页面
	 */
	public void saveOrUpdatePage(Page model);

	/**
	 * 得到页面
	 */
	public Page getPage(Integer pid);
	
	/**
	 * 新建或修改界面
	 */
	public void saveOrUpdateQuestion(Question model);
	
	/**
	 * 通过问题ID得到问题
	 */
	public Question getQuestion(Integer qid);
	
	/**
	 * 删除问题
	 */
	public void deleteQuestion(Integer qid);

	/**
	 * 删除页面
	 */
	public void deletePage(Integer pid);

	/**
	 * 删除问卷
	 */
	public void deleteSurvey(Integer sid);
	
	/**
	 * 状态转换
	 */
	public void toggleStatus(Integer sid);
	
	/**
	 * 清除答案
	 */
	public void clearAnswers(Integer sid);
	
	/**
	 * 更新数据库路径信息
	 */
	public void updateLogoPhotoPath(Integer sid, String path);
	
	/**
	 * 查找调查和页面集合
	 */
	public List<Survey> findSurveysWithPage(User user);
	
	/**
	 * 移动和复制页操作
	 * @param srcPid 操作页id
	 * @param targPid 目标页id
	 * @param pos 在目标页之前还是之后0之前,1之后
	 */
	public void MoveOrCopyPage(Integer srcPid, Integer targPid, int pos);

	/**
	 * 查询所有可以参与的调查
	 */
	public List<Survey> findAllAvailadleSurveys();
	
	/**
	 * 查询问卷第一页
	 */
	public Page getFirstPage(Integer sid);
	
	/**
	 * 通过页面ID下一页
	 */
	public Page getNextPage(Integer currPid);
	
	/**
	 * 通过页面ID获得前一页面
	 */
	public Page getPrePage(Integer currPid);

	/**
	 * 保存答案合集
	 */
	public void saveAnswers(List<Answer> Answers);

	/**
	 * 根据调查ID查询调查下的所有问题
	 */
	public List<Question> getQuestions(Integer sid);
	/**
	 * 根据调查ID查询调查下的所有答案
	 */
	public List<Answer> findAnswers(Integer sid);

}
