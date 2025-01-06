<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.kh.user.model.vo.Save"%>
<%@ page import="com.kh.product.model.vo.Product"%>



<%
    request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>찜 목록</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/savelist.css" />
</head>

<body>
<!--header-->
    <div id="header_wrap">
        <div id="header">
            <h1 class="logo">
                <a href="/semi/index.jsp"><img src="/semi/resources/img1/편의점 로고 예시1.png" alt="편리하조 로고"></a>
            </h1>
            <ul class="gnb">
                <li><a href="request.getContextPath() %>/views/store/gs.jsp">GS25</a></li>
                <li><a href="request.getContextPath() %>/views/store/cu.jsp">CU</a></li>
                <li><a href="request.getContextPath() %>/views/store/seven.jsp">7-ELEVEN</a></li>
                <li><a href="#">게시판</a></li>
                <li><a href="#">고객센터</a></li>
            </ul>
        </div>
    </div>
    <!--//header-->
        
    <!--sub_visual-->
    <div id="sub_visual" style="background:linear-gradient(90deg, #7040ff, #d588ff);">
        <h1>찜목록</h1>
        <p>회원님께서 담으신 찜목록 상품입니다.</p>
    </div>
    <!--//sub_visual-->
    
    <!--container-->
    <div id="sub_container">
       <div class="sub_title">
            <h3>나만의 놀이터 <span style="color:#fae100">편리</span><br>
            찜한 상품들을 확인해보세요
            </h3>
       </div>
       <ul class="menu_tab">
            <li class="selected"><a href="#">전체</a></li>
            <li><a href="#">과자</a></li>
            <li><a href="#">식품</a></li>
            <li><a href="#">음료수</a></li>
            <li><a href="#">아이스크림</a></li>
       </ul>

	<%
        // 서버에서 전달된 saveList 객체 가져오기
        List<Save> saveList = (List<Save>) request.getAttribute("saveList");

        if (saveList != null && !saveList.isEmpty()) {
    %>
	<div style="background-color: white; padding: 20px;">
		<ul>
			<% 
            for (Save item : saveList) { 
        %>
			<li><img src="<%= item.getProductImage() %>" alt="상품 이미지"
				style="width: 100px; height: 100px;" margin-botoom: 50px;> 	     <!-- 상품 이름 -->
       		


				<form action="<%= request.getContextPath() %>/kart/delete"
					method="post">
					<input type="hidden" name="productNo"
						value="<%= item.getProductNo() %>" />
					<button type="submit" class="delete-button">삭제</button>
				</form></li>


			<% 
            } 
        %>

		</ul>
		<%
        } else {
    %>
		<p>찜 목록이 비어 있습니다.</p>
		<%
        }
    %>
		<form action="<%= request.getContextPath() %>/kart/delete"
			method="post" style="display: inline;">
			<input type="hidden" name="deleteAll" value="true">
			<button type="submit">전체 삭제</button>
		</form>
		<button
			onclick="window.location.href='<%= request.getContextPath() %>/views/store/cu.jsp';">
			상품목록으로 이동</button>
	</div>
	<!--footer-->
    <div id="footer_wrap">
        <div id="footer">
            <p class="f_logo">
                <a href="#"><img src="/semi/resources/img1/편의점 로고 예시1.png" alt="logo"></a>
            </p>
            <div class="f_copyright">
                <ul class="menu">
                    <li><a href="#">회사소개</a></li>
                    <li><a href="#">고객센터</a></li>
                    <li><a href="#">IR</a></li>
                    <li><a href="#">인재채용</a></li>
                    <li><a href="#">찾아오시는길</a></li>
                    <li><a href="#" style="font-weight:bold;">개인정보처리방침</a></li>
                </ul>
                <p class="info">
                    서울특별시 강남구 테헤란로 130 호산빌딩, 5F, 6F<br>
                    사업자등록번호 : 487-86-00763│통신판매신고번호 : 851-87-00622<br>
                    고객만족센터 : 012-345-6789(무료상담전화)│대표전화 : 01-234-5678
                </p>
                <p class="copyright">
                    Copyright © 편리 Inc. All Rights Reserved.
                </p>
            </div>
            <div class="f_site">
                <select class="family">
                    <option value="">Family Site</option>
                    <option value="">CU</option>
                    <option value="">GS25</option>
                    <option value="">7-ELEVEN</option>
                </select>
                <div class="banner">
                    <img src="/semi/resources/img1/award_2017.jpg" alt="수상">
                </div>
            </div>
        </div>
    </div>
    <!--//footer-->

</body>
</html>