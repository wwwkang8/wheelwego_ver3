<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>Shop Homepage - Start Bootstrap Template</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/shop-homepage.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
	$(document).ready(function(){
		$(".cookingBtn").click(function(){
			var bookingNumber=$(this).parent().parent().parent().find(".bookingNumber").children(".bno").text();
			var bookingState=$(this).parent().parent().parent().find(".bookingState").children(".bstate").text();
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/afterLogin_mypage/updateState.do",
				data:"bookingState=조리중&bookingNumber="+bookingNumber,
				success:function(bookingResult){
					location.reload();
				}
			});
		});//click
		
		$(".finishedCookingBtn").click(function(){
			var bookingNumber=$(this).parent().parent().parent().find(".bookingNumber").children(".bno").text();
			var bookingState=$(this).parent().parent().parent().find(".bookingState").children(".bstate").text();
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/afterLogin_mypage/updateState.do",
				data:"bookingState=조리완료&bookingNumber="+bookingNumber,
				success:function(bookingResult){
					location.reload();
				}
			});
		});//click
	});//ready
</script>
<body>

    <!-- Page Content -->
    <div class="container">

        <div class="row">
            <div class="col-md-9" >

                <div class="row">
				<c:forEach items="${requestScope.customerBookingList }" var="customerBookingList">
                    <div class="col-sm-4 col-lg-4 col-md-4">
                        <div class="thumbnail">
                            <div class="caption">
                                <h4 class="pull-right">합계:${totalPrice }원</h4>
                                <h4><a href="#">예약번호:${customerBookingList.bookingNumber }</a>
                                </h4>
                            </div>
                            <div class="table">
                                <table>
                               		 <tr class="bookingNumber">
                                		<td>예약번호</td><td class="bno">${customerBookingList.bookingNumber }</td>
                                	</tr>
                                	<tr class="bookingState">
                                		<td>주문상태</td><span class="bookingResult"><td class="bstate">${customerBookingList.bookingState }</td></span>
                                	</tr>
                                	<tr>
                                		<td>주문자</td><td>${customerBookingList.customerId }</td>
                                	</tr>
                                	<tr>
                                		<td>예약일시</td><td>${customerBookingList.bookingDate}</td>
                                	</tr>
                                	<tr>
                                		<td>메뉴명</td><td>단가</td><td>수량</td><td>금액</td>
                                	</tr>
                                	<c:forEach items="${customerBookingList.bookingDetail }" var="bookingDetail">
                                	<c:set var="totalPrice"  value="${totalPrice+bookingDetail.menuPrice*bookingDetail.bookingQuantity }"/>
                                	<tr>
                                		<td>${bookingDetail.menuName }</td>
                                		<td>${bookingDetail.menuPrice }</td>
                                		<td>${bookingDetail.bookingQuantity }</td>
                                		<td>${bookingDetail.menuPrice*bookingDetail.bookingQuantity }</td>
                                	</tr>
                                	</c:forEach>
                                </table>
                                
						        <input type="hidden" class="bookingNumber" value="">
						        
                            </div>
                        </div>
                    </div>
                    </c:forEach>

                </div>

            </div>

        </div>

    </div>