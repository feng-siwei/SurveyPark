package com.feng.surveypark.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 自定义数据源路由器
 * @author 冯思伟
 *
 */
public class SurveyParkDateSourceRouter extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		SurveyToken token = SurveyToken.getCurrenToken();
		if (token != null) {
			int id =token.getCurrentSurvey().getId();
			//解除令牌绑定,防止下次数据链接请求路由到同一目标
			SurveyToken.unbinadToken();
			return (id % 2) == 1 ? "odd": "even" ;  
		}
		return null;
	}

}
