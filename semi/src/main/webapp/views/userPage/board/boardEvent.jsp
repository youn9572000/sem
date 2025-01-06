<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.kh.admin.model.vo.Board, com.kh.common.model.vo.PageInfo"%>
<%
	List<Board> list = (List<Board>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>행사게시판</title>
<link rel="stylesheet"	href="<%=request.getContextPath()%>/resources/css/board.css" type="text/css">
<style>
button {
	width: 100px !important;
	height: 40px !important;
	font-size: 14px;
}

.page-btn {
	width: 50px !important;
	height: 40px !important;
}
</style>
	
</head>
<body>


	<%@ include file="/views/common/menubar.jsp"%>
	
	<!--sub_visual-->
    <div id="sub_visual" style="background:linear-gradient(90deg, #7040ff, #d588ff);">
        <h1>행사게시판</h1>
        <p>편리와 관련된 행사를 안내 드립니다.
        </p>
    </div>
    <!--//sub_visual-->

	<main>
		<section class="notice-section">
			<h2>행사게시판</h2>

			<!-- 검색 및 정렬 -->
			<div class="search-bar">
				<div class="sort-dropdown">
					<button class="sort-icon-btn">정렬 ▼</button>
					<div class="sort-options">
						<a href="<%= request.getContextPath() %>/board/event?cpage=1&sort=date">날짜순</a>
    					<a href="<%= request.getContextPath() %>/board/event?cpage=1&sort=count">조회수순</a>
    					<a href="<%= request.getContextPath() %>/board/event?cpage=1&sort=plus">추천순</a>
					</div>
				</div>
				<input type="text" id="searchInput" placeholder="검색어를 입력하세요" />
				<button onclick="filterTable()">🔍 검색</button>
			</div>

			<!-- 공지사항 테이블 -->
			<table class="notice-table">
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>날짜</th>
						<th>추천수</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody id="noticeTableBody">
					<!-- JSP로 공지사항 데이터를 렌더링 -->
					<%if(list.isEmpty()){ %>
					<tr>
						<td colspan="6">조회된 리스트가 없습니다.</td>
					</tr>
					<%} else {%>
					<%for(Board b : list){ %>
					<tr>
						<td><%= b.getBoardNo() %></td>
						<td><%= b.getBoardTitle() %></td>
						<td><%= b.getBoardWriter() %></td>
						<td><%= b.getCreateDate() %></td>
						<td><%= b.getPuls() %></td>
						<td><%= b.getCount() %></td>
					</tr>
						<%} %>
					<%} %>
				</tbody>
			</table>

			<!-- 페이지네이션 -->
			<div class="pagination">
				<% if (currentPage > 1) { %>
			        <!-- 이전 페이지 이동 -->
			        <button class="page-btn" onclick="location.href='<%= request.getContextPath() %>/board/event?cpage=1'">&lt;&lt;</button>
			        <button class="page-btn" onclick="location.href='<%= request.getContextPath() %>/board/event?cpage=<%= (currentPage > 1) ? (currentPage - 1) : 1 %>'"<%= (currentPage <= 1) ? "disabled" : "" %>>&lt;</button>
			    <% } %>
			
			    <% for (int p = startPage; p <= endPage; p++) { %>
			    <button class="page-btn <%= (currentPage == p) ? "active" : "" %>" 
        			onclick="location.href='<%= request.getContextPath() %>/board/event?cpage=<%= p %>'">
    			<%= p %>
				</button>			    
			    <% } %>
			
			    <% if (currentPage < maxPage) { %>
			        <!-- 다음 페이지 이동 -->
			        <button class="page-btn" onclick="location.href='<%= request.getContextPath() %>/board/event?cpage=<%= (currentPage < maxPage) ? (currentPage + 1) : maxPage %>'"<%= (currentPage >= maxPage) ? "disabled" : "" %>>&gt;</button>
			        <button class="page-btn" onclick="location.href='<%= request.getContextPath() %>/board/event?cpage=<%= maxPage %>'">&gt;&gt;</button>
			    <% } %>
			</div>
		</section>
	</main>

	<script>
	function movePage(cpage){
		location.assign('<%= request.getContextPath() %>/board/event/list?cpage='+cpage);
	}
	
	$(function(){
		$(".notice-table>tbody>tr").click(function(){
			location.assign("<%= request.getContextPath() %>/board/detail?boardNo="+$(this).children().eq(0).text()+"&cPage=<%= currentPage %>");
		})
	})
	
	// 드롭다운 토글 (선택 사항)
	document.addEventListener('DOMContentLoaded', () => {
	    const dropdownButton = document.querySelector('.sort-icon-btn');
	    const dropdownMenu = document.querySelector('.sort-options');

	    dropdownButton.addEventListener('click', (event) => {
	        event.stopPropagation(); // 이벤트 버블링 방지
	        dropdownMenu.classList.toggle('show');
	    });

	    document.addEventListener('click', (event) => {
	        if (!dropdownMenu.contains(event.target)) {
	            dropdownMenu.classList.remove('show');
	        }
	    });
	});
    

    // 검색 기능
    function filterTable() {
        const searchInput = document.getElementById("searchInput").value.toLowerCase();
        const rows = document.querySelectorAll("#noticeTableBody tr");
        
        rows.forEach(row => {
            const title = row.children[1].innerText.toLowerCase();
            const author = row.children[2].innerText.toLowerCase();
            if (title.includes(searchInput) || author.includes(searchInput)) {
                row.style.display = ""; // 표시
            } else {
                row.style.display = "none"; // 숨김
            }
        });
    }
    </script>
    
    <!-- footer -->
    <div id="footer_wrap">
        <div id="footer">
            <p class="f_logo">
                <a href="#"><img src="/semi/resources/img1/logo.png" alt="logo"></a>
            </p>
            <div class="f_copyright">
                <ul class="menu">
                    <li><a href="#">회사소개</a></li>
                    <li><a href="#">고객센터</a></li>
                    <li><a href="#">IR</a></li>
                    <li><a href="#">인재채용</a></li>
                    <li><a href="#">찾아오시는길</a></li>
                    <li><a href="#" style="font-weight: bold;">개인정보처리방침</a></li>
                </ul>
                <p class="info">
                    서울특별시 강남구 테헤란로 130 호산빌딩, 5F, 6F<br> 사업자등록번호 :
                    487-86-00763│통신판매신고번호 : 851-87-00622<br> 고객만족센터 :
                    012-345-6789(무료상담전화)│대표전화 : 01-234-5678
                </p>
                <p class="copyright">Copyright © 편리 Inc. All Rights Reserved.</p>
            </div>
            <div class="f_site">
                <select class="family" onchange="window.location.href=this.value;">
                    <option value="#">Family Site</option>
                    <option value="https://cu.bgfretail.com">CU</option>
                    <option value="http://gs25.gsretail.com">GS25</option>
                    <option value="https://www.7-eleven.co.kr">7-ELEVEN</option>
                </select>
                <div class="banner">
                    <img src="/semi/resources/img1/award_2017.jpg" alt="수상">
                </div>
            </div>
        </div>
    </div>
    <!-- //footer -->

</body>
</html>