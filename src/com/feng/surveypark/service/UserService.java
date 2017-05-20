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

	
}
