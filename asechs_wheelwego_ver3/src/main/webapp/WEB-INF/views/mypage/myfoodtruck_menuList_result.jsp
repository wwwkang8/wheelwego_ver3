<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 박다혜 : 수정 완료 -->
<script type="text/javascript">
	alert("메뉴가 등록되었습니다.");
	location.href="${pageContext.request.contextPath}/afterLogin_mypage/myfoodtruck_menuList.do?foodtruckNumber=${sessionScope.foodtruckNumber}";
</script>