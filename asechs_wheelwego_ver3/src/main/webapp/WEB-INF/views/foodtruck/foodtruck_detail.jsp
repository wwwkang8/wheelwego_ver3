<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Inconsolata">
<style>
body, html {
    height: 100%;
    font-family: "Inconsolata", sans-serif;
}
.bgimg {
    background-position: center;
    background-size: cover;
    background-image: url("${pageContext.request.contextPath}/resources/upload/${truckDetailInfo.fileVO.filepath}");
    min-height: 75%;
}
.menu {
    display: none;
}
/* review button color */
.btn-default{
 background-color: #FDF5E6 !important;
}
/*별점*/
input[name="grade"]{
  display:none;
}

.star_point{
  font-size:20pt;
  color:gold;
  cursor:pointer;
}
.star_point_list{
  font-size:15pt;
  color:gold;
  cursor:pointer;
}
input[name="grade"]:checked + .star_point~label{
    color:lightgray;
}

</style>
<script>

$(document).ready(function(){
   $("input#insertBtn2").click(function(){
     var foodtruckNumber = $(this).attr('name');
     var id = "${sessionScope.memberVO.id}";
     //var img1=document.getElementById("${pageContext.request.contextPath }/resources/img/foodtruck/heartoff.png");
       var src = ($(this).attr('src')) ==='hearton.png'
     
    if(id==""){
       alert("로그인이 필요합니다.");
    }
    else if(${sessionScope.memberVO.memberType=='seller'}){
    	alert("일반회원 전용 서비스입니다.");
    }
    else{
     $.ajax({
      type:"post",
      url:"${pageContext.request.contextPath}/afterLogin_foodtruck/registerBookMark.do",
      data: {id: id, foodtruckNumber: foodtruckNumber}, 
      success:function(data){
         if(data=="on"){

			$("#insertBtn2").attr('src','${pageContext.request.contextPath}/resources/upload/hearton.png');
			alert("단골트럭으로 등록!");
			location.reload();
         }else{
 			$("#insertBtn2").attr('src','${pageContext.request.contextPath}/resources/upload/greyheart2.png');
            alert("단골트럭 등록해제");         
            location.reload();
         }
      }
   });
    }
});
});
</script>
<!-- Header -->
<header class="bgimg w3-display-container" id="home">
  <div class="w3-display-bottomleft w3-center w3-padding-large w3-hide-small">
    <span class="w3-tag">Open from 6am to 5pm</span>
  </div>
   <div class="container" id="maincontent" tabindex="-1">
      <div class="row">
         <div class="col-lg-12">
            <div class="w3-text-light-grey intro-text">
               <h1 class="name" style="color:light-grey; padding-right: 7cm;">${truckDetailInfo.foodtruckName}</h1>
               </div>
         </div>
      </div>
   </div>
     <div class="w3-display-bottomright w3-center w3-padding-large">
    <span class="w3-tag">${param.address}</span>
  </div>
</header>

<!-- Add a background color and large text to the whole page -->
<div class="w3-sand w3-large">
<!-- About Container -->
<div class="w3-container" id="about">
  <div class="w3-content" style="max-width:700px">
    <h5 class="w3-center w3-padding-32"><span class="w3-tag w3-wide">ABOUT US</span></h5> 
  <div align="center">
  <c:choose>
        <c:when test="${wishlistFlag==1}">
	       	 	<input type="image" id="insertBtn2" name = "${truckDetailInfo.foodtruckNumber}" src = "${pageContext.request.contextPath}/resources/upload/hearton.png"  style="width:45px;text-align: center; z-index: 10; ">
	        </c:when>
	        <c:otherwise>
	        	<input type="image" id="insertBtn2" name = "${truckDetailInfo.foodtruckNumber}" src = "${pageContext.request.contextPath}/resources/upload/greyheart2.png" style="width:45px;text-align: center; z-index: 10; ">
	        </c:otherwise>
        </c:choose>
    </div>
  
    <%-- <input type="image" id="insertBtn" name = "${truckDetailInfo.foodtruckNumber}" src = "${pageContext.request.contextPath }/resources/upload/greyheart2.png"> --%>
   <%--  <img src="${pageContext.request.contextPath}/resources/upload/${requestScope.truckDetailInfo.fileVO.filepath}" style="width:100%" ><br><br><br> --%>
