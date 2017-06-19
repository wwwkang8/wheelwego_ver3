<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.2.js"></script>
<div class="row">
<div class="col-sm-3"></div>
<div class="container col-sm-6">
 <h2>주문 내역</h2><br>
 <c:set var="memberInfo" value="${sessionScope.memberVO}"></c:set>
 <form id="bookingForm" action="${pageContext.request.contextPath}/afterLogin_foodtruck/bookingMenu.do" method="post">
 <input type="hidden" name="customerId" value="${sessionScope.memberVO.id}">
  <table class="table table-hover">
    <thead>
      <tr>
        <th>MENU</th>
        <th>PRICE</th>
        <th>AMOUNT</th>
        <th>TOTAL</th>
      </tr>
    </thead>
        <c:set value="0" var="total"/>
    <tbody>
   <c:set value="0" var="i"/>
    <c:forEach items="${requestScope.bvo.bookingDetail}" var="bookingList" >
    	<c:if test="${bookingList.menuName != ''&&bookingList.menuName!=null}">
	      <tr>
	        <td>${bookingList.menuName}
	        <input type="hidden" name="bookingDetail[${i}].menuId" value="${bookingList.menuId}"></td>
	        <td>${bookingList.menuPrice}</td>
	        <td>${bookingList.bookingQuantity}<input type="hidden" name="bookingDetail[${i}].bookingQuantity" value="${bookingList.bookingQuantity}"></td>
	        <td>${bookingList.bookingQuantity*bookingList.menuPrice}</td>
	        <c:set value="${total+bookingList.bookingQuantity*bookingList.menuPrice}" var="total"/>
	        <c:set value="${i+1}" var="i"/>
	      </tr>
    	</c:if>
      </c:forEach>
    </tbody>
  </table>
<div align="right">
<span style="font-weight: bold;">총 주문금액 : ${total}</span>
<br><br>
</div>
<div>
<h4>MY POINT</h4>
<div style="border:1px solid lightgray; text-align: center;">
<br>
	보유 포인트 : ${myPoint} <br>
	<input type="radio" name="pointOption" value="uncheck" checked>선택안함&nbsp;&nbsp;&nbsp;
	<input type="radio" name="pointOption" value="check">포인트차감<br>
	<input type="text" id="point" size="10" disabled="disabled" onkeydown="return showKeyCode(event)">p
<br><br>
</div>
</div><br><br>
<div align="right">
<span style="font-weight: bold;"  id = "totalAmount">최종결제금액 : ${total}</span>
<br><br>
</div>
      <input type = "hidden" name = "resultPoint" id = "resultPoint" value = "">
      <input type = "hidden" name = "resultTotalAmount" id = "resultTotalAmount" value = "">
 </form>
<div align="right">
<button type="button" class="btn btn-primary btn-sm " id="orderBtn">
<span class="glyphicon glyphicon-credit-card "></span> 결제하기
</button>
</div>
</div>
<div class="col-sm-3"></div>
</div>
<script>
$(document).ready(function(){
	$(":input[name=pointOption]").click(function() {
	    if($(this).val()=="uncheck"){
	    	$("input:text").val("");
			$("input:text").attr("disabled", true);
	    }else if($(this).val()=="check")
	    	$("input:text").attr("disabled", false);
	});
	
	 $(":input[type=text]").keyup(function() {
	        var myPoint="${myPoint}";
	        var point=$(this).val();
	        var totalAmount = ${total} - point;
	        if(point>parseInt(myPoint)){
	           alert("보유하신 포인트를 확인해주세요");
	           $("#totalAmount").html("최종결제금액 : ${total}");
	           $(this).val("");
	        }
	        else if (totalAmount<0){
	           alert("결제금액보다 더 많은 포인트를 사용할 수 없습니다.");
	           $(this).val("");
	        }else{
	    	    $("#totalAmount").html("최종결제금액 : " + totalAmount);
	        }
	   });
	 
	$("#orderBtn").click(function(){
	      $.ajax({
	           type:"POST",
	           url:"${pageContext.request.contextPath}/afterLogin_foodtruck/checkBooking.do", 
	           success:function(data){
	              if (data == "no"){
	                 alert("선행 주문이 있기 때문에, 결제가 불가능합니다.");
	                 return false;
	              }
	              else{
	                var point = $("#point").val().trim();
	                var totalAmount = ${total} - point;
	                
	                if (totalAmount == 0)
	                {
	                   alert("결제 금액이 0원이기 때문에, 결제를 진행하지 않습니다.");
	                   $("#resultPoint").val(point);
	                   $("#resultTotalAmount").val(totalAmount);
	                   $('#bookingForm').submit();         
	                }
	                   
/* 		  $("#resultPoint").val(point);
	 	  $("#resultTotalAmount").val(totalAmount);
		 $("#bookingForm").submit(); */
            
        var IMP = window.IMP; // 생략가능
      IMP.init('imp65309481'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
      
      IMP.request_pay({
          pg : 'inicis', // version 1.1.0부터 지원.
          pay_method : 'card',
          merchant_uid : 'merchant_' + new Date().getTime(),
          name : '주문명:결제테스트',
          amount : totalAmount,
          buyer_email : '',
          buyer_name : '${memberInfo.memberName}',
          buyer_tel : '${memberInfo.phoneNumber}',
          buyer_addr : '${memberInfo.address} ' + '${memberInfo.addressDetail}',
          buyer_postcode : '${memberInfo.postCode}',
          m_redirect_url : 'https://www.yourdomain.com/payments/complete'
      }, function(rsp) {
          if ( rsp.success ) {
              var msg = '결제가 완료되었습니다.';
              msg += '고유ID : ' + rsp.imp_uid;
              msg += '상점 거래ID : ' + rsp.merchant_uid;
              msg += '결제 금액 : ' + rsp.paid_amount;
              msg += '카드 승인번호 : ' + rsp.apply_num;
              
            $("#resultPoint").val(point);
            $("#resultTotalAmount").val(totalAmount);
            //$('#paymentForm').submit();
            //alert(msg);
        	 $("#bookingForm").submit();
          } else {
              var msg = '결제에 실패하였습니다.';
              msg += '에러내용 : ' + rsp.error_msg;
              alert(msg);
    	      }//else 
		  }) ;//결제
		  }//else
 	 }//success
 });  //ajax
}); //orderBtn
}); //document
function showKeyCode(event) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if( ( keyID >=48 && keyID <= 57 ) || ( keyID >=96 && keyID <= 105 ) )
	{
		return;
	}
	else
	{
		return false;
	}
}
</script>
