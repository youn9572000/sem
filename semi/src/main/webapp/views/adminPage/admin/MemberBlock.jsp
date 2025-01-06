<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.List, com.kh.admin.model.dto.BlockMemberDTO, com.kh.common.model.vo.PageInfo, com.kh.admin.model.vo.Block"%>
<%
	List<BlockMemberDTO> list = (List<BlockMemberDTO>) request.getAttribute("list");
	PageInfo pi = (PageInfo) request.getAttribute("pi");
	String filter = (String) request.getAttribute("filter");
	String result = (String) request.getAttribute("result");
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
<title>회원차단 페이지</title>



</head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/adminCss/block.css">
<body>
	<div class="container">

		<%@ include file="/views/common/AdminSidebar.jsp"%>

		<div class="main-content">
			<header>
				<h1>회원차단</h1>
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


			<!-- 검색 및 정렬 영역 -->
			<div class="controls">
				<form action="<%= request.getContextPath() %>/admin/MemberBlock"
					method="get" id="searchFrom">
					<div class="search-box">
					
						<input type="text" id="search-id" name="searchId" placeholder="아이디 입력" value="<%= (searchId != null) ? searchId : "" %>"> 
						<input type="hidden" name="sort-select" value="<%= (filter != null) ? filter : "all" %>">
						
						
						<input type="hidden" name="cpage" value="<%= pi.getCurrentPage() %>">

						<button type="button" class="search-btn"
							onclick="submitSearchForm()">조회</button>
					</div>
				</form>

				<!-- 회원 필터링 셀렉트박스 기능 -->
				<form action="<%= request.getContextPath() %>/admin/MemberBlock"
					method="get" id="filterForm">
					<div class="sort-box">
						<select id="sort-select" name="sort-select" placeholder="정렬기준"
							onchange="submitFilterForm()">
							<option value="all" <%= (filter == null || "all".equals(filter)) ? "selected" : "" %>>전체회원</option>
							<option value="blocked"
								<%= "blocked".equals(filter) ? "selected" : "" %>>차단회원</option>
							<option value="unblocked"
								<%= "unblocked".equals(filter) ? "selected" : "" %>>차단안된회원</option>
						</select>  
						<input type="hidden" name="searchId" value="<%= (searchId != null) ? searchId : "" %>">
						<input type="hidden" name="cpage" value="<%= pi.getCurrentPage() %>">
					</div>
				</form>


				<script>
				    function submitFilterForm() {
				    	const searchId = document.getElementById('search-id').value;
				        const filterForm = document.getElementById('filterForm').submit();
				        
				        if (searchId) {
				            const hiddenSearch = document.createElement("input");
				            hiddenSearch.setAttribute("type", "hidden");
				            hiddenSearch.setAttribute("name", "searchId");
				            hiddenSearch.setAttribute("value", searchId);
				            filterForm.appendChild(hiddenSearch);
				        }
				        
				        filterForm.submit();
				    }
				</script>
				
				<!-- 회원 차단, 해제 버튼 -->
				<div class="action-buttons">
					<button type="button" class="block-btn" onclick="showBlockAlert()">차단</button>
					<button type="button" class="unblock-btn" onclick="showReleaseAlert()">해제</button>
				</div>
			</div>

			<!-- 차단 알럿 모달 -->
			<div class="custom-alert" id="customAlert">
				<h2>차단 일수 설정</h2>
				<ul>
			        <li><input type="checkbox" name="blockReason" value="1-스팸성 댓글 작성"> 1일 - 스팸성 댓글 작성</li>
			        <li><input type="checkbox" name="blockReason" value="2-게시글 주제 일탈"> 2일 - 게시글 주제 일탈</li>
			        <li><input type="checkbox" name="blockReason" value="3-경미한 언어폭력"> 3일 - 경미한 언어폭력</li>
			        <li><input type="checkbox" name="blockReason" value="7-욕설 및 비하발언"> 7일 - 욕설 및 비하발언</li>
			        <li><input type="checkbox" name="blockReason" value="14-불법 컨텐츠 업로드"> 14일 - 불법 컨텐츠 업로드</li>
			        <li><input type="checkbox" name="blockReason" value="30-혐오 발언 및 차별적 행동"> 30일 - 혐오 발언 및 차별적 행동</li>
				</ul>
				<button id="confirmBlock">확인</button>
				<button onclick="closeAlert()">취소</button>
			</div>
			<div class="overlay" id="overlay"></div>
			
			<!-- 차단해제 알럿 모달 -->
			<div class="custom-alert" id="customAlert2">
				<h2>회원 차단해제 확인</h2>
				<ul>
					<li>선택하신 회원의 차단을 해제하시겠습니까?</li>				
				</ul>
				<button id="confirmRelease">확인</button>
				<button onclick="closeAlert()">취소</button>
			</div>
			<div class="overlay" id="overlay2"></div>

			<!-- 차단사유 선택 체크박스 하나만 선택되게 하는 기능 -->
			<script>
			    document.querySelectorAll('input[name="blockReason"]').forEach(checkbox => {
			        checkbox.addEventListener('click', function() {
			            document.querySelectorAll('input[name="blockReason"]').forEach(cb => {
			                if (cb !== this) {
			                    cb.checked = false;
			                }
			            });
			        });
			    });
			</script>
			
			<!-- 회원 차단/해제 데이터 저장 및  부분 -->
			<script>
			    // 행 데이터 저장용 배열
			    let selectedUsers  = [];
			
			    // 체크박스 클릭 시 선택된 행의 데이터 수집
			    document.addEventListener('change', function(e) {
			        if (e.target.name === 'blockCheckbox') {
			            const row = e.target.closest('tr');  // 체크된 행(tr) 찾기
			            const rowData = {
			                userNo: row.children[0].textContent,
			                userId: row.children[1].textContent,
			                blockReason: row.children[2].textContent,
			                blockDay: row.children[3].textContent,
			                blockDate: row.children[4].textContent,
			                rowElement: row  // DOM 요소 저장
			            };
			
			            // 체크박스가 체크된 경우 데이터 추가, 체크 해제 시 제거
			            if (e.target.checked) {
			            	selectedUsers.push(rowData);
			            } else {
			                selectedUsers = selectedUsers.filter(item => item.userNo !== rowData.userNo);
			            }
			            console.log("현재 selectedUsers: ", selectedUsers);

			        }
			    });
			    
			    function showBlockAlert() {
			        if (selectedUsers.length === 0) {
			            alert("차단할 회원을 선택하세요.");
			            return;
			        }
			        // 모달 제목과 버튼 텍스트를 차단 관련으로 변경
			        /* document.getElementById("alertTitle").textContent = "차단 일수 설정";
			        document.getElementById("confirmBlock").textContent = "차단 확인"; */
			        
			        document.getElementById("customAlert").style.display = "block";
			        document.getElementById("overlay").style.display = "block";
			    }
			    
			    function showReleaseAlert() {
			        if (selectedUsers.length === 0) {
			            alert("차단 해제할 회원을 선택하세요.");
			            return;
			        }
			     	// 모달 제목과 버튼 텍스트를 해제 관련으로 변경
			        /* document.getElementById("alertTitle").textContent = "차단 해제";
			        document.getElementById("confirmBlock").textContent = "차단 해제 확인"; */
			        
			        document.getElementById("customAlert2").style.display = "block";
			        document.getElementById("overlay2").style.display = "block";
			    }


			    function closeAlert() {
			    	document.getElementById("customAlert").style.display = "none";
			    	document.getElementById("overlay").style.display = "none";
			        document.getElementById("customAlert2").style.display = "none";
			        document.getElementById("overlay2").style.display = "none";
			    }
			    </script>

			<script>
			 // DOMContentLoaded 내에서 이벤트 리스너 추가
			    document.addEventListener('DOMContentLoaded', function () {
			        const confirmBlockBtn = document.getElementById('confirmBlock');
			        const confirmReleaseBtn = document.getElementById('confirmRelease');
			        
			        // confirmBlock 버튼에 이벤트 추가
			        if (confirmBlockBtn) {
			            confirmBlockBtn.addEventListener('click', function () {
			            	
			                console.log("confirmBlock 버튼 클릭됨");
			                console.log("선택된 회원 목록: ", selectedUsers);
			            	
			            
			                const selectedOption = document.querySelector('input[name="blockReason"]:checked');

			                if (!selectedOption) {
			                    alert("차단할 사유를 선택하세요.");
			                    return;
			                }
						  		  

					const [days, reason] = selectedOption.value.split('-');
					
		            // 선택된 회원에 대한 차단 정보 업데이트 (DOM 조작)
		            selectedUsers.forEach(user => {
		                /* console.log("현재 조작 중인 행: ", user.rowElement);
		                console.log("userId: ", user.userId); */
		                user.rowElement.children[2].textContent = reason;  // 차단 사유 업데이트	  
		                user.rowElement.children[3].textContent = days; // 차단 일수 업데이트
		            });

		            
		            // ====== [수정된 부분 - AJAX 요청 전송] ======
		            fetch('<%=request.getContextPath()%>/admin/blockUser', {
		                method: 'POST',
		                headers: {
		                    'Content-Type': 'application/json'
		                },
		                body: JSON.stringify(selectedUsers.map(user => ({
		                    member: {
		                    	userId: user.userId
		                    },
		                    block: {
			                    reason: reason,		                    	
		                    	blockDay: days
		                    }
		                })))
		            })
		            .then(response => response.json())
		            .then(data => {
		                if (data.success) {
		                    alert("회원 차단 완료");
		                    location.reload();
		                } else {
		                    alert("회원 차단 실패");
		                }
		            })
		            .catch(error => {
		                console.error("서버 오류 발생: ", error);
		                alert("서버에서 문제가 발생했습니다.");
		            });
		            // ========================================
		            
		            closeAlert();
		        });
		    }
			        
		});

		</script>
		
		
		
		
		
		<!-- 회원 차단해제  -->
		<script>
			 // DOMContentLoaded 내에서 이벤트 리스너 추가
			    document.addEventListener('DOMContentLoaded', function () {
			        const confirmReleaseBtn = document.getElementById('confirmRelease');
			        
			        // confirmRelease 버튼에 이벤트 추가
			        if (confirmReleaseBtn) {
			        	confirmReleaseBtn.addEventListener('click', function () {
			            	
		            
		            // ====== [AJAX 요청 전송] ======
		            fetch('<%=request.getContextPath()%>/admin/releaseUser', {
		                method: 'POST',
		                headers: {
		                    'Content-Type': 'application/json'
		                },
		                body: JSON.stringify(selectedUsers.map(user => ({
		                    member: {
		                    	userId: user.userId
		                    }
		                })))
		            })
		            .then(response => response.json())
		            .then(data => {
		                if (data.success) {
		                    alert("회원 차단해제 완료");
		                    location.reload();
		                } else {
		                    alert("회원 차단해제 실패");
		                }
		            })
 		            .catch(error => {
		                console.error("서버 오류 발생: ", error);
		                alert("서버에서 문제가 발생했습니다.");
		            }); 
		            // ========================================
		            
		            closeAlert();
		        });
		    }
			        
		});

		</script>

			<!-- 회원차단 테이블 -->
			<table class="list-area">
				<thead>
					<tr>
						<th>번호</th>
						<th>아이디</th>
						<th>차단 사유</th>
						<th>차단 일수</th>
						<th>차단 일시</th>
						<th>선택</th>
					</tr>
				</thead>
				<tbody id="blockMemberTable">
					<% 
								for(BlockMemberDTO b : list) { 
									 if (searchId == null || searchId.isEmpty() || b.getMember().getUserId().contains(searchId)) {
							%>
					<tr data-status="<%= b.getBlock().getReason() != null ? "blocked" : "unblocked" %>">
						<td><%= b.getMember().getUserNo() %></td>
						<td><%= b.getMember().getUserId() %></td>
						<td><%= b.getBlock().getReason() != null ? b.getBlock().getReason() : "N/A" %></td>
						<td><%= b.getBlock().getBlockDay() %></td>
						<td><%= b.getMember().getBlockDate() != null ? b.getMember().getBlockDate() : "-" %></td>
						<td><input type="checkbox" name="blockCheckbox"
							value="<%= b.getMember().getUserId() %>"></td>
					</tr>
					<% 
									 }
								} 
							%>

				</tbody>
			</table>



			<script>
					document.addEventListener('DOMContentLoaded', function() {
 					    let allRows = [];  // 전체 데이터 저장
						document.querySelectorAll('#blockMemberTable tr').forEach(row => {
						    allRows.push(row);					
						});

				    	document.getElementById('sort-select').addEventListener('change', function() {
				        const selectedValue = this.value;  // 선택된 값
				        const rows = document.querySelectorAll('#blockMemberTable tr');  // 모든 행 선택
				        const pagination = document.querySelectorAll('.pagination');
				        let visibleRows = 0;
				        
				        allRows.forEach(row => {
				            const status = row.getAttribute('data-status');
				            if (selectedValue === 'all' || selectedValue === status) {
				                row.style.display = '';
				                visibleRows++;
				            } else {
				                row.style.display = 'none';
				            }
				        });

				        // 페이지네이션 숨김/보이기
				        pagination.forEach(pg => {
				        	pg.style.display = (visibleRows <= 10) ? 'none' : 'block';				        	
				        });
				        
				    });
				   });
				</script>


			<script>
				    // 필터링 기능 - JavaScript로 클라이언트 측에서 필터링
				    function submitSearchForm() {
				        const searchId = document.getElementById('search-id').value.toLowerCase();
				        const searchForm = document.getElementById('searchFrom');
				        searchForm.submit();
				    }
				</script>



			<!-- 전체 선택/해제 기능 -->
			<script>
			function toggleAllCheckboxes(source) {
				  const checkboxes = document.querySelectorAll('input[name="blockCheckbox"]');
				   checkboxes.forEach(checkbox => {
				            checkbox.checked = source.checked;
				        });
				    }
			</script>

			<script>
	        	$(function() {
	        		$(".list-area>tbody>tr").click(function(){
	        			location.assign("<%= contextPath %>/MemberBlock/detail?bno="+$(this).children().eq(0).text()+"&cpage=<%= currentPage %>");
	        		});
	        	});
        	</script>

			<div align="center" class="pagination">
			    <% 
			    String searchIdParam = (searchId != null && !searchId.isEmpty()) ? "&searchId=" + searchId : ""; 
			    String sortSelectParam = (filter != null && !filter.isEmpty()) ? "&sort-select=" + filter : "";
			    %>
			    
			    <% if (pi.getCurrentPage() > 1) { %>
			    <!-- 처음 페이지로 이동 -->
			    <a href="<%= request.getContextPath() %>/admin/MemberBlock?cpage=1<%= sortSelectParam %><%= searchIdParam %>">&lt;&lt;</a>
			    <a href="<%= request.getContextPath() %>/admin/MemberBlock?cpage=<%= pi.getCurrentPage() - 1 %><%= sortSelectParam %><%= searchIdParam %>">&lt;</a>
			    <% } %>
			
			    <% for (int p = pi.getStartPage(); p <= pi.getEndPage(); p++) { %>
			    <a href="<%= request.getContextPath() %>/admin/MemberBlock?cpage=<%= p %><%= sortSelectParam %><%= searchIdParam %>"
			       class="<%= (pi.getCurrentPage() == p) ? "active" : "" %>"> <%= p %>
			    </a>
			    <% } %>
			
			    <% if (pi.getCurrentPage() < pi.getMaxPage()) { %>
			    <a href="<%= request.getContextPath() %>/admin/MemberBlock?cpage=<%= pi.getCurrentPage() + 1 %><%= sortSelectParam %><%= searchIdParam %>">&gt;</a>
			    <a href="<%= request.getContextPath() %>/admin/MemberBlock?cpage=<%= pi.getMaxPage() %><%= sortSelectParam %><%= searchIdParam %>">&gt;&gt;</a>
			    <% } %>
			</div>

			<script>
			    function movePage(cpage){
			        const sortValue = document.getElementById('sort-select').value;
			        const searchId = document.getElementById('search-id') ? document.getElementById('search-id').value : "";
			        
			        let url = '<%= request.getContextPath() %>/admin/MemberBlock?cpage=' + cpage + '&sort-select=' + sortValue;
			        
			        // 검색어가 있는 경우 URL에 추가
			        if (searchId) {
			            url += '&searchId=' + searchId;
			        }
			        
			        location.assign(url);			    
			    
			    };
			</script>
		</div>
	</div>
	</div>
	
	
	
	
	
	


	
</body>
</html>