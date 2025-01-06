<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.kh.user.model.vo.Member" %>	
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>편리하조 Main Page</title>

	<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/dropdown.css" type="text/css">

    <!-- Reset CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css1/reset.css" type="text/css">
    <!-- Main Style CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css1/style.css" type="text/css">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	
	
	
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

    <!-- jQuery Library -->
    <script src="<%= request.getContextPath() %>/resources/js1/jquery-1.9.1.min.js"></script>
    <!-- bxSlider CSS & JS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/js1/jquery.bxslider.css">
    <script src="<%= request.getContextPath() %>/resources/js1/jquery.bxslider.js"></script>
    
    <!-- bxSlider Initialization -->
    <script>
        $(document).ready(function () {
            $('.slider').bxSlider({
                auto: true,
                speed: 2000,
            });
        });
    </script>

    <!-- Mobile Menu Toggle Script -->
    <script type="text/javascript">
        $(document).ready(function () {
            $(".menu-toggle-btn").click(function () {
                $(".gnb").stop().slideToggle("fast");
            });
        });
    </script>
</head>
<body>
	<!-- header -->
    <div id="header_wrap">
        <div id="header">

            <!-- 로고 -->
            <a href="<%= request.getContextPath() %>/index.jsp">
                <img src="<%= request.getContextPath() %>/resources/img1/편의점 로고 예시1.png" alt="편리하조 로고">
            </a>
       

            <!-- 네비게이션 메뉴 -->
            <ul class="gnb">
                <li><a href="<%= request.getContextPath() %>/views/store/gs.jsp">GS25</a></li>
                <li><a href="<%= request.getContextPath() %>/views/store/cu.jsp">CU</a></li>
                <li><a href="<%= request.getContextPath() %>/views/store/seven.jsp">7-ELEVEN</a></li>
                <li class="menu-item" data-menu="게시판"><a>게시판</a></li>
				<li class="menu-item" data-menu="고객센터"><a>고객센터</a></li>
            </ul>

            <!-- 모바일 gnb 토글 버튼 -->
            <span class="menu-toggle-btn">
                <span></span>
                <span></span>
                <span></span>
            </span>

            <!-- 로그인, 회원가입, 마이페이지 -->
            <div class="user-menu">
                <%                
                    // 세션에서 로그인 사용자 확인
                    Member loginUser = (Member) session.getAttribute("loginUser");
                %>
                <% if (loginUser != null) { %>
                    <!-- 로그인 상태 -->
                    <span>안녕하세요, <%= loginUser.getUserId() %>님!</span>
                    <a href="<%= request.getContextPath() %>/member/logout" class="icon logout">로그아웃</a>
                    <a href="<%= request.getContextPath() %>/views/userPage/member/mypage.jsp" class="icon mypage">마이페이지</a>
                <% } else { %>
                    <!-- 비로그인 상태 -->
                    <a href="<%= request.getContextPath() %>/views/userPage/member/login.jsp" class="icon login">로그인</a>
                    <a href="<%= request.getContextPath() %>/views/userPage/member/Signup.jsp" class="icon signup">회원가입</a>
                    <a href="<%= request.getContextPath() %>/views/userPage/member/mypage.jsp" class="icon mypage">마이페이지</a>
                <% } %>
            </div>

        </div>
    </div>
    <!-- //header -->

    <!-- Main Content -->
    <div id="main-content">
        <!-- 기존 페이지 콘텐츠 -->
    </div>
    
	<script>// 대메뉴와 소메뉴 매핑
	const menuData = {
		    "게시판": [
		        { name: "공지사항", url: "<%=request.getContextPath()%>/board/notice" },
		        { name: "자유게시판", url: "<%=request.getContextPath()%>/board/free" },
		        { name: "행사게시판", url: "<%=request.getContextPath()%>/board/event" }
		    ],
		    "고객센터": [
		        { name: "자주 묻는 질문", url: "<%=request.getContextPath()%>/views/userPage/board/serviceFAQ.jsp" },
		        { name: "1:1 문의", url: "<%=request.getContextPath()%>/service/inquiry" }
		    ]
		};

		// 대메뉴에 소메뉴 동적 추가
		document.querySelectorAll('.menu-item').forEach(menuItem => {
		    const submenuData = menuData[menuItem.dataset.menu];

		    if (submenuData) {
		        // 소메뉴 생성
		        const submenu = document.createElement('ul');
		        submenu.classList.add('submenu');

		        submenuData.forEach(subItem => {
		            const li = document.createElement('li');
		            const a = document.createElement('a');
		            a.textContent = subItem.name;
		            a.href = subItem.url;
		            
		         // 폰트 크기 지정 및 border 속성 없애기 
                    a.style.fontSize = "15px";
                    a.style.fontWeight = "600";
                    
		            li.appendChild(a);
		            submenu.appendChild(li);
		        });

		        // 대메뉴 아래에 소메뉴 추가
		        menuItem.appendChild(submenu);
		        menuItem.style.backgroundColor="white";
		    }
		});
	</script>


</body>
</html>