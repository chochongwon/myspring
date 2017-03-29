function gfn_isNull(str) {
    if (str == null) return true;
    if (str == "NaN") return true;
    if (new String(str).valueOf() == "undefined") return true;   
    var chkStr = new String(str);
    if( chkStr.valueOf() == "undefined" ) return true;
    if (chkStr == null) return true;   
    if (chkStr.toString().length == 0 ) return true;  
    return false;
}
 
function ComSubmit(opt_formId) {
    this.formId = gfn_isNull(opt_formId) == true ? "commonForm" : opt_formId;
    this.url = "";
     
    if(this.formId == "commonForm"){
        $("#commonForm")[0].reset();
    }
     
    this.setUrl = function setUrl(url){
        this.url = url;
    };
     
    this.addParam = function addParam(key, value){
        $("#"+this.formId).append($("<input type='hidden' name='"+key+"' id='"+key+"' value='"+value+"' >"));
    };
     
    this.submit = function submit(){
        var frm = $("#"+this.formId)[0];
        frm.action = this.url;
        frm.method = "post";
        frm.submit();  
    };
}

/*
 * ComSubmit 와 달라진점은 setCallback이라는 함수와 submit() 대신에 ajax() 라는 함수가 추가되었다.
 * 
 * setCallback은 ajax를 이용하여 데이터를 전송한 후 호출될 콜백함수의 이름을 지정하는 함수이다. 
 * Ajax는 클라이언트와 비동기적으로 수행되기 때문에 return을 받을수가 없다. 
 * 따라서 클라이언트가 서버에 어떠한 동작을 요청하고 그에 따른 결과가 다시 클라이언트측에 전달될 때 호출되는것이 콜백함수다.
 * 여기서는 setCallback이라는 함수를 이용하여 ajax 요청 후 호출될 함수의 이름을 지정하는 것이다.
 * 
 * 다음은 ajax() 함수이다. 이 함수가 실질적인 ajax 기능을 수행한다. 
 * submit의 경우 어떠한 요청을 하면 화면이 바뀌기때문에 그 안의 기능이 많지 않았지만, 
 * ajax의 경우는 설정을 해야할 게 몇가지가 있다. 
 * jQuery를 이용한 ajax는 여러가지 설정할 수 있는데, 여기서는 간단히 몇가지만 설정을 하였다. 
 * url은 호출할 url을 의미하고, type은 POST 또는 GET 방식의 통신을 설정한다. 여기서는 그냥 POST로 지정을 하였다. 
 * 그리고 data 부분이 ajax를 이용하여 서버에 요청을 할 때 서버로 전달할 인자(Parameter)를 의미한다. 
 * 원래는 저런 방식으로 하지않고 object 형식으로 data를 지정하지만, 
 * 여기서는 addParam또는 form 자체를 전송하기 때문에 저런식으로 하였다.
 * 그 다음 async는 동기식과 비동기식의 통신방식을 의미한다. 
 * 동기식은 클라이언트 -> 서버 -> 클라이언트의 과정에서 서버의 답변이 올때까지 다른 일을 수행하지 못하고 기다리기만 하는 방식이고,
 * 비동기식은 요청을 보내고 다른일을 수행할 수 있다.
 * 여기서 나온 설정 및 다른 설정은 jQuery 공식홈페이지의 ajax 부분을 보면 자세하게 나와있다. 
 * (http://api.jquery.com/jquery.ajax/)
 * 
 */
var gfv_ajaxCallback = "";
function ComAjax(opt_formId){
    this.url = "";     
    this.formId = gfn_isNull(opt_formId) == true ? "commonForm" : opt_formId;
    this.param = "";
     
    if(this.formId == "commonForm"){
        var frm = $("#commonForm");
        if(frm.length > 0){
            frm.remove();
        }
        var str = "<form id='commonForm' name='commonForm'></form>";
        $('body').append(str);
    }
     
    this.setUrl = function setUrl(url){
        this.url = url;
    };
    
    //  setCallback은 ajax를 이용하여 데이터를 전송한 후 호출될 콜백함수의 이름을 지정하는 함수이다. 
    this.setCallback = function setCallback(callBack){
        fv_ajaxCallback = callBack;
    };
 
    this.addParam = function addParam(key,value){
        this.param = this.param + "&" + key + "=" + value;
    };
     
    this.ajax = function ajax(){
        if(this.formId != "commonForm"){
            this.param += "&" + $("#" + this.formId).serialize();
        }
        $.ajax({
            url : this.url,   
            type : "POST",  
            data : this.param,
            async : false,
            success : function(data, status) {
                if(typeof(fv_ajaxCallback) == "function"){
                    fv_ajaxCallback(data);
                }
                else {
                    eval(fv_ajaxCallback + "(data);");
                }
            }
        });
    };
}

