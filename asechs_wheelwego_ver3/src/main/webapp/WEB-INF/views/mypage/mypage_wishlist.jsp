<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<style>
 .container-fluid {
      padding: 40px 50px 30px 40px;
  }
  .bg-grey {
      background-color: #f6f6f6;
  }
  .thumbnail {
      padding: 0 0 15px 0;
      border: 1px solid light grey;
      height:280px;
      position: relative;
      display: block;
  }
  .thumbnail img {
     /*  width: 100%;
      height:100%; */
      margin-bottom: 10px;
      
  }
  .col-sm-6{
  width: 50%;
  height: 300px;
  }
  .paging a {
    color: grey; 
   }
</style>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<style>
img{
    border-radius: 8px;
}
.overlay{
   border-bottom-left-radius: 8px;
   border-bottom-right-radius: 8px;
}
</style>
<jsp:include page="../mypage/mypage.jsp"/>
<br><br>

<div class="row">
<div class="container col-sm-12">
<c:choose>
<c:when test="${fn:length(wishlist.truckList) != 0}">
  <c:forEach items="${wishlist.truckList}" var="wishList">
      <div class="w3-third col-sm-4" >    
         <div class="w3-one" align="center">
<%--             <a href="${pageContext.request.contextPath}/foodtruck/foodTruckAndMenuDetail.do?foodtruckNo=${wishList.foodtruckNumber}">
               <img height="250px" width="260px" src="${pageContext.request.contextPath}/resources/upload/${wishList.fileVO.filepath}" style="position:relative;  z-index: 1; ">
                </a> --%>
            <%-- <a href="${pageContext.request.contextPath}/foodtruck/foodTruckAndMenuDetail.do?foodtruckNo=${wishList.foodtruckNumber}&latitude=${param.latitude}&longitude=${param.longitude}"> --%>
               <img class = "imgName" height="250px" width="260px" src="${pageContext.request.contextPath}/resources/upload/${wishList.fileVO.filepath}" style="position:relative;  z-index: 1;">
                <!-- </a> -->
                  <input  type="image" id="insertBtn" name = "${wishList.foodtruckNumber}" src = "${pageContext.request.contextPath }/resources/upload/hearton.png" 
                      style="right:40px;opacity: 0.8; z-index: 2;" >
               <div class="overlay" >
                  <div class="txtOverLay">
                     <h4>${wishList.foodtruckName}</h4>
                     <h5 id = "${wishList.foodtruckName}" class="address"></h5>
                     <input type="hidden" class="foodtruckNo" value="${wishList.foodtruckNumber}">
                  </div>
               </div>
            </div>
          </div>
  </c:forEach>
</c:when>
<c:otherwise>
         등록된 단골트럭이 없습니다.
</c:otherwise>
</c:choose>
</div>
</div>

<p class="paging text-center" style="font-size: 17px; color:black;">
   <c:set var="pb" value="${requestScope.wishlist.pagingBean}"></c:set>
   <!-- 
         step2 1) 이전 페이지 그룹이 있으면 이미지 보여준다. (img/left_arrow_btn.gif)
                     페이징빈의 previousPageGroup 이용 
               2)  이미지에 이전 그룹의 마지막 페이지번호를 링크한다. 
                      hint)   startPageOfPageGroup-1 하면 됨        
    -->      
   <c:if test="${pb.previousPageGroup}">
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/wishlist.do?pageNo=${pb.endPageOfPageGroup-1}&id=${pb.customerId}&latitude=${param.latitude}&longitude=${param.longitude}">◀&nbsp; </a>
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
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/wishlist.do?pageNo=${i}&id=${pb.customerId}&latitude=${param.latitude}&longitude=${param.longitude}">${i}</a> 
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
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/wishlist.do?pageNo=${pb.endPageOfPageGroup+1}&id=${pb.customerId}&latitude=${param.latitude}&longitude=${param.longitude}">${i}</a>
   <a href="${pageContext.request.contextPath}/afterLogin_mypage/wishlist.do?pageNo=${pb.endPageOfPageGroup+1}&id=${pb.customerId}&latitude=${param.latitude}&longitude=${param.longitude}">▶</a>
   <!-- <img src="img/right_arrow_btn.gif"> -->
   </c:if>
</p>

 <script>
$(document).ready(function(){

   $(".imgName").click(function(){
        var foodtruckNo=$(this).next().next().find(".foodtruckNo").val();
        var address=$(this).next().next().find(".address").html();
        location.href= "${pageContext.request.contextPath}/foodtruck/foodTruckAndMenuDetail.do?foodtruckNo="+foodtruckNo+"&latitude=${param.latitude}&longitude=${param.longitude}&address="+address;
      //alert(address.html());foodturckNumber")
   });
   
   
   
   $("input#insertBtn").click(function(){
        var foodtruckNumber = $(this).attr('name');
        var id = "${sessionScope.memberVO.id}";
        
        if(confirm("즐겨찾기를 해제하시겠습니까?") == false) {
                return false;
        }        
           
         $.ajax({
             type:"POST",
             url:"${pageContext.request.contextPath}/afterLogin_mypage/deleteWishList.do",
             data : {id: id, foodtruckNumber: foodtruckNumber} ,
             success:function(data){
                location.reload();
             },
             error:function(request,status,error){
                 alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
             }
          });
   });   
});

</script>

<script type="text/javascript">
   <c:forEach items="${requestScope.wishlist.truckList}" var="wishList" varStatus="status">
   var mapInfo = naver.maps.Service.reverseGeocode({
        location: new naver.maps.LatLng("${wishList.latitude}", "${wishList.longitude}"),
    }, function(status, response) {
        if (status !== naver.maps.Service.Status.OK) {
            //return alert('Something wrong!');
           document.getElementById("${wishList.foodtruckName}").innerHTML = "위치정보없음";
        }

        var result = response.result, // 검색 결과의 컨테이너
            items = result.items; // 검색 결과의 배열     
            document.getElementById("${wishList.foodtruckName}").innerHTML = items[0].address;
    });
   </c:forEach>
</script>
 

 
 
 
 