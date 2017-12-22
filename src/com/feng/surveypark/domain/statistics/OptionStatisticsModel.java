package com.feng.surveypark.domain.statistics;

import java.io.Serializable;


/**
 * 选项统计模型
 * @author feng3
 *
 */
public class OptionStatisticsModel implements Serializable{
	private static final long serialVersionUID = 8483197066400415783L;
	//选项标签
	private String optionLabel;
	//选项索引
	private int optionIndex;
	//矩阵行标签
	private String matrixRowLabel;
	private int matrixRowIndex;
	//矩阵列标签
	private String matrixColLabel;
	private int matrixColIndex;
	//矩阵下拉
	private String matrixSelectLabel;
	private int matrixSelectIndex;

	// 回答人数
	private int count;

	public String getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}

	public int getOptionIndex() {
		return optionIndex;
	}

	public void setOptionIndex(int optionIndex) {
		this.optionIndex = optionIndex;
	}

	public String getMatrixRowLabel() {
		return matrixRowLabel;
	}

	public void setMatrixRowLabel(String matrixRowLabel) {
		this.matrixRowLabel = matrixRowLabel;
	}

	public int getMatrixRowIndex() {
		return matrixRowIndex;
	}

	public void setMatrixRowIndex(int matrixRowIndex) {
		this.matrixRowIndex = matrixRowIndex;
	}

	public String getMatrixColLabel() {
		return matrixColLabel;
	}

	public void setMatrixColLabel(String matrixColLabel) {
		this.matrixColLabel = matrixColLabel;
	}

	public int getMatrixColIndex() {
		return matrixColIndex;
	}

	public void setMatrixColIndex(int matrixColIndex) {
		this.matrixColIndex = matrixColIndex;
	}

	public String getMatrixSelectLabel() {
		return matrixSelectLabel;
	}

	public void setMatrixSelectLabel(String matrixSelectLabel) {
		this.matrixSelectLabel = matrixSelectLabel;
	}

	public int getMatrixSelectIndex() {
		return matrixSelectIndex;
	}

	public void setMatrixSelectIndex(int matrixSelectIndex) {
		this.matrixSelectIndex = matrixSelectIndex;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
