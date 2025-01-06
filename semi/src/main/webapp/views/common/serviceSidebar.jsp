<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/serviceHS.css"
	type="text/css">

</head>
<body>

	<div class="container">
		<div class="sidebar">
			<h1 class="logo">
				<a href="/semi/index.jsp"> <img
					src="<%=request.getContextPath()%>/resources/img1/logo.png"
					alt="편리하조 로고">
				</a>
			</h1>
			<h3><a href="<%=request.getContextPath()%>/views/userPage/board/serviceFAQ.jsp">자주 하는 질문</a></h3>
			<h3>
				<a href="<%=request.getContextPath()%>/views/userPage/board/serviceInquiry.jsp">1:1 문의</a>
			</h3>
			<h3><a href="<%=request.getContextPath()%>/views/userPage/member/idCheck.jsp">아이디/비밀번호 찾기</a></h3>
		</div>
	</div>
</body>
</html>