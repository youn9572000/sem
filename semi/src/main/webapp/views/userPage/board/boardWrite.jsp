<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.kh.user.model.vo.Category" %>
<% List<Category> list = (List<Category>) request.getAttribute("list"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글작성</title>
<link rel="stylesheet"	href="<%=request.getContextPath()%>/resources/css/board2.css"	type="text/css">



</head>
<body>

	<%@ include file="/views/common/menubar.jsp"%>
	
	<!--sub_visual-->
    <div id="sub_visual" style="background:linear-gradient(90deg, #7040ff, #d588ff);">
        <h1>글 작성</h1>
       
        <p>회원님의 다양한 마음을 전달해 주세요.</p>
    </div>
    <!--//sub_visual-->

	<div class="wrapper">
        <div class="container">
        	<h2>글 작성란</h2>
            <form action="<%=request.getContextPath()%>/board/write" method="post" enctype="multipart/form-data" >
                <div class="form-group"><br><br>
                    <label for="title">제목</label>
                    <input type="text" id="title" name="title" placeholder="제목을 입력하세요." required>
                </div>
                <div class="form-group">
                    <label for="content">내용</label>
                    <textarea id="content" name="content" placeholder="내용을 입력하세요." required></textarea>
                </div>
                <div>
                	<!-- 카테고리 선택 Select Box -->
					<select name="category">
                		<% for(Category c : list) {%>
                            <option value="<%= c.getBoardCategory()%>"><%=c.getCategoryName() %></option>
                         <%} %>
                	</select>
                </div>
                <div class="form-group file-upload">
                    <label for="file">첨부 파일</label>
                    <input type="file" id="file" name="upfile">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn">작성 완료</button>
                    <button type="button" class="btn" onclick="history.back()">취소</button>
                </div>
            </form>
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