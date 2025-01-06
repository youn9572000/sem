<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>편리하조 회원가입 약관동의</title>
    <link rel="stylesheet" href="/semi/resources/css1/reset.css" type="text/css">
    <link rel="stylesheet" href="/semi/resources/css1/TermSection.css" type="text/css">
    
    <!--제이쿼리 라이브러리 링크-->
    <script src="/semi/resources/js1/jquery-1.9.1.min.js"></script>

    <!--파일링크-->
    <link rel="stylesheet" href="/semi/resources/js1/jquery.bxslider.css"> <!--bx슬라이더 CSS 연결-->
    <script src="/semi/resources/js1/jquery.bxslider.js"></script> <!--제이쿼리 bx슬라이더 파일 연결-->
    
    <!--제이쿼리 입력-->
    <script type="text/javascript">
    
    //btnFindUid & btnFindPW 클릭시 함수 실행
    $(document).ready(function(){
    	$("#btnFindUid").click(btnFindUid);
    	$("#next-button").click(showSignupSection);
    });
    
    // 약관 동의 후 회원가입 폼 표시
    function showSignupSection(){
    	const termsService = $("#terms-service").is(":checked");
    	const termsPrivacy = $("#terms-privacy").is(":checked");
    	
    	if(!termsService || !termsPrivacy){
    		alert("필수 약관에 동의해주세요.");
    		return;
    	}
    	
    	$(".terms-section").hide();
    	$(".signup-section").show();
    	
    }
    </script>
</head>

<body>
    <!--header-->
    <div id="header_wrap">
        <div id="header">
            <h1 class="logo">
                <a href="/semi/index.jsp"><img src="/semi/resources/img1/logo.png" alt="편리하조 로고"></a>
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
        <h1>회원가입</h1>
        <p>'편리' 사이트에 로그인 하시고 다양한 혜택을 찾아가세요.</p>
    </div>
    <!--//sub_visual-->

    <!--container-->
    <div id="sub_container">
            <!-- 회원가입 약관동의 -->
            <div class="terms-section">
            	<h2>회원가입 약관동의</h2><br><br>
            	<form id="terms-form">
            		<div>
            			<input type="checkbox" id="terms-service" required>
            			<label for="terms-service">(필수) 서비스 이용약관</label>
            			<a href="#">내용보기</a><br><br>
            		</div>
            		<div>
            			<input type="checkbox" id="terms-privacy" required>
            			<label for="terms-privacy">(필수) 개인정보 처리방침</label>
            			<a href="#">내용보기</a><br><br>
            		</div>
            		<div>
            			<input type="checkbox" id="terms-marketing">
            			<label for="terms-marketing">(선택) 마케팅용 이메일 수신 동의</label>
            			<a href="#">내용보기</a><br><br><br><br>
            		</div>
            		<button type="button" id="next-button" onclick="window.location.href='/semi/views/userPage/member/Signup.jsp';">다음으로</button>
            	</form>
            </div>
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