<p style="text-align:center;"><i>${truckDetailInfo.introduction}</i></p><br>
<div class="w3-panel w3-leftbar w3-light-grey" align="center" >
	<p></p>
    <p  style="font-size: 16px;"><span class="glyphicon glyphicon-map-marker"></span>&nbsp;${param.address}</p>
   <p><strong><span class="glyphicon glyphicon-star" style="color:orange"></span></strong> ${requestScope.truckDetailInfo.avgGrade} &nbsp;&nbsp;&nbsp;&nbsp;
   <strong><span class="glyphicon glyphicon-heart" style="color:red"></span> </strong>${requestScope.truckDetailInfo.wishlistCount}</p>
</div>
 </div>
  </div>


<!-- Menu Container -->
<div class="w3-container" id="menu">
  <div class="w3-content" style="max-width:700px">
 
    <h5 class="w3-center w3-padding-48"><span class="w3-tag w3-wide">THE MENU</span></h5>
  
    <div class="w3-row w3-center w3-card-2 w3-padding">
      <a href="javascript:void(0)" onclick="openMenu(event, 'Eat');" id="myLink">
        <div class="w3-col s12 tablink">Eat</div> 
      </a>
    </div>

    <div id="Eat" class="w3-container menu w3-padding-48 w3-card-2">
       <c:forEach items="${requestScope.truckDetailInfo.foodList}" var="foodList">
       <br>
       <img src="${pageContext.request.contextPath}/resources/upload/${foodList.fileVO.filepath}" style="width:100%" ><br>
      <h5 class="w3-text-grey text-center">${foodList.menuName}</h5>
      <p class="w3-text-grey text-center">${foodList.menuPrice}</p><br><br>
      </c:forEach>
    </div>
  </div>
</div>

<!-- Contact/Area Container -->
<div class="w3-container" id="where" style="padding-bottom:32px;">
  <div class="w3-content" style="max-width:700px">
    <h5 class="w3-center w3-padding-48"><span class="w3-tag w3-wide">WHERE TO FIND US</span></h5>
    <%-- <p style="text-align:center" id = "${truckDetailInfo.foodtruckName}"></p> --%>
    <center><p>${param.address}</p></center>
    <!-- <iframe src="http://map.naver.com/?elng=b481b050171860a69f8e9feaa83f5ce7&menu=route&etext=%EB%8F%84%EC%B0%A9%EC%A7%80%EC%9D%B4%EB%A6%84&elat=bde725fc7a337cbc16aedc4da72b54ae&pathType=1&slng=b5980529d9dd3d387c515f33c92a0f06&stext=%EC%B6%9C%EB%B0%9C%EC%A7%80%EC%9D%B4%EB%A6%84&slat=e50da12d3af9afe511ca714e39883728" style="width:100%;height:400px;"></iframe> -->
    <div id="map" style="width:100%;height:400px;"></div>

    <c:if test="${sessionScope.memberVO!=null&&sessionScope.memberVO.memberType!='seller'}">
    <h5 class="w3-center w3-padding-32"><span class="w3-tag w3-wide">REVIEW</span></h5>
   <form action="${pageContext.request.contextPath}/afterLogin_foodtruck/registerReview.do" target="_blank" method="post">
    <input type="radio" name="grade" id="star-1" value="1"/>
    <label for="star-1" class="star_point" style="width:20px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-2" value="2"/>
    <label for="star-2" class="star_point" style="width:20px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-3" value="3" checked="checked"/>
    <label for="star-3" class="star_point" style="width:20px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-4" value="4"/>
    <label for="star-4" class="star_point" style="width:20px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-5" value="5"/>
    <label for="star-5" class="star_point" style="width:20px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
<table class="content">
<div class="form-group">
   <tr>
      <td>
         <table>
            <tr>
               <div class="form-group">
			      <textarea class="form-control" rows="5" cols="100" id="content" name="reviewContent" required="required" style="resize: none;"></textarea>
			   </div>
            </tr>
            <tr>
               <input type="button" id="registerBtn" value="Register" class="btn btn-default" style="float: right;"/>                
            </tr>
         </table>
      </td>
   </tr>
   </div>
</table>
</form>
</c:if>
    <!-- review 결과 table -->
    <h5 class="w3-center w3-padding-32"><span class="w3-tag w3-wide">REVIEW LIST</span></h5>      
  <table class="table table-hover" id="table">
    <thead>
      <tr>
        <th>SCORE</th>
        <th>COMMENT</th>
        <th>WRITER</th>
        <th>DATE</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
     <c:forEach items="${reviewlist.reviewList}" var="reviewVO">
      <tr>
