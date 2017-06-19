<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<title>W3.CSS</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
 <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
<style>
.w3-button {width:150px;}
</style>

<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=3IKOAkT6zc5H36iz7sma&submodules=geocoder"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
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
                  
                  $.ajax({
                     url:"${pageContext.request.contextPath}/afterLogin_mypage/setTruckGPS.do",
                     data:"sellerId=${sessionScope.memberVO.id}&latitude="+latitude+"&longitude="+longitude,
                     type:"post",
                     success:function(result){
                        alert(result);
                     }
                  })
                  opener.location.href="${pageContext.request.contextPath}/home.do";
                  self.close();
              });                
           }
       }).open();
   }
</script>

<script type="text/javascript">
function geoFindMe() {
     if (!navigator.geolocation){
       alert("지오로케이션을 지원하지 않습니다!");
       return;
     }
     function success(position) {
       var latitude  = position.coords.latitude;
       var longitude = position.coords.longitude;

       $.ajax({
          url:"${pageContext.request.contextPath}/afterLogin_mypage/setTruckGPS.do",
          data:"sellerId=${sessionScope.memberVO.id}&latitude="+latitude+"&longitude="+longitude,
          type:"post",
          success:function(result){
             alert(result);
          }
       })
       //location.href = "${pageContext.request.contextPath}/afterLogin_mypage/setTruckGPS.do?sellerId=${sessionScope.memberVO.id}&latitude="+latitude+"&longitude="+longitude; 
       opener.location.href="${pageContext.request.contextPath}/home.do";
       self.close();

     };

     function error() {
        alert("사용자의 위치를 찾을 수 없습니다!");
     };
     navigator.geolocation.getCurrentPosition(success, error);
}
</script>
<body>

<div class="w3-container">
  <br>
  <center><button class="w3-button w3-orange" onclick="geoFindMe()">위치 자동설정</button></center><br>
  <center><button class="w3-button w3-orange" onclick="sample6_execDaumPostcode()">위치 수동설정</button></center><br>
</div>
</body>
</html>