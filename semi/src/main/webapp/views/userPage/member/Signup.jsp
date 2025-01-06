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
    <div id="sub_container">
            <!-- 회원가입 약관동의 -->
            <!-- 회원가입 -->
            <div class="signup-section">
            	<h1>회원가입</h1>
            	
            	<!-- 예빈 추가한 코드 -->
            	<form id="enroll-form" action="<%= request.getContextPath() %>/user/userInsert" method="post">
            	<!-- 이메일 인증 상태 확인을 위핸 hidden input -->
            	<input type="hidden" id="emailVerified" name="emailVerified" value="false">
            	
	                <table>
	                 <tr>
	                    <td>&nbsp;&nbsp;이메일</td>
	                    <td><input type="email" name="email" id="email" style="width: 250px;" placeholder="본인인증 가능한 이메일을 입력해주세요." required></td>
	                    <td>
	                    	<button type="button" onclick="sendVerificationEmail();">본인 인증</button>
	                    </td>
	                </tr>
                </table>
                
                <script>
                function sendVerificationEmail(){
                	let email = $("#email").val();
                	
                	if(!email){
                		alert("이메일을 입력해주세요.");
                		return;
                	}
                	
                    $.ajax({
                        type: "POST",
                        url: '<%= request.getContextPath() %>/user/checkEmail',
                        data: { email: email },
                        success: function(response) {
                            if(response === 'exists') {
                                alert("이미 존재하는 이메일입니다.");
                                return;  // 중복된 경우 여기서 함수 종료
                            } else {
                                // 이메일이 존재하지 않으면 이메일 저장 및 인증 메일 발송
                                $.ajax({
                                    type: "POST",
                                    url: '<%= request.getContextPath() %>/user/storeEmail',
                                    data: { email: email },
                                    success: function(res) {
                                        if (res === 'success') {
                                            // 인증 메일 발송
                                            $.ajax({
                                                type: "POST",
                                                url: '<%= request.getContextPath()%>/user/sendVerification',
                                                data: { email: email },
                                                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                                                success: function(result) {
                                                    if(result === 'success'){
                                                        alert("인증 메일이 발송되었습니다. 이메일을 확인해주세요.");
                                                    } else {
                                                        alert("인증 메일 발송에 실패했습니다. 다시 시도해주세요.");
                                                    }
                                                },
                                                error: function() {
                                                    alert("서버와의 통신 중 오류가 발생했습니다.");
                                                }
                                            });
                                        } else {
                                            alert("이메일 저장에 실패했습니다. 다시 시도해주세요.");
                                        }
                                    },
                                    error: function() {
                                        alert("서버와의 통신 중 오류가 발생했습니다.");
                                    }
                                });
                            }
                        },
                        error: function() {
                            alert("서버와의 통신 중 오류가 발생했습니다.");
                        }
                    });
                }
                </script>
                
                
                <style>
				 
					#email::placeholder {
					    font-size: 11px;
					    color: gray;
					}
					
					   td {
				        padding: 10px 5px;  /* 위아래로 10px, 좌우로 5px 간격 추가 */
				    }
									
				</style>
                
                
                
            	<div align="center" class="btn-container">
	                <button type="submit" disabled id="signup-btn" title="이메일 본인인증을 해주셔야 합니다.">회원가입</button>
	                <button type="reset" id="reset-btn">초기화</button>
            	</div>
            	</form>
            	<!-- 여기까지 -->
            </div>
            
            <style>
    .btn-container {
        display: flex;
        justify-content: center;
        gap: 20px;  /* 버튼 사이 간격 */
    }

    .btn-container button {
        width: 100px;  /* 버튼 너비 조절 */
        height: 40px;  /* 버튼 높이 조절 */
        font-size: 16px;
        border: none;
        border-radius: 5px;
    }

    
			</style>
            
            
            
            
            
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