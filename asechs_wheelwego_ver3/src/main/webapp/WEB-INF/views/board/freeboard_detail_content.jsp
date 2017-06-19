<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%-- 호겸 게시판 상세보기 --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1"> 

<!-- Bootstrap --> 
<link href="css/bootstrap.min.css" rel="stylesheet"> 
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script> 
<script src="js/bootstrap.min.js"></script>
<!-- 댓글 게시판 디자인 W3에서 끌어옴. 정호 -->
 <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
  
<script type="text/javascript">
	$(document).ready(function(){
		// 목록으로 가기
		$("#boardListBtn").click(function()	{
			location.href="${pageContext.request.contextPath}/freeboard_list.do";
		})//수정버튼
		$("#modifyBtn").click(function()	{
			location.href="${pageContext.request.contextPath}/afterLogin_board/freeboard_update_form.do?no="+${requestScope.detail_freeboard.no};
		})// 삭제버튼
		$("#deleteBtn").click(function()	{
			var info=confirm("게시물을 삭제합니까?");
			if(info){				
			location.href="${pageContext.request.contextPath}/afterLogin_board/freeboardDelete.do?no="+${requestScope.detail_freeboard.no};
			}else{
			}
		})// 삭제버튼 끝
		
		
		$(":input[name=deleteCommentBtn]").click(function(){
			var commentNo=$(this).parent().parent().find(".commentNo").text();
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/afterLogin_board/deleteFreeboardComment.do",
					data:"commentNo="+commentNo+"&contentNo=${requestScope.detail_freeboard.no}",
					success:function(data){
						location.href="${pageContext.request.contextPath}/board/freeboard_detail_content.do?no=${requestScope.detail_freeboard.no}";
					}
				});//ajax
		});//click
		
		//<input type="hidden" name="id" value="${sessionScope.memberVO.id }">
		
		$(":input[name=updateCommentBtn]").click(function(){ 	 	 	
			var commentNo=$(this).parent().parent().find(".commentNo").text();
			var contentNo=${requestScope.detail_freeboard.no};
			var sessionId=$(this).next().val();
			window.name="parentForm";
			window.open("${pageContext.request.contextPath}/afterLogin_board/freeboard_update_comment.do?commentNo="+commentNo+"&contentNo="+contentNo
					,"commentUpdateForm", "width=570, height=350, resizable=no, scrollbars=no");
		});//ajax
	})//ready
</script> 
<div class="panel panel-default" style="background-color: "> 
<div class="panel-heading" style="background-color: #F6E3CE"><h3><b>자유게시판 상세보기</b></h3></div> 
<div 	class="panel-body">
<div class="container"> 
<%-- 상세보기--%>
		<div class="row"> 
			<div class="col-md-6">
			<div class="form-group" style="margin-left: 15px"> 
			<label for="name">글번호</label> 
			<input type="text" class="form-control" value="${requestScope.detail_freeboard.no}" readonly style="background-color: white"> 
			</div> 
			</div> 
			
			<div class="col-md-5"> 
			<div class="form-group" style="margin-right: 10px"> 
			<label for="writer">작성자</label> 
			<input type="text" class="form-control" value="${requestScope.name.memberName}" readonly style="background-color: white"> 
			</div> 
			</div> 
		</div> 
		
		<%-- 글 제목   --%>
		<div class="col-md-11"> 
		<div class="form-group"> 
		<label for="title">글제목</label> 
		<input type="text" class="form-control" value="${requestScope.detail_freeboard.title}" readonly style="background-color: white"> 
		</div> 
		</div>
		 
		 <%-- 글 내용--%>
		<div class="col-md-11"> 
		<div class="form-group"> 
		<label for="content">글내용</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font size=2 >${requestScope.detail_freeboard.timePosted} &nbsp;&nbsp; 
		조회수 : ${requestScope.detail_freeboard.hits}</font>
		<textarea class="form-control" rows="10" readonly style="background-color: white">${requestScope.detail_freeboard.content }</textarea> 
		</div></div><br><br><br><br><br><br><br><br><br><br><br><br><br>
		
		<%-- 사진 들어갈 공간 임시로 올림 --%>
		<div class="form-group,col-md-8" style="margin-left: 20px"> 
		<c:forEach items="${requestScope.fileNameList }" var="fileNameList">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<img class="img-rounded" src="${pageContext.request.contextPath }/resources/img/${fileNameList.filepath}" width="300px" height="200px" >
		</c:forEach>
		</div>
		<br>
		<br>
		
		<%-- 목록, 수정 , 삭제 버튼  --%>
		<div class="center-block" style='width:400px' align="center"> 
		<button type="button" class="btn btn-info"  id="boardListBtn" style="background-color: #2dcb73">목록</button>&nbsp;&nbsp;&nbsp;
			<%-- login 적용 시 위로 두칸 올린다 --%>
			 <c:if test="${requestScope.detail_freeboard.id==sessionScope.memberVO.id}">
			 <button type="button" class="btn btn-info"  id="modifyBtn" style="background-color: #2dcb73">수정</button>&nbsp;&nbsp;&nbsp;
			<button type="button" class="btn btn-info"  id="deleteBtn" style="background-color: #2dcb73">삭제</button>
			 </c:if>
			 </div> 
</div> 
</div> <!--panel end--> 
</div> 

 <link rel="stylesheet" href="http://fontawesome.io/assets/font-awesome/css/font-awesome.css">
