<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.kh.admin.model.vo.Board, com.kh.user.model.dto.BoardDTO" %>
<%
    BoardDTO board = (BoardDTO) request.getAttribute("board");
	Board b = board.getB();
    String referer = (String) request.getAttribute("referer");
%>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의상세내용</title>

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
				<div class="inquiry detailed-inquiry">
					<!-- 문의 상세보기 내용이 들어갈 영역 -->
					<div class="detailed-header">
						<h2>📄 문의 상세보기</h2>
					</div>

					<div class="detailed-info">
						<div class="info-row">
							<span class="label">📝 제목:</span> 
							<span id="title"><%= b.getBoardTitle() %></span>
						</div>
						<div class="info-row">
							<span class="label">👤 작성자:</span> 
							<span id="author"><%= b.getBoardWriter() %></span>
						</div>
						<div class="info-row">
							<span class="label">⏰ 작성시간:</span> 
							<span id="time"><%= b.getCreateDate() %></span>
						</div>
					</div>

					<div class="detailed-content">
						<h3>📌 내용</h3>
						<p id="content"><%= b.getBoardContent() %></p>
					</div>

					<div class="detailed-comment">
						<h3>💬 관리자 답변</h3>
						<p id="comment">이마트 24도 등록예정이니 조금만 기다려주시면 감사하겠습니다.</p>
						<p class="comment-timestamp" id="comment-time">2024-12-11 22:40</p>
					</div>

					<div class="detailed-buttons">
						<button onclick="editContent()">✏️ 수정</button>
						<button onclick="deleteContent()">🗑️ 삭제</button>
						<button onclick="goToList()">📋 목록</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>

		// 🛠️ 버튼 기능
		function editContent() {
			window.location.href = 'edit.html';
		}

		function deleteContent() {
			if (confirm('정말 삭제하시겠습니까?')) {
				alert('삭제되었습니다.');
				window.location.href = 'delete?boardNo=<%= b.getBoardNo() %>';				   
			}
		}

		function goToList() {
			window.location.href = 'inquiry';
		}

		// 페이지 로드 시 데이터 렌더링
		window.onload = renderData;
	</script>



</body>
</html>