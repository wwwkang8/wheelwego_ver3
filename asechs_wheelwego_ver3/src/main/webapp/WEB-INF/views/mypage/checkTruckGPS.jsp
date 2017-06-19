<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
function popupOpen(){
   var popUrl = "${pageContext.request.contextPath}/afterLogin_mypage/updateTruckGPS.do?";   //팝업창에 출력될 페이지 URL

   var popOption = "width=370, height=360, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)

      window.open(popUrl,"",popOption);
      setTimeout(function(){
           win.close()
       }, 3000);
   }
</script>


<c:choose>
   <c:when test="${requestScope.gpsInfo.latitude != null}">
      <script>
         if (confirm("푸드 트럭 위치를 이동하시겠습니까?") == true){
            location.href = "${pageContext.request.contextPath}/afterLogin_mypage/set_TruckGPS.do?sellerId=${sessionScope.memberVO.id}";
         }else{
            location.href = "${pageContext.request.contextPath}/home.do";
         }      
      </script>
   </c:when>
   <c:otherwise>
      <script>
         if (confirm("푸드 트럭 위치를 설정하시겠습니까?") == true){
            popupOpen();
            //geoFindMe();
         }else{
            location.href = "${pageContext.request.contextPath}/home.do";
         }      
      </script>   
   </c:otherwise>
</c:choose>