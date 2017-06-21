<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--  호겸 마이페이지에서 내가 쓴 게시글 보기 :수정완료 -->
<style>
.star_point_list {
	font-size: 10pt;
	color: gold;
	cursor: pointer;
}
.paging a {
	color: grey;
}
</style>
<script>
	$(document).ready(function(){
		$("#freeboardBtn").click(function(){
			location.href="${pageContext.request.contextPath}/afterLogin_mypage/showMyContentByFreeList.do?id=${sessionScope.memberVO.id}";
		});
		$("#businessBtn").click(function(){
			location.href="${pageContext.request.contextPath}/afterLogin_mypage/showMyContentBybusinessList.do?id=${sessionScope.memberVO.id}";
		});
		$("#qnaBtn").click(function(){
			location.href="${pageContext.request.contextPath}/afterLogin_mypage/showMyContentByqnaList.do?id=${sessionScope.memberVO.id}";
		});
	});//ready
</script>
<input type="hidden" value="${sessionScope.memberVO.id}" id="id">
<jsp:include page="./mypage.jsp"/><br>
<div align="center">
	<button type="button" id="freeboardBtn" class="btn btn-warning">자유게시판</button>&nbsp;&nbsp;
	<button type="button" id="businessBtn" class="btn btn-warning">창업게시판</button>	&nbsp;&nbsp;
	<button type="button" id="qnaBtn" class="btn btn-warning">QnA	게시판</button>&nbsp;&nbsp;
</div>
<br>
