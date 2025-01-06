<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>편리하조 아이디 찾기</title>
    <link rel="stylesheet" href="/semi/resources/css1/reset.css" type="text/css">
    <link rel="stylesheet" href="/semi/resources/css1/style.css" type="text/css">

    <script>
        function sendVerificationCode(){
            const email = document.getElementById('userId').value;

            if(email === ""){
                alert("이메일을 입력해주세요.");
                return;
            }

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/semi/member/sendVerificationCode", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onload = function(){
                if(xhr.status === 200){
                    alert("인증번호가 이메일로 전송되었습니다.")
                }
            };

            xhr.send("email" + encodeURIComponent(email));
        }
    </script>
</head>
<body>
    <!--header-->
    <div id="header_wrap">
        <div id="header">
            <h1 class="logo">
                <a href="/semi/index.jsp"><img src="/semi/resources/img1/편의점 로고 예시1.png" alt="편리하조 로고"></a>
            </h1>
            <ul class="gnb">
                <li><a href="#">GS25</a></li>
                <li><a href="#">CU</a></li>
                <li><a href="#">7-ELEVEN</a></li>
                <li><a href="#">게시판</a></li>
                <li><a href="#">고객센터</a></li>
            </ul>
        </div>
    </div>
    <!--//header-->
        
    <!--sub_visual-->
    <div id="sub_visual" style="background:linear-gradient(90deg, #7040ff, #d588ff);">
        <h1>아이디 찾기</h1>
        <p>'편리' 사이트에 로그인 하시고 다양한 혜택을 찾아가세요.</p>
    </div>
    <!--//sub_visual-->

    <!--container-->
    <div id="sub_container">
        <form id="registrationForm" action="/semi/LoginServlet" method="post">
            <label for="userId">등록한 이메일로 찾기</label><br>
            <input type="text" id="userId" name="userId" placeholder="등록한 이메일을 입력해주세요" required><br><br>
            <label for="password">인증번호를 입력해주세요</label><br>
            <input type="password" id="password" name="password" placeholder="인증번호" required><br><br>
            <button type="button">아이디 찾기</button>
        </form>
        <div class="user-login">
            <a href="#" class="icon login">비밀번호 찾기</a>
            <a href="#" class="icon mypage">회원가입</a>
         </div>
    </div>
    <!--//container-->

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