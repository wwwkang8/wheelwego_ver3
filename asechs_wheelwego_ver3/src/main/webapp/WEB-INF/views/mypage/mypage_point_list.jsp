<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:include page="../mypage/mypage.jsp"/>

<br><br>

 <div class="row">
 <div class="col-sm-2"></div>
 <div class="container col-sm-8" >
	<div align="center"><h3><strong>MY POINT : </strong>${myPoint}<strong>p</strong></h3></div>
	<Br><br>
  <c:choose>
      <c:when test="${pointList.pointList!='[]'}">      
  <table class="table table-hover">
    <thead>
      <tr>
        <th>FoodTruckName</th>
        <th>Plus/Minus</th>
        <th>Point</th>
        <th>Date</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${pointList.pointList}" var="pointVO" varStatus="status">
      <tr>
        <td>${pointVO.foodtruckName}</a></td>
        <td>
        	<c:choose>
        		<c:when test="${pointVO.pointType=='add'}">
        			적립
        		</c:when>
        		<c:otherwise>
        			차감
        		</c:otherwise>
        	</c:choose>
        </td>
        <td>${pointVO.point}</td>
        <td>${pointVO.pointDate}</td>
      </tr>
     </c:forEach>
    </tbody>
  </table>
  </c:when>
        <c:otherwise>
         	포인트 내역이 존재하지 않습니다.
      </c:otherwise>
   </c:choose>
</div>
<div class="col-sm-2"></div>
</div>
<br>
    
    <p class="paging text-center" style="font-size: 17px; color:black;">
   <c:set var="pb" value="${requestScope.pointList.pagingBean}"></c:set>
   <!-- 
         step2 1) 이전 페이지 그룹이 있으면 이미지 보여준다. (img/left_arrow_btn.gif)
                     페이징빈의 previousPageGroup 이용 
               2)  이미지에 이전 그룹의 마지막 페이지번호를 링크한다. 
                      hint)   startPageOfPageGroup-1 하면 됨        
    -->      
   <c:if test="${pb.previousPageGroup}">
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/showMyPointList.do?nowPage=${pb.startPageOfPageGroup-1}&customerId=${sessionScope.memberVO.id}">
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
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/showMyPointList.do?nowPage=${i}&customerId=${sessionScope.memberVO.id}"">${i}</a> 
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
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/showMyPointList.do?nowPage=${pb.endPageOfPageGroup+1}&customerId=${sessionScope.memberVO.id}">
   ▶<!-- <img src="img/right_arrow_btn.gif"> --></a>
   </c:if>
   </p>
   <br>