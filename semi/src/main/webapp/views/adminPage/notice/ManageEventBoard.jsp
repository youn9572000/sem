<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<%@ page
	import="java.util.List, com.kh.admin.model.vo.Board, com.kh.common.model.vo.PageInfo"%>
<%
	List<Board> list = (List<Board>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
%>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>행사게시판 관리</title>
<!-- 공통 CSS는 AdminSidebar.jsp에 포함됨 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/adminCss/notice.css">
</head>
<body>
	<div class="container">
		<!-- 사이드바 삽입 -->
		<%@ include file="/views/common/AdminSidebar.jsp"%>

		<!-- 메인 컨텐츠 -->
		<div class="main-content">
			<header>
				<h1>행사게시판 관리</h1>
				<div class="admin-icons">
					<a href="#" class="tooltip" id="logout-btn"> <img
						src="<%=contextPath%>/resources/logout이미지.png" alt="로그아웃">
					</a> <a href="<%= contextPath %>/views/adminPage/admin/MainPage.jsp"
						class="tooltip" id="home-btn"> <img
						src="<%=contextPath%>/resources/mainPage이동.png" alt="메인페이지 이동"
						id="home">
					</a>
				</div>
				<script>
					document.getElementById('logout-btn').setAttribute(
							'data-tooltip', '로그아웃');
					document.getElementById('home-btn').setAttribute(
							'data-tooltip', '메인페이지 이동');
				</script>
			</header>
			<!-- 검색 박스 -->
			            <form class="search-box" action="${pageContext.request.contextPath}/admin/search"
				method="get">
				<input type="hidden" name="type" value="event" /> <input
					type="text" name="keyword" placeholder="검색어를 입력하세요"
					value="${param.keyword}" />
				<button type="submit">검색</button>
			</form>


			<!-- 테이블 -->
			<form action="<%=request.getContextPath()%>/noticeDelete"
				method="post" onsubmit="return confirm('선택한 항목을 삭제하시겠습니까?');">
				<table>
					<thead>
						<tr>
							<th><input type="checkbox" id="checkAll"
								onclick="toggleAllCheckboxes(this)"> 전체 선택</th>
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회수</th>
						</tr>
					</thead>
					<tbody>
						<%if(list.isEmpty()){ %>
						<tr>
							<td colspan="6">조회된 리스트가 없습니다.</td>
						</tr>
						<%} else {%>
						<%for(Board b : list){ %>
						<tr>
							<td><input type="checkbox" name="eboardNo"
								value="<%= b.getBoardNo() %>"></td>
							<td><%= b.getBoardNo() %></td>
							<td><%= b.getBoardTitle() %></td>
							<td><%= b.getBoardWriter() %></td>
							<td><%= b.getCreateDate() %></td>
							<td><%= b.getCount() %></td>
						</tr>
						<%} %>
						<%} %>
					</tbody>

				</table>

				<!-- 액션 버튼 -->
				<div class="action-buttons">
					<button type="submit" class="unblock-btn">선택삭제</button>
				</div>
			</form>

			<!-- 페이지네이션 -->
			<div align="center" class="pagination">
				<% if (currentPage > 1) { %>
				<!-- 이전 페이지 이동 -->
				<a href="<%= request.getContextPath() %>/admin/elist?cpage=1">&lt;&lt;</a>
				<a
					href="<%= request.getContextPath() %>/admin/elist?cpage=<%= currentPage - 1 %>">&lt;</a>
				<% } %>

				<% for (int p = startPage; p <= endPage; p++) { %>
				<a href="<%= request.getContextPath() %>/admin/elist?cpage=<%= p %>"
					class="<%= (currentPage == p) ? "active" : "" %>"> <%= p %>
				</a>
				<% } %>

				<% if (currentPage < maxPage) { %>
				<!-- 다음 페이지 이동 -->
				<a
					href="<%= request.getContextPath() %>/admin/elist?cpage=<%= currentPage + 1 %>">&gt;</a>
				<a
					href="<%= request.getContextPath() %>/admin/elist?cpage=<%= maxPage %>">&gt;&gt;</a>
				<% } %>
			</div>

			<script>
        	function movePage(cpage){
        		location.assign('<%= contextPath %>/admin/elist/list?cpage='+cpage);
        	}
        </script>
			<script>
    function toggleAllCheckboxes(source) {
        const checkboxes = document.querySelectorAll('input[name="boardNo"]');
        checkboxes.forEach(checkbox => {
            checkbox.checked = source.checked;
        });
    }
</script>
</body>
</html>
