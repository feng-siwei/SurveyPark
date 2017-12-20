package com.feng.surveypark.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feng.surveypark.dao.impl.AnswerDaoImpl;
import com.feng.surveypark.dao.impl.QuestionDaoImpl;

import com.feng.surveypark.domain.Question;
import com.feng.surveypark.domain.statistics.OptionStatisticsModel;
import com.feng.surveypark.domain.statistics.QuestionStatisticsModel;
import com.feng.surveypark.service.StatisticsService;

/**
 * 统计服务类
 * @author feng3
 *
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService{

	@Resource
	private QuestionDaoImpl questionDao;
	@Resource
	private AnswerDaoImpl answerDao;
	/**
	 * 统计问题
	 * 
	 */
	@Override
	public QuestionStatisticsModel statistics(Integer qid) {
		QuestionStatisticsModel qsm = new QuestionStatisticsModel();
		Question question = questionDao.getEntity(qid);
		qsm.setQuestion(question);
		String hql = "select count(*) from Answer a where a.questionId=? ";
		int count = ((Long)answerDao.uniqueResult(hql, qid)).intValue();
		qsm.setCount(count);
		
		//统计选项
		//人数
		int optCount = 0;
		String hql1 = "select count(*) from Answer a where a.questionId=?"
				+ "and concat(',',a.answerIds,',') like ?";
		
		int qt = question.getQuestionType();
		switch (qt) {
			//非矩阵
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				String[] optArr = question.getOptionArr();
				
				OptionStatisticsModel osm = null;
				//统计每个选项人数
				for (int i = 0; i < optArr.length; i++) {
					osm = new OptionStatisticsModel();
					osm.setOptionLabel(optArr[i]);
					osm.setOptionIndex(i);
					optCount = ((Long)answerDao.uniqueResult(hql1, qid, "%,"+i+",%")).intValue();
					osm.setCount(optCount);
					qsm.getOsms().add(osm);
				}
				//对其他选项处理
				if (question.isOther()) {
					osm = new OptionStatisticsModel();
					osm.setOptionLabel("其他");
					optCount = ((Long)answerDao.uniqueResult(hql1, qid, "%other%")).intValue();
					osm.setCount(optCount);
					qsm.getOsms().add(osm);
				}
				
				break;
				
			//矩阵	
			case 6:
			case 7:
			case 8:
				//行
				String[] rows = question.getMatrixRowTitleArr();
				//列
				String[] cols = question.getMatrixColTitleArr();
				//下拉
				String[] opts = question.getMatrixSelectOptionArr();
				for (int i = 0; i < rows.length; i++) {
					for (int j = 0; j < cols.length; j++) {
						//非下拉框
						if (qt != 8) {
							osm = new OptionStatisticsModel();
							osm.setMatrixRowLabel(rows[i]);
							osm.setMatrixRowIndex(i);
							osm.setMatrixColLabel(cols[j]);
							osm.setMatrixColIndex(j);
							optCount = ((Long)answerDao.uniqueResult(hql1, qid, "%,"+ i +"_"+ j +",%")).intValue();
							osm.setCount(optCount);
							qsm.getOsms().add(osm);				
						}
						//下拉框
						else {
							for (int k = 0; k < opts.length; k++) {
								osm = new OptionStatisticsModel();
								osm.setMatrixRowLabel(rows[i]);
								osm.setMatrixRowIndex(i);
								osm.setMatrixColLabel(cols[j]);
								osm.setMatrixColIndex(j);
								osm.setMatrixSelectLabel(opts[k]);
								osm.setMatrixSelectIndex(k);
								optCount = ((Long)answerDao.uniqueResult(hql1, qid, "%,"+ i +"_"+ j +"_"+ k +",%")).intValue();
								osm.setCount(optCount);
								qsm.getOsms().add(osm);
							}				
						}
					}
					
				}
				
				break;
		}
		return qsm;
	}
	
}
