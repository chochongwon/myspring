<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Login</title>
</head>

<body>

<h1>Login</h1>
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
    <c:if test="${not empty param.fail}">
    <tr>
        <td colspan="2">
            <font color="red">
            <p>Your login attemtp was not successful, try again.</p>
            <p>Reason: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
            </font>
            <c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION" />
        </td>
    </tr>
    </c:if>
</table>
</form>
</body>
</html>
