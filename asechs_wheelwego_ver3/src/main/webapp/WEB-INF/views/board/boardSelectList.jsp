<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script>
       $(document).ready(function(){
    	   // 마우스 오버시 게시판 확인
          $("#freeboard").mouseover(function(){
           		$("#freeb").append("<font color=orange>"+"자유 게시판"+"</font>");
          })// 마우스 떠날시 게시판 확인 초기화
          $("#freeboard").mouseleave(function(){
        	  $("#freeb").text("");
          })
           $("#businessboard").mouseover(function(){
           		$("#businessb").append("<font color=orange>"+"창업 게시판"+"</font>");
          })//
          $("#businessboard").mouseleave(function(){
        	  $("#businessb").text("");
          })
           $("#qnaboard").mouseover(function(){
           		$("#qnab").append("<font color=orange>"+"Q&A 게시판"+"</font>");
          })//
          $("#qnaboard").mouseleave(function(){
        	  $("#qnab").text("");
          })
       })//ready
    </script>
<div class="container text-center">    
  <h3><font color="orange">What To Do</font></h3><br>
  <div class="row">
    <div class="col-sm-4">
      <a href="${pageContext.request.contextPath}/freeboard_list.do">
      <img src="${pageContext.request.contextPath}/resources/img/자유게시판.png" 
      class="img-rounded" style="width:80%" id="freeboard" >
      </a>
     <h3><span id="freeb"></span></h3>
    </div>
    <div class="col-sm-4"> 
      <a href="${pageContext.request.contextPath}/business_list.do">
      <img src="${pageContext.request.contextPath}/resources/img/창업게시판.png" 
      class="img-rounded" style="width:65%"  id="businessboard" >
    </a>
    <h3><span id="businessb"></span></h3>
    </div>
    <div class="col-sm-4"> 
      <a href="${pageContext.request.contextPath}/qna_list.do">
      <img src="${pageContext.request.contextPath}/resources/img/qna게시판.png" 
      class="img-rounded w3-hover-black" style="width:65%"  id="qnaboard" >       
       </a>
       <h3><span id="qnab"></span></h3>
    </div>
    </div>
  </div>
