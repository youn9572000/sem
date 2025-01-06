<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%= contextPath %>/resources/css/adminCss/sidebar.css">
</head>
<body>
	
       <!-- 좌측 메뉴 -->
	<aside class="sidebar">
		<a href="<%=contextPath%>/index.jsp"> <img
			src="<%=contextPath%>/resources/편리로고.png" alt="편리logo">
		</a>
		<ul>
			<li class="menu-item" data-menu="회원관리">회원관리</li>
			<li class="menu-item" data-menu="게시글관리">게시글관리</li>
			<li class="menu-item" data-menu="꾸미기">꾸미기</li>
			<li class="menu-item"
				onclick="window.location.href='<%= contextPath %>/admin/report'"
				style="cursor: pointer;">신고관리</li>
			<li class="menu-item" data-menu="상품관리"
				onclick="location.href='<%=contextPath%>/admin/productManage'">상품관리
			</li>
		</ul>
	</aside>

	<script>
    // 대메뉴와 소메뉴 매핑
    const menuData = {
        "회원관리": [
            { name: "회원 차단", url: "<%= contextPath %>/admin/MemberBlock" },
            { name: "회원 삭제", url: "<%= contextPath %>/admin/MemberDelete" }
        ],
        "게시글관리": [
            { name: "공지사항 관리", url: "<%= contextPath %>/admin/mlist" },
            { name: "자유게시판 관리", url: "<%= contextPath %>/admin/flist" },
            { name: "행사게시판 관리", url: "<%= contextPath %>/admin/elist" }
        ],
        "꾸미기": [
            { name: "스킨편집", url: "<%=contextPath%>/views/adminPage/admin/editSkin.jsp" },
            { name: "메뉴", url: "manageMenu.jsp" }
        ]
    };

    // 대메뉴 클릭 이벤트 추가
    document.querySelectorAll('.menu-item').forEach(menuItem => {
        menuItem.addEventListener('click', () => {
            const existingSubmenu = menuItem.nextElementSibling;

            // 소메뉴가 이미 열려 있으면 제거 (토글)
            if (existingSubmenu && existingSubmenu.classList.contains('submenu')) {
                existingSubmenu.remove();
            } else {
                // 소메뉴 생성
                const submenu = document.createElement('ul');
                submenu.classList.add('submenu');

                // 소메뉴 항목 추가
                const submenuItems = menuData[menuItem.dataset.menu] || [];
                submenuItems.forEach(subItem => {
                    const li = document.createElement('li');
                    li.textContent = subItem.name;

                    // 소메뉴 클릭 시 페이지 이동 이벤트 추가
                    li.addEventListener('click', () => {
                        window.location.href = subItem.url;
                    });

                    submenu.appendChild(li);
                });

                // 소메뉴를 클릭한 대메뉴 바로 아래에 삽입
                menuItem.after(submenu);
            }
        });
    });
</script>

</body>
</html>