<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
      height:310px;
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
  height: 380px;
  }
.paging a {
    color: grey; 
}
select{
text-align-last:center;
}

select .selected{
text-indent: center;
}

</style>

<script>

$(document).ready(function(){
   var option="${requestScope.option}";
   var sel=document.getElementById("option");
   for(var i=0; i<sel.options.length; i++){
      if(sel.options[i].value==option){
         sel.options[i].selected = true;
      }
   }

	$(".detailLink").bind("click",function(){
		var address=$(this).parent().find(".address").text();
		var foodtruckNo=$(this).parent().find(":input[name=foodtruckNo]").val();
		var latitude=$(this).parent().find(":input[name=latitude]").val();
		var longitude=$(this).parent().find(":input[name=longitude]").val();
		$(this).attr("href","${pageContext.request.contextPath}/foodtruck/foodTruckAndMenuDetail.do?foodtruckNo="+foodtruckNo+"&latitude="+latitude+"&longitude="+longitude+"&address="+address);
	});

   $("input#insertBtn").click(function(){
     var foodtruckNumber = $(this).attr('name');
     var insertBtn=$(this);
     var id = "${sessionScope.memberVO.id}";

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
         $(insertBtn).attr('src','${pageContext.request.contextPath}/resources/upload/hearton.png'); 
            alert("단골트럭으로 등록!");
           location.reload();  
         }else{
            $(insertBtn).attr('src','${pageContext.request.contextPath}/resources/upload/greyheart2.png');
            alert("단골트럭 등록해제");         
            location.reload();  
         }
      }
   });
    }
});
   $(":input[name=option]").change(function(){
      var optionVal=$(this).val();
      var flag="${requestScope.flag}";
      if(flag=="false"){
           location.href="${pageContext.request.contextPath}/searchFoodTruckByName.do?latitude=${param.latitude}&longitude=${param.longitude}&name=${param.name}&option="+optionVal;
      }else{
           location.href="${pageContext.request.contextPath}/searchFoodTruckByGPS.do?latitude=${param.latitude}&longitude=${param.longitude}&option="+optionVal;
      }
   });
});
</script>

<script type="text/javascript">
   <c:forEach items="${requestScope.pagingList.truckList}" var="truckInfo" varStatus="status">
   var mapInfo = naver.maps.Service.reverseGeocode({
        location: new naver.maps.LatLng("${truckInfo.latitude}", "${truckInfo.longitude}"),
    }, function(status, response) {
        if (status !== naver.maps.Service.Status.OK) {
            //return alert('Something wrong!');
        }

        var result = response.result, // 검색 결과의 컨테이너
            items = result.items; // 검색 결과의 배열
            
            document.getElementById("${truckInfo.foodtruckName}").innerHTML = items[0].address;
    });
   </c:forEach>
</script>
<div class="container-fluid text-center bg-grey">
  <h3>FOODTRUCK LIST</h3><br>
  <div class="row">
  <div class="col-xs-4"></div>
  <div class="col-xs-4">
  <select name="option" id="option" class="form-control">
     <option value="byDate" class="selected">최신순</option>
      <option value="byAvgGrade" class="selected">평점순</option>
     <option value="byWishlist" class="selected">즐겨찾기순</option>
  </select>
