<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.kh.user.model.vo.Review"%>
<%@ page import="com.kh.product.model.vo.Product"%>
<%@ page import="com.kh.product.model.service.ProductService"%>
<%@ page import="com.kh.user.model.service.ReviewService"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.List"%>
<%@ page
	import="java.util.Collections, java.text.SimpleDateFormat, java.util.List"%>

<%
// URL로 전달된 pno 값 가져오기
String pno = request.getParameter("pno");

// pno가 없거나 비어 있는 경우 예외 처리
if (pno == null || pno.trim().isEmpty()) {
	out.println("<p>상품 번호가 유효하지 않습니다.</p>");
	return;
}

// pno를 정수로 변환
int productNo;
try {
	productNo = Integer.parseInt(pno);
} catch (NumberFormatException e) {
	out.println("<p>상품 번호가 올바르지 않습니다.</p>");
	return;
}

// ProductService와 ReviewService를 사용하여 데이터 조회
ProductService productService = new ProductService();
Product product = productService.getProductById(productNo);

// 상품 정보가 없을 경우 처리
if (product == null) {
	out.println("<p>해당 상품 정보를 불러올 수 없습니다.</p>");
	return;
}

// 리뷰 리스트 조회
ReviewService reviewService = new ReviewService();
List<Review> reviewList = reviewService.selectReviewList(productNo);
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><%=product.getProductName()%> 상세보기</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/review.css">
<%@ include file="/views/common/menubar.jsp"%>

<!--sub_visual-->
<div id="sub_visual"
	style="background: linear-gradient(90deg, #7040ff, #d588ff);">
	<h1>상품 자세히 보기</h1>
	<p>관련 상품을 '찜 등록' 할 수 있습니다.</p>
</div>
<!--//sub_visual-->
</head>

