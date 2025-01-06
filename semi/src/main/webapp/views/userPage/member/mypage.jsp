<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.kh.user.model.vo.Member"%>
<%@ page import="com.kh.user.model.vo.Reply"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이페이지</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/mypage.css"
	type="text/css">
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>

<script>
//새 비밀번호 확인 자바스크립트 검증
function validatePassword() {
    const newPassword = document.getElementById("new_password").value;
    const confirmPassword = document.getElementById("confirm_password").value;
    if (newPassword !== confirmPassword) {
        alert("새 비밀번호가 일치하지 않습니다.");
        return false;
    }
    return true;
}

function showSection(sectionId) {
    // 모든 섹션 숨기기
    document.querySelectorAll('.mypage-container > div').forEach(section => {
        section.style.display = 'none'; // 모든 섹션 숨기기
    });

    // 클릭한 섹션 보이기
    const targetSection = document.getElementById(sectionId);
    if (targetSection) {
        targetSection.style.display = 'block'; // 선택된 섹션 보이기
    }

    // 활성화 클래스 업데이트
    document.querySelectorAll('.mypage-nav a').forEach(link => link.classList.remove('active')); // 모든 링크에서 'active' 제거
    const clickedLink = document.querySelector(`a[data-section="${sectionId}"]`);
    if (clickedLink) {
        clickedLink.classList.add('active'); // 현재 클릭한 링크에 'active' 추가
    }

    // 특정 섹션의 내용을 초기화 (중복 렌더링 방지)
    if (sectionId === 'my-posts') {
        document.querySelector('#comments-bottom ul').innerHTML = '';
    } else if (sectionId === 'comments-bottom') {
        document.querySelector('#my-posts ul').innerHTML = '';
    }
}

document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.mypage-nav a').forEach(navLink => {
        navLink.addEventListener('click', function (e) {
            e.preventDefault();
            const sectionId = this.getAttribute('data-section');
            showSection(sectionId);

            // AJAX 요청 처리
            if (sectionId === 'my-posts') {
                // 글 목록 불러오기
                fetch('/semi/member/myBoardTitles')
                    .then(response => {
                        if (!response.ok) throw new Error("네트워크 응답이 올바르지 않습니다.");
                        return response.text();
                    })
                    .then(data => {
                        document.querySelector('#my-posts ul').innerHTML = data; // 응답 데이터를 삽입
                    })
                    .catch(error => {
                        console.error('글 목록 불러오기 오류:', error);
                        alert('글 목록을 불러오는 데 실패했습니다.');
                    });
            } else if (sectionId === 'comments-bottom') {
                // 댓글 목록 불러오기
                fetch('/semi/member/myComments')
                    .then(response => {
                        if (!response.ok) throw new Error("네트워크 응답이 올바르지 않습니다.");
                        return response.text();
                    })
                    .then(data => {
                        document.querySelector('#comments-bottom ul').innerHTML = data; // 응답 데이터를 삽입
                    })
                    .catch(error => {
                        console.error('댓글 목록 불러오기 오류:', error);
                        alert('댓글 목록을 불러오는 데 실패했습니다.');
                    });
            }
        });
    });
    function deleteAccount() {
        if (confirm('정말로 계정을 삭제하시겠습니까?')) {
            window.location.href = 'deleteAccount.jsp';
        }
    }

    // 초기화 - 첫 번째 섹션만 보이도록 설정
    showSection('sub_containe'); // 기본적으로 회원정보 섹션 표시
});
    </script>
