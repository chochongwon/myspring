<%@ page contentType="text/html; charset=euc-kr" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page session="false" %> 
<!DOCTYPE html> 
<head> 
 <meta http-equiv="Content-Type" content="text/html; charset=euc-kr"/> 
 <title>mapProcedureListBoard1</title> 
</head>  
<body>
<h1>mapProcedureListBoard1</h1> 
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
	    <!-- jstl 태그 -->
		<c:forEach var="item" items="${list}"> 
			<tr> 
				<td>${item['IDX']}</td> 
				<td>${item['PARENT_IDX']}</td> 
				<td>${item['TITLE']}</td> 
				<td>${item['CONTENTS']}</td> 
				<td>${item['HIT_CNT']}</td>
				<td>${item['DEL_GB']}</td> 
				<td>${item['CREA_DTM']}</td>  
				<td>${item['CREA_ID']}</td>  
			</tr>
		</c:forEach> 
	</tbody> 
</table>
<br />
<button onclick="history.back();">뒤로</button>
</body>
</html>