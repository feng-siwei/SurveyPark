package com.feng.surveypark.domain;


import com.feng.surveypark.util.StringUtil;

/**
 * 问题实体
 * @author feng3
 *
 */
public class Question extends BaseEntity{
	
	private static final long serialVersionUID = 1215819511827672133L;
	
	//拆分成Arr需要的字符串回车换行
	private final String tag ="\r\n"; 
	
	// 题型0-8
	/*
	 * 0单选横向
	 * 1 单选竖向
	 * 2多选横向
	 * 3多选竖向
	 * 4下拉框
	 * 5文本框
	 * 6矩阵单选
	 * 7矩阵多选
	 * 8矩阵下拉框
	 * 
	 */
	private int questionType;
	// 标题
	private String title;
	// 选项
	private String options;
//	private String[] optionArr;
	// 其他项
	private boolean other;

	// 其他项样式:0-无 1-文本框 2-下拉列表
	private int otherStyle;
	// 其他项下拉选项
	private String otherSelectOptions;
//	private String[] otherSelectOptionArr;

	// 矩阵式行标题集
	private String matrixRowTitles;
//	private String[] matrixRowTitleArr;

	// 矩阵式列标题集
	private String matrixColTitles;
//	private String[] matrixColTitleArr;

	// 矩阵是下拉选项集
	private String matrixSelectOptions;
//	private String[] matrixSelectOptionArr;
	
	
	//建立到页面的关联关系
	//因为远程调用统计会使用到深度复制,把page切断,否则会出现懒加载
	private transient Page page;
	
	
	
	
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public int getQuestionType() {
		return questionType;
	}
	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
//		this.optionArr = StringUtil.strToArr(options, tag);		
	}
	public boolean isOther() {
		return other;
	}
	public void setOther(boolean other) {
		this.other = other;
	}
	public int getOtherStyle() {
		return otherStyle;
	}
	public void setOtherStyle(int otherStyle) {
		this.otherStyle = otherStyle;
	}
	public String getOtherSelectOptions() {
		return otherSelectOptions;
	}
	public void setOtherSelectOptions(String otherSelectOptions) {
		this.otherSelectOptions = otherSelectOptions;
//		this.otherSelectOptionArr = StringUtil.strToArr(otherSelectOptions, tag);
		
	}
	public String getMatrixRowTitles() {
		return matrixRowTitles;
	}
	public void setMatrixRowTitles(String matrixRowTitles) {
		this.matrixRowTitles = matrixRowTitles;
//		this.matrixRowTitleArr = StringUtil.strToArr(matrixRowTitles, tag);
	}
	public String getMatrixColTitles() {
		return matrixColTitles;
	}
	public void setMatrixColTitles(String matrixColTitles) {
		this.matrixColTitles = matrixColTitles;
//		this.matrixColTitleArr = StringUtil.strToArr(matrixColTitles, tag);
	}
	public String getMatrixSelectOptions() {
		return matrixSelectOptions;
	}
	public void setMatrixSelectOptions(String matrixSelectOptions) {
		this.matrixSelectOptions = matrixSelectOptions;
//		this.matrixSelectOptionArr = StringUtil.strToArr(matrixSelectOptions, tag);
	}
//	-----------------------------------------------------------------------修改了get方法
	public String[] getOptionArr() {
		return StringUtil.strToArr(options, tag);
	}
//	public void setOptionArr(String[] optionArr) {
//		
//		this.optionArr = optionArr;
//	}
	public String[] getOtherSelectOptionArr() {
		return StringUtil.strToArr(otherSelectOptions, tag);
	}
//	public void setOtherSelectOptionArr(String[] otherSelectOptionArr) {
//		this.otherSelectOptionArr = otherSelectOptionArr;
//	}
	public String[] getMatrixRowTitleArr() {
		return StringUtil.strToArr(matrixRowTitles, tag);
	}
//	public void setMatrixRowTitleArr(String[] matrixRowTitleArr) {
//		this.matrixRowTitleArr = matrixRowTitleArr;
//	}
	public String[] getMatrixColTitleArr() {
		return StringUtil.strToArr(matrixColTitles, tag);
	}
//	public void setMatrixColTitleArr(String[] matrixColTitleArr) {
//		this.matrixColTitleArr = matrixColTitleArr;
//	}
	public String[] getMatrixSelectOptionArr() {
		return StringUtil.strToArr(matrixSelectOptions, tag);
	}
//	public void setMatrixSelectOptionArr(String[] matrixSelectOptionArr) {
//		this.matrixSelectOptionArr = matrixSelectOptionArr;
//	}
	
	
	
}
