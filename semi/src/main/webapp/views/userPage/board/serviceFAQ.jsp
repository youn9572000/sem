<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>자주 하는 질문 TOP 5</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/service.css"
	type="text/css">

</head>
<body>
	<div class="container">
		<!-- Sidebar Include -->
		<div><%@ include file="/views/common/serviceSidebar.jsp"%></div>

		<div class="content">
			<!-- Header Include -->
			<div><%@ include file="/views/common/serviceHeader.jsp"%></div>

			<div class="inquiry">
				<div class="faq-container">
					<div class="faq-title">
						자주 하는 질문 <strong>TOP 5</strong>
					</div>
					<div class="faq-item">
						<div class="faq-question">
							<span>🔍 아이디/비밀번호는 어디서 변경하나요?</span> <span class="faq-toggle">▼</span>
						</div>
						<div class="faq-answer">설정 메뉴에서 아이디 및 비밀번호를 변경할 수 있습니다.</div>
					</div>
					<div class="faq-item">
						<div class="faq-question">
							<span>🔍 다른 편의점 정보는 안나오나요?</span> <span class="faq-toggle">▼</span>
						</div>
						<div class="faq-answer">현재 제공되는 정보 외 다른 정보는 준비 중입니다.</div>
					</div>
					<div class="faq-item">
						<div class="faq-question">
							<span>🔍 사이트에서 상품을 구매할 수는 없나요?</span> <span class="faq-toggle">▼</span>
						</div>
						<div class="faq-answer">온라인 구매는 현재 지원되지 않습니다.</div>
					</div>
					<div class="faq-item">
						<div class="faq-question">
							<span>🔍 편의점 쿠폰은 발행이 안되나요?</span> <span class="faq-toggle">▼</span>
						</div>
						<div class="faq-answer">일부 상품에 한해 쿠폰이 발행됩니다.</div>
					</div>
				</div>
			</div>


		</div>
	</div>


	<script>
    // JavaScript: FAQ 토글 기능
    document.querySelectorAll('.faq-item').forEach(item => {
      item.addEventListener('click', () => {
        item.classList.toggle('active');
      });
    });
  </script>





</body>
</html>
