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
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>고객센터</title>

<link rel="stylesheet"	href="<%=request.getContextPath()%>/resources/css/service.css"	type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/board.css"	type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
    <div class="container">
        <!-- Sidebar Include -->        
        <div><%@ include file="/views/common/serviceSidebar.jsp"%></div>

        <div class="content">
            <!-- Header Include -->            
            <div><%@ include file="/views/common/serviceHeader.jsp"%></div>

            <div class="inquiry">
                <h2>1:1 문의</h2>
                
                <div class="write-button">
                    <a href="<%=request.getContextPath()%>/service/write">글 작성</a>
                </div>
                
                <table class="service-table">
                    <thead>
                        <tr>
                            <th width="20%">번호</th>
                            <th width="40%">제목</th>
                            <th width="20%">작성자</th>
                            <th width="20%">작성일</th>
                        </tr>
                    </thead>
                    <tbody id="inquiryTableBody">
                        <!-- 데이터가 동적으로 삽입될 부분 -->
                        <%if(list.isEmpty()){ %>
						<tr>
							<td colspan="4">조회된 리스트가 없습니다.</td>
						</tr>
						<%} else {%>
						<%for(Board b : list){ %>
						<tr>
							<td><%= b.getBoardNo() %></td>
							<td><%= b.getBoardTitle() %></td>
							<td><%= b.getBoardWriter() %></td>
							<td><%= b.getCreateDate() %></td>
						</tr>
							<%} %>
						<%} %>
                    </tbody>
                </table>
            </div>

            <!-- 페이지네이션 -->
			<div class="pagination">
				<% if (currentPage > 1) { %>
			        <!-- 이전 페이지 이동 -->
			        <button class="page-btn" onclick="location.href='<%= request.getContextPath() %>/service/inquiry?cpage=1'">&lt;&lt;</button>
			        <button class="page-btn" onclick="location.href='<%= request.getContextPath() %>/service/inquiry?cpage=<%= (currentPage > 1) ? (currentPage - 1) : 1 %>'"<%= (currentPage <= 1) ? "disabled" : "" %>>&lt;</button>
			    <% } %>
			
			    <% for (int p = startPage; p <= endPage; p++) { %>
			    <button class="page-btn <%= (currentPage == p) ? "active" : "" %>" 
        			onclick="location.href='<%= request.getContextPath() %>/service/inquiry?cpage=<%= p %>'">
    			<%= p %>
				</button>
			    
			    <% } %>
			
			    <% if (currentPage < maxPage) { %>
			        <!-- 다음 페이지 이동 -->
			        <button class="page-btn" onclick="location.href='<%= request.getContextPath() %>/service/inquiry?cpage=<%= (currentPage < maxPage) ? (currentPage + 1) : maxPage %>'"<%= (currentPage >= maxPage) ? "disabled" : "" %>>&gt;</button>
			        <button class="page-btn" onclick="location.href='<%= request.getContextPath() %>/service/inquiry?cpage=<%= maxPage %>'">&gt;&gt;</button>
			    <% } %>
        	</div>
        </div>	
    </div>

    <script>
		function movePage(cpage){
			location.assign('<%= request.getContextPath() %>/service/inquiry/list?cpage='+cpage);
		}
	
		$(function(){
			$(".service-table>tbody>tr").click(function(){
				location.assign("<%= request.getContextPath() %>/service/detail?boardNo="+$(this).children().eq(0).text()+"&cPage=<%= currentPage %>");
			})
		})
    </script>
</body>
</html>
