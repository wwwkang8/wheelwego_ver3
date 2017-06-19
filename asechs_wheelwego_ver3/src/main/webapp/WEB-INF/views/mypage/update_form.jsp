<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<c:set var="customer" value="customer"/> 

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<style>
#pwCheckView{
	font-size: 15px;
}
</style>
<script>
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;

                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postCode').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('address').value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('addressDetail').focus();
            }
        }).open();
    }
</script>

<%-- <form method="post" id="updateForm" action="${pageContext.request.contextPath}/afterLogin_mypage/updateMember.do">
   아이디 <input type="text" name="id" maxlength="10" value="${sessionScope.memberVO.id}" readonly="readonly"><br>
   비밀번호  <input type="password" name="passwordPre" maxlength="10" id = "passwordPre" onkeypress="caps_lock(event)" required="required"><span id="capsLockCheckView"></span><br>
   비밀번호체크 <input type="password" name="password" maxlength="10" id = "password" onkeypress="caps_lock(event)" required="required"><span id="pwCheckView"></span><br>
   이름 : <input type="text" name="memberName" maxlength = "10" value="${sessionScope.memberVO.memberName}" readonly="readonly"><br>
   주소 <input type="text" id="postCode" name = "postCode" placeholder="우편번호" value = "${sessionScope.memberVO.postCode}" readonly="readonly">
        <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
        <input type="text" id="address" name = "address" placeholder="주소" value = "${sessionScope.memberVO.address}" readonly="readonly">
        <input type="text" id="addressDetail" name = "addressDetail" maxlength="50" placeholder="상세주소" value = "${sessionScope.memberVO.addressDetail}" required="required"><br>     
   휴대전화 번호 <input type="text" name = "phoneNumber" value = "${sessionScope.memberVO.phoneNumber}" maxlength="11" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' required="required"><span id="phoneNumberCheckView"></span><br>
   
   <c:choose>
   	<c:when test="${sessionScope.memberVO.memberType == customer}">
   		<input type="radio" name="memberType" value="customer" checked="checked"> 일반회원&nbsp;
   		<input type="radio" name="memberType" value="seller"> 창업자회원<br>
   	</c:when>
    <c:otherwise>
   		<input type="radio" name="memberType" value="customer"> 일반회원&nbsp;
   		<input type="radio" name="memberType" value="seller" checked="checked"> 창업자회원<br>
   		사업자 번호 <input type='text' name = 'businessNumber' value = "${sessionScope.memberVO.businessNumber}" maxlength='10' onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' required='required'>"   	
   	</c:otherwise>
   </c:choose>
   <!-- <span id="businessNumberView"></span><span id='businessNumberCheckView'></span><br> -->
   <input type="submit" value="수정하기">
   <input type="button" id = "cancelBtn" value="취소하기">
