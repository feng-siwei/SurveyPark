/**
 * 
 */
package com.feng.surveypark.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feng.surveypark.dao.impl.AnswerDaoImpl;
import com.feng.surveypark.dao.impl.PageDaoImpl;
import com.feng.surveypark.dao.impl.QuestionDaoImpl;
import com.feng.surveypark.dao.impl.SurveyDaoImpl;
import com.feng.surveypark.domain.Answer;
import com.feng.surveypark.domain.Page;
import com.feng.surveypark.domain.Question;
import com.feng.surveypark.domain.Survey;
import com.feng.surveypark.domain.User;
import com.feng.surveypark.service.SurveyService;
import com.feng.surveypark.util.DataUtil;
import com.feng.surveypark.util.ValidateUtil;

/**
 * @author feng3
 *	这是一个问题的服务实现类
 */
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

	@Resource
	private SurveyDaoImpl surveyDao;
	@Resource
	private PageDaoImpl pageDao;
	@Resource
	private QuestionDaoImpl questionDao;
	@Resource
	private AnswerDaoImpl answerDao;
	
/**
 * 	新建调查
 */
	@Override
	public Survey newSurvey(User u) {
		Survey survey = new Survey();
		Page page = new Page();
//		关系关联
		page.setSurvey(survey);
		survey.getPages().add(page);
		survey.setUser(u);
//		数据库保存
		surveyDao.saveEntity(survey);
		pageDao.saveEntity(page);
		return survey;
	}
	
	/**
	 * 查询自己的调查
	 */
	@Override
	public List<Survey> findMySurvey(User user){
		String hql = "from Survey s where s.user.id = ?";
		return surveyDao.findEntityByHQL(hql, user.getId());
	}

	/**
	 * 根据调查id得到id
	 */
	@Override
	public Survey getSurvey(Integer sid) {
		return surveyDao.getEntity(sid);
	}

	/**
	 * 得到调查,并带有所以子元素,以解决懒加载问题
	 */
	@Override
	public Survey getSurveyChilden(Integer sid) {
		Survey survey = this.getSurvey(sid);
		for (Page page : survey.getPages()) {
			page.getQuestions().size();
		}
		return survey;
	}

	/**
	 * 更新问卷
	 */
	@Override
	public void updateSurvey(Survey model) {
		surveyDao.updateEntity(model);
		
	}
	/**
	 * 更新或新建页面
	 */
	@Override
	public void saveOrUpdatePage(Page model) {
		pageDao.saveOrUpdateEntity(model);
	}
	
	/**
	 * 获得页面
	 */
	@Override
	public Page getPage(Integer pid) {
		return pageDao.getEntity(pid);
	}

	/**
	 * 新建或更新问题
	 */
	@Override
	public void saveOrUpdateQuestion(Question model) {
		questionDao.saveOrUpdateEntity(model);
	}

	/**
	 * 通过问题ID得到问题
	 */
	@Override
	public Question getQuestion(Integer qid) {
		
		return questionDao.getEntity(qid);
	}

	/**
	 * 删除问题
	 */
	@Override 
	public void deleteQuestion(Integer qid) {
		//先删答案
		String hql1 = "delete from Answer a where a.questionId = ? ";
		answerDao.batchEntityByHQL(hql1, qid);
		//再删除问题
		String hql2 = "delete from Question q where q.id = ? ";
		answerDao.batchEntityByHQL(hql2, qid);
	}

	/**
	 * 删除页面
	 */
	@Override
	public void deletePage(Integer pid) {
		// 先删除答案
		String hql1 = "delete from Answer a where a.questionId "
				+ "in (select q.id from Question q where q.page.id = ? )";
		answerDao.batchEntityByHQL(hql1, pid);
		//再删除问题
		String hql2 = "delete from Question q where q.page.id = ?";
		questionDao.batchEntityByHQL(hql2, pid);
		//最后删除页面
		String hql3 = "delete from Page p where p.id = ?";
		pageDao.batchEntityByHQL(hql3, pid);
		
	}
	
	/**
	 * 删除问卷
	 */
	@Override
	public void deleteSurvey(Integer sid) {
		//删除答案
		String hql1 = "delete from Answer a where a.surveyId = ? ";
		answerDao.batchEntityByHQL(hql1, sid);
		//删除问题 hibernate支持多级链接查询,
		//但不支持两级以上链接的写操作,不能使用q.page.survey.id
		String hql2 = "delete from Question q where q.page.id "
				+ "in (select p.id from Page p where p.survey.id = ?)";
		questionDao.batchEntityByHQL(hql2, sid);
		//删除页面
		String hql3 = "delete from Page p where p.survey.id = ?";
		pageDao.batchEntityByHQL(hql3, sid);
		//删除页面
		String hql4 = "delete from Survey s where s.id = ?";
		pageDao.batchEntityByHQL(hql4, sid);
	}

	/**
	 * 状态转换
	 */
	@Override
	public void toggleStatus(Integer sid) {
		//sql语句不支持!s.closed
		Survey survey = this.getSurvey(sid);
		String hql = "update Survey s set s.closed = ? where s.id = ?";
		surveyDao.batchEntityByHQL(hql,!survey.isClosed(),sid);
	}

	/**
	 * 删除答案
	 */
	@Override
	public void clearAnswers(Integer sid) {
		String hql = "delete from Answer a where a.surveyId = ?";
		surveyDao.batchEntityByHQL(hql, sid);	
	}

		
	/**
	 * 更新数据库路径信息
	 */
	@Override
	public void updateLogoPhotoPath(Integer sid, String path) {
		String hql = "update Survey s set s.logoPhotoPath =? where s.id = ?";
		surveyDao.batchEntityByHQL(hql, path , sid);
		
	}

	/**
	 * 查找调查和页面集合
	 */
	@Override
	public List<Survey> findSurveysWithPage(User user) {
		String hql = "from Survey s where s.user.id = ?";
		List<Survey> surveys =  surveyDao.findEntityByHQL(hql, user.getId());
		//强行初始化Pages集合,解决懒加载
		for (Survey survey : surveys) {
			survey.getPages().size();
		}
		return surveys;
	}
	
	/**
	 * 移动和复制页操作
	 * @param srcPid 操作页id
	 * @param targPid 目标页id
	 * @param pos 在目标页之前还是之后0之前,1之后
	 */
	@Override
	public void MoveOrCopyPage(Integer srcPid, Integer targPid, int pos) {
		Page srcPage = pageDao.getEntity(srcPid);
		Page targPage = pageDao.getEntity(targPid);
		Survey srcSurvey = srcPage.getSurvey();
		Survey targSurvey = targPage.getSurvey();
		if (srcSurvey.getId().equals(targSurvey.getId())) {
			//移动
			setOrderno(srcPage,targPage,pos);
					
		}else {
			//复制
			//防止懒加载
			srcPage.getQuestions().size();
			Page copy = (Page) DataUtil.deeplyCopy(srcPage);
			//与新的调查问卷进行关联
			copy.setSurvey(targSurvey);
			pageDao.saveEntity(copy);
			//保存问题
			for (Question question : copy.getQuestions()) {
				questionDao.saveEntity(question);
			}
			//移动操作
			setOrderno(copy, targPage, pos);
		}
		
	}
	
	/**
	 * 设置页序,移动操作
	 * @param srcPage 操作页面
	 * @param targPage	目标页面
	 * @param pos	0之前/1之后
	 */
	private void setOrderno(Page srcPage, Page targPage, int pos) {
		if (pos == 0) {
			//之前
			if (isFirstPage(targPage)) {
				//目标页是首页处理
				srcPage.setOrderno(targPage.getOrderno() - 0.01f);
			}else {
				Page prePage = getPrePage(targPage);
				srcPage.setOrderno((prePage.getOrderno()+targPage.getOrderno())/2);
			}
		}else {
			//之后
			if (isLastPage(targPage)) {
				//目标页是尾页处理
				srcPage.setOrderno(targPage.getOrderno() + 0.01f);
			}else {
				Page nextPage = getNextPage(targPage);
				srcPage.setOrderno((targPage.getOrderno()+nextPage.getOrderno())/2);
			}
		}
		
	}

	/**
	 * 获得下一页面
	 */
	private Page getNextPage(Page targPage) {
		String hql = "from Page p "
				+ "where p.orderno > ? and p.survey.id = ? "
				+ "order by orderno asc";
		return pageDao.findEntityByHQL(hql, targPage.getOrderno(), targPage.getSurvey().getId()).get(0);
	}

	/**
	 * 获得前一页面
	 */
	private Page getPrePage(Page targPage) {
		String hql = "from Page p "
				+ "where p.orderno < ? and p.survey.id = ? "
				+ "order by orderno desc";
		return pageDao.findEntityByHQL(hql, targPage.getOrderno(), targPage.getSurvey().getId()).get(0);
	}
	
	/**
	 * 获得总页数
	 */
	public long getPageCount (Integer sid) {
		String hql = "select count(p) from Page p where p.survey.id = ?";
		return (long) surveyDao.uniqueResult(hql, sid);
	}
	
	
	/**
	 * 是否是尾页
	 */
	private boolean isLastPage(Page targPage) {
		String hql = "from Page p where p.orderno > ? and p.survey.id = ?";
		List<Page> pages = pageDao.findEntityByHQL(hql, 
				targPage.getOrderno(), targPage.getSurvey().getId()); 
		return !ValidateUtil.isValid(pages);
	}

	/**
	 * 是否是首页
	 */
	private boolean isFirstPage(Page targPage) {
		String hql = "from Page p where p.orderno < ? and p.survey.id = ?";
		List<Page> pages = pageDao.findEntityByHQL(hql, 
				targPage.getOrderno(), targPage.getSurvey().getId()); 
		return !ValidateUtil.isValid(pages);
	}

	/**
	 * 查询所有可以参与的调查
	 */
	@Override
	public List<Survey> findAllAvailadleSurveys() {
		String hql = "from Survey s where s.closed = ?";
		List<Survey>  surveys = surveyDao.findEntityByHQL(hql, false);		
		return surveys;
	}
	
	/**
	 * 查询调查首页
	 */
	@Override
	public Page getFirstPage(Integer sid) {
		String hql = "from Page p where p.survey.id = ? order by p.orderno asc";
		Page p = pageDao.findEntityByHQL(hql, sid).get(0);
		p.getQuestions().size();
		p.getSurvey().getTitle();
		return p;
	}

	/**
	 * 通过ID得到下一页
	 */
	@Override
	public Page getNextPage(Integer currPid) {
		Page currPage = pageDao.getEntity(currPid);
		currPage = this.getNextPage(currPage);
		currPage.getQuestions().size();
		return currPage;
	}

	/**
	 * 通过ID得到上一页
	 */
	@Override
	public Page getPrePage(Integer currPid) {
		Page currPage = pageDao.getEntity(currPid);
		currPage = this.getPrePage(currPage);
		currPage.getQuestions().size();
		return currPage;
	}
	
	/**
	 * 保存答案合集
	 */
	@Override
	public void saveAnswers(List<Answer> answers) {
		String uuid = UUID.randomUUID().toString();
		Date date = new Date();
		for (Answer answer : answers) {
			answer.setUuid(uuid);
			answer.setAnswerTime(date);
			answerDao.saveEntity(answer);
		}
		
	}

	/**
	 * 根据调查ID查询调查下的所有问题
	 */
	@Override
	public List<Question> getQuestions(Integer sid) {
		String hql = "from Question q where q.page.survey.id = ?";
		return questionDao.findEntityByHQL(hql, sid);
	}

	/**
	 * 根据调查ID查询调查下的所有答案
	 */
	@Override
	public List<Answer> findAnswers(Integer sid) {
		String hql = "from Answer a where a.surveyId = ? order by a.uuid";
		List<Answer> answers = answerDao.findEntityByHQL(hql, sid);		
		return answers;
	}
	
}
