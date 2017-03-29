<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Email Check!!</title>
</head>
<body>
daum, naver, yahoo, hanmail 계정은 입력할 수 없습니다. <br/>
<form name="validation" onSubmit="return checkbae()">
이메일 주소 입력 :
<input type="text" size=18 name="emailcheck"/><br/>
<input type="submit"/>
</form>
<script language="JavaScript1.2">
var invalidaddress = new Array();
invalidaddress[0]="daum";
invalidaddress[1]="naver";
invalidaddress[2]="yahoo";
invalidaddress[3]="hanmail";

var testresults;
function checkemail(){
	var invalidcheck = 0;
	var str=document.validation.emailcheck.value;
	var filter="/^(\w+(?:\.\w+)*@((?:\w+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i";
	if(filter.test(str)) {
		var tempstring = str.split("@");
		tempstring = tempstring[1].split(".");
		for(i=0;i<invalidaddress.length;i++){
			if(tempstring[0]==invalidadress[i]) {
				invalidcheck = 1;
			}
			if(invalidcheck!=1) {
				testresults=true;
			} else {
				alert("입력 금지된 계정입니다.");
				testresults=false;
			}
		}
	} else {
		alert("이메일 주소가 유효하지 않습니다.");
		testresults=false;
	}
	return (testresults);
}

function checkbae(){
	if(document.layers||document.getElementById||document.all) {
		return checkemail();
	} else {
		return true;
	}	
}
</script>
</body>
</html>