<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<%@ page
	import="java.util.List, com.kh.admin.model.vo.Board, com.kh.common.model.vo.PageInfo,
                 com.kh.admin.model.vo.Reply, com.kh.admin.model.vo.Review"%>
<%
List<Board> boardList = (List<Board>) request.getAttribute("boardList");
List<Reply> replyList = (List<Reply>) request.getAttribute("replyList");
List<Review> reviewList = (List<Review>) request.getAttribute("reviewList");

PageInfo boardPi = (PageInfo) request.getAttribute("boardPi");
PageInfo replyPi = (PageInfo) request.getAttribute("replyPi");
PageInfo reviewPi = (PageInfo) request.getAttribute("reviewPi");
%>

<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>신고 관리</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/adminCss/reportManagement.css">
</head>
<body>
	<div class="container">
			<!-- 사이드바 -->
	
	        <%@ include file="/views/common/AdminSidebar.jsp" %>


		<!-- 메인 콘텐츠 -->
		<div class="main-content">
			<div class="header-container">
				<h1>신고 관리</h1>
				<div class="admin-icons">
					<a href="#" class="tooltip" id="logout-btn"> <img
						src="<%=contextPath%>/resources/logout이미지.png" alt="로그아웃">
					</a> <a href="<%=contextPath%>/views/adminPage/admin/MainPage.jsp"
						class="tooltip" id="home-btn"> <img
						src="<%=contextPath%>/resources/mainPage이동.png" alt="메인페이지 이동">
					</a>
				</div>
			</div>
			</header>
			<!-- 게시글 신고 목록 -->
			<div class="report-card">
				<h2>게시글 신고 목록</h2>
				<table>
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회수</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody>
						<%
						if (boardList != null && !boardList.isEmpty()) {
							for (Board board : boardList) {
						%>
						<tr>
							<td><%=board.getBoardNo()%></td>
							<td><%=board.getBoardTitle()%></td>
							<td><%=board.getBoardWriter()%></td>
							<td><%=board.getCreateDate()%></td>
							<td><%=board.getCount()%></td>
							<td>
								<form action="<%=request.getContextPath()%>/ReportManagerDelete"
									method="post" onsubmit="return confirm('이 게시글을 삭제하시겠습니까?');">
									<input type="hidden" name="boardNo"
										value="<%=board.getBoardNo()%>">
									<button type="submit" class="delete-btn">삭제</button>
								</form>
							</td>

						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="6">신고된 게시글이 없습니다.</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
				<!-- 페이징 처리 -->
				<!-- 게시글 페이징 -->
				<div class="pagination">
					<%
				for (int boardPage = boardPi.getStartPage(); boardPage <= boardPi.getEndPage(); boardPage++) {
				%>
					<a
						href="?boardPage=<%=boardPage%>&replyPage=<%=replyPi.getCurrentPage()%>&reviewPage=<%=reviewPi.getCurrentPage()%>"
						class="<%=boardPi.getCurrentPage() == boardPage ? "on" : ""%>">
						<%=boardPage%>
					</a>
					<%
				}
				%>
				</div>
				</table>
			</div>


			<!-- 댓글 신고 목록 -->
			<div class="report-card">
				<h2>댓글 신고 목록</h2>
				<ul class="report-list">
					<%
					if (replyList != null && !replyList.isEmpty()) {
					%>
					<%
					for (Reply reply : replyList) {
					%>
					<li>

						<div class="content">
							<%=reply.getReplyContent()%>
						</div>
						<div class="date">
							게시글 제목:
							<%=reply.getRboardTitle()%>
							<%=reply.getCreateDate()%>
						</div>
					<li>
						<form action="<%=request.getContextPath()%>/ReportManagerDelete"
							method="post" onsubmit="return confirm('이 댓글을 삭제하시겠습니까?');">
							<input type="hidden" name="replyNo"
								value="<%=reply.getReplyNo()%>">
							<button type="submit" class="delete-btn">삭제</button>
						</form>
					</li>
					</li>
					<%
					}
					%>
					<%
					} else {
					%>
					<li>신고된 댓글이 없습니다.</li>
					<%
					}
					%>
				</ul>
				<!-- 댓글 페이징 -->
				<div class="pagination">
					<%
					for (int replyPage = replyPi.getStartPage(); replyPage <= replyPi.getEndPage(); replyPage++) {
					%>
					<a
						href="?boardPage=<%=boardPi.getCurrentPage()%>&replyPage=<%=replyPage%>&reviewPage=<%=reviewPi.getCurrentPage()%>"
						class="<%=replyPi.getCurrentPage() == replyPage ? "on" : ""%>">
						<%=replyPage%>
					</a>
					<%
					}
					%>
				</div>



			</div>

			<!-- 리뷰 신고 목록 -->
			<div class="report-card">
				<h2>리뷰 신고 목록</h2>
				<ul class="report-list">
					<%
					if (reviewList != null && !reviewList.isEmpty()) {
					%>
					<%
					for (Review review : reviewList) {
					%>
					<li>

						<div class="content">
							<%=review.getReviewContent()%>
						</div>
						<div class="date">
							<%=review.getProductName()%>
							<%=review.getReviewDate()%>
						</div>
					<li>
						<form action="<%=request.getContextPath()%>/ReportManagerDelete"
							method="post" onsubmit="return confirm('이 리뷰를 삭제하시겠습니까?');">
							<input type="hidden" name="reviewNo"
								value="<%=review.getReviewNo()%>">
							<button type="submit" class="delete-btn">삭제</button>
						</form>
					</li>
					</li>

					<%
					}
					%>
					<%
					} else {
					%>
					<li>신고된 리뷰가 없습니다.</li>
					<%
					}
					%>
				</ul>
				<!-- 리뷰 페이징 -->
				<div class="pagination">
					<%
					for (int reviewPage = reviewPi.getStartPage(); reviewPage <= reviewPi.getEndPage(); reviewPage++) {
					%>
					<a
						href="?boardPage=<%=boardPi.getCurrentPage()%>&replyPage=<%=replyPi.getCurrentPage()%>&reviewPage=<%=reviewPage%>"
						class="<%=reviewPi.getCurrentPage() == reviewPage ? "on" : ""%>">
						<%=reviewPage%>
					</a>
					<%
					}
					%>
				</div>

			</div>

		</div>
	</div>

	<script>
    function toggleAll(type) {
        const checkboxes = document.querySelectorAll(`input[name="${type}Ids"]`);
        const checkAll = document.getElementById(`checkAll${type.charAt(0).toUpperCase() + type.slice(1)}s`);
        checkboxes.forEach(checkbox => checkbox.checked = checkAll.checked);
    }
</script>
</body>
</html>
