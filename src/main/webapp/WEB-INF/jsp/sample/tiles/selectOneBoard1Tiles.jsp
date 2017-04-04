<%@ page pageEncoding="UTF-8"%>
<h1>Board View</h1>

<table border="1">
<tbody>
	<tr>
		<th>번호</th>
		<td>${vo.idx}</td>
		<th>작성자</th>
		<td>${vo.crea_id}</td>
		<th>작성일</th>
		<td>${vo.crea_dtm}</td>
	</tr>
	<tr>
		<th>제목</th>
		<td colspan="3">${vo.title}</td>
		<th>조회수</th>
		<td>${vo.hit_cnt}</td>
	</tr>
	<tr>
		<th>내용</th>
		<td colspan="5">${vo.contents}</td>
	</tr>
</tbody>
</table>
