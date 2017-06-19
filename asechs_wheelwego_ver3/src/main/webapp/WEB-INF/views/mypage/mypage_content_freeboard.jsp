<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="./mypage_content.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

 <div class="row">
 <div class="col-sm-2"></div>
 <div class="container col-sm-8">
<table class="table table-hover">
	<thead>
		<tr>
			<th>NO</th>
			<th>Title</th>
			<th>timePosted</th>
			<th>hits</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${requestScope.contentList.boardList}" var="boardL">
			<tr>
				<td class="no">${boardL.no}</td>
				<td>${boardL.title}</td>
				<td>${boardL.timePosted}</td>
				<td>${boardL.hits}</td>
				<td>
        <button type="button" class="updateBtn btn btn-default btn-sm">
          <span class="   glyphicon glyphicon-pencil"></span>
        </button>
         <button type="button" class="deleteBtn btn btn-default btn-sm">
          <span class="glyphicon glyphicon-remove"></span>
        </button>
      </td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</div>
<div class="col-sm-2"></div>

<br>
<p class="paging text-center" style="font-size: 17px; color:black;">
   <c:set var="pb" value="${requestScope.contentList.pagingBean}"></c:set>
   <!-- 
         step2 1) 이전 페이지 그룹이 있으면 이미지 보여준다. (img/left_arrow_btn.gif)
                     페이징빈의 previousPageGroup 이용 
               2)  이미지에 이전 그룹의 마지막 페이지번호를 링크한다. 
                      hint)   startPageOfPageGroup-1 하면 됨        
    -->      
   <c:if test="${pb.previousPageGroup}">
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/showMyContentByFreeList.do?contentPageNo=${pb.startPageOfPageGroup-1}&id=${sessionScope.memberVO.id}">
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
   <c:forEach var="i" begin="${pb.startPageOfPageGroup}" end="${pb.endPageOfPageGroup}">
   <c:choose>
   <c:when test="${pb.nowPage!=i}">
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/showMyContentByFreeList.do?contentPageNo=${i}&id=${sessionScope.memberVO.id}"">${i}</a> 
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
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/showMyContentByFreeList.do?contentPageNo=${pb.endPageOfPageGroup+1}&id=${sessionScope.memberVO.id}">
   ▶<!-- <img src="img/right_arrow_btn.gif"> --></a>
   </c:if>
   </p>
   <br>
<Script type="text/javascript">
    $(document).ready(function(){
       $(".updateBtn").click(function(){
    	   var contentNo=$(this).parent().parent().children(".no").text();
			   if(confirm("등록된 게시물을 수정하시겠습니까?")){
				  alert("수정페이지로 넘어간다");
				  location.href="${pageContext.request.contextPath}/afterLogin_board/freeboard_update_form.do?no="+contentNo;
			  }
       });// click
       $(".deleteBtn").click(function(){
    	   var contentNo=$(this).parent().parent().children(".no").text();
          if(confirm("등록된 리뷰를 삭제하시겠습니까?")){
             $.ajax({
                url:"${pageContext.request.contextPath}/afterLogin_mypage/freeboardDeleteInMaypage.do",
                type:"post",
                data:"contentNo="+contentNo,
				success:function(data){
					if(data=="deleteOk"){
						alert("삭제되었습니다.");
						location.href="${pageContext.request.contextPath}/afterLogin_mypage/showMyContentByFreeList.do?id=${sessionScope.memberVO.id}";
					}
				}
             });
          }
       });//click
    });//ready

 </Script>