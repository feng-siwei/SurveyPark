package com.feng.surveypark.struts2.action;

import java.awt.Color;
import java.awt.Font;


import javax.annotation.Resource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.datasource.SurveyToken;
import com.feng.surveypark.domain.Question;
import com.feng.surveypark.domain.statistics.OptionStatisticsModel;
import com.feng.surveypark.domain.statistics.QuestionStatisticsModel;
import com.feng.surveypark.service.StatisticsService;
import com.feng.surveypark.service.SurveyService;

/**
 * 图标生成Action
 * @author feng3
 *
 */
@Controller
@Scope("prototype")
public class ChartOutputAction extends BaseAction<Question> {
	private static final long serialVersionUID = -1426629377490493209L;

	/* 2D饼图  */
	private static final int CHARTTYPE_PIE_2D = 0;
	/* 3D饼图  */
	private static final int CHARTTYPE_PIE_3D = 1;
	/* 2D水平柱状图  */
	private static final int CHARTTYPE_BAR_2D_H = 2;
	/* 2D竖直柱状图 */
	private static final int CHARTTYPE_BAR_2D_V = 3;
	/* 3D水平柱状图 */
	private static final int CHARTTYPE_BAR_3D_H = 4;
	/* 3D竖直柱状图 */
	private static final int CHARTTYPE_BAR_3D_V = 5;
	/* 2D折线图 */
	private static final int CHARTTYPE_LINE_2D = 6;
	/* 3D折线图 */
	private static final int CHARTTYPE_LINE_3D = 7;

	
	private Integer qid;
	private Integer sid;
	
	@Resource
	private SurveyService surveyService ;
	
	//图标类型
	private int chartType; 
	
	@Resource
	private StatisticsService statisticsService;
	
	
	@Override
	public String execute() throws Exception {
		
		return SUCCESS;
	}
	

