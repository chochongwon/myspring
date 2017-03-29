<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
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
             
        </tbody>
    </table>
    
    <!-- div태그의 PAGE_NAVI 부분은 앞으로 페이징 태그가 그려질 부분이다. -->
    <div id="PAGE_NAVI"></div>
    <!-- input태그의 PAGE_INDEX 에는 현재 페이지 번호가 저장될것이다. -->
    <input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
     
    <br/>
    <a href="#this" class="btn" id="write">글쓰기</a>
     
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
        $(document).ready(function(){
            fn_selectBoardList(1); // 최초화면호출시 1페이지의 내용을 조회한다.
             
            $("#write").on("click", function(e){ //글쓰기 버튼
                e.preventDefault();
                fn_openBoardWrite();
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
			comSubmit.addParam("IDX", obj.parent().find("#IDX").val()); // addParam은 서버로 전송될 key와 value로 받는다.
			comSubmit.submit();
        }
        
        /*
        	pageNo는 호출하고자 하는 페이지 번호이다.
         */
        function fn_selectBoardList(pageNo){
            var comAjax = new ComAjax(); // common.js 의 ComAjax를 사용한다.
            comAjax.setUrl("<c:url value='/sample/selectBoardListPagingAjax.do' />");
            comAjax.setCallback("fn_selectBoardListCallback"); // Ajax 요청이 완료된 후 호출될 함수의 이름을 지정하는 함수
            comAjax.addParam("PAGE_INDEX",pageNo); // 현재 페이지번호
            comAjax.addParam("PAGE_ROW", 15); // 한 페이지에 보여줄 행(데이터)의 수
            comAjax.ajax();
        }
        /*
        	이 함수는 ajax 호출이 되고 난 후 실행되는 콜백함수로 여기서는 화면을 다시 그리는 역할을 수행한다.
        	Ajax는 기본적으로 비동기식호출이기 때문에 서버에 요청을 하고 그 결과값을 받더라도 화면의 전환이 일어나지 않는다. 
        	따라서 결과값을 받은 후, 데이터의 갱신 등을 따로 해줘야한다. 
        	이것은 submit을 할때와 다른 점으로, 화면 갱신이 일어나지 않기 때문에 JSTL등을 이용하여 목록 등을 만들수가 없다.
        	
        	여기서 data 파라미터는 서버에서 전송된 json 형식의 결과값이다. 
         */
        function fn_selectBoardListCallback(data){
            var total = data.TOTAL;
            var body = $("table>tbody");
            body.empty();
            // 조회결과가 없을경우 처리
            if(total == 0){
                var str = "<tr>" +
                                "<td colspan='4'>조회된 결과가 없습니다.</td>" +
                            "</tr>";
                body.append(str);
            }
            else{
            	/*
            		gfn_renderPaging 함수를 수행하기 위해서 파라미터를 만든다.
            		
            		Javascript에서 "var 변수명 = {} " 이렇게 선언을 하면 Object가 만들어지고, 
            		거기에 각각 key와 value 형식으로 값을 추가할 수 있다.
            		
            	 */
                var params = {
                    divId : "PAGE_NAVI",
                    pageIndex : "PAGE_INDEX",
                    totalCount : total,
                    eventName : "fn_selectBoardList"
                };
                gfn_renderPaging(params); // params 값을 이용하여 페이징 태그를 만들어 낸다.
                
                /*
                	데이터로 테이블 목록을 만든다.
                	data.list 가 서버에서 보내준 데이터이고, 
                	이를 이용해서 jQuery의 .each 함수를 사용하여 HTML 태그를 만들어준다.
                 */
                var str = "";
                $.each(data.list, function(key, value){
                    str += "<tr>" +
                                "<td>" + value.IDX + "</td>" +
                                "<td class='title'>" +
                                    "<a href='#this' name='title'>" + value.TITLE + "</a>" +
                                    "<input type='hidden' name='IDX' id='IDX' value=" + value.IDX + ">" +
                                "</td>" +
                                "<td>" + value.HIT_CNT + "</td>" +
                                "<td>" + value.CREA_DTM + "</td>" +
                            "</tr>";
                });
                body.append(str);
                
                // 새롭게 추가된 각각의 목록의 제목에 상세보기로 이동할 수 있도록 click 이벤트를 바인딩 해준다.
                $("a[name='title']").on("click", function(e){ //제목
                    e.preventDefault();
                    fn_openBoardDetail($(this));
                });
            }
        }
    </script>
</body>
</html>