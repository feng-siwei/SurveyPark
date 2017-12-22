<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="divOuterFrame">
	<div class="divInnerFrame">欢迎使用SurveyDoor调查系统!</div>
</div>
<div class="divWhiteLine"></div>
<div class="divNavigatorOuterFrame">
	<div class="divNavigatorInnerFrame">
		<s:a action="LoginAction_toLoginPage" namespace="/">[首页]&nbsp;</s:a>
		<s:a action="SurveyAction_newSurvey" namespace="/">[新建调查]&nbsp;</s:a>
		<s:a action="SurveyAction_mySurvey" namespace="/">[我的调查]&nbsp;</s:a>
		<s:a action="EngageSurveyAction_findAllAvailadleSurveys" namespace="/">[参与调查]&nbsp;</s:a>
		<s:a action="RegAction_toRegPage" namespace="/">[用户注册]&nbsp;</s:a>
		<s:a action="UserAuthorizeAction_findAllUsers" namespace="/">[用户授权管理]&nbsp;</s:a>
		<s:a action="RoleAction_findAllRoles" namespace="/">[角色管理]&nbsp;</s:a>
		<s:a action="RightAction_findAllRights" namespace="/">[权限管理]&nbsp;</s:a>
		<s:a action="LogAction_findAllLogs" namespace="/">[日志管理]&nbsp;</s:a>
	</div>
</div>
<div class="divWhiteLine"></div>