	public JFreeChart getChart() {
		JFreeChart chart = null;
		try {
			//字体设置
			Font songTi = new Font("宋体", 0, 20);
			//数据统计
			
			//获得问题项
			QuestionStatisticsModel qsm = statisticsService.statisticQuestion(qid, sid);
			
			//绑定令牌到当前线程中去
			SurveyToken token = new SurveyToken();
			token.setCurrentSurvey(surveyService.getSurvey(sid));
			SurveyToken.bindingToken(token);
			//获得答案项
			qsm = statisticsService.statisticAnswer(qid, sid, qsm);
			
			//饼图
			DefaultPieDataset pieds = null;
			//种类数据集(柱状图与折线图)
			DefaultCategoryDataset cateds = null;
			
			//构造数据集
			if (chartType<2) {
				//饼图
				pieds = new DefaultPieDataset();
				for (OptionStatisticsModel osm : qsm.getOsms()) {
					pieds.setValue(osm.getOptionLabel(), osm.getCount());
				}	
			}else {
				cateds = new DefaultCategoryDataset();
				for (OptionStatisticsModel osm : qsm.getOsms()) {
					cateds.addValue(osm.getCount(),"人数" ,osm.getOptionLabel());
				}	
			}
				
			//判断要求绘制的图形
			
			switch (chartType) {
			//2D饼图
			case CHARTTYPE_PIE_2D:
				chart = ChartFactory.createPieChart(qsm.getQuestion().getTitle(), pieds, true, false, false);
				break ;
			//3D饼图	
			case CHARTTYPE_PIE_3D:
				chart = ChartFactory.createPieChart3D(qsm.getQuestion().getTitle(), pieds, true, false, false);
				//前景色透明
				chart.getPlot().setForegroundAlpha(0.6f);
				break ;
			//2D水平柱状图	
			case CHARTTYPE_BAR_2D_H:
				chart = ChartFactory.createBarChart(qsm.getQuestion().getTitle(), "选项", "人数", cateds,
						PlotOrientation.HORIZONTAL, false, false, false);
				break ;
			//2D竖直柱状图
			case CHARTTYPE_BAR_2D_V:
				chart = ChartFactory.createBarChart(qsm.getQuestion().getTitle(), "选项", "人数", cateds,
						PlotOrientation.VERTICAL, false, false, false);
			//3D水平柱状图(bug,无法显示效果)
			case CHARTTYPE_BAR_3D_H:
				chart = ChartFactory.createBarChart3D(qsm.getQuestion().getTitle(), "选项", "人数", cateds,
						PlotOrientation.HORIZONTAL, false, false, false);
			//3D竖直柱状图
			case CHARTTYPE_BAR_3D_V:
				chart = ChartFactory.createBarChart3D(qsm.getQuestion().getTitle(), "选项", "人数", cateds,
						PlotOrientation.VERTICAL, false, false, false);
				break ;
			//2D折线图
			case CHARTTYPE_LINE_2D:
				chart = ChartFactory.createLineChart(qsm.getQuestion().getTitle(), "选项", "人数", cateds,
						PlotOrientation.VERTICAL, false, false, false);
				break ;
			//3D折线图
			case CHARTTYPE_LINE_3D:
				chart = ChartFactory.createLineChart3D(qsm.getQuestion().getTitle(), "选项", "人数", cateds,
						PlotOrientation.VERTICAL, false, false, false);
				break ;
			}
		
			//字体z中文设置
			chart.getTitle().setFont(songTi);
			
			//chart.setBackgroundImageAlpha(0.2f);
			
			//设置饼图特效
			if(chart.getPlot() instanceof PiePlot){
				//设置下方栏为中文
				chart.getLegend().setItemFont(songTi);
				//绘图区
				PiePlot pieplot = (PiePlot) chart.getPlot();
				pieplot.setLabelFont(songTi);
//				pieplot.setExplodePercent(0, 0.1);
				
				pieplot.setStartAngle(-15);
				pieplot.setDirection(Rotation.CLOCKWISE);
				pieplot.setNoDataMessage("没有数据显示");
				//pieplot.setForegroundAlpha(0.5f);
				//pieplot.setBackgroundImageAlpha(0.3f);
				
				pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("选项:{0}({1}人-{2})"));
				
			}
			
			//设置2D柱状图
			else if (chartType<4) {
				//绘图区
				CategoryPlot plot = chart.getCategoryPlot();
				//高级设置
				BarRenderer renderer = new BarRenderer();
				plot.setRenderer(renderer);
				//设置柱状图上的数字
				renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator()); 
				renderer.setBaseItemLabelsVisible(true);
				//数字颜色
				renderer.setBaseItemLabelPaint(Color.BLACK);
				
				
				chart.getCategoryPlot().getRangeAxis().setLabelFont(songTi);
				chart.getCategoryPlot().getRangeAxis().setTickLabelFont(songTi);
				chart.getCategoryPlot().getDomainAxis().setLabelFont(songTi);
				chart.getCategoryPlot().getDomainAxis().setTickLabelFont(songTi);
			}
//			3d柱状图
			else if (chartType<6) {
				//绘图区
				CategoryPlot plot = chart.getCategoryPlot();
				//高级设置
				BarRenderer3D renderer = new BarRenderer3D();
				plot.setRenderer(renderer);
				//设置柱状图上的数字
				renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator()); 
				renderer.setBaseItemLabelsVisible(true);
				//数字颜色
				renderer.setBaseItemLabelPaint(Color.BLACK);
				
				chart.getCategoryPlot().getRangeAxis().setLabelFont(songTi);
				chart.getCategoryPlot().getRangeAxis().setTickLabelFont(songTi);
				chart.getCategoryPlot().getDomainAxis().setLabelFont(songTi);
				chart.getCategoryPlot().getDomainAxis().setTickLabelFont(songTi);
			}
			
			//设置折线图效果
			else{
				chart.getCategoryPlot().getRangeAxis().setLabelFont(songTi);
				chart.getCategoryPlot().getRangeAxis().setTickLabelFont(songTi);
				chart.getCategoryPlot().getDomainAxis().setLabelFont(songTi);
				chart.getCategoryPlot().getDomainAxis().setTickLabelFont(songTi);
				
			}
			return chart;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public int getChartType() {
		return chartType;
	}

	public void setChartType(int chartType) {
		this.chartType = chartType;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
}
