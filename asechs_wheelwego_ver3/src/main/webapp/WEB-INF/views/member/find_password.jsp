<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="row">
  <div class="col-sm-4"></div>
  <div>
<div class="container col-xs-3">
	<div class="page-header text-center">
	<h1><small>새 비밀번호 설정</small></h1>
	</div>
  <form class="form-group" id="find_id_form">
    <div class="form-group">
      <label for="id">ID</label>
      <input type="text" class="form-control" id="id" placeholder="" name="id" autofocus>
    </div>
    <div class="form-group">
      <label for="memberName">Name</label>
      <input type="text" class="form-control" id="memberName" placeholder="" name="memberName">
    </div>
    <div class="form-group">
      <label for="phoneNumber">Phone Number</label>
      <input type="text" class="form-control" id="phoneNumber" placeholder="" name="phoneNumber">
    </div>
        <div class="form-group">
      <label for="password">새로운 비밀번호</label>
      <input type="password" class="form-control" id="password" placeholder="" name="password">
    </div>
    <input type="button" class="btn btn-primary" id="findpwBtn" value="OK" style="float: right;">
  </form>
</div>
</div>
<div class="col-sm-4"></div>
</div>

    <script type="text/javascript">
$(document).ready(function(){
   $("#findpwBtn").click(function(){

      var id=$("#id").val();
	  var name=$("#memberName").val();
	  var tel = $("#phoneNumber").val();
	  var pw = $("#password").val();
	  
	  
/*       var id=$(":input[name=id]").val();
      var name=$(":input[name=memberName]").val();
      var tel = $(":input[name=phoneNumber]").val();
      var pw = $(":input[name=password]").val(); */

       $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/forgetMemberPassword.do",            
            data:"id="+id+"&memberName="+name+"&phoneNumber="+tel+"&password="+pw, 
            success:function(result){    
               if(result==0){
                  alert("일치하는 정보가 없습니다. 다시 입력해주세요.");
                    $('form').each(function(){
                         this.reset();
                       });
                    $(":input[name=id]").focus();
               }
               else{

            	   alert("비밀번호가 성공적으로 변경되었습니다. 다시 로그인해주세요.");
            	   location.href="${pageContext.request.contextPath}/home.do";
              	 }
               }
            })//ajax                    
   }); // click
}); // ready
</script>