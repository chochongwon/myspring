<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- 접속은 http://localhost:8080/spring/testFilter.do?param=<test> 로 요청하면 모든 소스가 대문자로 변경되고 <test>는 test로 변경됨 -->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" charset=UTF-8" />
	<title>Test Servlet Filter</title>
</head>
<body>

<h1>Test Servlet Filter!</h1> 
<h1><%= request.getParameter("param") %></h1>
</body>
</html>