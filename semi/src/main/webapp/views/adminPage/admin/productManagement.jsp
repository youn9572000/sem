<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import = "java.util.List, com.kh.product.model.vo.Product, com.kh.common.model.vo.PageInfo"%>
<%
	List<Product> list = (List<Product>) request.getAttribute("list");
 	PageInfo pi = (PageInfo) request.getAttribute("pi");
 	String productName = (String) request.getAttribute("productName"); 
	
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage(); 	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품관리 페이지</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/adminCss/product.css">
</head>

<body>
	<div class="container">

		<%@ include file="/views/common/AdminSidebar.jsp"%>
		<div class="main-content">
			<div class="header">
				<h1>상품관리</h1>
				<div class="admin-icons">
					<a href="#" class="tooltip" id="logout-btn"> <img
						src="<%= contextPath %>/resources/logout이미지.png" alt="로그아웃">
					</a> <a href="<%= contextPath %>/views/adminPage/admin/MainPage.jsp" class="tooltip" id="home-btn"> <img
						src="<%= contextPath %>/resources/mainPage이동.png" alt="메인페이지 이동"
						id="home">
					</a>
				</div>
			</div>

			<script>
				document.getElementById('logout-btn').setAttribute('data-tooltip', '로그아웃');
				document.getElementById('home-btn').setAttribute('data-tooltip', '메인페이지 이동');
			</script>


		<div class="controls">
		   <form action="<%= request.getContextPath() %>/admin/productManage" method="get" id="searchForm">
			<div class="search-bar">
				<input type="text" id="product-name" name="productName" placeholder="상품명 입력" value="<%= (productName != null) ? productName : "" %>">
				<input type="hidden" id="storeNo" name="storeNo" value="<%= request.getParameter("storeNo") != null ? request.getParameter("storeNo") : "" %>">
				<button type="button" class="search-btn" onclick="submitSearchForm()">검색</button>
			</div>
		  </form>	
		</div>

			<div class="main-content">
				<div class="action-buttons-wrapper">
					<button class="add-btn" onclick="showModal()">추가</button>
					<button class="delete-btn" onclick="deleteSelectedProducts()">삭제</button>
				</div>
				<div class="header">
					<div class="nav-bar">
						<button type="button" class="store-btn" data-store="all" onclick="filterByStore('all')">전체상품</button>
						<button type="button" class="store-btn" data-store="2" onclick="filterByStore('2')">CU</button>
						<button type="button" class="store-btn" data-store="3" onclick="filterByStore('3')">7-Eleven</button>
						<button type="button" class="store-btn" data-store="1" onclick="filterByStore('1')">GS25</button>
					</div>
				</div>

				<div class="table-wrapper">
					<table>
						<tr class="headName">
							<th>상품번호</th>
							<th>상품이름</th>
							<th>가격</th>
							<th>이미지</th>
							<th>상품선택</th>
						</tr>

						<tbody id="productList">
							<%
							if (list != null && !list.isEmpty()) {
							%>
								<%
								for (Product p : list) {
								%>
							<tr class="product-item <%=p.getStoreNo()%>">
								<td><%=p.getProductNo()%></td>
								<td><%=p.getProductName()%></td>
								<td><%=p.getProductPrice()%>원</td>
								<td><img src="<%=p.getImageUrl()%>"
									alt="<%=p.getProductName()%>" width="100"></td>
								<td><input type="checkbox" class="productCheckbox"
								value="<%= p.getProductNo() %>"></td>
							</tr>
							<%
							}
							%>
							<%
							} else {
							%>
							<tr>
								<td colspan="4">조회된 상품이 없습니다.</td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>

				<script>
				    function filterStore(store) {
				        const products = document.querySelectorAll('.product-item');
				        products.forEach(product => {
				            if (store === 'all' || product.classList.contains(store)) {
				                product.style.display = 'block';
				            } else {
				                product.style.display = 'none';
				            }
				        });
				    }
				</script>

				<!-- 상품 추가 모달 창 -->
				<div id="modal" class="modal" style="display: none;">
					<div class="modal-content">
						<h2>상품 추가</h2>
						<select id="storeSelect" onchange="loadProductsByStore()">
							<option value="">편의점 이름 선택</option>
							<option value="CU">CU</option>
							<option value="GS25">GS25</option>
							<option value="7-Eleven">7-Eleven</option>
						</select> 
						
						
						<select id="productSelect">
							<option value="">상품 선택</option>
						</select>
						
						
						<div class="modal-buttons">
							<button class="confirm-btn" onclick="addSelectedProduct()">추가하기</button>
							<button class="cancel-btn" onclick="closeModal()">취소</button>
						</div>
					</div>
				</div>

				<script>
			        // 모달 열기
			        function showModal() {
			            document.getElementById('modal').style.display = 'block';
			        }
			
			        // 모달 닫기
			        function closeModal() {
			            document.getElementById('modal').style.display = 'none';
			        }
			        
			        function loadProductsByStore() {
			        	const storeSelect = document.getElementById('storeSelect');
			        	const selectedStore = storeSelect.value;
			        	
			        	const productSelect = document.getElementById('productSelect');
			        	productSelect.innerHTML = '<option value="">상품 선택</option>';
			        	
			        	if(!selectedStore) return;
			        	
			        	fetch('<%= request.getContextPath()%>/admin/getProductsByStore?storeName=' + selectedStore)
			        		.then(response => response.json())
			        		.then(data => {
			        			
			        			data.forEach(product => {
			        				const option = document.createElement('option');
			        				option.value = product.productNo;
			        				option.textContent = product.productName;
			        				productSelect.appendChild(option);
			        			});
			        			
			        		})
			        		.catch(error => {
			        			console.error('상품 목록 불러오기 실패', error);
			        			alert('상품 목록을 불러오는 데 문제가 발생했습니다.');
			        		});
			        }
			        
			    </script>
			    
			    <script>
			    	function deleteSelectedProducts(){
			    		const checkboxes = document.querySelectorAll('.productCheckbox:checked');
			    		const selectedProductNos = Array.from(checkboxes).map(cb => ({
			    			productNo: cb.value
			    		}));
			    		
			    		if(selectedProductNos.length === 0){
			    			alert('삭제할 상품을 선택해주세요.');
			    			return;
			    		}
			    		
			    		if(!confirm('선택한 상품을 삭제하시겠습니까?')){
			    			return;
			    		}
			    		
			    		fetch('<%= request.getContextPath() %>/admin/deleteProducts', {
			    			method: 'POST',
			    			headers: {
			    				'Content-Type': 'application/json'
			    			},
			    			body: JSON.stringify(selectedProductNos)
			    		})
			    		 .then(response => response.json())
			    		    .then(data => {
			    		    	console.log("서버 응답 데이터:", data);  // 디버깅 추가
			    		        if (data.success) {
			    		            alert('선택한 상품이 삭제되었습니다.');
			    		            location.reload();  // 페이지 새로고침
			    		        } else {
			    		            alert('상품 삭제에 실패했습니다.');
			    		        }
			    		    })
			    		    .catch(error => {
			    		        console.error('상품 삭제 실패', error);
			    		        alert('서버 오류 발생');
			    		    });
			    	}
			    </script>
			    
			    <script>
			    	function addSelectedProduct() {
			    		const store = document.getElementById('storeSelect').value;
			    		const productNo = document.getElementById('productSelect').value;
			    		
			    		if(!store || !productNo){
			    			alert('편의점과 상품을 모두 선택하세요.');
			    			return;
			    		}
			    		
			    		const requestData = {
			    				storeName: store,
			    				productNo: productNo
			    		};
			    		
			    		fetch('<%= request.getContextPath() %>/admin/addProduct', {
			    			method: 'POST',
			    			headers: {
			    				'Content-Type': 'application/json'
			    			},
			    			body: JSON.stringify(requestData)
			    		})
			    		.then(response => response.json())
			    		.then(data => {
			    			if(data.success){
			    				alert('상품이 추가되었습니다.');
			    				location.reload();
			    			}else {
			    				alert('상품 추가에 실패했습니다.');
			    			}
			    		})
			    		.catch(error => {
			    			console.error('상품 추가 실패', error);
			    			alert('서버 오류 발생');
			    		});
			    	}
			    </script>
			    
			    <script>
			    	function filterByStore(storeNo){
			    		const currentPage = <%= pi.getCurrentPage()%>;
			    		location.href = '<%= request.getContextPath()%>/admin/productManage?storeNo=' + storeNo + '&cpage=1';
			    	}
			    </script>
			    
			    <script>
			    	function submitSearchForm(){
			    		const productName = document.getElementById("product-name").value;
			    		const storeNo = document.getElementById('storeNo').value;
			    		const searchForm = document.getElementById('searchForm');
			    		
			    	    if (storeNo) {
			    	        searchForm.action += "?storeNo=" + storeNo;
			    	    }

			    		searchForm.submit();
			    	
			    	}
			    </script>
			    
			</div>
				<div class="pagination">
				    <% 
				        String productNameParam = (productName != null && !productName.isEmpty()) ? "&productName=" + productName : "";
				    String storeNoParam = (request.getParameter("storeNo") != null) ? "&storeNo=" + request.getParameter("storeNo") : "";
				    %>
				
				    <% if (pi.getCurrentPage() > 1) { %>
				        <a href="<%= request.getContextPath() %>/admin/productManage?cpage=1<%= productNameParam %><%= storeNoParam %>">&lt;&lt;</a>
				        <a href="<%= request.getContextPath() %>/admin/productManage?cpage=<%= pi.getCurrentPage() - 1 %><%= productNameParam %><%= storeNoParam %>">&lt;</a>
				    <% } %>
				
				    <% for (int p = pi.getStartPage(); p <= pi.getEndPage(); p++) { %>
				        <a href="<%= request.getContextPath() %>/admin/productManage?cpage=<%= p %><%= productNameParam %><%= storeNoParam %>"><%= p %></a>
				    <% } %>
				
				    <% if (pi.getCurrentPage() < pi.getMaxPage()) { %>
				        <a href="<%= request.getContextPath() %>/admin/productManage?cpage=<%= pi.getCurrentPage() + 1 %><%= productNameParam %><%= storeNoParam %>">&gt;</a>
				        <a href="<%= request.getContextPath() %>/admin/productManage?cpage=<%= pi.getMaxPage() %><%= productNameParam %><%= storeNoParam %>">&gt;&gt;</a>
				    <% } %>
			</div>
			
			<script>
				function filterByStore(storeNo) {
				    const productName = document.getElementById("product-name").value;
				    const currentPage = <%= pi.getCurrentPage() %>;
	
				    let url = '<%= request.getContextPath() %>/admin/productManage?storeNo=' + storeNo + '&cpage=1';
	
				    if (productName) {
				        url += '&productName=' + encodeURIComponent(productName);
				    }
	
				    location.href = url;
				}
			</script>
		</div>
	</div>
</body>
</html>