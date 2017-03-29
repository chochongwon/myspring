<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Home</title>
</head>
<body>
	<h1>Home!</h1>
	
	<form id="loginForm" name="loginForm" method="POST" action="<c:url value="/j_spring_security_check" />">
	<input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<table>
	    <tr>
	        <td><label for="loginid">id</label></td>
	        <td><input type="text" id="loginid" name="loginid" value="" /></td>
	    </tr>
	    <tr>
	        <td><label for="loginpwd">pwd</label></td>
	        <td><input type="password" id="loginloginpwdid" name="loginpwd" value="" /></td>
	    </tr>
	    <tr>
	        <td colspan="2"><input type="submit" id="loginbtn" value="로그인" /></td>
	    </tr>
	</table>
	</form>
	
	<P>  The time on the server is ${serverTime}. </P>
	<table columns=1>
	</table>
	<table columns=2>
	<tr><td><font color=blue>The basPath is: </font></td><td>${basePath}</td></tr>
	<tr><td><font color=blue>The cPath is: </font></td><td>${cPath}</td></tr>
	<tr><td><font color=blue>The sPath is: </font></td><td>${sPath}</td></tr>
	<tr><td><font color=blue>The sessionId is: </font></td><td>${sessionId}</td></tr>
	<tr><td><font color=blue>The sessionObj is: </font></td><td>${session}</td></tr>
	<tr><td><font color=blue>The nodeId is: </font></td><td>${nodeId}</td></tr>
	<tr><td><font color=blue>The server hostName is: </font></td><td>${hostName}</td></tr>
	<tr><td><font color=blue># of requests placed on session: </font></td><td>${count}</td></tr>
	</table>
     
</body>
</html>