<body>


	<!-- 상품 정보 출력 -->
	<div id="product-container">
		<img src="<%=product.getImageUrl()%>" alt="상품 이미지"
			style="width: 100%; max-width: 300px; height: auto; margin-top: 20px; margin-right: 20px; margin-bottom: 20px; border-radius: 8px; box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1)">

		<!-- 상품 텍스트 존 -->
		<h3 style="font-weight: 500;">
			상품명:<%=product.getProductName()%>
			<hr>
			<p style="font-size: 15px;">
				<span style="font-weight: bold">가격</span>:
				<%=product.getProductPrice()%>원
			</p>
			<p style="font-size: 15px;">
				<span style="font-weight: bold">상품설명</span>: 풍성한 재료와 신선한 야채들의 조합
		</h3>

	</div>
	<!-- 찜 등록 폼 -->
	<form action="<%=request.getContextPath()%>/kart/insert" method="post"
		style="width: 300px; height: 50px; border: none; border-radius: none; box-shadow: none;">
		<input type="hidden" name="productNo"
			value="<%=product.getProductNo()%>"> <input type="hidden"
			name="productName" value="<%=product.getProductName()%>">
		<button type="submit"
			style="margin-bottom: 220px; margin-left: 120px; background-color: #fae100; color: white; border: 0.4px; font-weight: bold;">
			찜 등록</button>
	</form>


	<!-- 리뷰 작성 폼 -->
	<%
	Member user = (Member) session.getAttribute("loginUser");
	if (user != null) { // 로그인된 경우
		System.out.println("로그인성공");
	%>
	<!-- 리뷰 작성 폼 -->
	<div class="review-container">
		<form action="<%=request.getContextPath()%>/review/create"
			method="post">
			<textarea name="reviewContent" placeholder="리뷰를 작성해주세요" required
				style="text-align: center;"></textarea>

			<div class="star-rating">
				<!-- Hidden input to store the selected score -->
				<input type="hidden" name="reviewScore" id="selectedRating"
					value="0" required>
				<!-- 5 stars for rating selection -->
				<%
				for (int i = 1; i <= 5; i++) {
				%>
				<span class="star" data-rating="<%=i%>">&#9733;</span>
				<%
				}
				%>
			</div>
			<input type="hidden" name="productNo"
				value="<%=product.getProductNo()%>"> <input type="hidden"
				name="reviewWriter" value="<%=user.getUserId()%>">
			<button type="submit">리뷰 등록</button>
		</form>
	</div>
	<%
	} else { // 로그인되지 않은 경우
	%>
	<p style="margin-top: 20px;">
		리뷰를 작성하려면 <a
			href="<%=request.getContextPath()%>/views/userPage/member/login.jsp">로그인</a>해주세요.
	</p>
	<%
	}
	%>

	<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	%>

	<ul class="review-list">
		<%
		if (reviewList != null && !reviewList.isEmpty()) {
			Collections.sort(reviewList, (r1, r2) -> r2.getReviewDate().compareTo(r1.getReviewDate()));

			for (Review review : reviewList) {
				boolean isReviewOwner = user != null && review.getReviewWriter() == user.getUserId();
		%>
		<li class="review-item">

			<p class="review-writer">
				작성자:<%=review.getReviewWriter()%></p>
			<p id="review-content-<%=review.getReviewNo()%>"
				class="review-content">
				내용:
				<%=review.getReviewContent()%>
			</p>
			<p>
				<span class="stars"> <%
 for (int i = 1; i <= 5; i++) {
 %> <span
					class="<%=i <= review.getReviewScore() ? "filled-star" : "empty-star"%>">&#9733;</span>
					<%
					}
					%>
				</span>
			</p>
			<p style="margin: 15px;">
				<span class="review-actions"> <%
 if (isReviewOwner) {
 %> <!-- 수정 버튼 -->
					<button class="small-btn edit-review-btn"
						data-review-no="<%=review.getReviewNo()%>" style="width: 50px;">수정</button>
					<!-- 숨겨진 수정 폼 -->
					<div id="edit-form-<%=review.getReviewNo()%>" class="edit-form"
						style="display: none;">
						<form class="review-edit-form">
							<h2 style="font-size: 18px; font-weight: 600;">"용우님, 수정하실
								부분을 작성해 주세요"</h2>
							<br> <br>
							<textarea name="reviewContent" placeholder="리뷰 내용을 입력하세요"
								style="width: 550px; height: 500px; text-align: center;"><%=review.getReviewContent()%></textarea>
							<br> <input type="number" name="reviewScore"
								value="<%=review.getReviewScore()%>" min="1" max="5"
								style="width: 20%;">
							<div
								style="display: flex; justify-content: flex-end; gap: 10px; margin-top: 10px;">
								<button type="button" class="save-review-btn"
									data-review-no="<%=review.getReviewNo()%>"
									style="background-color: grey;">저장</button>
								<button type="button" class="cancel-edit-btn"
									data-review-no="<%=review.getReviewNo()%>"
									style="background-color: grey;">취소</button>
							</div>
						</form>
					</div> <!-- 삭제 버튼 -->
					<form action="<%=request.getContextPath()%>/review/delete"
						method="post"
						style="border: none; border-radius: none; box-shadow: none; padding: none; width: 0px; height: 0px;">
						<input type="hidden" name="reviewNo"
							value="<%=review.getReviewNo()%>"> <input type="hidden"
							name="productNo" value="<%=product.getProductNo()%>">
						<button class="small-btn delete-btn" type="submit"
							onclick="return confirm('정말로 삭제하시겠습니까?');"
							style="width: 50px; margin-top: 7px;">삭제</button>
					</form> <%
 }
 %> <span class="review-status">
    <%=(review.getReviewDec() != null && review.getReviewDec().equals("Y")) ? "신고됨" : "정상"%>
</span>
			</p>
			<p style="margin: 15px;"><%=(review.getReviewDate() != null) ? sdf.format(review.getReviewDate()) : "작성일 없음"%></p>
			<%
			if (review.getReviewDec().equals("N")) {
			%>
			<button class="report-btn" data-review-no="<%=review.getReviewNo()%>"
				data-review-status="N">신고</button> <%
 } else if (isReviewOwner) {
 %>
			<button class="cancel-report-btn"
				data-review-no="<%=review.getReviewNo()%>" data-review-status="Y">신고
				취소</button> <%
 }
 %>
		</li>
		<%
		}
		} else {
		%>
		<li>아직 작성된 리뷰가 없습니다.</li>
		<%
		}
		%>
	</ul>
	<script>
    const contextPath = '<%=request.getContextPath()%>'; // JSP에서 contextPath 가져오기

 // 페이지 로드 시 초기화
  // 페이지 로드 시 초기화
