package com.feng.surveypark.struts2.action;

import java.text.DecimalFormat;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.Question;
import com.feng.surveypark.domain.statistics.OptionStatisticsModel;
import com.feng.surveypark.domain.statistics.QuestionStatisticsModel;
import com.feng.surveypark.service.StatisticsService;

/**
 * 复杂选项的统计处理
 * @author feng3
 *
 */
@Controller
@Scope("prototype")
public class MatrixStatisticsAction extends BaseAction<Question> {
	private static final long serialVersionUID = -5204315397896206934L;
	
	@Resource
	private StatisticsService statisticsService ;
	
	private Integer qid;
	private QuestionStatisticsModel qsm ;
	private String[] colors = {
			"#ff0000",
			"#00ff00",
			"#0000ff",
			"#ffff00",
			"#ff00ff",
			"#00ffff",
			"#ffffff"
			
	};
	
	@Override
	public String execute() throws Exception {
		this.qsm = statisticsService.statistics(qid);
		return ""+qsm.getQuestion().getQuestionType();
	}
	
	//比例计算
	public String getScale(int rindex ,int cindex) {
		int qcount = qsm.getCount();
		int ocount = 0;
		for (OptionStatisticsModel osm : qsm.getOsms()) {
			if (osm.getMatrixRowIndex() == rindex 
					&& osm.getMatrixColIndex() == cindex) {
				ocount = osm.getCount();
				break;
			}
		}
		float scale = 0;
		if (qcount !=0) {
			scale = (float)ocount/(float)qcount;
		}
		scale = scale*100;
		DecimalFormat format = new DecimalFormat();
		format.applyPattern("#,##0.00");
		return ""+ocount+"人("+format.format(scale)+"%)";		
	}
	
	//比例计算下拉框统计
		public String getScale(int rindex ,int cindex,int oindex) {
			int qcount = qsm.getCount();
			int ocount = 0;
			for (OptionStatisticsModel osm : qsm.getOsms()) {
				if (osm.getMatrixRowIndex() == rindex 
						&& osm.getMatrixColIndex() == cindex
						&& osm.getMatrixSelectIndex() == oindex) {
					ocount = osm.getCount();
					break;
				}
			}
			float scale = 0;
			if (qcount !=0) {
				scale = (float)ocount/(float)qcount;
			}
			scale = scale*100;
			DecimalFormat format = new DecimalFormat();
			format.applyPattern("#,##0.00");
			return ""+ocount+"人("+format.format(scale)+"%)";		
		}
	
		//下拉选项长度控制
		public int getPercent(int rindex ,int cindex,int oindex) {
			int qcount = qsm.getCount();
			int ocount = 0;
			for (OptionStatisticsModel osm : qsm.getOsms()) {
				if (osm.getMatrixRowIndex() == rindex 
						&& osm.getMatrixColIndex() == cindex
						&& osm.getMatrixSelectIndex() == oindex) {
					ocount = osm.getCount();
					break;
				}
			}
			float scale = 0;
			if (qcount !=0) {
				scale = (float)ocount/(float)qcount;
			}
			scale = scale*100;
			
			return (int) scale;		
		}

	
	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public QuestionStatisticsModel getQsm() {
		return qsm;
	}

	public void setQsm(QuestionStatisticsModel qsm) {
		this.qsm = qsm;
	}


	public String[] getColors() {
		return colors;
	}


	public void setColors(String[] colors) {
		this.colors = colors;
	}
	
}
