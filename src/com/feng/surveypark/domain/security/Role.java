package com.feng.surveypark.domain.security;

import java.util.HashSet;
import java.util.Set;

import com.feng.surveypark.domain.BaseEntity;
/**
 * 角色实体
 * @author feng3
 *
 */
		
public class Role extends BaseEntity{
	private static final long serialVersionUID = 7790922712726783901L;
	
	private String roleName;
	
	//角色值 用于超级管理员的识别(-1)
	private String roleValue;
	//角色描述
	private String roleDesc;

	// 建立role到right多对多关联
	private Set<Right> rights = new HashSet<Right>();

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleValue() {
		return roleValue;
	}

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Set<Right> getRights() {
		return rights;
	}

	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}
}
