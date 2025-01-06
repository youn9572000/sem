<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>스킨 편집</title>
    <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/adminCss/skin.css">
    <style>
        /* 추가 CSS */
        .main-content {
            overflow-y: auto;
            height: calc(100vh - 40px); /* 헤더 제외한 전체 높이 */
            padding: 20px;
            background-color: #fff;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- 사이드바 -->
     <%@ include file="/views/common/AdminSidebar.jsp" %>
      
        <!-- 메인 컨텐츠 -->
        <main class="main-content">
            <!-- 1번 섹션 -->
            <div class="section">
                <h2>1) 스킨 편집</h2>
                <form>
                    <label for="logo">로고 (180px)</label>
                    <input type="text" id="logo" placeholder="로고(넓이 180px)">
                    <button>이미지 업로드</button>

                    <label for="banner">이벤트 배너</label>
                    <input type="file" id="banner">
                    <button>이미지 업로드</button>
                </form>
            </div>

            <!-- 2번 섹션 -->
            <div class="section">
                <h2>2) COMMUNITY 섹션</h2>
                <form>
                    <label for="banner1">배너 1 배경 이미지</label>
                    <input type="file" id="banner1">
                    <button>이미지 업로드</button>

                    <label for="bannerUrl">배너 1 바로가기 URL</label>
                    <input type="url" id="bannerUrl" placeholder="URL 입력">

                    <label for="bannerText">배너 1 문구</label>
                    <input type="text" id="bannerText" placeholder="문구 입력">

                    <label for="bannerColor">배너 1 문구 색상</label>
                    <input type="color" id="bannerColor">
                </form>
            </div>
        </main>
    </div>
</body>
</html>