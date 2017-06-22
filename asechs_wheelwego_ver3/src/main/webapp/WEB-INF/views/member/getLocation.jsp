<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 황윤상 : 위치정보를 받아오면 자바스크립트를 별도의 파일로 둠 -->
<script type="text/javascript">
	if (!navigator.geolocation) {
		alert("지오로케이션을 지원하지 않습니다!");
		document.getElementById('latitude').value = "37.566824";
		document.getElementById('longitude').value= "126.978522";		
	}
	function success(position) {
		var latitude = position.coords.latitude;
		var longitude = position.coords.longitude;
		
		document.getElementById('latitude').value = latitude;
		document.getElementById('longitude').value= longitude;
		alert(latitude);
		alert(longitude);
		var gpsForm = document.getElementById("gpsForm"); 
		gpsForm.submit();	
	};
	function error() {
		alert("사용자의 위치를 찾을 수 없습니다!");
		document.getElementById('latitude').value = "37.566824";
		document.getElementById('longitude').value= "126.978522";			
	};
	navigator.geolocation.getCurrentPosition(success, error);
</script>

<form method="post" action="${pageContext.request.contextPath}/main.do" id="gpsForm">
	<input 	type = "hidden" id = "latitude" name = "latitude" value = "">
	<input type = "hidden" id = "longitude" name = "longitude" value = "">
</form>
