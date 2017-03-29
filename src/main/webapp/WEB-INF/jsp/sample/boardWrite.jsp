<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
	<form id="frm" name="frm" enctype="multipart/form-data">
		<table class="board_view">
			<colgroup>
				<col width="15%">
				<col width="*"/>
			</colgroup>
			<caption>게시글 작성</caption>
			<tbody>
				<tr>
					<th scope="row">제목</th>
					<td><input type="text" id="TITLE" name="TITLE" class="wdp_90"></input></td>
				</tr>
				<tr>
					<td colspan="2" class="view_text">
						<textarea rows="20" cols="100" title="내용" id="CONTENTS" name="CONTENTS"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div id="fileDiv">
			<p>
				<input type="file" id="file" name="file_0">
				<a href="#this" class="btn" id="delete" name="delete">삭제</a>
			</p>
		</div>
		
		<br/><br/>
		<a href="#this" class="btn" id="addFile">파일 추가</a>
		<a href="#this" class="btn" id="write">작성하기</a>
		<a href="#this" class="btn" id="list">목록으로</a>
	</form>
     
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
		var gfv_count = 1;
		
        $(document).ready(function(){
            $("#list").on("click", function(e){ //목록 버튼
                e.preventDefault();
                fn_openBoardList();
            });
            
            $("#write").on("click", function(e){ //작성하기 버튼
                e.preventDefault();
                fn_insertBoard();
            });            
            
            $("#addFile").on("click", function(e){ //파일 추가 버튼
                e.preventDefault();
                fn_addFile();
            });
             
            $("a[name='delete']").on("click", function(e){ //삭제 버튼
                e.preventDefault();
                fn_deleteFile($(this));
            });
        });
        
        function fn_openBoardList(){
        	/*         	
				common.js 에 submit 기능을 하는 함수를 만들었는데, 그것이 ComSubmit 객체이다. 
				ComSubmit 객체는 객체가 생성될 때, 폼의 아이디가 인자값으로 들어오면 그 폼을 전송하고, 
				파라미터가 없으면 숨겨둔 폼을 이용하여 데이터를 전송하도록 구현되었다.
        	 */
		    var comSubmit = new ComSubmit();
		    /*
		    CmSubmit 객체내의 setUrl 함수를 이용하여 호출하고 싶은 주소를 입력하도록 하였다. 
			여기서 <curl 태그는 JSTL 태그로, 이 태그를 이용하여 ContextPath를 자동으로 붙이도록 하였다. 
			만약 JSTL을 이용하지 않는다면, comSubmit.setUrl("/myspring/sample/openBoardList.do"); 라고 작성하면 된다. 
			다음으로 submit 함수로 전송한다. 일반적으로 폼(form)은 다음과 같이 만든다. 
			<form id="frm" name="frm" method="post" action="/first/sample/openBoardList.do"></form>
			그 후, <input type="submit" value="전송"/>와 같은 태그를 이용하여 전송(submit)을 한다. 
			그렇지만 이 경우, 폼을 만들필요가 없는 부분에서도 폼을 만들거나, 동일한 내용을 반복해서 작성해야 하는 불편함이 있다. 
			그래서 필자는 위와같이 submit 기능을 하는 객체를 만들어서 사용한다. 
			지금 여기서는 오히려 더 불편하게 생각할수도 있는데, 프로젝트를 진행하다보면 이렇게 하는 방식의 장점을 발견할 수 있다.
		     */
		    comSubmit.setUrl("<c:url value='/sample/openBoardList.do' />");
		    comSubmit.submit();
        }
        
		function fn_insertBoard(){
		    var comSubmit = new ComSubmit("frm");
		    comSubmit.setUrl("<c:url value='/sample/insertBoard.do' />");
		    comSubmit.submit();
		}        
		
		function fn_addFile(){
			/*
				<input type='file'> 태그의 name이 동일할 경우, 서버에는 단 하나의 파일만 전송되는 문제가 발생한다. 
				따라서 gfv_count 라는 전역변수를 선언하고, 태그가 추가될때마다 그 값을 1씩 증가시켜서 name값이 
				계속 바뀌도록 하였다. 
				이 처리는 추가로 파일의 크기나 유효성 검사, 파일개수제한, 파일의 순서등이 필요하다. 
			 */
            var str = "<p><input type='file' name='file_"+(gfv_count++)+"'><a href='#this' class='btn' name='delete'>삭제</a></p>";
            $("#fileDiv").append(str);
            $("a[name='delete']").on("click", function(e){ //삭제 버튼
                e.preventDefault();
                fn_deleteFile($(this));
            });
        }
         
        function fn_deleteFile(obj){
            obj.parent().remove();
        }
    </script>
</body>
</html>