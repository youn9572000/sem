<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page
	import="java.util.List, com.kh.admin.model.vo.Board, com.kh.user.model.vo.Attachment, 
com.kh.user.model.dto.BoardDTO, com.kh.admin.model.vo.Reply"%>
<%
BoardDTO board = (BoardDTO) request.getAttribute("board");
Board b = board.getB();
Attachment at = board.getAt();
List<Attachment> a = (List<Attachment>) request.getAttribute("a");
String referer = (String) request.getAttribute("referer");
List<Reply> replyList = (List<Reply>) request.getAttribute("replyList");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유 게시판</title>
<link rel="stylesheet"	href="<%=request.getContextPath()%>/resources/css/board2.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
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
	
    <div class="container">
		<!-- 게시판 헤더 -->
		<div class="header">
			<div class="title" id="post-title"><%=b.getBoardTitle()%></div>
			<div class="meta">
				작성자: <span id="post-author"><%=b.getBoardWriter()%></span> | 날짜: <span
					id="post-date"><%=b.getCreateDate()%></span>
			</div>
		</div>

		<!-- 본문 내용 -->
		<div class="content">
			<div class="notice" id="post-content"><%=b.getBoardContent()%></div>
			<div>
				<%if (at.getOriginName() == null) {	%>
				<%} else {%>
				<img
					src="<%=request.getContextPath() + at.getFilePath() + at.getChangeName()%>"
					alt="이미지"> <br> <a
					href="<%=request.getContextPath() + at.getFilePath() + at.getChangeName()%>"
					download="<%=at.getOriginName()%>"><%=at.getOriginName()%></a>
				<%}	%>
			</div>
		</div>

        <!-- 댓글 섹션 -->
        <div class="comments" id="comments-section">
            <!-- 나중에 JS로 동적으로 추가 가능 -->
            <div class="comment">
                <div class="author">아무개</div>
                <div class="date">2024-09-18</div>
                <p>전 셰프 메밀국수 장국과 이마트 생메밀면 올 여름 자주 해먹었어요.</p>
            </div>
            <div class="comment">
                <div class="author">마당개</div>
                <p>저두요...</p>
            </div>
        </div>

        <!-- 댓글 입력 -->
        <div class="comment-input">
            <input type="text" placeholder="댓글 작성란" id="comment-input">
            <button id="add-comment-btn">댓글 등록</button>
        </div>

        <!-- 추천 버튼 -->
		<div class="recommend">
			<button id="recommend-btn">👍 추천</button>
			<!-- 목록으로 버튼 -->
			<%
			if (referer != null && !referer.isEmpty()) {
			%>
			<button type="button" class="list-btn"
				onclick="location.href='<%=referer%>'">목록으로</button>
			<%
			} else {
			%>
			<button type="button" class="list-btn"
				onclick="location.href='<%=request.getContextPath()%>/board/notice'">목록으로</button>
			<%}%>
		</div>
	</div>
	
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