var gfv_pageIndex = null;
var gfv_eventName = null;
/*
 * gfn_renderPaging는 페이징 태그를 작성하는 역할을 한다.
 *
 * 이 함수를 사용할 때 필요한 파라미터들
 * 
 * divId : 페이징 태그가 그려질 div
 * pageIndx : 현재 페이지 위치가 저장될 input 태그 id
 * recordCount : 페이지당 레코드 수
 * totalCount : 전체 조회 건수
 * eventName : 페이징 하단의 숫자 등의 버튼이 클릭되었을 때 호출될 함수 이름
 * 
 */
function gfn_renderPaging(params){
    var divId = params.divId; //페이징이 그려질 div id
    gfv_pageIndex = params.pageIndex; //현재 위치가 저장될 input 태그
    var totalCount = params.totalCount; //전체 조회 건수
    var currentIndex = $("#"+params.pageIndex).val(); //현재 위치
    if($("#"+params.pageIndex).length == 0 || gfn_isNull(currentIndex) == true){
        currentIndex = 1;
    }
     
    var recordCount = params.recordCount; //페이지당 레코드 수
    if(gfn_isNull(recordCount) == true){
        recordCount = 20;
    }
    var totalIndexCount = Math.ceil(totalCount / recordCount); // 전체 인덱스 수
    gfv_eventName = params.eventName;
     
    $("#"+divId).empty();
    /*
     * preStr, postStr, str 변수는 각각 맨앞으로 이동 태그,  1~10 등과 같은 인덱스 태그, 맨 뒤로 이동 태그를 담당한다.
     * 전체의 인덱스가 10을 초과할 경우 preStr 변수에는 맨앞, 앞 태그를 작성하고, 
     * 전체의 인덱스가 10 이하일 경우, 맨앞으로 이동 태그만 만들것이다.
     * 마찬가지로 맨뒤, 뒤 태그도 postStr에 작성된다. 이는 전체의 인덱스에 따라 유동적으로 결정될것이다. 
     * 그 다음 str 변수에는 인덱스가 담길것이다. 
     * 
     * 각 태그는 <a>태그를 사용해서 작성했으며, 각 태그가 클릭되었을 때 _movePage라는 함수를 호출하게 되어있다.
     * _movePage는 해당 태그가 클릭되었을 때, JSP에서 선언한 함수를 호출하게끔 구성되어있다. 
     */
    var preStr = "";
    var postStr = "";
    var str = "";
     
    var first = (parseInt((currentIndex-1) / 10) * 10) + 1;
    var last = (parseInt(totalIndexCount/10) == parseInt(currentIndex/10)) ? totalIndexCount%10 : 10;
    var prev = (parseInt((currentIndex-1)/10)*10) - 9 > 0 ? (parseInt((currentIndex-1)/10)*10) - 9 : 1;
    var next = (parseInt((currentIndex-1)/10)+1) * 10 + 1 < totalIndexCount ? (parseInt((currentIndex-1)/10)+1) * 10 + 1 : totalIndexCount;
     
    if(totalIndexCount > 10){ //전체 인덱스가 10이 넘을 경우, 맨앞, 앞 태그 작성
        preStr += "<a href='#this' class='pad_5' onclick='_movePage(1)'>[<<]</a>" +
                "<a href='#this' class='pad_5' onclick='_movePage("+prev+")'>[<]</a>";
    }
    else if(totalIndexCount <=10 && totalIndexCount > 1){ //전체 인덱스가 10보다 작을경우, 맨앞 태그 작성
        preStr += "<a href='#this' class='pad_5' onclick='_movePage(1)'>[<<]</a>";
    }
     
    if(totalIndexCount > 10){ //전체 인덱스가 10이 넘을 경우, 맨뒤, 뒤 태그 작성
        postStr += "<a href='#this' class='pad_5' onclick='_movePage("+next+")'>[>]</a>" +
                    "<a href='#this' class='pad_5' onclick='_movePage("+totalIndexCount+")'>[>>]</a>";
    }
    else if(totalIndexCount <=10 && totalIndexCount > 1){ //전체 인덱스가 10보다 작을경우, 맨뒤 태그 작성
        postStr += "<a href='#this' class='pad_5' onclick='_movePage("+totalIndexCount+")'>[>>]</a>";
    }
     
    for(var i=first; i<(first+last); i++){
        if(i != currentIndex){
            str += "<a href='#this' class='pad_5' onclick='_movePage("+i+")'>"+i+"</a>";
        }
        else{
            str += "<b><a href='#this' class='pad_5' onclick='_movePage("+i+")'>"+i+"</a></b>";
        }
    }
    $("#"+divId).append(preStr + str + postStr);
}

/*
 * 페이징 태그를 클릭하였을 경우 해당 페이지로 이동하는 역할을 한다. 
 */
function _movePage(value){
    $("#"+gfv_pageIndex).val(value);
    if(typeof(gfv_eventName) == "function"){
        gfv_eventName(value);
    }
    else {
        eval(gfv_eventName + "(value);");
    }
}
