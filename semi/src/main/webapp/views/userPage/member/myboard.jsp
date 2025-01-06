<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이페이지</title>
<link rel="stylesheet" href="styles.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/mypage.css"
	type="text/css">
</head>
<body>
	<header>
		<div class="header-container">
            <h1 class="logo">
                <a href="/semi/index.jsp">
                    <img src="<%=request.getContextPath() %>/resources/img/logo.png" alt="편리하조 로고">
                </a>
            </h1>
            <h2>마이페이지</h2>
        </div>
	</header>

	<main>
		<section class="mypage-container">
			<nav class="mypage-nav">
				<ul>
					<li><a href="#" class="active">회원정보</a></li>
					<li><a href="#">등록한 게시글</a></li>
					<li><a href="#">등록한 댓글</a></li>
					<li><a href="#">찜 목록</a></li>
				</ul>
			</nav>

			<div class="mypage-content">
				<%@ include file="/views/common/myBoardContent.jsp"%>
			</div>
		</section>
	</main>


	<script>
		function deleteAccount() {
			if (confirm('정말로 계정을 삭제하시겠습니까?')) {
				window.location.href = 'deleteAccount.jsp';
			}
		}
	</script>
</body>
</html>
