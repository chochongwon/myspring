<%@ page contentType="text/html; charset=euc-kr" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<h1>selectListBoard1Tiles</h1>

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
		<td><a href="<c:url value='/app/tiles/selectOneBoard1Tiles.tiles?idx=${item.idx}'/>">${item.title}</a></td>
		<td>${item.contents}</td>
		<td>${item.hit_cnt}</td>
		<td>${item.del_gb}</td>
		<td>${item.crea_dtm}</td>
		<td>${item.crea_id}</td>
    </tr>
</c:forEach>
</tbody>
</table>
