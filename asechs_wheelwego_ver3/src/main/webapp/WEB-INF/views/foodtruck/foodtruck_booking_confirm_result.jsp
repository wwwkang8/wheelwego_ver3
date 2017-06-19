<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	alert("주문 완료! 주문 내역은 마이페이지에서 확인 가능합니다.");
	location.href="${pageContext.request.contextPath}/home.do";
</script>