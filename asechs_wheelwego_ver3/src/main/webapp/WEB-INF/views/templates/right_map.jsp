<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<div id="map" style="width:100%;height:480px;"></div>

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
	<c:forEach items="${requestScope.pagingList.truckList}" var="truckInfo" varStatus="status">
       {
    	   latitude : "${truckInfo.latitude}",
    	   longtitude : "${truckInfo.longitude}",
    	   foodtruckName : "${truckInfo.foodtruckName}"
       }
       <c:if test="${not status.last}">,</c:if>
    </c:forEach>
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
	
