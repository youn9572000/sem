<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>관리자 메인 페이지</title>
  
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function () {
    $.ajax({
        url: '<%= request.getContextPath() %>/visitor?action=display',
        method: 'GET',
        success: function (response) {
            console.log("응답 데이터: ", response);

            // response를 직접 사용
            $('#totalVisitors').text(response.totalVisitors || 0);
            $('#todayVisitors').text(response.todayVisitors || 0);
            $('#yesterdayVisitors').text(response.yesterdayVisitors || 0);
        },
        error: function (xhr, status, error) {
            console.error("데이터 로드 실패:", error);
        }
    });
});
</script>
</head>

<body>
    <div class="container">
        <%@ include file="/views/common/AdminSidebar.jsp"%>
        <!-- 메인 컨텐츠 -->
        <main class="main-content">
            <header>
                <h1>방문자수 통계</h1>
                <div class="stats">
                    <div>
                        <span>전체 방문자</span>  
                       <strong id="totalVisitors">0</strong>
                    </div>
                    <div>
                        <span>Today</span>  
                      <strong id="todayVisitors">0</strong>
                    </div>
                    <div>
                        <span>Yesterday</span> 
                         <strong id="yesterdayVisitors">0</strong>
                    </div>
                </div>
            </header>

            <!-- 1:1 문의 -->
            <section class="inquiries">
                <h2>1:1 문의</h2>
                <table>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>안녕하세요</td>
                            <td>관리자</td>
                            <td>2024-12-16</td>
                        </tr>
                    </tbody>
                </table>
            </section>
        </main>
    </div>
</body>
</html>
