<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

   <!-- 호겸 게시판 글 수정하기 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<!-- Bootstrap --> 
<link href="css/bootstrap.min.css" rel="stylesheet"> 
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script> 

<script src="js/bootstrap.min.js"></script> 
<script type="text/javascript">
	$(document).ready(function(){
		$("#cancelBtn").click(function(){//취소버튼 작동
			location.href="${pageContext.request.contextPath}/board/qna_detail_content.do?no="+${requestScope.detail_qna.no};
		})
		$("input[name='file[0]']").click(function(){
			//
		})
	})//ready
</script>
<div class="panel panel-default"> 
<div class="panel-heading" style="background-color: #F6E3CE"><h3>Q&A 게시판 수정</h3></div> 

<div class="panel-body">

<div class="container"> 
<form action="qna_updateBoard.do" method="post" enctype="multipart/form-data">  
		<div class="row"> 
			<div class="col-md-3">
			<div class="form-group" style="margin-left: 15px;"> 
			<label for="name">글번호</label> 
			<input type="text" class="form-control" name="no" id="no" value="${requestScope.detail_qna.no}" readonly> 
			</div> 
			</div> 
			
			<div class="col-md-7"> 
			<div class="form-group"> 
			<label for="writer">작성자</label> 
			<input type="text" class="form-control" name="id" id="writer" value="${requestScope.name.memberName }" readonly> 
			</div> 
			</div> 
		</div> 
		
		<div class="col-md-11"> 
		<div class="form-group"> 
		<label for="title">글제목</label> 
		<input type="text" class="form-control" name ="title" id="title" value="${requestScope.detail_qna.title }" required="required"> 
		</div> </div>
		 
		<div class="col-md-11" style="margin-right: 70px;"> 
		<div class="form-group"> 
		<label for="content">글내용</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font size=2 >${requestScope.detail_qna.timePosted} &nbsp;&nbsp; 
		조회수 : ${requestScope.detail_qna.hits}</font>
		<textarea class="form-control" rows="10" name="content" id="content" required="required">${requestScope.detail_qna.content }</textarea> 
		</div></div><br><br><br><br>
		
		<div class="form-group" style="margin-left: 20px;"> 
		<%-- <c:forEach items="${requestScope.fileNameList }" var="fileNameList" begin="0" end="2"> --%>
		<label for="File">첨부파일 1</label> 
		<input type="file" name="file[0]" >
		</div>
		<%-- <img class="img-rounded" src="${pageContext.request.contextPath }/resources/img/${fileNameList.filepath}" width="300px" height="200px"> 
		</c:forEach> --%>
		<div class="form-group" style="margin-left: 20px;"> 
		<label for="File">첨부파일 2</label> 
		<input type="file" name="file[1]"> 
		</div> 
		<div class="form-group" style="margin-left: 20px;"> 
		<label for="File">첨부파일 3</label> 
		<input type="file" name="file[2]" > 
		</div><br><br>
		<!-- 버튼  -->
		<div class="center-block" style='width:400px' align="center"> 
		<input type="reset"  class="btn btn-info" value="다시작성" style="background-color: #2dcb73">&nbsp;&nbsp;
		<input type="submit"  class="btn btn-info" value="수정" style="background-color: #2dcb73">&nbsp;&nbsp;
		<input type="button"  class="btn btn-info" value="취소" id="cancelBtn" style="background-color: #2dcb73"></div> 
</form> 
</div>
</div> <!--panel end--> 
</div> 

    