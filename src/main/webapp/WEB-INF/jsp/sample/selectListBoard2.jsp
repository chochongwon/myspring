<%@ page contentType="text/html; charset=euc-kr" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page session="false" %> 
<!DOCTYPE html> 
<head> 
 <meta http-equiv="Content-Type" content="text/html; charset=euc-kr"/> 
 <title>selectListBoard2</title> 
</head>  
<h1>selectListBoard2</h1> 
<table border="1"> 
	<thead> 
		<tr> 
			<th>번호</th> 
			<th>제목</th> 
			<th>내용</th> 
		</tr> 
	</thead> 
	<tbody> 
	    <!-- jstl 태그 -->
		<c:forEach var="item" items="${list}" varStatus="status"> 
			<tr> 
				<td>${item.NUM}</td> 
				<td>${item.TITLE}</td> 
				<td>${item.DESCRIPTION}</td> 
			</tr> 
		</c:forEach> 
	</tbody> 
</table>