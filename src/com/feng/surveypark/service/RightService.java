package com.feng.surveypark.service;

import java.util.List;
import java.util.Set;

import com.feng.surveypark.domain.security.Right;

public interface RightService extends BaseService<Right> {
	
	/**
	 * 保存更新权限
	 */
	void saveOrUpdateRight(Right model);

	/**
	 * 按照URL追加权限
	 */
	void appendRightByURL(String url);

	/**
	 *批量更改权限
	 */
	void batchUpdateRights(List<Right> allRights);
	
	/**
	 * 根据给定的id数组返回权限集合
	 */
	List<Right> findRightsInRange(Integer[] ids);
	
	/**
	 * 获得角色没有取的的权限
	 */
	List<Right> findRightsNotRole(Set<Right> rights);

	/**
	 * 得到当前最大的权限位
	 */
	int getMaxRightPos();

}