<!-- 별점 리스트 img -->
<c:choose>
    <c:when test="${reviewVO.grade eq '1'}">
    <td>
    <input type="radio" name="grade" id="star-1" value="1"/>
    <label for="star-1" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    </td>
    </c:when>
    <c:when test="${reviewVO.grade eq '2'}">
    <td>
    <input type="radio" name="grade" id="star-1" value="1"/>
    <label for="star-1" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    <input type="radio" name="grade" id="star-2" value="2"/>
    <label for="star-2" class="star_point_list" style="width:10px">
      <span><i class="fa fa-star" aria-hidden="true"></i></span>
    </label>
    </td>
    </c:when>
    <c:when test="${reviewVO.grade eq '3'}">
    <td>
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
    </td>
    </c:when>
    <c:when test="${reviewVO.grade eq '4'}">
    <td>
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
    </td>
    </c:when>
    <c:otherwise>
    <td>
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
    </td>
    </c:otherwise>
</c:choose>
<!-- 별점 리스트 img -->
        <td>${reviewVO.reviewContent}</td>
        <td>${reviewVO.customerId}</td>
        <td>${reviewVO.reviewTimeposted}</td>
        <td>
        <c:if test="${sessionScope.memberVO.id!=null && sessionScope.memberVO.id==reviewVO.customerId}">
        <input type="hidden" value="${reviewVO.reviewNo}" name="reviewNo">
          <input type="hidden" value="${truckDetailInfo.foodtruckNumber}" name="truckNo">
          <button type="button" class="deleteBtn btn btn-default btn-sm">
          <span class="glyphicon glyphicon-remove"></span>
        </button>
        </c:if>
        </td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
<!-- review 결과 테이블 -->
<p class="paging text-center">
   <c:set var="pb" value="${reviewlist.pagingBean}"></c:set>
   <!-- 
         step2 1) 이전 페이지 그룹이 있으면 이미지 보여준다. (img/left_arrow_btn.gif)
                     페이징빈의 previousPageGroup 이용 
               2)  이미지에 이전 그룹의 마지막 페이지번호를 링크한다. 
                      hint)   startPageOfPageGroup-1 하면 됨        
    -->      
   <c:if test="${pb.previousPageGroup}">
   <a href="${pageContext.request.contextPath}/foodtruck/foodTruckAndMenuDetail.do?reviewPageNo=${pb.startPageOfPageGroup-1}&foodtruckNo=${truckDetailInfo.foodtruckNumber}">
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
   <a href="${pageContext.request.contextPath}/foodtruck/foodTruckAndMenuDetail.do?reviewPageNo=${i}&foodtruckNo=${truckDetailInfo.foodtruckNumber}">${i}</a> 
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
   <a href="${pageContext.request.contextPath}/foodtruck/foodTruckAndMenuDetail.do?reviewPageNo=${pb.endPageOfPageGroup+1}&foodtruckNo=${truckDetailInfo.foodtruckNumber}">
   ▶<!-- <img src="img/right_arrow_btn.gif"> --></a>
   </c:if>
   </p>

  </div>
</div>
</div>
<!-- End page content -->


<!-- Add Google Maps -->
<script>
/* function myMap()
{
  myCenter=new google.maps.LatLng(41.878114, -87.629798);
  var mapOptions= {
    center:myCenter,
    zoom:12, scrollwheel: false, draggable: false,
    mapTypeId:google.maps.MapTypeId.ROADMAP
  };
  var map=new google.maps.Map(document.getElementById("googleMap"),mapOptions);

  var marker = new google.maps.Marker({
    position: myCenter,
  });
  marker.setMap(map);
} */