</form> --%>
 <div class="col-md-12" style="overflow:auto;">
        <div class="page-header text-center">
    	    <h1><small>회원정보 수정 </small></h1>
        </div>
	<form class="form-horizontal" method="post" id="updateForm" action="${pageContext.request.contextPath}/afterLogin_mypage/updateMember.do">
        <div class="form-group">
          <label class="col-sm-3 control-label" for="inputEmail">아이디</label>
        <div class="col-sm-6">
               <input type="text"  class="form-control" name="id"   id="InputEmail"  maxlength="10" value="${sessionScope.memberVO.id}" readonly="readonly"><span id="idCheckView"></span>
        </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3 control-label" for="inputPassword">비밀번호</label>
        <div class="col-sm-6">
         <input type="password" class="form-control"  name="passwordPre" maxlength="10"  placeholder="비밀번호를 입력하세요" id = "passwordPre" onkeypress="caps_lock(event)" required="required"><span id="capsLockCheckView"></span>
        </div>
        </div>
          <div class="form-group">
              <label class="col-sm-3 control-label" for="inputPasswordCheck">비밀번호 확인</label>
             <div class="col-sm-6">
             <input type="password" class="form-control" name="password" maxlength="10" id = "password" onkeypress="caps_lock(event)" required="required">
              <p id="pwCheckView"></p>
             </div>
          </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="inputName">이름</label>
          <div class="col-sm-6">
           <input type="text" class="form-control"  name="memberName" maxlength="10" value="${sessionScope.memberVO.memberName}" readonly="readonly">
          </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="inputAddress">주소</label>
          <div class="col-sm-6">
          	<div class="col-sm-9">
            <input type="text"   class="form-control"  id="postCode" name = "postCode" placeholder="우편번호" value = "${sessionScope.memberVO.postCode}" readonly="readonly">
      		 </div>
      		 <div  class="col-sm-3">
      		 <input type="button"   class="form-control"  onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
      		 </div>
      		 <input type="text"   class="form-control"  id="address" name = "address" placeholder="주소" value = "${sessionScope.memberVO.address}" readonly="readonly"><br>
      		 <input type="text"  class="form-control"  id="addressDetail" name = "addressDetail" maxlength="50" placeholder="상세주소" value = "${sessionScope.memberVO.addressDetail}" required="required"><br>
          </div>
          </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="inputNumber">휴대폰번호</label>
              <div class="col-sm-6">
                <input type="tel"   class="form-control" name = "phoneNumber"   value = "${sessionScope.memberVO.phoneNumber}" maxlength="11" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' required="required"><span id="phoneNumberCheckView"></span>
              </div>
        </div>
        
            
	<c:choose>
   	<c:when test="${sessionScope.memberVO.memberType=='customer'}">
          <div class="form-group">
            <label class="col-sm-3 control-label" for="inputNumberCheck">회원 타입</label>
          <div class="col-sm-6">
         	   <input type="radio" name="memberType" value="customer" checked="checked" > 일반회원&nbsp;
         </div>
         </div>
   	</c:when>
    <c:otherwise>
              <div class="form-group">
            <label class="col-sm-3 control-label" for="inputNumberCheck">회원 타입</label>
              <div class="col-sm-6">
       	   	   <input type="radio" name="memberType" value="seller" checked="checked" > 창업자회원<br>
       	   	   </div>
       	   	   </div>
       	   	   <div class="form-group">
          <label class="col-sm-3 control-label" for="inputPassword">사업자 번호</label>
        	<div class="col-sm-6">
            <input type="text"   class="form-control"  id="businessNumber" name = "businessNumber" placeholder="사업자번호" value = "${sessionScope.businessNumber}" readonly="readonly"><br>
            </div>
            </div>
   	</c:otherwise>
   </c:choose>
        <div class="form-group">
          <div class="col-sm-12 text-center">
            <button class="btn btn-primary" type="submit">정보수정<i class="fa fa-check spaceLeft"></i></button>
            <button class="btn btn-danger" type="button" id="cancelBtn">수정취소<i class="fa fa-times spaceLeft"></i></button>
          </div>
        </div>
        </form>
          <hr>
        </div>
<script type="text/javascript">
 
function caps_lock(e) {
        var keyCode = 0;
        var shiftKey = false;
        keyCode = e.keyCode;
        shiftKey = e.shiftKey;
        if (((keyCode >= 65 && keyCode <= 90) && !shiftKey) || ((keyCode >= 97 && keyCode <= 122) && shiftKey)) {
           show_caps_lock();
           setTimeout("hide_caps_lock()", 1500);
           $("#capsLockCheckView").html("Caps Lock 키가 눌림").css("background","pink");
        }
    }

function show_caps_lock() {
   $("#capsLockCheckView").html("Caps Lock 키가 눌림").css("background","pink");
}

function hide_caps_lock() {
   $("#capsLockCheckView").html("");
}

function onlyNumber(event){
   event = event || window.event;
   var keyID = (event.which) ? event.which : event.keyCode;
   if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
      return;
   else
      return false;
}
function removeChar(event) {
   event = event || window.event;
   var keyID = (event.which) ? event.which : event.keyCode;
   if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
      return;
   else
      event.target.value = event.target.value.replace(/[^0-9]/g, "");
}

$(document).ready(function(){
      var checkResultPw="";
      //var memberType = "";
      var checkResultPhone = "";
      /*var checkResultBusiness = "";*/

      
      $("#updateForm").submit(function(){     
         if(checkResultPw==""){
            alert("비밀번호를 체크하세요");
            return false;
         }
         if ($("#address").val() == ""){
            alert("주소를 입력하세요.");
            return false;
         }  
      });
      
      $("#cancelBtn").click(function(){
    	  if(confirm("회원 정보 수정을 취소하시겠습니까?")){
    		  location.href="${pageContext.request.contextPath}/home.do";
    	  }
      });
      
      $(":input[name=passwordPre]").keyup(function(){
         var password = $(this).val().trim();
         
         if(password.length<4 || password.length>10){
            $("#pwCheckView").html("비밀번호는 4자이상 10자 이하입니다");
            checkResultPw="";
         }
         else
         {
            $("#pwCheckView").html("");
            checkResultPw=password;
         }
      });
  
      
      $(":input[name=password]").keyup(function(){
         var password = $(this).val().trim();
         
         if($("#passwordPre").val() != password){
            $("#pwCheckView").html("암호불일치!");
            checkResultPw="";
         }
         else{
            $("#pwCheckView").html("암호일치!");
            checkResultPw=password;
         }
      });
   
});//ready
</script>