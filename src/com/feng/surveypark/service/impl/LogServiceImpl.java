package com.feng.surveypark.service.impl;


import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.feng.surveypark.dao.BaseDao;
import com.feng.surveypark.domain.Log;
import com.feng.surveypark.service.LogService;
import com.feng.surveypark.util.LogUtil;
/**
 * 日志服务实现类
 * @author feng3
 *
 */
@Service("logService")
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService {
	
	@Override
	@Resource(name = "logDao")
	public void setDao(BaseDao<Log> dao) {
		super.setDao(dao);
	}

	/**
	 * 因为动态的储存日志,所以重写save方法
	 */
	@Override
	public void saveEntity(Log log) {
		String sql = "insert into "+LogUtil.generateLogTableName(0)
				+"(id,operator,opertime,opername,operparams,operresult,resultmsg) values(?,?,?,?,?,?,?)";
	
//		HttpSession session = ServletActionContext.getRequest().getSession();
//		SessionImplementor session = new sessi
		
		String uuid = UUID.randomUUID().toString();
		
		
		this.executeSQL(sql,uuid,log.getOperator(),log.getOperTime(),log.getOperName(),
				log.getOperParams(),log.getOperResult(),log.getResultMsg());
	}

	/**
	 * 查询最近的日志
	 * 默认当月和上个月
	 * @return
	 */
	@Override
	public List<Log> findNearestLogs() {
		String sql = "select * from "+LogUtil.generateLogTableName(-1)
				+" union select * from "+LogUtil.generateLogTableName(0);
		
		return this.findObjectBySQL(sql);
	}
	
	
	
	

	
}