<div class="container">
	<div class="row">
		<h3>댓글창</h3>
	</div>
    
    <div class="row">
    
    <div class="col-md-12">
    						<div class="widget-area no-padding blank">
								<div class="status-upload">
									<form method="post" action="${pageContext.request.contextPath }/afterLogin_board/writeFreeboardComment.do" id="freeboardCommentForm">
										<textarea placeholder="댓글을 입력해주세요" name="comment" required="required"></textarea>
										<input type="hidden" name="id" value="${sessionScope.memberVO.id }">
										<input type="hidden" name="contentNo" value="${requestScope.detail_freeboard.no }">
										<button type="submit" class="btn btn-success green" id="submitBtn"><i class="fa fa-share"></i> Share</button>
									</form>
								</div><!-- Status Upload  -->
							</div><!-- Widget Area -->
						</div>
    </div>
</div>
<div class="container">
  <h2>댓글 게시판</h2>
  <p>여러분의 자유로운 의견을 올려주세요</p>            
  <table class="table table-striped">
    <thead>
      <tr>
      	<th>댓글번호</th>
        <th>글쓴이</th>
        <th>글내용</th>
        <th>작성일시</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.freeboardCommentList }" var="freeboardCommentList">
    	<tr>
    		<td class="commentNo">${freeboardCommentList.commentNo }</td>
    		<td>${freeboardCommentList.id }</td>
    		<td class="comment">${freeboardCommentList.comment }</td>
    		<td>${freeboardCommentList.timePosted }</td>
    		<c:if test="${sessionScope.memberVO.id==freeboardCommentList.id }">
    		<td>
				<input type="button" class="btn btn-success green" name="updateCommentBtn" value="수정">
				<input type="hidden" name="memberId" value="${sessionScope.memberVO.id }">
			</td>
			<td>
				<input type="button" class="btn btn-success green"  name="deleteCommentBtn" value="삭제">
			</td>
			</c:if>
    	</tr>
    </c:forEach>
    </tbody>
  </table>
</div>


<!-- 댓글창 CSS 임시적으로 여기에 배치 -->
<style>
	body{ background: #fafafa;}
.widget-area.blank {
background: none repeat scroll 0 0 rgba(0, 0, 0, 0);
-webkit-box-shadow: none;
-moz-box-shadow: none;
-ms-box-shadow: none;
-o-box-shadow: none;
box-shadow: none;
}
body .no-padding {
padding: 0;
}
.widget-area {
background-color: #fff;
-webkit-border-radius: 4px;
-moz-border-radius: 4px;
-ms-border-radius: 4px;
-o-border-radius: 4px;
border-radius: 4px;
-webkit-box-shadow: 0 0 16px rgba(0, 0, 0, 0.05);
-moz-box-shadow: 0 0 16px rgba(0, 0, 0, 0.05);
-ms-box-shadow: 0 0 16px rgba(0, 0, 0, 0.05);
-o-box-shadow: 0 0 16px rgba(0, 0, 0, 0.05);
box-shadow: 0 0 16px rgba(0, 0, 0, 0.05);
float: left;
margin-top: 30px;
padding: 25px 30px;
position: relative;
width: 100%;
}
.status-upload {
background: none repeat scroll 0 0 #f5f5f5;
-webkit-border-radius: 4px;
-moz-border-radius: 4px;
-ms-border-radius: 4px;
-o-border-radius: 4px;
border-radius: 4px;
float: left;
width: 100%;
}
.status-upload form {
float: left;
width: 100%;
}
.status-upload form textarea {
background: none repeat scroll 0 0 #fff;
border: medium none;
-webkit-border-radius: 4px 4px 0 0;
-moz-border-radius: 4px 4px 0 0;
-ms-border-radius: 4px 4px 0 0;
-o-border-radius: 4px 4px 0 0;
border-radius: 4px 4px 0 0;
color: #777777;
float: left;
font-family: Lato;
font-size: 14px;
height: 142px;
letter-spacing: 0.3px;
padding: 20px;
width: 100%;
resize:vertical;
outline:none;
border: 1px solid #F2F2F2;
}

.status-upload ul {
float: left;
list-style: none outside none;
margin: 0;
padding: 0 0 0 15px;
width: auto;
}
.status-upload ul > li {
float: left;
}
.status-upload ul > li > a {
-webkit-border-radius: 4px;
-moz-border-radius: 4px;
-ms-border-radius: 4px;
-o-border-radius: 4px;
border-radius: 4px;
color: #777777;
float: left;
font-size: 14px;
height: 30px;
line-height: 30px;
margin: 10px 0 10px 10px;
text-align: center;
-webkit-transition: all 0.4s ease 0s;
-moz-transition: all 0.4s ease 0s;
-ms-transition: all 0.4s ease 0s;
-o-transition: all 0.4s ease 0s;
transition: all 0.4s ease 0s;
width: 30px;
cursor: pointer;
}
.status-upload ul > li > a:hover {
background: none repeat scroll 0 0 #606060;
color: #fff;
}
.status-upload form button {
border: medium none;
-webkit-border-radius: 4px;
-moz-border-radius: 4px;
-ms-border-radius: 4px;
-o-border-radius: 4px;
border-radius: 4px;
color: #fff;
float: right;
font-family: Lato;
font-size: 14px;
letter-spacing: 0.3px;
margin-right: 9px;
margin-top: 9px;
padding: 6px 15px;
}
.dropdown > a > span.green:before {
border-left-color: #2dcb73;
}
.status-upload form button > i {
margin-right: 7px;
}
</style>

