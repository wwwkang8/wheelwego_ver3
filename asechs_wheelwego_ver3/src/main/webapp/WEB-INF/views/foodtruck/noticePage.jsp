<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<Script type="text/javascript">
<%--박다혜 수정 완료--%>
$(document).ready(function(){
	
	var bookingNumber=""; //seller의 예약번호를 저장
	var memberId="${sessionScope.memberVO.id}";
	var memberType="${sessionScope.memberVO.memberType}";
	var customerBookingNumber=""; //customer의 예약번호를 저장
	 var customerTimer="";
	
		function customerAjaxCall(){
			/*
				예약번호에 해당하는 예약상태가 만약 ok가 된다면
				주문한 음식이 나온것이므로 alertify plugin을 이용하여 알림창을 띄운다.
				그 후, clearInterval을 통해 ajax통신을 끊는다.
			*/
			$.ajax({	
				method:"post",
				url:"${pageContext.request.contextPath}/afterLogin_foodtruck/getBookingStateBybookingNumber.do",
				data:"bookingNumber="+customerBookingNumber,
				success:function(state){
						if(state=="ok"){		
							alertify.log("주문하신 음식이 나왔습니다!");
							clearInterval(customerTimer);
					}  
				}
			});
	}//customerAjaxCall
		function sellerAjaxCall(){
		/*
			결제완료상태인 최근 예약 번호와 예약 상태에 상관없이 사업자의 이전 예약 번호를 비교한다.
			결제완료 상태인 최근 예약 번호(result)가 이전 예약번호(bookingNumber)보다 크다면
			주문이 들어온 것이므로 alertify plugin을 이용하여 알림창을 띄운다.
			그 후 이전 예약번호(bookingNumber)에 최근 예약번호(result)를 할당한다.
		*/
		$.ajax({
				method:"post",
				url:"${pageContext.request.contextPath}/afterLogin_foodtruck/getRecentlyBookingNumberBySellerId.do",
				data:"id="+memberId,
				success:function(result){
					if(parseInt(result)>parseInt(bookingNumber)){		
						bookingNumber=result;
						alertify.log("새로운 주문내역을 확인해주세요!");
					}
				}
			});
		}//sellerAjaxCall
	
	if(memberId!=""&&memberType=="seller"){ //로그인된 상태이고 seller라면
		//ajax통신하여 예약 상태에 상관 없이 사업자의 최근 예약 번호를 가져온다.
		$.ajax({
			method:"post",
			url:"${pageContext.request.contextPath}/afterLogin_foodtruck/getPreviousBookingNumberBySellerId.do",
			data:"id=${sessionScope.memberVO.id}",
			success:function(result){
					//alert("이전 예약번호"+result);
					bookingNumber=result;
				}
			});
			//주문이 들어오는지 알기 위해 setInterval로 반복하여 통신한다.
			setInterval(sellerAjaxCall, 1000);
	}//seller

	if(memberId!=""&&memberType=="customer"){//로그인된 상태이고 customer라면
		/*
			ajax통신하여 결제완료상태인 예약번호가 있는지 확인한다. 즉, 현재 주문이 있는지 확인한다.
			현재 주문이 있다면(customerBookingNumber가 있다면)
			주문상태가 변경되는지 확인하기 위해 customerAjaxCall 함수를 반복적으로 실행한다.
		*/
		$.ajax({
			method:"post",
			url:"${pageContext.request.contextPath}/afterLogin_foodtruck/getPreviousBookingNumberByCustomerId.do",
			data:"id=${sessionScope.memberVO.id}",
			success:function(result){
					customerBookingNumber=result;
					if(customerBookingNumber !="" && customerBookingNumber!=null &&customerBookingNumber!=0){ //bookingNumber가 있으면
			    	   customerTimer=setInterval(customerAjaxCall, 1000);
			       }
				}
			});
	}//customer
});	
</Script>