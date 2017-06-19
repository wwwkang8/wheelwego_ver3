<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<script type="text/javascript">
	alert(" ${requestScope.memberVO.id}님 환영합니다!");
	location.href="${pageContext.request.contextPath}/home.do";
</script>
