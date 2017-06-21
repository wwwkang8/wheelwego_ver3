<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	//황윤상 : 푸드트럭에 저장된 좌표를 기반으로 주소 변환	
	<c:forEach items="${requestScope.trucklist}" var="truckInfo" varStatus="status">
	var mapInfo = naver.maps.Service.reverseGeocode({
	     location: new naver.maps.LatLng("${truckInfo.latitude}", "${truckInfo.longitude}"),
	 }, function(status, response) {
	     if (status !== naver.maps.Service.Status.OK) {
	         document.getElementById("${truckInfo.foodtruckName}").innerHTML="위치 정보 없음";
	     }
	
	     var result = response.result, // 검색 결과의 컨테이너
	         items = result.items; // 검색 결과의 배열
	         if(items[0].address=="" || items[0].address==null){
	         }else{
	         document.getElementById("${truckInfo.foodtruckName}").innerHTML = items[0].address;
	         }
	 });
	</c:forEach>
	
	//황윤상 : Post 방식의 에러를 방지하기 위한 새로고침 발생 시 이벤트 처리 부분
	var i=0
	window.document.onkeydown = protectKey;
	function down() {
	        window.footer_cart.scrollBy(0,31)
	        return;
	}
	function up() {
	        window.footer_cart.scrollBy(0,-31)
	        return;
	}
	function protectKey()
	{
		if(event.keyCode == 116) //새로고침을 막는 스크립트.. F5 번키..
		{
			event.keyCode = 0;
			location.href = "${pageContext.request.contextPath}/home.do"            
		}
		else if ((event.keyCode == 78) && (event.ctrlKey == true))	//CTRL + N 즉 새로 고침을 막는 스크립트....
		{
			event.keyCode = 0;
			location.href = "${pageContext.request.contextPath}/home.do"
		}
	}
	
	//황윤상 : 사용자의 좌표 기반으로 푸드트럭 리스트를 검색 (자동검색)
	function searchFoodTruckByGPS() {
		location.href = "${pageContext.request.contextPath}/searchFoodTruckByGPS.do?latitude=${sessionScope.latitude}&longitude=${sessionScope.longitude}";
	}
	
	//황윤상 : 사용자의 검색어에 따라 푸드트럭 리스트를 검색 (수동검색)
	function sample6_execDaumPostcode() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
	            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	            var fullAddr = ''; // 최종 주소 변수
	            var extraAddr = ''; // 조합형 주소 변수
	
	            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	            if (data.userSelectedType == 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                fullAddr = data.roadAddress;
	
	            } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                fullAddr = data.jibunAddress;
	            }
	
	            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
	            if(data.userSelectedType == 'R'){
	                //법정동명이 있을 경우 추가한다.
	                if(data.bname !== ''){
	                    extraAddr += data.bname;
	                }
	                // 건물명이 있을 경우 추가한다.
	                if(data.buildingName !== ''){
	                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                }
	                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
	                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
	            }
	               
	           var mapInfo = naver.maps.Service.geocode({
	               address: fullAddr
	           }, function(status, response) {
	               if (status !== naver.maps.Service.Status.OK) {
	                  alert('입력한 주소를 찾을 수 없습니다.');
	                  return;
	               }
	               var result = response.result, // 검색 결과의 컨테이너
	                   items = result.items; // 검색 결과의 배열
	                   
	               var latitude  = items[0].point.y;
	               var longitude = items[0].point.x;
	               
	               location.href = "${pageContext.request.contextPath}/searchFoodTruckByGPS.do?latitude="+latitude+"&longitude="+longitude;
	           });                
	        }
	    }).open();
	}
	
	//정현지 : 추천리스트(hover) 클릭 시, 상세 페이지로 이동
	function hoverClick(foodtruckNo,address){
		location.href = "${pageContext.request.contextPath}/foodtruck/foodTruckAndMenuDetail.do?foodtruckNo="+foodtruckNo+"&latitude=${sessionScope.latitude}&longitude=${sessionScope.longitude}"+"&address="+address;
	} 
	
	$(document).ready(function(){
	   $(".detailLink").click(function(){
	      var address=$(this).find(".address").text();
	      var foodtruckNo=$(this).find(":input[name=foodturckNo]").val();
	     hoverClick(foodtruckNo,address);
	   });
	});
</script>

<!-- Header -->
<header>
   <div class="container" id="maincontent" tabindex="-1">
      <div class="row">
         <div class="col-lg-12">
            <div class="intro-text">
               <h1 class="name">Wheel<img src="${pageContext.request.contextPath}/resources/img/포크.png">  we go!</h1>
               <hr>
               </div>

            <div class="col-lg-3"></div>
            <div class="social col-lg-1">
              <ul>
                <li>
                 <a class="dropdown-toggle" href="#" data-toggle="dropdown"><i class="fa fa-map-marker fa-3x"></i></a>
                 <div class="dropdown-menu" style="padding: 15px; padding-bottom: 15px;" id="roundCorner">
                 <form>
                  <input class="btn btn-warning" onclick="sample6_execDaumPostcode()" style="width: 100%;" value="수동검색" style="">
                  <input class="btn btn-warning" onclick="searchFoodTruckByGPS()" style="width: 100%;" value="자동검색" style="">
              	 </form>
               </div>
                </li>
              </ul>
            </div>
           <div class="col-lg-4">
           <!-- 푸드트럭 검색 폼 -->
         	<form class="subscribe_form" action="${pageContext.request.contextPath}/searchFoodTruckByName.do">      	  
              <input type="text" placeholder="Search foodtruck!" class="email" id="name" name="name" required="required">
              <input type="submit" class="subscribe" name="search" value="Search">
            </form>
            
           </div>
             <div class="col-lg-4"></div>
         </div>
      </div>
   </div>
</header>

<section id="portfolio">
   <div class="container">

         <div class="col-lg-12 text-center">
            <h2 class="page-header">recommend list</h2><br><br><br>
         </div>
         
      <div class="row" >
      <!-- begin, end 값을 설정하여 전체 트럭 목록 중, 6개만 랜덤으로 뽑아낸다 -->
      <c:forEach items="${trucklist}" begin="0" end="8" var="truckVO">
         <div class="col-lg-4 col-sm-6 portfolio-item" >
            <div class="flip-container"
               ontouchstart="this.classList.toggle('hover');" style="margin: 0 auto;">
               <div class="flipper">  
<!--                   <a href="#portfolioModal1" onclick="hoverClick(this.id)" id=${truckVO.foodtruckNumber} class="portfolio-link"
                        data-toggle="modal"> -->
               <div style="CURSOR:pointer" class="detailLink">
                     <input type="hidden" name="foodturckNo" value="${truckVO.foodtruckNumber}">
                      <input type="hidden" name="latitude" value="${truckVO.latitude}">
                       <input type="hidden" name="longitude" value="${truckVO.longitude}">
                     <div class="front" >
                        <img class="img-circle  center-block food-img img-responsive"
                           src="${pageContext.request.contextPath}/resources/upload/${truckVO.fileVO.filepath}"
                           alt="" >
                     </div>
                     <div class="back cbx-back-side">
                        <div class="text-center back-single-text">
                           <p>${truckVO.foodtruckName}</p>
                           <p class="truck-simple-info"><br><br><br>
              <span class="glyphicon glyphicon-map-marker"></span> <span id="${truckVO.foodtruckName}" class="address"></span><br>
            <Br><span class="glyphicon glyphicon-star" style="color:orange"></span> ${truckVO.avgGrade}</p>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
         </c:forEach>
       </div>
      <!-- row -->
      <hr>
   </div>
   <!-- container -->
</section>