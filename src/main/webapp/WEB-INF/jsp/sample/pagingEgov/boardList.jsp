<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<!-- 전자정부 프레임워크 페이징을 위해 태그 라이브러리 추가 --> 
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
</head>
<body>
    <h2>게시판 목록</h2>
    <table class="board_list">
        <colgroup>
            <col width="10%"/>
            <col width="*"/>
            <col width="15%"/>
            <col width="20%"/>
        </colgroup>
        <thead>
            <tr>
                <th scope="col">글번호</th>
                <th scope="col">제목</th>
                <th scope="col">조회수</th>
                <th scope="col">작성일</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${fn:length(list) > 0}">
                    <c:forEach var="row" items="${list}" varStatus="status">
                        <tr>
                            <td>${row.IDX }</td>
                            <td class="title">
                                <a href="#this" name="title">${row.TITLE }</a>
                                <input type="hidden" id="IDX" value="${row.IDX }">
                            </td>
                            <td>${row.HIT_CNT }</td>
                            <td>${row.CREA_DTM }</td>
                        </tr>
                    </c:forEach> 
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="4">조회된 결과가 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>  
        </tbody>
    </table>
    
    <!--
    	이 부분이 화면에서 페이징으로 바뀔 부분이다.  
    	전자정부 프레임워크에서는 페이징을 커스텀태그를 이용하여 화면에 보여주도록 구성되어있다.
    	paginationInfo는 페이징 태그를 만들기 위해서 필요한 정보들을 의미한다. 
    	AbstractDAO에서 결과를 반환할 때, paginationInfo와 result를 반환하는데 paginationInfo가 여기서 사용되는 것이다.
    	jsFunction은 페이징 태그를 클릭했을 때 수행할 함수(fn_search)를 의미한다. 
    	페이지 실행 시 글목록이 설정한 값인 15개씩 보여지면서 하단에는 [처음][이전] 1~10 [다음][마지막] 이라는 태그가 생긴다.
     -->
    <c:if test="${not empty paginationInfo}">
        <ui:pagination paginationInfo = "${paginationInfo}" type="text" jsFunction="fn_search"/>
    </c:if>
    <!-- hidden 태그를 놓고, 현재 페이지 번호를 저장하도록 한다. -->
    <input type="hidden" id="currentPageNo" name="currentPageNo"/>
     
    <br/>
    <a href="#this" class="btn" id="write">글쓰기</a>
     
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#write").on("click", function(e){ //글쓰기 버튼
                e.preventDefault();
                fn_openBoardWrite();
            });
             
            $("a[name='title']").on("click", function(e){ //제목
                e.preventDefault();
                fn_openBoardDetail($(this));
            });
        });
         
        function fn_openBoardWrite(){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/openBoardWrite.do' />");
            comSubmit.submit();
        }
         
        function fn_openBoardDetail(obj){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/openBoardDetail.do' />");
            comSubmit.addParam("IDX", obj.parent().find("#IDX").val());
            comSubmit.submit();
        }
        
        /*
        	게시판 목록을 호출할 때 currentPageNo라는 값을 같이 전송해준다.
         */
        function fn_search(pageNo){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/openBoardListPagingEgov.do' />");
            comSubmit.addParam("currentPageNo", pageNo);
            comSubmit.submit();
        }
    </script>
</body>
</html>