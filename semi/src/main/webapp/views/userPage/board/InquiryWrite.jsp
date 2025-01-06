<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.kh.user.model.vo.Category" %>
<% List<Category> list = (List<Category>) request.getAttribute("list"); %>		
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet"	href="<%=request.getContextPath()%>/resources/css/service.css"	type="text/css">
<link rel="stylesheet"	href="<%=request.getContextPath()%>/resources/css/inquiry.css"	type="text/css">

</head>
<body>

	<div class="container">
		<!-- Sidebar Include -->
		<div><%@ include file="/views/common/serviceSidebar.jsp"%></div>

		<div class="content">
			<!-- Header Include -->
			<div><%@ include file="/views/common/serviceHeader.jsp"%></div>

			<div class="inquiry">

				<h2 class="inquiry-title">1:1 문의</h2>  
				<form action="<%=request.getContextPath()%>/service/write" method="post" class="inquiry-form">
				
					<div class="form-group">
						<label for="title" class="form-label">📌 제목</label> 
						<input type="text" id="title" name="title" class="form-control"	placeholder="제목을 입력해 주세요." required>
					</div>
					
					<div class="form-group">
						<label for="content" class="form-label">📝 내용</label>
						<textarea id="content" name="content" class="form-control"	rows="6" placeholder="문의 내용을 입력해 주세요." required></textarea>
					</div>
					<button type="submit" class="btn-submit">🚀 문의등록하기</button>
				</form>

			</div>


		</div>
	</div>

</body>
</html>