<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <jsp:include page="../mypage/mypage.jsp"/>
 <br><br>
    <div class="_40h7m" >
    <form class="form-signin" action="${pageContext.request.contextPath}/afterLogin_mypage/updateMyfoodtruck.do" method="post" enctype="multipart/form-data">
    <div class="_ljqf0 col-lg-12">
    		<div  class="col-lg-12" align="center">
    		 <button id="profileBtn" type="button" onclick=document.all.file.click(); >
    			<c:choose>
    				<c:when test="${truckVO.fileVO.filepath!='defaultTruck.jpg'}">
    			<img id="profileImg"  src="${pageContext.request.contextPath}/resources/upload/${truckVO.fileVO.filepath}" align="right">    				
    				</c:when>
    				<c:otherwise>
    				<img id="profileImg"  src="${pageContext.request.contextPath}/resources/upload/defaultTruck.jpg" align="middle">
    				</c:otherwise>
    			</c:choose>
    		</button>
    </div>
		<input type="file" name="foodtruckFile" id="file" style="display: none;"/> 
		</div>
 		<br><BR>
 		<div>
    		<aside>
    			<label for="foodtruckName">ID</label>
    		</aside>
    		<div>
    				<input type="text" name="" class="form-control" value="${sessionScope.memberVO.id }" readonly="readonly">
    		</div>
    	</div>
    	   <br>
    	<div>
    		<aside>
    			<label for="foodtruck">푸드트럭 넘버</label>
    		</aside>
    		<div>
    				<input type="text" name="foodtruckNumber" placeholder="푸드트럭 넘버를 입력해주세요" class="form-control" value="${truckVO.foodtruckNumber}" readonly="readonly">
    		</div>
    	</div>
    	<BR>
    	<div>
    		<aside>
    			<label for="foodtruckName">이름</label>
    		</aside>
    		<div>
    				<input type="text" name="foodtruckName" placeholder="푸드트럭 이름을 입력해주세요" class="form-control" value="${truckVO.foodtruckName}" required="required">
    		</div>
    	</div>
    		<br>
    	<div>
    		<aside>
    			<label for="foodtruck"><span class="glyphicon glyphicon-star" style="color:orange"> </span>&nbsp;&nbsp;${truckVO.avgGrade}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
    		<label for="foodtruck"><span class="glyphicon glyphicon-heart" style="color:red"></span>&nbsp;&nbsp;${truckVO.wishlistCount}</label>
    		</aside>
    	</div>
	<br>
    	<div>
    		<aside>
    			<label for="foodtruck">푸드트럭 소개</label>
    		</aside>
    		<div>
    				<textarea rows="4" cols="50"  name="introduction" placeholder="푸드트럭을 소개해주세요!" style="resize: none;" class="form-control"  required="required">${truckVO.introduction}</textarea>
    		</div>
    	</div>
	<br>
		<div align="center">
    		<input type="submit"  class="btn btn-warning"  value="수정" > &nbsp;&nbsp;&nbsp;
   			<input type="button" class="btn btn-warning"  id="deleteTruckBtn" value="트럭 삭제"> 
   	</div>
   </form>
   </div>
   <br><br>
   <div id="insertForm"></div>
  <script type="text/javascript">
  $(document).ready(function(){
      function readURL(input) {
          if (input.files && input.files[0]) {
              var reader = new FileReader(); //파일을 읽기 위한 FileReader객체 생성
              reader.onload = function (e) {
              //파일 읽어들이기를 성공했을때 호출되는 이벤트 핸들러
                  $("#profileImg").attr("src", e.target.result);
                  //이미지 Tag의 SRC속성에 읽어들인 File내용을 지정
                  //(아래 코드에서 읽어들인 dataURL형식)
              }                   
              reader.readAsDataURL(input.files[0]);
              //File내용을 읽어 dataURL형식의 문자열로 저장
          }
      }//readURL()--

      //file 양식으로 이미지를 선택(값이 변경) 되었을때 처리하는 코드
      $("#file").change(function(){
          readURL(this);
      });

      $("#deleteTruckBtn").click(function(){
    	  if(confirm("트럭을 삭제하시겠습니까?")){
    	  location.href="${pageContext.request.contextPath}/afterLogin_mypage/deleteMyTruck.do?foodtruckNumber=${truckVO.foodtruckNumber}";
    	  }
      });
      
   });
  

</script>