// Tabbed Menu
function openMenu(evt, menuName) {
  var i, x, tablinks;
  x = document.getElementsByClassName("menu");
  for (i = 0; i < x.length; i++) {
     x[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablink");
  for (i = 0; i < x.length; i++) {
     tablinks[i].className = tablinks[i].className.replace(" w3-dark-grey", "");
  }
  document.getElementById(menuName).style.display = "block";
  evt.currentTarget.firstElementChild.className += " w3-dark-grey";
}
document.getElementById("myLink").click();
</script>
<script>
   $(document).ready(function(){
      $("#registerBtn").click(function(){
         var grade = $("input[name=grade]:checked").val();
         var reviewContent = $("#content").val();
         var d = new Date();
         if(confirm("리뷰를 등록하시겠습니까?")){
            $.ajax({
            	type:"post",
            	url:"${pageContext.request.contextPath}/afterLogin_foodtruck/registerReview.do",
                data:"grade="+grade+"&reviewContent="+reviewContent+"&customerId=${sessionScope.memberVO.id}"+"&foodtruckNumber=${truckDetailInfo.foodtruckNumber}"
                +"&reviewTimeposted="+d.toString(),
                success:function(result){
                	alert("등록되었습니다.");
                	location.href="${pageContext.request.contextPath}/foodtruck/foodTruckAndMenuDetail.do?foodtruckNo=${truckDetailInfo.foodtruckNumber}";   	
                }
            })
         }
      });  

		$(".deleteBtn").click(function(){
			var reviewNo=$(this).parent().parent().find(":input[name=reviewNo]").val();
	    	var truckNo =$(this).parent().parent().find(":input[name=truckNo]").val();
 			if(confirm("등록된 리뷰를 삭제하시겠습니까?")){
 				$.ajax({
 					url:"${pageContext.request.contextPath}/afterLogin_mypage/deleteMyReview.do",
 					type:"post",
 					data:"reviewNo="+reviewNo,
 					success:function(data){
 						if(data=="deleteOk"){
 							alert("삭제하였습니다.");
 							location.href="${pageContext.request.contextPath}/foodtruck/foodTruckAndMenuDetail.do?foodtruckNo="+truckNo;
 						}
 					} 					
 				});
 			}
 		});
   });
</script>

<script>
var HOME_PATH = window.HOME_PATH || '.';
var position = new naver.maps.LatLng("${param.latitude}", "${param.longitude}");

var map = new naver.maps.Map('map', {
    center: position,
    zoom: 8,
    mapTypeId: naver.maps.MapTypeId.NORMAL    
});

var infowindow = new naver.maps.InfoWindow();

var symbolMarker = new naver.maps.Marker({
    position: position,
    map: map,
    icon: {
        url: "${pageContext.request.contextPath}/resources/img/location.png",
        size: new naver.maps.Size(40, 35),
        origin: new naver.maps.Point(0, 0),
        anchor: new naver.maps.Point(11, 35)        	
    }
});
var foodTruckInfo = [
       {
     	   latitude : "${requestScope.truckDetailInfo.latitude}",
    	   longtitude : "${requestScope.truckDetailInfo.longitude}",
    	   foodtruckName : "${requestScope.truckDetailInfo.foodtruckName}" 	   
       }
];

var latlngs = [
];

for (var i = 0; i < foodTruckInfo.length; i++)
{
	latlngs.push(new naver.maps.LatLng(foodTruckInfo[i].latitude, foodTruckInfo[i].longtitude));
}	

var markers = [];
var infoWindows = [];

for (var i=0, ii=latlngs.length; i<ii; i++) {
    var icon = {
            url: HOME_PATH +'/img/example/sp_pins_spot_v3.png',
            size: new naver.maps.Size(24, 37),
            anchor: new naver.maps.Point(12, 37),
            origin: new naver.maps.Point(i * 29, 0)
        },
        marker = new naver.maps.Marker({
            position: latlngs[i],
            map: map,
            icon: {
                url: HOME_PATH +'/img/example/sp_pins_spot_v3.png',
                size: new naver.maps.Size(24, 37),
                anchor: new naver.maps.Point(12, 37),
                origin: new naver.maps.Point(0, 0)
            },
            zIndex: 100
        });
    
    var infoWindow = new naver.maps.InfoWindow({
        content: '<div style="width:150px;text-align:center;padding:5px;">"' + foodTruckInfo[i].foodtruckName + '"</div>'
    });    

    markers.push(marker);
    infoWindows.push(infoWindow);
}

function updateMarkers(map, markers) {

    var mapBounds = map.getBounds();
    var marker, position;

    for (var i = 0; i < markers.length; i++) {

        marker = markers[i]
        position = marker.getPosition();

        if (mapBounds.hasLatLng(position)) {
            showMarker(map, marker);
        } else {
            hideMarker(map, marker);
        }
    }
}

function showMarker(map, marker) {

    if (marker.setMap()) return;
    marker.setMap(map);
}

function hideMarker(map, marker) {

    if (!marker.setMap()) return;
    marker.setMap(null);
}

function getClickHandler(seq) {
    return function(e) {
        var marker = markers[seq],
            infoWindow = infoWindows[seq];

        if (infoWindow.getMap()) {
            infoWindow.close();
        } else {
            infoWindow.open(map, marker);
        }
    }
}
for (var i=0, ii=markers.length; i<ii; i++) {
    naver.maps.Event.addListener(markers[i], 'click', getClickHandler(i));
}
</script>