window.onload = () => {
    const contextPath = '<%=request.getContextPath()%>'; // JSP에서 contextPath 가져오기

    // 신고 버튼 이벤트 추가
    document.querySelectorAll(".report-btn").forEach(button => {
        button.addEventListener("click", () => handleReportAction(button, 'Y', contextPath));
    });

    // 신고 취소 버튼 이벤트 추가
    document.querySelectorAll(".cancel-report-btn").forEach(button => {
        button.addEventListener("click", () => handleReportAction(button, 'N', contextPath));
    });
};
document.addEventListener("DOMContentLoaded", () => {
    const stars = document.querySelectorAll(".star-rating .star");
    const selectedRating = document.getElementById("selectedRating");

    // 별점 선택 이벤트
    stars.forEach(star => {
        star.addEventListener("click", () => {
            const rating = parseInt(star.getAttribute("data-rating"));

            // 모든 별을 기본 상태로 초기화
            stars.forEach(s => s.classList.remove("selected"));

            // 선택된 별 및 이전 별들을 활성화
            for (let i = 0; i < rating; i++) {
                stars[i].classList.add("selected");
            }

            // 선택한 점수 저장
            selectedRating.value = rating;
        });
    });
});

// 신고 또는 신고 취소 처리
function handleReportAction(button, newStatus, contextPath) {
    const reviewNo = button.getAttribute("data-review-no");
    const requestURL = `\${contextPath}/review/report`; // POST 요청

    // 신고 취소 시 사용자 확인
    if (newStatus === 'N' && !confirm("신고를 취소하시겠습니까?")) {
        return; // 사용자가 취소를 누르면 동작 중단
    }

    // 서버 요청
    fetch(requestURL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `reviewNo=\${reviewNo}&newStatus=\${newStatus}`
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: \${response.status}`);
            }
            return response.text(); // 서버 응답 처리
        })
        .then(data => {
            if (data === "success") {
                alert(newStatus === 'Y' ? "신고가 접수되었습니다." : "신고가 취소되었습니다.");

                // 상태 업데이트
                const statusElement = button.closest(".review-item").querySelector(".review-status");
                if (statusElement) {
                    statusElement.textContent = newStatus === 'Y' ? "신고됨" : "정상";
                }

                // 버튼 변경 (신고 <-> 신고 취소)
                toggleReportButton(button, newStatus);
            } else {
                alert("로그인후 이용해주세요!.");
            }
        })
        .catch(error => {
            alert(`서버와 통신 중 오류가 발생했습니다:\${error.message}`);
        });
}
// 페이지 로드 시 상태 동기화
function fetchReviewsAndUpdateUI() {
    const requestURL = `${contextPath}/review/list`; // 리뷰 리스트 API

    fetch(requestURL)
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                data.reviews.forEach(review => {
                    const reviewItem = document.querySelector(`.review-item[data-review-no="${review.reviewNo}"]`);
                    if (reviewItem) {
                        const reportButton = reviewItem.querySelector(".report-btn");
                        const cancelReportButton = reviewItem.querySelector(".cancel-report-btn");
                        const statusElement = reviewItem.querySelector(".review-status");

                        if (review.reviewStatus === "Y") {
                            // 신고 상태
                            if (statusElement) statusElement.textContent = "신고됨";
                            if (reportButton) reportButton.style.display = "none";
                            if (cancelReportButton) cancelReportButton.style.display = "inline-block";
                        } else {
                            // 정상 상태
                            if (statusElement) statusElement.textContent = "정상";
                            if (reportButton) reportButton.style.display = "inline-block";
                            if (cancelReportButton) cancelReportButton.style.display = "none";
                        }
                    }
                });
            } else {
                console.error("리뷰 데이터를 가져오는 데 실패했습니다.");
            }
        })
        .catch(error => {
            console.error("리뷰 데이터를 가져오는 중 오류 발생:", error);
        });
}

// 버튼 상태 전환
function toggleReportButton(button, newStatus) {
    const reviewItem = button.closest(".review-item");

    // 기존 버튼 제거
    button.remove();

    // 새 버튼 생성
    const newButton = document.createElement("button");
    newButton.classList.add(newStatus === 'Y' ? "cancel-report-btn" : "report-btn");
    newButton.setAttribute("data-review-no", button.getAttribute("data-review-no"));
    newButton.setAttribute("data-review-status", newStatus);
    newButton.textContent = newStatus === 'Y' ? "신고 취소" : "신고";

    // 새 버튼 삽입
    reviewItem.appendChild(newButton);

    // 새 버튼에 이벤트 리스너 추가
    newButton.addEventListener("click", () => handleReportAction(newButton, newStatus === 'Y' ? 'N' : 'Y', '<%=request.getContextPath()%>'));  
}
function enableEditMode(reviewNo) {
    const reviewItem = document.querySelector(`.review-item[data-review-no='\${reviewNo}']`);
    reviewItem.querySelector('.review-content-container').style.display = 'none';
    reviewItem.querySelector('.review-edit-container').style.display = 'block';
}
//수정 모드 취소
function cancelEdit(reviewNo) {
    const reviewItem = document.querySelector(`.review-item[data-review-no='\${reviewNo}']`);
    reviewItem.querySelector('.review-content-container').style.display = 'block';
    reviewItem.querySelector('.review-edit-container').style.display = 'none';
}
//별점 설정
function setRating(starElement) {
    const stars = starElement.parentNode.querySelectorAll('.star');
    const rating = parseInt(starElement.getAttribute('data-rating'), 10);

    stars.forEach((star, index) => {
        star.classList.toggle('selected', index < rating);
    });
    starElement.parentNode.setAttribute('data-selected-rating', rating);
}
document.querySelectorAll(".edit-review-btn").forEach(button => {
    button.addEventListener("click", () => {
        const reviewNo = button.getAttribute("data-review-no");
        const editForm = document.getElementById(`edit-form-\${reviewNo}`);

        if (editForm) {
            editForm.style.display = "block"; // 수정 폼 표시
        } else {
            console.error(`수정 폼을 찾을 수 없습니다: edit-form-\${reviewNo}`);
        }
    });
});


document.querySelectorAll(".cancel-edit-btn").forEach(button => {
    button.addEventListener("click", () => {
        const reviewNo = button.getAttribute("data-review-no");
        const editForm = document.getElementById(`edit-form-\${reviewNo}`);
        editForm.style.display = "none"; // 수정 폼 숨기기
    });
});
document.querySelectorAll(".save-review-btn").forEach(button => {
    button.addEventListener("click", () => {
        const reviewNo = button.getAttribute("data-review-no");
        const editForm = document.getElementById(`edit-form-\${reviewNo}`);
        const content = editForm.querySelector("textarea[name='reviewContent']").value;
        const score = editForm.querySelector("input[name='reviewScore']").value;

        // 입력값 검증
        if (!content.trim()) {
            alert("리뷰 내용을 입력하세요.");
            return;
        }
        if (score < 1 || score > 5) {
            alert("별점은 1~5 사이여야 합니다.");
            return;
        }

        // AJAX 요청
        fetch(`\${contextPath}/review/update`, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: `reviewNo=\${reviewNo}&content=\${encodeURIComponent(content)}\&reviewScore=\${score}`,
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: \${response.status}`);
            }
            return response.json(); // JSON 응답 처리
        })
        .then(data => {
            if (data.success) {
                alert(data.message);
                // UI 업데이트
                editForm.style.display = "none";
                document.querySelector(`#review-content-\${reviewNo}`).innerText = content;
            } else {
                alert(data.message);
            }
        })
        .catch(error => console.error("수정 요청 중 오류 발생:", error));
    });
});

