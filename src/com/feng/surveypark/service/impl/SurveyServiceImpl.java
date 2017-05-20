/**
 * 
 */
package com.feng.surveypark.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feng.surveypark.dao.impl.PageDaoImpl;
import com.feng.surveypark.dao.impl.SurveyDaoImpl;
import com.feng.surveypark.domain.Page;
import com.feng.surveypark.domain.Survey;
import com.feng.surveypark.domain.User;
import com.feng.surveypark.service.SurveyService;

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
		surveyDao.sayeEntity(survey);
		pageDao.sayeEntity(page);
		return survey;
	}
	
	/**
	 * 查询自己的调查
	 */
	public List<Survey> findMySurvey(User user){
		String hql = "from Survey s where s.user.id = ?";
		return surveyDao.findEntityByHQL(hql, user.getId());
	}
}
