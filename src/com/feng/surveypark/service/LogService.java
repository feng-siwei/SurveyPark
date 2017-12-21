package com.feng.surveypark.service;


import java.util.List;

import com.feng.surveypark.domain.Log;

public interface LogService extends BaseService<Log> {

	/**
	 * 查询最近的日志
	 * 默认当月和上个月
	 * @return
	 */
	public List<Log> findNearestLogs();
	

}
