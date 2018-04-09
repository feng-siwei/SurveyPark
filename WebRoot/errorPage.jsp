<<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>错误页面</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/styles.css" />'>
	</head>
	<body>
		<s:include value="/header.jsp" />
		<table>
			<tr>
				
				<img class="centered" style="max-width: 400px"  src="img/bad-luck.gif" alt="错误图片"><br/>
			</tr>
			<tr>
				<h2>诶呀,出错了~~~(╯°口°)╯(┴—┴</h2>
				<s:actionerror></s:actionerror>
			</tr>
			
			
		</table>	
	</body>
</html>		