</head>
<body>
	<header>
		<div class="header-container">
			<h1 class="logo">
				<a href="/semi/index.jsp"> <img
					src="<%=request.getContextPath()%>/resources/img/logo.png"
					alt="편리하조 로고">
				</a>
			</h1>
			<h2>마이페이지</h2>
		</div>
	</header>

	<main>
		<section class="mypage-container">
			<nav class="mypage-nav">
				<ul>
					<li><a href="#" class="active" data-section="sub_containe">회원정보</a></li>
					<li><a href="<%=request.getContextPath()%>/member/mypage.jsp"
						data-section="my-posts">등록한 글 목록</a></li>
					<li><a href="#" data-section="comments-bottom">등록한 댓글 목록</a></li>
					<li><a href="<%=request.getContextPath()%>/kart/savelist"
						onclick="window.location.href=this.href">찜 목록</a></li>
				</ul>
			</nav>

			<!-- 회원정보 섹션 -->
			<div id="sub_containe">
				<form action="<%=request.getContextPath()%>/member/update"
					method="POST" onsubmit="return validatePassword()">
					<%
                // 세션에서 loginUser 객체 가져오기
                Member loginUser = (Member) session.getAttribute("loginUser");

                // 로그인하지 않은 상태면 로그인 페이지로 리디렉션
                if (loginUser == null) {
                    response.sendRedirect("/login.jsp");
                    return;
                }
                %>

					<!-- 아이디 수정 -->
					<div
						style="width: 1800px; height: 1000px; background-color: none; margin: 50px auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; display: flex; align-items: center; justify-content: space-between;">
						<div style="width: 60%;">
							<label for="userId"
								style="display: block; font-weight: bold; margin-bottom: 8px; font-size: 16px; color: #333;">아이디:</label><br>
							<input type="text" id="userId" name="userId"
								value="<%=loginUser.getUserId()%>" readonly
								style="width: 70%; padding: 12px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px; box-sizing: border-box;"><br>
							<br>

							<!-- 이메일 수정 -->
							<label for="email"
								style="display: block; font-weight: bold; margin-bottom: 8px; font-size: 16px; color: #333;">이메일:</label><br>
							<input type="email" id="email" name="email"
								value="<%=loginUser.getEmail()%>" required
								style="width: 70%; padding: 12px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px; box-sizing: border-box;"><br>
							<br>

							<!-- 현재 비밀번호 입력 -->
							<label for="password"
								style="display: block; font-weight: bold; margin-bottom: 8px; font-size: 16px; color: #333;">현재
								비밀번호:</label><br> <input type="password" id="password"
								name="password" placeholder="현재 비밀번호" required
								style="width: 70%; padding: 12px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px; box-sizing: border-box;"><br>
							<br>

							<!-- 새 비밀번호 입력 -->
							<label for="new_password"
								style="display: block; font-weight: bold; margin-bottom: 8px; font-size: 16px; color: #333;">새
								비밀번호:</label><br> <input type="password" id="new_password"
								name="new_password" placeholder="새 비밀번호" required
								style="width: 70%; padding: 12px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px; box-sizing: border-box;"><br>
							<br>

							<!-- 새 비밀번호 확인 -->
							<label for="confirm_password"
								style="display: block; font-weight: bold; margin-bottom: 8px; font-size: 16px; color: #333;">새
								비밀번호 확인:</label><br> <input type="password" id="confirm_password"
								name="confirm_password" placeholder="새 비밀번호 확인" required
								style="width: 70%; padding: 12px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 4px; font-size: 16px; box-sizing: border-box;"><br>
							<br>

							<!-- Submit 버튼 -->
							<button type="submit"
								style="border-radius: 4px; background-color: grey; color: white;">수정</button>
				</form>
				
				
		<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteForm" 
        style="width:100px; height:30px; border-radius:4px; background-color:grey; color:white;font-size:15px;">
    회원탈퇴
</button>
<div class="modal fade" id="deleteForm" tabindex="-1" role="dialog"
			aria-labelledby="deleteFormLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="deleteFormLabel">회원탈퇴 확인</h5>
					</div>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					<div class="modal-body" align="center">
						<b>탈퇴 후 복구가 불가능합니다.<br>정말로 탈퇴하시겠습니까?
						</b><br>
						<br>
						<form action="<%= request.getContextPath() %>/member/delete"
							method="post">
							<table>
								<tr>
									<td>비밀번호</td>
									<td><input type="password" name="userPwd" required></td>
								</tr>
							</table>
							<br>
							<button type="submit" class="btn btn-danger btn-sm">탈퇴하기</button>
						</form>
					</div>
				</div>
			</div>
		</div>


</div>
			

		</section>

		<!-- 등록한 글 목록 섹션 -->
		<div id="my-posts" style="display: none;">
			<h2>내가 작성한 글 목록</h2>
			<ul>
				<%
        List<String> titles = (List<String>) request.getAttribute("titles");
        if (titles != null && !titles.isEmpty()) {
            for (String title : titles) {
        %>
				<li><%= title %></li>
				<%
            }
        } else {
        %>
				<li>작성한 글이 없습니다.</li>
				<%
        }
        %>
			</ul>
		</div>

		<div id="comments-bottom">
    <h2>내가 작성한 댓글 목록</h2>
   <ul>
    <% 
        List<String> replies = (List<String>) request.getAttribute("replies");
        if (replies != null && !replies.isEmpty()) { 
    %>
        <% for (String replyContent : replies) { %>
            <li><%= replyContent %></li>
        <% } %>
    <% } else { %>
        <li>작성한 댓글이 없습니다.</li>
    <% } %>
</ul>
</div>
		</section>



	</main>
</body>
</html>
