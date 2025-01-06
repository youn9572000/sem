<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page
	import="java.util.List, com.kh.admin.model.vo.Member, com.kh.common.model.vo.PageInfo"%>
<%
	List<Member> list = (List<Member>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	String searchId = (String) request.getAttribute("searchId");
	
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원삭제 페이지</title>
</head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/adminCss/delete.css">
<body>
	<div class="container">

		<%@ include file="/views/common/AdminSidebar.jsp"%>

		<div class="main-content">
			<header>
				<h1>회원삭제</h1>
				<div class="admin-icons">
					<a href="#" class="tooltip" id="logout-btn"> <img
						src="<%=contextPath%>/resources/logout이미지.png" alt="로그아웃">
					</a> <a href="<%= contextPath %>/views/adminPage/admin/MainPage.jsp" class="tooltip" id="home-btn"> <img
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

			<form action="<%= request.getContextPath() %>/admin/MemberDelete" method="get" id="searchFrom">
				<div class="search-box">
					<input type="text" id="search-id" name="searchId" placeholder="아이디 입력" value="<%= (searchId != null) ? searchId : "" %>">
					
					<input type="hidden" name="cpage" value="<%= pi.getCurrentPage() %>">
					
					<button type="button" class="serarch-btn" 
					onclick="submitSearchForm()">조회</button>
				</div>
				</form>
				
				
			<style>
			    .search-box {
			        display: flex;
			        justify-content: flex-start;
			        align-items: center;
			        gap: 10px;
			    }
			    
			    .search-box input[type="text"] {
			        width: 200px;
				    padding: 8px;
				    border: 1px solid #ddd;
				    border-radius: 4px;
				    margin-right: 10px;
			    }
			
			    .search-box button {
			        padding: 8px 15px;
				    border: none;
				    background-color: #5cb85c;
				    color: white;
				    border-radius: 4px;
				    cursor: pointer;
			    }
			
			    .search-box button:hover {
			        background-color: #45a049;
			    }
			</style>
							
				<div class="action-button">
					<button type="button" class="delete-btn" onclick="showDeleteAlert()">회원삭제</button>
				</div>
				
				<div class="custom-alert" id="customAlert">
					<h2>회원 삭제 확인</h2>
					<ul>
						<li>선택하신 회원을 삭제하시겠습니까?</li>				
					</ul>
					<button id="confirmDelete">확인</button>
					<button onclick="closeAlert()">취소</button>
				</div>
				<div class="overlay" id="overlay"></div>
				
				<script>
					let selectedUsers = [];
					
					document.addEventListener('change', function(e){
						if(e.target.name === 'deleteCheckbox') {
							const row = e.target.closest('tr');
							const rowData = {
									userNo: row.children[0].textContent,
									userId: row.children[1].textContent,
									enrollDate: row.children[2].textContent,
									rowElement: row
							};
							
							if(e.target.checked){
								selectedUsers.push(rowData);
							} else {
								selectedUsers = selectedUsers.filter(item => item.userNo !== rowData.userNo);
				            }
							console.log("현재 selectedUsers: ", selectedUsers);
						}
					});
					
					function showDeleteAlert(){
						if(selectedUsers.length === 0){
							alert("차단할 회원을 선택하세요.");
							return;
						}
						
						document.getElementById("customAlert").style.display = "block";
				        document.getElementById("overlay").style.display = "block";
						
					}
					
					function closeAlert(){
				    	document.getElementById("customAlert").style.display = "none";
				    	document.getElementById("overlay").style.display = "none";						
					}
			
				</script>
				
				<script>
					document.addEventListener('DOMContentLoaded', function(){
						const confirmDeleteBtn = document.getElementById('confirmDelete');
						
						if(confirmDeleteBtn){
							confirmDeleteBtn.addEventListener('click', function(){
								
							   console.log("confirmBlock 버튼 클릭됨");
					           console.log("선택된 회원 목록: ", selectedUsers);
					           
					           selectedUsers.forEach(user => {
					        	   user.rowElement.children[1].textContent = user.userId
					           });
					           
					           fetch('<%= request.getContextPath()%>/admin/DeleteUser', {
					        	   method: 'POST',
					        	   headers: {
					        		   'Content-Type': 'application/json'
					        	   },
					        	   body: JSON.stringify(selectedUsers.map(user => ({
					        		   
					        			   userId: user.userId
					        	   })))
					           })
					           .then(response => response.json())
					           .then(data => {
					        	   if(data.success){
					        		   alert("회원 삭제 완료");
					        		   location.reload();
					        	   } else {
					        		   alert("회원 삭제 실패");
					        	   }
					           })
					           .catch(error => {
					        	   console.error("서버 오류 발생: ", error);
					        	   alert("서버에서 문제가 발생했습니다.");
					           });
					           
					           closeAlert();
					             
							});
						}
					});
				</script>
				
				<table>
					<thead>
						<tr>
							<th>번호</th>
							<th>아이디</th>
							<th>가입일</th>
							<th>선택</th>
						</tr>
					</thead>
					<tbody id="DeleteMemberTable">
						<% for(Member m : list) { %>
							<tr>
								<td><%= m.getUserNo() %></td>
								<td><%= m.getUserId() %></td>
								<td><%= m.getEnrollDate() %></td>
								<td><input type="checkbox" name="deleteCheckbox"
									value="<%= m.getUserId()%>"></td>
								
							</tr>
						<% } %>
					</tbody>
				</table>
				
								
				<script>
				    // 필터링 기능 - JavaScript로 클라이언트 측에서 필터링
				    function submitSearchForm() {
				        const searchId = document.getElementById('search-id').value.toLowerCase();
				        const table = document.getElementById('DeleteMemberTable');
				        const rows = table.getElementsByTagName('tr');
				        const pagination = document.getElementsByClassName('pagination');
				        const searchForm = document.getElementById('searchFrom');
				        searchForm.submit();
				        // 각 행을 확인하여 필터링 수행
				        for (let i = 0; i < rows.length; i++) {
				            const userId = rows[i].getElementsByTagName('td')[1].innerText.toLowerCase();
				            if (userId.includes(searchId)) {
				                rows[i].style.display = '';
				            }else if(searchId === '') {
				            	rows[i].style.display = '';
				            } else {
				                rows[i].style.display = 'none';
				            }
				        }
				        
  				        for (let i = 0; i < pagination.length; i++) {
				        	pagination[i].style.display = searchId === '' ? '' : 'block';
				        }  
				    }
				</script>
				
				
				<div class="pagination">
				    <% 
				        String searchIdParam = (searchId != null && !searchId.isEmpty()) ? "&searchId=" + searchId : ""; 
				    %>
				
				    <% if (pi.getCurrentPage() > 1) { %>
				        <a href="<%= request.getContextPath() %>/admin/MemberDelete?cpage=1<%= searchIdParam %>">&lt;&lt;</a>
				        <a href="<%= request.getContextPath() %>/admin/MemberDelete?cpage=<%= pi.getCurrentPage() - 1 %><%= searchIdParam %>">&lt;</a>
				    <% } %>
				
				    <% for (int p = pi.getStartPage(); p <= pi.getEndPage(); p++) { %>
				        <a href="<%= request.getContextPath() %>/admin/MemberDelete?cpage=<%= p %><%= searchIdParam %>"><%= p %></a>
				    <% } %>
				
				    <% if (pi.getCurrentPage() < pi.getMaxPage()) { %>
				        <a href="<%= request.getContextPath() %>/admin/MemberDelete?cpage=<%= pi.getCurrentPage() + 1 %><%= searchIdParam %>">&gt;</a>
				        <a href="<%= request.getContextPath() %>/admin/MemberDelete?cpage=<%= pi.getMaxPage() %><%= searchIdParam %>">&gt;&gt;</a>
				    <% } %>
				</div>
				
				
			</form>
		</div>
	</div>
</body>
</html>
