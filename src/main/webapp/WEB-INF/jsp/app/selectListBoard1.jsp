<%@ page contentType="text/html; charset=euc-kr" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page session="false" %> 
<!DOCTYPE html> 
<head> 
 <meta http-equiv="Content-Type" content="text/html; charset=euc-kr"/> 
 <title>selectListBoard1</title> 
</head>  
<h1>selectListBoard1</h1> 
<table border="1"> 
	<thead> 
		<tr> 
			<th>번호</th> 
			<th>상위번호</th> 
			<th>제목</th> 
			<th>내용</th> 
			<th>조회수</th>
			<th>삭제구분</th> 
			<th>작성일</th>  
			<th>작성자</th>
		</tr> 
	</thead> 
	<tbody> 
	    <!-- jstl 태그 -->
		<c:forEach var="item" items="${list}"> 
			<tr> 
				<td>${item.idx}</td> 
				<td>${item.parent_idx}</td> 
				<td>${item.title}</td> 
				<td>${item.contents}</td> 
				<td>${item.hit_cnt}</td> 
				<td>${item.del_gb}</td> 
				<td>${item.crea_dtm}</td> 
				<td>${item.crea_id}</td> 
			</tr> 
		</c:forEach> 
	</tbody> 
</table>