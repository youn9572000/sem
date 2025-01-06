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
	<h3>등록한 게시글</h3>
        <table class="board-table">
            <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성일</th>
                    <th>조회수</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td><a href="#">cu 꿈조합 공유합니다[2]</a></td>
                    <td>2024.12.10</td>
                    <td>30</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>3</td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>4</td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>5</td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>6</td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </tbody>
        </table>
        <div class="pagination">
            <a href="#">1</a>
            <a href="#">2</a>
        </div>
        <div class="filter-dropdown">
            <label for="board-filter">게시판 필터기능</label>
            <select id="board-filter">
                <option value="all">전체</option>
                <option value="recent">최신순</option>
                <option value="popular">인기순</option>
            </select>
        </div>
</body>
</html>
