<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>편리하조 아이디 및 비밀번호 찾기</title>
    <link rel="stylesheet" href="/semi/resources/css1/reset.css" type="text/css">
    <link rel="stylesheet" href="/semi/resources/css1/idCheck.css" type="text/css">
    <script src="/semi/resources/js1/jquery-1.9.1.min.js"></script>
</head>
<body>
    <!--header-->
    <div id="header_wrap">
        <div id="header">
            <h1 class="logo">
                 <a href="<%= request.getContextPath() %>/index.jsp"><img src="/semi/resources/img1/logo.png" alt="logo"></a>
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
        <h1>아이디 및 비밀번호 찾기</h1>
        <p>'편리' 사이트에 로그인하시고 다양한 혜택을 누리세요.</p>
    </div>
    <!--//sub_visual-->
    
    <!--container-->
    <div id="sub_container">
        <!-- 아이디 찾기 -->
        <form id="findIdForm" method="post">
            <fieldset>
            
                <ul id="findID">
                    <li><label for="Email">E-mail</label><input type="text" id="Email" name="Email" placeholder="이메일 입력"></li>
                    <li><input type="button" id="btnFindUid" value="아이디 찾기"></li>
                </ul>
            </fieldset>
        </form>

        <hr>

        <!-- 비밀번호 찾기 -->
        <form id="findPwForm" method="post">
            <fieldset>
             
                <ul id="findPW">
                    <li><label for="email">E-mail</label><input type="text" id="email" name="email" placeholder="이메일 입력"></li>
                    <li><label for="userId">ID</label><input type="text" id="userId" name="userId" placeholder="아이디 입력"></li>
                    <li><input type="button" id="sendCode" value="인증번호 발송"></li>
                    <li><label for="verificationCode">인증번호</label><input type="text" id="verificationCode" name="verificationCode" placeholder="인증번호 입력" disabled></li>
                    <li><input type="button" id="verifyCode" value="인증번호 확인" disabled></li>
                    <li><label for="newPassword">새 비밀번호</label><input type="password" id="newPassword" name="newPassword" placeholder="새 비밀번호 입력" disabled></li>
                    <li><input type="button" id="btnChangePw" value="비밀번호 변경" disabled></li>
                </ul>
            </fieldset>
        </form>
    </div>
    <!--//container-->

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
        </div>
    </div>
    <!-- //footer -->

    <!-- Script -->
    <script>
        // 아이디 찾기
        $("#btnFindUid").click(function () {
            var email = $("#Email").val();
            if (!email) {
                alert("이메일을 입력하세요.");
                return;
            }
            $.ajax({
                url: "/semi/member/idfind",
                type: "POST",
                data: { email: email },
                success: function (response) {
                    if (response.userId) {
                        alert("아이디는: " + response.userId);
                    } else {
                        alert(response.message);
                    }
                },
                error: function () {
                    alert("아이디 찾기 중 오류가 발생했습니다.");
                }
            });
        });

        // 인증번호 발송
        $("#sendCode").click(function () {
            var email = $("#email").val();
            var userId = $("#userId").val();
            if (!email || !userId) {
                alert("이메일과 아이디를 모두 입력하세요.");
                return;
            }
            $.ajax({
                url: "/semi/member/pwdcheck",
                type: "POST",
                data: { email: email, userId: userId },
                success: function (response) {
                    alert(response.message);
                    $("#verificationCode").prop("disabled", false);
                    $("#verifyCode").prop("disabled", false);
                },
                error: function () {
                    alert("인증번호 발송 중 오류가 발생했습니다.");
                }
            });
        });

        // 인증번호 확인
        $("#verifyCode").click(function () {
            var email = $("#email").val();
            var code = $("#verificationCode").val();
            if (!email || !code) {
                alert("이메일과 인증번호를 입력하세요.");
                return;
            }
            $.ajax({
                url: "/semi/member/verifyCode",
                type: "POST",
                data: { email: email, code: code },
                success: function () {
                    alert("인증이 완료되었습니다.");
                    $("#newPassword").prop("disabled", false);
                    $("#btnChangePw").prop("disabled", false);
                },
                error: function () {
                    alert("인증번호가 일치하지 않습니다.");
                }
            });
        });

        // 비밀번호 변경
        $("#btnChangePw").click(function () {
            var email = $("#email").val();
            var newPassword = $("#newPassword").val();
            if (!newPassword) {
                alert("새 비밀번호를 입력하세요.");
                return;
            }
            $.ajax({
                url: "/semi/member/changePassword",
                type: "POST",
                data: { email: email, newPassword: newPassword },
                success: function () {
                    alert("비밀번호가 성공적으로 변경되었습니다.");
                },
                error: function () {
                    alert("비밀번호 변경 중 오류가 발생했습니다.");
                }
            });
        });
    </script>
</body>
</html>
