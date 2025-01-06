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
<style>
/*---------------------myreply---------------------------*/
/* General Styles */
body {
	font-family: 'Arial', sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f9fafc;
}

.container {
	max-width: 600px;
	margin: 50px auto;
	background: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

h1 {
	text-align: center;
	margin-bottom: 20px;
}

/* Tabs */
.tabs {
	display: flex;
	justify-content: space-around;
	margin-bottom: 20px;
	border-bottom: 2px solid #e0e0e0;
}

.tab-btn {
	background: none;
	border: none;
	padding: 10px 20px;
	cursor: pointer;
	font-size: 16px;
	color: #555;
}

.tab-btn.active {
	border-bottom: 2px solid #0078ff;
	color: #0078ff;
	font-weight: bold;
}

/* Tab Content */
.tab-content {
	display: none;
}

.tab-content.active {
	display: block;
}

/* Comments Section */
.comment-container {
	display: flex;
	flex-direction: column;
	gap: 10px;
}

.comment-item {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 10px;
	background: #f8f9fa;
	border: 1px solid #e0e0e0;
	border-radius: 5px;
}

.comment-item input[type="checkbox"] {
	margin-right: 10px;
}

.comment-details {
	display: flex;
	flex-direction: column;
	flex-grow: 1;
}

.comment-details span {
	font-weight: bold;
	margin-bottom: 3px;
}

.comment-details small {
	font-size: 12px;
	color: #888;
	margin-bottom: 5px;
}

.comment-details p {
	font-size: 14px;
	margin: 0;
}

/* Actions */
.actions {
	margin-top: 20px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.actions label {
	font-size: 14px;
}

.actions button {
	background-color: #4A47A3;
	color: white;
	border: none;
	padding: 8px 12px;
	border-radius: 4px;
	cursor: pointer;
}
</style>	
	
</head>
<body>
	<h2>등록한 댓글</h2>
	<div class="comment-container">
		<div class="comment-item">
			<input type="checkbox" class="comment-select">
			<div class="comment-details">
				<span>반갑습니다~</span> <small>2024-12-01</small>
				<p>편의점 꿀 조합 알려드립니다.</p>
			</div>
		</div>

	</div>
	<div class="actions">
		<label> <input type="checkbox" id="select-all"> 전체 선택
		</label>
		<button id="delete-selected">선택 삭제</button>
	</div>
</body>
</html>
