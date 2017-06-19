<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


   <div class=" text-center"> <h1 class="page-header">MY Page</h1> </div>
   <div align="center">
  <button type="button" id="deleteAccountBtn" class="btn btn-warning">회원탈퇴</button>&nbsp;&nbsp;
  <button type="button" id="updateBtn" class="btn btn-warning">회원정보수정</button>&nbsp;&nbsp; 
 
<c:choose>
	<c:when test="${sessionScope.memberVO.memberType=='seller'}">
		<c:choose>
			<c:when test="${truckNumber==null&&truckVO.foodtruckNumber==null && truckNumber==null}">
				<button type="button" id="registerTruckBtn" class="btn btn-warning">MY TRUCK 등록</button>&nbsp;&nbsp;
			</c:when>
			<c:otherwise>
			<button type="button" id="updateTruckBtn"class="btn btn-warning">MY TRUCK 설정</button>&nbsp;&nbsp;
			<button type="button" id="menuBtn"class="btn btn-warning">MENU</button>&nbsp;&nbsp;
			<button type="button" id="myTruckBtn"class="btn btn-warning">MY TRUCK PAGE</button>&nbsp;&nbsp;
			<a href="${pageContext.request.contextPath}/afterLogin_mypage/checkTruckGPS.do?sellerId=${sessionScope.memberVO.id}" class="btn btn-warning" role="button">TRUCK 위치 설정</a>&nbsp;&nbsp;
			<button type="button" id="sellerBookingListBtn" class="btn btn-warning" >온라인 주문현황</button><br>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
				<button type="button" id="wishlistBtn"class="btn btn-warning">단골트럭</button>&nbsp;&nbsp;
			<button type="button" id="reviewBtn"class="btn btn-warning">MY REVIEW</button>&nbsp;&nbsp;
			<!-- 강정호가 만든 버튼 잠시 주석처리. 밑에 현지가 만든 버튼 사용할 것임 -->
			<!-- <button type="button" id="contentBtn"class="btn btn-warning">MY CONTENT</button>&nbsp;&nbsp;
			<button type="button" id="customerBookingListBtn" class="btn btn-warning">나의 주문 내역</button>  -->

			<button type="button" id="contentBtn"class="btn btn-warning">MY CONTENT</button>&nbsp;&nbsp;
			<button type="button" id="orderBtn" class="btn btn-warning">MY ORDER</button>&nbsp;&nbsp;
			<button type="button" id="pointBtn" class="btn btn-warning">MY POINT</button>&nbsp;&nbsp;
	</c:otherwise>
</c:choose>
</div>

<script>
var latitude;
var longitude;
var address;

function checkDelete(){
if (confirm("정말 탈퇴하시겠습니까?") == true){    //확인
    return true;
}else{   //취소
    return false;
}
}



function geoFindMe() {
if (!navigator.geolocation){
   alert("지오로케이션을 지원하지 않습니다!");
   return;
}
  function success(position) {
    latitude  = position.coords.latitude;
    longitude = position.coords.longitude;
    //
    //location.href = "${pageContext.request.contextPath}/searchFoodTruckByName.do?latitude="+latitude+"&longitude="+longitude+"&name="+searchFoodtruckName;
    
    
   var mapInfo = naver.maps.Service.reverseGeocode({
        location: new naver.maps.LatLng(latitude, longitude),
    }, function(status, response) {
        if (status !== naver.maps.Service.Status.OK) {
            //return alert('Something wrong!');
        }

        var result = response.result, // 검색 결과의 컨테이너
            items = result.items; // 검색 결과의 배열
            if(items[0].address=="" || items[0].address==null){
               address = "";
               //document.getElementById("${truckInfo.foodtruckName}").innerHTML="위치 정보 없음";
            }else{
               address = items[0].address;
               //alert(address);
               //document.getElementById("${truckInfo.foodtruckName}").innerHTML = items[0].address;
            }
    });       
   
  };
  function error() {
     alert("사용자의 위치를 찾을 수 없습니다!");
  };
  navigator.geolocation.getCurrentPosition(success, error);
}

$(document).ready(function(){
geoFindMe();
		$("#deleteAccountBtn").click(function(){
			if(confirm("계정을 삭제하시겠습니까?")){
				location.href="${pageContext.request.contextPath}/afterLogin_mypage/checkPasswordForm.do?command=deleteAccount";
			}
		});
		$("#updateBtn").click(function(){
				location.href="${pageContext.request.contextPath}/afterLogin_mypage/checkPasswordForm.do?command=update_form";
		});
		$("#registerTruckBtn").click(function(){
			location.href="${pageContext.request.contextPath}/afterLogin_mypage/registerMyfoodtruck.do";
	});
		$("#updateTruckBtn").click(function(){
			location.href="${pageContext.request.contextPath}/afterLogin_mypage/myfoodtruck_page.do";
	});
		$("#menuBtn").click(function(){
			location.href="${pageContext.request.contextPath}/afterLogin_mypage/myfoodtruck_menuList.do";
	});
		$("#myTruckBtn").click(function(){
			location.href = "${pageContext.request.contextPath}/foodtruck/foodTruckAndMenuDetail.do?foodtruckNo=${truckNumber}"+"&latitude="+latitude+"&longitude="+longitude+"&address="+address;
	});
		$("#wishlistBtn").click(function(){
			location.href = "${pageContext.request.contextPath}/afterLogin_mypage/wishlist.do?id=${sessionScope.memberVO.id}&latitude="+latitude+"&longitude="+longitude;
	});
		$("#reviewBtn").click(function(){
			location.href="${pageContext.request.contextPath}/afterLogin_mypage/showMyReviewList.do?customerId=${sessionScope.memberVO.id}";
	});
		$("#contentBtn").click(function(){
			location.href="${pageContext.request.contextPath}/afterLogin_mypage/showMyContentList.do?customerId=${sessionScope.memberVO.id}";
	});

		$("#sellerBookingListBtn").click(function(){
			//location.href="${pageContext.request.contextPath}/afterLogin_mypage/sellerBookingList.do?sellerId=${sessionScope.memberVO.id}";
			location.href="${pageContext.request.contextPath}/afterLogin_mypage/sellerBookingList.do?sellerId=${sessionScope.memberVO.id}";
		});
		//강정호가 만든 소비자 주문 확인 내역 보는 버튼 잠시 주석. 밑에 만든 현지것으로 대신함
		/* $("#customerBookingListBtn").click(function(){
			location.href="${pageContext.request.contextPath}/afterLogin_mypage/customerBookingList.do?customerId=${sessionScope.memberVO.id}"; */

		$("#orderBtn").click(function(){
			location.href="${pageContext.request.contextPath}/afterLogin_mypage/customerBookingList.do?customerId=${sessionScope.memberVO.id}";
		});
		$("#pointBtn").click(function(){
			location.href="${pageContext.request.contextPath}/afterLogin_mypage/showMyPointList.do?customerId=${sessionScope.memberVO.id}";
		});
});
</script>