<%@ page contentType="text/html; charset=euc-kr" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page session="false" %> 
<!DOCTYPE html> 
<head> 
 <meta http-equiv="Content-Type" content="text/html; charset=euc-kr"/> 
 <title>procedureListBoard1</title> 
</head> 
<body> 
<h1>procedureListBoard1</h1> 
<h2>${name}</h2> 
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
<br />
<button onclick="history.back();">뒤로</button>
</body>
</html>