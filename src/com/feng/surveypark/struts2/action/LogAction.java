package com.feng.surveypark.struts2.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.Log;
import com.feng.surveypark.service.LogService;
import com.feng.surveypark.util.StringUtil;

@Controller
@Scope("prototype")
public class LogAction extends BaseAction<Log> {
	private static final long serialVersionUID = -1724456723847081901L;

	@Resource                                               
	private LogService logService;
	
	private List<Log> allLogs;

//	查看所有日志
	public String findAllLogs() {
		this.allLogs = logService.findNearestLogs();
		return "logListPage";
	}
	
	
	
	
	/**
	 * 超长字符处理
	 * 页面方法
	 * @return
	 */
	public String getDescString(String str) {
		return StringUtil.getDescString(str);
		
	}
	
	
//	_________________________________________________________________________
	
	public List<Log> getAllLogs() {
		return allLogs;
	}

	public void setAllLogs(List<Log> allLogs) {
		this.allLogs = allLogs;
	}
	
}
