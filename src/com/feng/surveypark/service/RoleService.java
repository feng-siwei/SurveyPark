package com.feng.surveypark.service;

import java.util.List;
import java.util.Set;

import com.feng.surveypark.domain.security.Role;

public interface RoleService extends BaseService<Role> {
	/**
	 * 保存/更新角色
	 * @param role 角色
	 * @param ownRightIds 获取权限id的数组
	 */
	void saveOrUpdateRole(Role role, Integer[] ownRightIds);
	
	/**
	 * 查询不在指定范围类的角色集合
	 * @param roles 已有权限
	 * @return 未有权限
	 */
	List<Role> findRolesNotInRange(Set<Role> roles);

}
