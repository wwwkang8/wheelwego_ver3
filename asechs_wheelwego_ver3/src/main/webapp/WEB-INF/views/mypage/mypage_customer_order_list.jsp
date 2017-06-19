<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:include page="../mypage/mypage.jsp"/>

<br><br>
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="css/shop-homepage.css" rel="stylesheet">
    <!-- Page Content -->
    <div class="container">

        <div class="row">
        <div class="col-md-1" style="width: 10%"></div>
            <div class="col-md-9">
                <div class="row">
            <c:forEach items="${requestScope.myBookingList}" var="bookingList">
                    <div class="col-sm-4 col-lg-4 col-md-4" style="border:1px solid lightgray; padding-right:10px; height:350px;">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 style="color: orange;">예약번호 : ${bookingList.bookingNumber}</h4>
                            </div>
                            <div class="table" style="padding: 15px;">
                                <table>
                                   <tr class="bookingState">
                                      <td style="font-weight: bold; padding-bottom: 10px;">주문상태</td><td class="bstate" style="padding-bottom: 10px;"><span class="bookingResult">${bookingList.bookingState }</span></td>
                                   </tr>
                                   <tr>
                                      <td style="font-weight: bold; padding-bottom: 10px;">예약일시</td><td style="padding-bottom: 10px; width:70%;">${bookingList.bookingDate}</td>
                                   </tr>
                                   <tr>
                                      <td style="font-weight: bold; width:96px; padding-bottom: 15px;">메뉴명</td>
                                      <td style="font-weight: bold; width:30px; padding-bottom: 15px;">단가</td>
                                      <td style="font-weight: bold; width:30%; padding-bottom: 15px;">수량</td>
                                      <td style="font-weight: bold; width:33px; padding-bottom: 15px;">금액</td>
                                   </tr>
                                   <c:set value="0" var="totalPrice"/>
                                   <c:set value="0" var="i"/>
                                   <c:forEach items="${bookingList.bookingDetail }" var="bookingDetail">
                                   <tr>
                                      <td style=" padding-bottom: 15px;">${bookingDetail.menuName }
                                      <input type="hidden" name="bookingDetail[${i}].menuId" value="${bookingDetail.menuId}">
                                      </td>                             
                                      <td style=" padding-bottom: 15px;">${bookingDetail.menuPrice}</td>
                                      <td style=" padding-bottom: 15px;">${bookingDetail.bookingQuantity}
                                      <input type="hidden" name="bookingDetail[${i}].bookingQuantity" value="${bookingDetail.bookingQuantity}">
                                      </td>
                                      <td style=" padding-bottom: 15px;">${bookingDetail.menuPrice*bookingDetail.bookingQuantity }</td>
                                   <c:set value="${totalPrice+bookingDetail.bookingQuantity*bookingDetail.menuPrice}" var="totalPrice"/>
                                <c:set value="${i+1}" var="i"/>
                                   </tr>
                                   </c:forEach>
                                   <tr>
                                   <td>
                                   <h5 style="width:100px;">TOTAL&nbsp; ${totalPrice}</h5>
                                   </td>
                                   </tr>
                                </table>              
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
<BR><BR><BR><BR>
<p class="paging text-center" style="font-size: 17px; color:black;">
   <c:set var="pb" value="${requestScope.listVO.pagingBean}"></c:set>
   <!-- 
         step2 1) 이전 페이지 그룹이 있으면 이미지 보여준다. (img/left_arrow_btn.gif)
                     페이징빈의 previousPageGroup 이용 
               2)  이미지에 이전 그룹의 마지막 페이지번호를 링크한다. 
                      hint)   startPageOfPageGroup-1 하면 됨        
    -->      
   <c:if test="${pb.previousPageGroup}">
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/customerBookingList.do?pageNo=${pb.startPageOfPageGroup-1}&customerId=${sessionScope.memberVO.id}">
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
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/customerBookingList.do?pageNo=${i}&customerId=${sessionScope.memberVO.id}">${i}</a> 
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
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/customerBookingList.do?pageNo=${pb.endPageOfPageGroup+1}&customerId=${sessionScope.memberVO.id}">
   ▶<!-- <img src="img/right_arrow_btn.gif"> --></a>
   </c:if>
   </p>
   <br>