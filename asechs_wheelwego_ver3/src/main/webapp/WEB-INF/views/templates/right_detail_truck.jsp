<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
.btn:focus{
 outline:none !important;
 }
</style>
<div class="container">
   <div class="dropdown">
      <button class="btn btn-default dropdown-toggle" type="button"
         data-toggle="dropdown">
         SELECT <span class="caret"></span>
      </button>
      <ul class="dropdown-menu">
         <c:forEach items="${requestScope.truckDetailInfo.foodList}" var="foodList">
            <li>
             <a href="" name="menu" value="${foodList.menuName}">
             ${foodList.menuName}</a>
            <input type="hidden" value="${foodList.menuPrice}">
            <input type="hidden" value="${foodList.menuId}">
            </li>
         </c:forEach>
      </ul>
   </div>

  <form name="form" method="post" id="orderform" action="${pageContext.request.contextPath}/afterLogin_foodtruck/foodtruck_booking_confirm.do">
   <table class="table table-hover" id="testTable" style="width:30%">
   <thead>
   <tr>
   <th>MENU</th>
   <th style="width:30%;">AMOUNT</th>
   <th>TOTAL</th>
   <th></th>
   </tr>
   <thead>
   <tbody>
   </tbody>
   </table>
   <hr>
   TOTAL  <span id="total" style="font-weight: bold;"></span><span style="padding-left: 150px;"><input type="button" id="orderBtn" class="btn btn-xs" value="order" style="position: fixed; right:30px"></span>
   </form>
</div>
<script>
$(document).ready(function(){
    $("#orderBtn").click(function(){
       $("#orderform").submit();
        /* if(confirm("주문하시겠습니까?")){
           alert("주문페이지로 이동합니다.");
           $("#orderform").submit();
        }  */
     });  
}); // ready
</script>
<script>
   $(document).ready(function() {
      var cnt=0;
      var arr = [''];
      $("ul.dropdown-menu a").click(function(e){
         var menu = $(this).attr('value');
         var price = $(this).next().val();
         var menuId = $(this).next().next().val();
         var flag = false;
         var id = "${sessionScope.memberVO.id}";     
         if(id==""){
             alert("로그인이 필요합니다.");
          }
          else if("${sessionScope.memberVO.memberType}"=='seller'){
             alert("일반회원 전용 서비스입니다.");
          }
         
          else{

         for(var i=0; i<arr.length; i++)   {
            if(arr[i]==menu){
               flag=true;
            }
         }
         if(flag==false){
            if ("${requestScope.bookingPossible}" == "no"){
               alert("해당 푸드트럭 근처에 위치하고 있지 않기 때문에 주문이 불가능합니다.")
               return false;
            }
            else{
               arr.push(menu);
            }
         }
         else{
            alert("이미 메뉴를 선택하셨습니다. 수량을 체크해주세요.");
            return false;
         }
         e.preventDefault();
          $("#testTable > tbody:last-child").append("<tr>"+
                       "<td><input type='hidden' name='bookingDetail["+cnt+"].menuName' value='"+menu+"'>"+menu+"</td>"+
                       "<input type='hidden' name='bookingDetail["+cnt+"].menuId' value='"+menuId+"'>"+
                       "<td>"+
                       "<input type='hidden' class='menuPrice' name='bookingDetail["+cnt+"].menuPrice' value='"+price+"'>"+
                       "<input type='number' name='bookingDetail["+cnt+"].bookingQuantity' class='countId' value='1' size='1' style='width:30%;' onclick='change()' min='1'>"+
                       "</td>"+
                       "<td><input type='text' class='sumId' name='sum' size='4' readonly value="+price+"></td>"+
                       "<td>"+
                       "<span class='glyphicon glyphicon-remove' role='button'></span></td></tr>");

          
            cnt++;
          totalPrice();
          }

       }); //dropdown
      $("#testTable").on("change",":input[type=number]",function(){
         var unitPrice=$(this).parent().find(".menuPrice").val();
         var price=$(this).parent().next().find(":input[name=sum]");
         var amount=$(this).val();
         price.val(parseInt(unitPrice)*parseInt(amount));

         totalPrice();
      });//수량 및 총액계산
      
      $("#testTable").on("click",".glyphicon",function(){
         var menu = $(this).attr('value');
         
         if(confirm("메뉴를 삭제하시겠습니까?")){
            $(this).parent().parent().remove();
            //arr.pop(menu);
            totalPrice();
         }
      });
   
   }); //ready
function totalPrice(){
   var sum_val=0;
    for(var i=0; i<document.getElementsByName("sum").length; i++){
       sum_val += parseInt(document.getElementsByName("sum")[i].value);
    }
    $("#total").text(sum_val);
   }
</script>