document.querySelectorAll(".update-btn").forEach(button => {
    button.addEventListener("click", () => {
        const reviewNo = button.getAttribute("data-review-no");
        const content = prompt("수정할 내용을 입력하세요:");
        let score;

        // 별점 입력 및 검증
        while (true) {
            score = prompt("수정할 별점을 입력하세요 (1~5):");
            if (score && !isNaN(score) && score >= 1 && score <= 5) {
                break; // 입력값이 유효하면 반복 종료
            } else {
                alert("별점은 1~5 사이의 숫자여야 합니다.");
                break;
            }
        }

        // AJAX 요청 보내기
        if (content && score) {
            fetch(`\${contextPath}/review/update`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: `reviewNo=\${reviewNo}&content=\${encodeURIComponent(content)}\&reviewScore=\${reviewScore}`,
            })
              
                	 .then(response => {
                         if (!response.ok) {
                             throw new Error(`HTTP error! status: \${response.status}`);
                         }
                         return response.json();
                     })
                .then(data => {
                    if (data.success) {
                        alert(data.message);
                        // UI 업데이트 (수정된 리뷰 표시)
                        document.querySelector(`#review-content-\${reviewNo}`).innerText = content;
                        
                    } else {
                        alert(data.message);
                    }
                })
                .catch(error => {
                    console.error("오류 발생:", error);
                    alert("수정 중 문제가 발생했습니다.");
                });
        }
    });
});
</script>

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