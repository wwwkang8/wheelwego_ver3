<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>글목록</title>
<script type="text/javascript">
$(document).ready(function(){
	$("#writeBtn").click(function(){
		location.href="${pageContext.request.contextPath}/afterLogin_board/qna_write_form.do";
	})//click
})//ready
</script>
<body>
	<table class="table table-hover">
		<caption><h3>Q&A게시판 목록</h3></caption>
		<thead>
			<tr>
				<th class="no">NO</th>
				<th class="title">제목</th>
				<th class="name">아이디</th>
				<th class="date">작성일</th>
				<th class="hit">HIT</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.qnaBoardList.boardList }" var="qnaBoardList">
				<tr>
					<td>${qnaBoardList.no }</td>
					<td><a href="${pageContext.request.contextPath}/board/qna_detail_content.do?no=${qnaBoardList.no}">${qnaBoardList.title }</a></td>
					<td>${qnaBoardList.id }</td>
					<td>${qnaBoardList.timePosted }</td>
					<td>${qnaBoardList.hits }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br></br>
	<p class="paging" align="center">
	<%-- 코드를 줄이기 위해 pb 변수에 pagingBean을 담는다. --%>
	<c:set var="pb" value="${requestScope.qnaBoardList.pagingBean}"></c:set>
	<!-- 
			step2 1) 이전 페이지 그룹이 있으면 이미지 보여준다. (img/left_arrow_btn.gif)
				   		페이징빈의 previousPageGroup 이용 
				   2)  이미지에 이전 그룹의 마지막 페이지번호를 링크한다. 
				   	    hint)   startPageOfPageGroup-1 하면 됨 		 
	 -->      
	<c:if test="${pb.previousPageGroup}">
	<a href="${pageContext.request.contextPath}/qna_list.do?pageNo=${pb.startPageOfPageGroup-1}">
	<!-- <img src="img/left_arrow_btn.gif"> -->
	◀&nbsp; </a>	
	</c:if>
	<!-- step1. 1)현 페이지 그룹의 startPage부터 endPage까지 forEach 를 이용해 출력한다
				   2) 현 페이지가 아니면 링크를 걸어서 서버에 요청할 수 있도록 한다.
				      현 페이지이면 링크를 처리하지 않는다.  
				      PagingBean의 nowPage
				      jstl choose 를 이용  
				      예) <a href="list.do?pageNo=...">				   
	 -->		
	<c:forEach var="i" begin="${pb.startPageOfPageGroup}" 
	end="${pb.endPageOfPageGroup}">
	<c:choose>
	<c:when test="${pb.nowPage!=i}">
	<a href="${pageContext.request.contextPath}/qna_list.do?pageNo=${i}">${i}</a> 
	</c:when>
	<c:otherwise>
	${i}
	</c:otherwise>
	</c:choose>
	&nbsp;
	</c:forEach>	 
	<!-- 
			step3 1) 다음 페이지 그룹이 있으면 이미지(img/right_arrow_btn.gif) 보여준다. 
				   		페이징빈의 nextPageGroup 이용 
				   2)  이미지에 이전 그룹의 마지막 페이지번호를 링크한다. 
				   	    hint)   endPageOfPageGroup+1 하면 됨 		 
	 -->   
	<c:if test="${pb.nextPageGroup}">
	<a href="${pageContext.request.contextPath}/qna_list.do?pageNo=${pb.endPageOfPageGroup+1}">
	▶<!-- <img src="img/right_arrow_btn.gif"> --></a>
	</c:if>
	</p>
	<table align="right"><tr>
	<td><button type="button" class="btn btn-info" id="writeBtn" style="background-color: #2dcb73">글쓰기</button></td></tr>
	</table><br><br>
</body>
