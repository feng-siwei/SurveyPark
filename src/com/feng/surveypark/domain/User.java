package com.feng.surveypark.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.feng.surveypark.domain.security.Right;
import com.feng.surveypark.domain.security.Role;

/*
 * 用户实体类
 */

public class User extends BaseEntity{
	private static final long serialVersionUID = -1551059638544647159L;
	
	private String email;
	private String password;
	private String nickName;
	private Date regDate = new Date();
	//角色集合
	private Set<Role> roles = new HashSet<Role>();
	//用户总权限码 需要初始值为0而非null 所以不用Long类;
	private long[] rightSum;
	//超级管理员标记位
	private boolean superAdmin;
	
	/**
	 * 计算用户权限方法
	 */
	public void calculateRightSum() {
		int pos = 0;
		long code = 0;
		for (Role role : roles) {
			//是否是超级管理员
			if ("-1".equals(role.getRoleValue())) {
				superAdmin = true;
				roles=null;
				return;
			}
			for (Right right : role.getRights()) {
				pos = right.getRightPos();
				code = right.getRightCode();
				rightSum[pos] = rightSum[pos] | code; 
			}
		}
		//设置roles为空是为了降低session的大小.
		roles = null;
		
		
	}
	
	/**
	 * 判读用户是否拥有权限
	 * @param right 查询权限
	 * @return boolean 是否拥有权限
	 */
	public boolean hasRight(Right right) {
		int pos = right.getRightPos();
		long code = right.getRightCode();
		long ret =  rightSum[pos] & code;	
		return !(ret == 0);
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	} 
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public long[] getRightSum() {
		return rightSum;
	}
	public void setRightSum(long[] rightSum) {
		this.rightSum = rightSum;
	}



	public boolean isSuperAdmin() {
		return superAdmin;
	}



	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}
	
}
