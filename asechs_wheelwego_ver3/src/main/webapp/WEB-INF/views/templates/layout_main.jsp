<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles"  uri="http://tiles.apache.org/tags-tiles" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Wheel, We go!</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/alertify/themes/alertify.core.css" />
	<link rel="stylesheet"  type="text/css" href="${pageContext.request.contextPath}/resources/alertify/themes/alertify.default.css" id="toggleCSS" />
	<script src="${pageContext.request.contextPath}/resources/alertify/lib/alertify.min.js"></script>

 <!-- jQuery -->
    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
    <!-- Plugin JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
    
    <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
	<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=3IKOAkT6zc5H36iz7sma&submodules=geocoder"></script>    
  
  
    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Theme CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/asechs.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css">
</head>
<body id="page-top" class="index">
	<div id="header"><tiles:insertAttribute name="header" /></div>
	<div id="wrapper">
	<div id="main" ><tiles:insertAttribute name="main" /></div>
	</div>
	<jsp:include page="../foodtruck/noticePage.jsp"/>
	<div id="page_footer"><tiles:insertAttribute name="footer" /></div>
</body>
</html>