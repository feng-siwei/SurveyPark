package com.feng.surveypark.service;

import com.feng.surveypark.domain.User;

public interface UserService extends BaseService<User> {
	
	/**
	 * 判断邮箱是否占用
	 */
	boolean isRegisted(String email);

	/**
	 * 验证登入信息
	 */
	User validateLoginInfo(String email, String password);
	
	/**
	 * 修改用户授权
	 * @param user 用户
	 * @param ownRoleIds 权限id数组
	 */
	void updateAuthorize(User user, Integer[] ownRoleIds);

	/**
	 * 清除用户授权
	 * @param userId 用户id
	 */
	void clearAuthorize(Integer userId);

	
}
