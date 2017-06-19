<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
/*별점*/
input[name="grade"]{
  display:none;
}

.star_point{
  font-size:20pt;
  color:gold;
  cursor:pointer;
}
input[name="grade"]:checked + .star_point~label{
    color:lightgray;
}
</style>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
<div class="col-sm-2"></div>
<div class="col-sm-8">
<form method="post" id="updateForm" action="${pageContext.request.contextPath}/afterLogin_mypage/updateMyReview.do">
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
   <tr>
      <td>
         <table>
            <tr>
               <td>
               <div class="form-group row">
               <%-- <input type="hidden" name="reviewNo" value="${reviewVO.reviewNo}">
               TruckNo <input type="text" class="form-control" name="foodtruckNumber" value="${reviewVO.foodtruckNumber}" readonly="readonly"></input>
               | Writer <input type="text" class="form-control" name="customerId" value="${reviewVO.customerId}" readonly="readonly"></input>   
               | Date <input type="text" class="form-contro " name="reviewTimeposted" value="${reviewVO.reviewTimeposted}" readonly="readonly"></input><br> --%>   
                <input type="hidden" name="reviewNo" value="${reviewVO.reviewNo}">
                <div class="col-xs-2">
                   <label for="ex1">TruckNo</label>
                   <input class="form-control" id="ex1" type="text" name="foodtruckNumber" value="${reviewVO.foodtruckNumber}" readonly="readonly">
                 </div>
                 <div class="col-xs-3">
                   <label for="ex1">TruckName</label>
                   <input class="form-control" id="ex1" type="text" name="foodtruckName" value="${reviewVO.foodtruckName}" readonly="readonly">
                 </div>
                 <div class="col-xs-3">
                   <label for="ex2">Writer</label>
                   <input class="form-control" id="ex2" type="text" name="customerId" value="${reviewVO.customerId}" readonly="readonly">
                 </div>
                 <div class="col-xs-4">
                   <label for="ex3">Date</label>
                   <input class="form-control" id="ex3" type="text" name="reviewTimeposted" value="${reviewVO.reviewTimeposted}" readonly="readonly">
                 </div>
               </div>
               </td>
            </tr>
            <tr></tr>
            <tr>
               <td>                  
      <textarea rows="5" cols="100" id="content" class="form-control" name="reviewContent" required="required" style="resize: none;" autofocus>${reviewVO.reviewContent}</textarea>
               </td>
            </tr>
            <tr>
            <td valign="middle">  
            <br>
               <input type="submit" value="Update" class="btn" style="float: right;"></input>         
               </td>            
            </tr>
         </table>
      </td>
   </tr>
</table>
</form>   
</div>
<div class="col-sm-2"></div>
</div>
<script>
   var grade=   "${reviewVO.grade}";
$(document).ready(function(){
   var gradeRadio=$(":input[name=grade]");
   for(var i=0; i<gradeRadio.length;i++){
         //alert(gradeRadio[i].value);
         gradeRadio[i].checked=true;
      }
      //alert(gradeRadio);
})
</script>