</div>
<div class="col-xs-4"></div>
</div>
  <hr>
  <div class="row text-center" style="position:relative;">
  <c:forEach items="${requestScope.pagingList.truckList}" var="truckInfo">
    <div class="col-sm-6">
      <div class="thumbnail">
   <input type="hidden" name="foodtruckNo" value="${truckInfo.foodtruckNumber}">
      <input type="hidden" name="latitude" value="${truckInfo.latitude}">
      <input type="hidden" name="longitude" value="${truckInfo.longitude}">
      <a class="detailLink" href="${pageContext.request.contextPath}/foodtruck/foodTruckAndMenuDetail.do?foodtruckNo=${truckInfo.foodtruckNumber}&latitude=${truckInfo.latitude}&longitude=${truckInfo.longitude}">
        <img src="${pageContext.request.contextPath}/resources/upload/${truckInfo.fileVO.filepath}" style="width:300px;height:220px;">
        </a>
        <c:choose>
        <c:when test="${requestScope.heartWishlist!='[]'&& requestScope.heartWishlist!=null}">
        <c:forEach items="${requestScope.heartWishlist}" var="wishlistInfo">
        <c:choose>
           <c:when test="${wishlistInfo.foodtruckNumber eq truckInfo.foodtruckNumber}">
                 <input type="image" id="insertBtn" name = "${truckInfo.foodtruckNumber}" src = "${pageContext.request.contextPath}/resources/upload/hearton.png" style="z-index: 10;">
           </c:when>
           <c:otherwise>
              <input type="image" id="insertBtn" name = "${truckInfo.foodtruckNumber}" src = "${pageContext.request.contextPath}/resources/upload/greyheart2.png">
           </c:otherwise>
        </c:choose>
        </c:forEach>
       </c:when>
       <c:otherwise>
          <input type="image" id="insertBtn" name = "${truckInfo.foodtruckNumber}" src = "${pageContext.request.contextPath}/resources/upload/greyheart2.png">
       </c:otherwise>
       </c:choose>
        <strong style="font-size:15px; ">${truckInfo.foodtruckName}</strong><br>
        <strong style="font-size:15px; "><span class="glyphicon glyphicon-star" style="color:orange"> </span>&nbsp;&nbsp;${truckInfo.avgGrade}&nbsp;&nbsp;&nbsp;&nbsp; <span class="glyphicon glyphicon-heart" style="color:red"></span>&nbsp;&nbsp;${truckInfo.wishlistCount }</strong>
        <br><p id = "${truckInfo.foodtruckName}" style="font-size:13px; color: grey;" class="address"></p>
      </div>
    </div>
  </c:forEach>
  </div>
  <p class="paging text-center" style="font-size: 17px; color:black;">
   <c:set var="pb" value="${requestScope.pagingList.pagingBean}"></c:set>
   <!-- 
         step2 1) 이전 페이지 그룹이 있으면 이미지 보여준다. (img/left_arrow_btn.gif)
                     페이징빈의 previousPageGroup 이용 
               2)  이미지에 이전 그룹의 마지막 페이지번호를 링크한다. 
                      hint)   startPageOfPageGroup-1 하면 됨        
    -->      
   <c:if test="${pb.previousPageGroup}">
         <c:choose>
            <c:when test="${requestScope.flag==false}">
               <a href="${pageContext.request.contextPath}/searchFoodTruckByName.do?pageNo=${pb.startPageOfPageGroup-1}&latitude=${param.latitude}&longitude=${param.longitude}&name=${requestScope.name}&option=${requestScope.option}">◀&nbsp; </a>
            </c:when>
            <c:otherwise>
               <a href="${pageContext.request.contextPath}/searchFoodTruckByGPS.do?pageNo=${pb.startPageOfPageGroup-1}&latitude=${param.latitude}&longitude=${param.longitude}&option=${requestScope.option}">◀&nbsp; </a>
            </c:otherwise>         
         </c:choose>   
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
         <c:choose>
            <c:when test="${requestScope.flag==false}">
               <a href="${pageContext.request.contextPath}/searchFoodTruckByName.do?pageNo=${i}&latitude=${param.latitude}&longitude=${param.longitude}&name=${requestScope.name}&option=${requestScope.option}">${i}</a>
            </c:when>
            <c:otherwise>
               <a href="${pageContext.request.contextPath}/searchFoodTruckByGPS.do?pageNo=${i}&latitude=${param.latitude}&longitude=${param.longitude}&option=${requestScope.option}">${i}</a>
            </c:otherwise>         
         </c:choose> 
   </c:when>
   <c:otherwise>
   ${i}
   </c:otherwise>
   </c:choose>
   &nbsp;
   </c:forEach>

   <c:if test="${pb.nextPageGroup}">
         <c:choose>
            <c:when test="${requestScope.flag==false}">
               <a href="${pageContext.request.contextPath}/searchFoodTruckByName.do?pageNo=${pb.endPageOfPageGroup+1}&latitude=${param.latitude}&longitude=${param.longitude}&name=${requestScope.name}&option=${requestScope.option}">▶</a>
            </c:when>
            <c:otherwise>
               <a href="${pageContext.request.contextPath}/searchFoodTruckByGPS.do?pageNo=${pb.endPageOfPageGroup+1}&latitude=${param.latitude}&longitude=${param.longitude}&option=${requestScope.option}">▶</a>
            </c:otherwise>         
         </c:choose>   
   </c:if>   
</p>
<br><br>
</div>
<br><br>
<br><br>


<script type="text/javascript">
   <c:forEach items="${requestScope.pagingList.truckList}" var="truckInfo" varStatus="status">
   var mapInfo = naver.maps.Service.reverseGeocode({
        location: new naver.maps.LatLng("${truckInfo.latitude}", "${truckInfo.longitude}"),
    }, function(status, response) {
        if (status !== naver.maps.Service.Status.OK) {
            //return alert('Something wrong!');
        }

        var result = response.result, // 검색 결과의 컨테이너
            items = result.items; // 검색 결과의 배열
            if(items[0].address=="" || items[0].address==null){
               document.getElementById("${truckInfo.foodtruckName}").innerHTML="위치 정보 없음";
            }else{
            document.getElementById("${truckInfo.foodtruckName}").innerHTML = items[0].address;
            }
    });
   </c:forEach>

</script>
