<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.star_point_list{
  font-size:10pt;
  color:gold;
  cursor:pointer;
}
.paging a {
    color: grey;
}
</style>

<jsp:include page="../mypage/mypage.jsp"/>

<br><br>

 <div class="row">
 <div class="col-sm-2"></div>
 <div class="container col-sm-8">
  <c:choose>
      <c:when test="${reviewList.reviewList!='[]'}">      
  <table class="table table-hover">
    <thead>
      <tr>
      <th></th>
        <th>FoodTruck</th>
        <th>Content</th>
        <th>Score</th>
        <th>Date</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${reviewList.reviewList}" var="reviewVO" varStatus="status">
      <tr>
        <td><input type="hidden" name="reviewNo" value="${reviewVO.reviewNo}"></td>
        <td>${reviewVO.foodtruckName}</td>
        <td>${reviewVO.reviewContent}</td>
        <td>
    <c:choose>
    <c:when test="${reviewVO.grade eq '1'}">
    <input type="radio" name="grade" id="star-1" value="1"/>
    <label for="star-1" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    </c:when>
    <c:when test="${reviewVO.grade eq '2'}">
    <input type="radio" name="grade" id="star-1" value="1"/>
    <label for="star-1" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-2" value="2"/>
    <label for="star-2" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    </c:when>
    <c:when test="${reviewVO.grade eq '3'}">
    <input type="radio" name="grade" id="star-1" value="1"/>
    <label for="star-1" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-2" value="2"/>
    <label for="star-2" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-3" value="3" checked="checked"/>
    <label for="star-3" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    </c:when>
    <c:when test="${reviewVO.grade eq '4'}">
    <input type="radio" name="grade" id="star-1" value="1"/>
    <label for="star-1" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-2" value="2"/>
    <label for="star-2" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
        <input type="radio" name="grade" id="star-3" value="3" checked="checked"/>
    <label for="star-3" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-4" value="4"/>
    <label for="star-4" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    </c:when>
    <c:otherwise>
    <input type="radio" name="grade" id="star-1" value="1"/>
    <label for="star-1" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-2" value="2"/>
    <label for="star-2" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-3" value="3" checked="checked"/>
    <label for="star-3" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-4" value="4"/>
    <label for="star-4" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-5" value="5"/>
    <label for="star-5" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    </c:otherwise>
</c:choose>
</td>
        <td>${reviewVO.reviewTimeposted}</td>
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
  </c:when>
        <c:otherwise>
         등록된 리뷰가 없습니다.
      </c:otherwise>
   </c:choose>
</div>
<div class="col-sm-2"></div>
</div>
<br>
<p class="paging text-center" style="font-size: 17px; color:black;">
   <c:set var="pb" value="${requestScope.reviewList.pagingBean}"></c:set>
   <!-- 
         step2 1) 이전 페이지 그룹이 있으면 이미지 보여준다. (img/left_arrow_btn.gif)
                     페이징빈의 previousPageGroup 이용 
               2)  이미지에 이전 그룹의 마지막 페이지번호를 링크한다. 
                      hint)   startPageOfPageGroup-1 하면 됨        
    -->      
   <c:if test="${pb.previousPageGroup}">
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/showMyReviewList.do?reviewPageNo=${pb.startPageOfPageGroup-1}&customerId=${sessionScope.memberVO.id}">
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
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/showMyReviewList.do?reviewPageNo=${i}&customerId=${sessionScope.memberVO.id}"">${i}</a> 
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
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/showMyReviewList.do?reviewPageNo=${pb.endPageOfPageGroup+1}&customerId=${sessionScope.memberVO.id}">
   ▶<!-- <img src="img/right_arrow_btn.gif"> --></a>
   </c:if>
   </p>
   <br>


 <Script type="text/javascript">
    $(document).ready(function(){
       $(".updateBtn").click(function(){
          var reviewNo=$(this).parent().parent().find("input[name=reviewNo]").val();
          if(confirm("등록된 리뷰를 수정하시겠습니까?")){
             location.href="${pageContext.request.contextPath}/afterLogin_mypage/mypage_review_update.do?reviewNo="+reviewNo;
          }
       });
       $(".deleteBtn").click(function(){
          var reviewNo=$(this).parent().parent().find("input[name=reviewNo]").val();
          var row=$(this).parent().parent();
          if(confirm("등록된 리뷰를 삭제하시겠습니까?")){
             $.ajax({
                url:"${pageContext.request.contextPath}/afterLogin_mypage/deleteMyReview.do",
                type:"post",
                data:"reviewNo="+reviewNo,
                success:function(data){
                   if(data=="deleteOk"){
                      alert("삭제하였습니다.");
                      //row.remove();
                      location.href="${pageContext.request.contextPath}/afterLogin_mypage/showMyReviewList.do?customerId=${sessionScope.memberVO.id}";
                   }
                }                
             });
          }
       });
    });

 </Script>