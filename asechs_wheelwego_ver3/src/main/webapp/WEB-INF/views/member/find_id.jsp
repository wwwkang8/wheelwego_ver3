<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
.form-control-static{
font-size: 15px;
}
</style>
<div class="page-header text-center">
	<h1><small>아이디 찾기</small></h1>
	</div>

<form id="find_id_form">
  <div class="form-group col-xs-3">
    <label for="memberName">Name</label>
    <input type="text" class="form-control" name="memberName" autofocus>
  </div>
  <div class="form-group col-xs-3">
    <label for="phoneNumber">Phone Number</label>
    <input type="text" class="form-control" name="phoneNumber">
  </div>
  <br>
  <!-- 이슈 관리
  button type="submit"으로 해서 계속 페이지가 reloading 되었음. 
  button type="submit"이 아니라 input type="button" 으로 명시해줘야 했음 -->
 <input type="button" class="btn btn-primary" id="findidBtn" value="Find ID"><br>
<a href="${pageContext.request.contextPath}/member/find_password.do">forgot Password?</a>
</form>

<div class="form-group">
      <div class="col-sm-10">
        <p class="form-control-static" id="result_id"></p>
      </div>
</div>

<script type="text/javascript">
$(document).ready(function(){
   $("#findidBtn").click(function(){
      var name=$(":input[name=memberName]").val();
      var tel=$(":input[name=phoneNumber]").val();
       $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/forgetMemberId.do",            
            data:"memberName="+name+"&phoneNumber="+tel, 
            success:function(result){    
                if(result==""){
                  alert("일치하는 정보가 없습니다. 다시 입력해주세요.");
                    $('form').each(function(){
                         this.reset();
                       });
                    $(":input[name=memberName]").focus();
               }
               else
                  $("#result_id").html("아이디는 "+ result + " 입니다. 다시 로그인해주세요.");
               }
            });//ajax                    
   }); // click
}); // ready
</script>
