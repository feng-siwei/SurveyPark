package com.feng.surveypark.domain.security;

import com.feng.surveypark.domain.BaseEntity;

/**
 * 权限实体
 * @author feng3
 *
 */
public class Right extends BaseEntity {
	private static final long serialVersionUID = -1971978562518914014L;
	private String rightName = "未命名";
	//url地址
	private String rightUrl;
	//描述
	private String rightDesc;
	/**
	 *  权限码,1<<n
	 */
	private long rightCode;
	/**
	 * 权限位,从0开始,增加权限位数
	 */
	private int rightPos; 
	
	/**
	 * 是否是公共资源
	 */
	private boolean common ;
	
	
	
	
	
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public String getRightUrl() {
		return rightUrl;
	}
	public void setRightUrl(String rightUrl) {
		this.rightUrl = rightUrl;
	}
	public String getRightDesc() {
		return rightDesc;
	}
	public void setRightDesc(String rightDesc) {
		this.rightDesc = rightDesc;
	}
	public long getRightCode() {
		return rightCode;
	}
	public void setRightCode(long rightCode) {
		this.rightCode = rightCode;
	}
	public int getRightPos() {
		return rightPos;
	}
	public void setRightPos(int rightPos) {
		this.rightPos = rightPos;
	}
	public boolean isCommon() {
		return common;
	}
	public void setCommon(boolean common) {
		this.common = common;
	}
}
