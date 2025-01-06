<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>편리하조 회원가입</title>
    <link rel="stylesheet" href="/semi/resources/css1/reset.css" type="text/css">
    <link rel="stylesheet" href="/semi/resources/css1/Signup.css" type="text/css">
    
    <!-- 부트스트랩 CSS 연결 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!--제이쿼리 라이브러리 링크-->
    <script src="/semi/resources/js1/jquery-1.9.1.min.js"></script>
    
    <!-- 부트스트랩 JS 연결 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

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
    
    
    
    
    <!-- 예빈 추가한 코드 -->
    
    <%
	    String email = (String) session.getAttribute("signupEmail");
	    if(email == null) {
	        email = "";
	    }
	%>
	
    <script>
    let idChecked = false; // 아이디 중복 체크 여부 확인 변수
    
    function idCheck(){
        // 내가 입력한 id값을 server로 전달하여 중복된 사용자인지 조회.
        // 단, 화면이 새로고침되면 안되므로 비동기식으로 요청
        // 만약 중복된 사용자라면 서버는 결과값으로 "NNNNN",
        // 중복되지 않았다면 결과값으로 "YYYYY"

        $.ajax({
            url : '<%= request.getContextPath()%>/user/idCheck',
            data : {userId : $("#enroll-form [name=userId]").val()},
            success : function(result){
                if(result == "NNNNN"){
                    alert("이미 존재하는 회원의 아이디입니다.");
                    idChecked = false;  // 중복된 경우 체크 실패
                } else if(result == "YYYYY"){
                    if(confirm("사용 가능한 아이디입니다. 사용하시겠습니까?")){
                        //아이디값 확정
                        $("#enroll-form [name=userId]").attr('readonly', true);
                        $("#enroll-form :submit").removeAttr('disabled');
                        let submitBtn = $("#signup-btn");
                        submitBtn.attr('title', ''); // title 속성 제거
                        idChecked = true;  // 중복 체크 성공
                    }
                }
            },
            error : function(){
                console.log('에러발생');
                idChecked = false;
            }
        });
    }
    
    $(document).ready(function() {
        $("#enroll-form").on("submit", function(e) {
            if (!idChecked) {
                alert("아이디 중복 확인을 먼저 진행해 주세요.");
                e.preventDefault();  // 중복 체크가 안 된 경우 폼 제출 방지
            }
        });
    });
	</script>
	
	<!-- 비밀번호 일치, 정규식 일치 확인 -->
	<script>
	document.addEventListener("DOMContentLoaded", function(){
        // 비밀번호, 비밀번호 확인 필드 이벤트 리스너
        $("#password, #confirmPassword").on("keyup", function() {
            let password = $("#password").val();
            let confirmPassword = $("#confirmPassword").val();
            
            // 비밀번호 일치 여부 확인
            if (password !== confirmPassword) {
                $("#passwordError").css('display', 'block');
            } else {
                $("#passwordError").css('display', 'none');
            }
        });

        // 폼 제출 시 비밀번호 정규식 체크
        $("#enroll-form").on("submit", function(e) {
            let password = $("#password").val();
            let confirmPassword = $("#confirmPassword").val();
            let passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,15}$/;
            
            if (password !== confirmPassword) {
                alert("비밀번호가 일치하지 않습니다.");
                $("#passwordError").css('display', 'block').css('important', true);;  // 에러 메시지 표시
                e.preventDefault();  // 폼 제출 차단
            }
            
            if (!passwordRegex.test(password)) {
                alert("비밀번호는 8~15자, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.");
                e.preventDefault();
            }
        });
    });
</script>
<!-- 여기까지 -->
    
    
    
    
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
        <h1>회원가입</h1>
        <p>'편리' 사이트에 로그인 하시고 다양한 혜택을 찾아가세요.</p>
    </div>
    <!--//sub_visual-->

    <!--container-->
        <!--container-->
    <div id="sub_container">
            <!-- 회원가입 약관동의 -->
            <!-- 회원가입 -->
            <div class="signup-section">
            	<h1>회원가입</h1>
            	
            	<!-- 예빈 추가한 코드 -->
            	
            	    <!-- 이메일 인증 상태 메시지 -->
				    <%
				    Boolean emailVerified = (Boolean) session.getAttribute("emailVerified");
				    if (emailVerified != null && emailVerified) {
				    %>
				        <p style="color: green;">이메일 인증이 완료되었습니다!</p>
				    <%
				    } else {
				    %>
				        <p style="color: red;">이메일 인증을 진행해 주세요.</p>
				    <%
				    }
				    %>
            	
            	<form id="enroll-form" action="<%= request.getContextPath() %>/user/userInsert" method="post">
            	
            	<!-- 이메일 인증 상태 확인을 위핸 hidden input -->
                <input type="hidden" id="emailVerified" name="emailVerified" value="false">
            	
            	<table>
	              	<tr>
	                    <td>* 아이디</td>
	                    <td><input type="text" name="userId" maxlength="12" required style="width: 250px;"></td>
	                    <td><button type="button" onclick="idCheck();">중복확인</button></td>
	                </tr>
	                <tr>
	                    <td>* 비밀번호</td>
	                    <td>
	                    <input type="password" name="userPwd" id="password" maxlength="15" required
	                    pattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,15}$"
	                    title="비밀번호는 8~15자, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
	                    placeholder="8~15자, 대문자, 소문자, 숫자, 특수문자를 포함"
	                    style="width: 250px;">
	                    </td>
	                    <td></td>
	                </tr>
	                <tr>
	                    <td>* 비밀번호 확인</td>
	                    <td>
	                    <input type="password" name="confirmPwd" id="confirmPassword" maxlength="15" 
	                    placeholder="위와 동일한 비밀번호 입력" style="width: 250px;" required>
	                    <span id="passwordError" style="color: red; font-size: 12px; display: none;">*비밀번호가 일치하지 않습니다.*</span>
	                    </td>
	                </tr>
	                 <tr>
	                    <td>&nbsp;&nbsp;이메일</td>
	                    <td><input type="email" name="email" id="email" style="width: 250px;" 
	                    placeholder="행사정보를 받을 수 있는 이메일을 입력해주세요." value="<%= email %>" readonly></td>
	                </tr>
                </table>
                
                <style>
                
				    #password::placeholder,
				    #email::placeholder,
					#confirmPassword::placeholder {
					    font-size: 11px;
					    color: gray;
					}
					
					   td {
				        padding: 10px 5px;  /* 위아래로 10px, 좌우로 5px 간격 추가 */
				    }
									
				</style>
                
            	<div align="center">
	                <button type="submit" disabled id="signup-btn" title="아이디 중복확인을 해주셔야 합니다.">회원가입</button>
	                <button type="reset">초기화</button>
            	</div>
            	</form>
            	<!-- 여기까지 -->
            </div>
            
            
            
            
            
             <!-- 모달을 실행할 버튼 & 필요 없을시 삭제 必 -->
                <div class="modal-btn-container" style="display:flex; justify-content: center;">

                </div>

                <!-- 모달 -->
                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-lg modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="exampleModalLabel">나는 찬둥 회원님 ♥</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                회원가입 성공 하셨어요 ♥
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">메인 페이지</button>
                                <button type="button" class="btn btn-primary">리뷰 게시판 이동</button>
                            </div>
                        </div>
                    </div>
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