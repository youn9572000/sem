<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="com.kh.product.model.vo.Product"%>
<%
    request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>CU 상품탭</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/common.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/review.css">
<%@ include file="/views/common/menubar.jsp"%>
<!--sub_visual-->
<div id="sub_visual"
	style="background: linear-gradient(90deg, #7040FF, #D588FF);">
	<h1>CU</h1>
	<p>CU와 관련된 상품들을 소개해 드립니다.</p>
</div>
<!--//sub_visual-->
</head>
<body>
	<div id="app">
		<!-- 카테고리 탭 -->
		<div class="tab-bar">
			<div class="tab" data-category="all">전체</div>
			<div class="tab" data-category="snack">간식</div>
			<div class="tab" data-category="food">식품</div>
			<div class="tab" data-category="drink">음료수</div>
			<div class="tab" data-category="ice">아이스크림</div>
			<div class="tab" data-category="noodle">라면</div>
			<div class="tab" data-category="fast">즉석 식품</div>
			<div class="tab" data-category="plus">행사 식품</div>
		</div>
		<!-- 검색창 -->
		<div class="container">
			<img src="/semi/resources/img1/main_visual_illu.png" alt="cu-char">
			<div class="row" style="justify-content: center; margin-top: 30px;">
				<form id="searchForm">
					<input type="text" class="form-control" placeholder="상품을 입력해주세요"
						id="searchText">
					<button type="button" class="btn btn-success" id="searchButton">
						상품 검색</button>
				</form>
			</div>
		</div>
		<div id="banner-container"
			style="display: none; text-align: center; margin: 20px">
			<button class="banner-button" data-subcategory="oneplus"
				style="margin: 10px; padding: 10px 20px">1+1 상품</button>
			<button class="banner-button" data-subcategory="twoplus"
				style="margin: 10px; padding: 10px 20px">2+1 상품</button>
		</div>
		<!-- 이미지 리스트 컨테이너 -->
		<div class="content" id="image-container"></div>
	</div>
	<script>
	const API_URL = "http://192.168.30.5:5000/api/images";
	const contextPath = "<%= request.getContextPath() %>";
	  async function fetchImages(category = "all", subcategory = "", searchText = "") {
		  console.log("category:", category, "subcategory:", subcategory, "searchText:", searchText);
          try {
              const response = await fetch(API_URL); // API 호출
              const data = await response.json();
              console.log("API 응답 데이터:", data); // API 응답 디버깅
              console.log("입력된 검색어:", searchText); // 검색어 디버깅
              if (!data.success) {
                  console.error("API 호출 실패: 성공 플래그가 false입니다.");
                  return;
              }
              const container = document.getElementById("image-container");
              if (!container) {
                  return;
              }
              console.log("PRODUCT_NAME 값과 타입 확인:", data.images.map((image) => ({
                  name: image.PRODUCT_NAME,
                  type: typeof image.PRODUCT_NAME,
              })));
              container.innerHTML = ""; // 컨테이너 초기화
              // 필터링된 이미지 리스트 생성
            const filteredImages = data.images.filter((image) => {
    const hasProductNo = image.PRODUCT_NO !== undefined && image.PRODUCT_NO !== null;
    console.log("hasProductNo:", hasProductNo);
    // CATEGORY_NAME을 항상 배열로 처리
    const categoryNames = Array.isArray(image.CATEGORY_NAME)
        ? image.CATEGORY_NAME
        : image.CATEGORY_NAME.split(",").map((item) => item.trim());
    // 카테고리 및 서브카테고리 필터링
    const matchesCategory = category === "all" || categoryNames.includes(category);
    console.log("matchesCategory:", matchesCategory);
    const matchesSubcategory = subcategory ? categoryNames.includes(subcategory) : true;
    console.log("matchesSubcategory:", matchesSubcategory);
///////////////////////////////////////////////////////////////////////////////
    const matchesSearch = searchText
    ? Array.isArray(image.PRODUCT_NAME)
        ? image.PRODUCT_NAME.some((name) =>
              name.toLowerCase().includes(searchText.toLowerCase())
          )
        : typeof image.PRODUCT_NAME === "string" &&
          image.PRODUCT_NAME.toLowerCase().includes(searchText.toLowerCase())
    : true;
    return hasProductNo && matchesCategory && matchesSubcategory && matchesSearch;
});
              console.log("Filtered Images:", filteredImages); // 필터링된 이미지 디버깅
              if (filteredImages.length === 0) {
                  container.innerHTML = "<p>검색 결과가 없습니다.</p>";
                  return;
              }
              filteredImages.forEach((image) => {
                  console.log("상품 데이터 디버깅:", image);
                  if (!image.PRODUCT_NO) {
                      console.error("상품 번호(PRODUCT_NO)가 없습니다. 이 상품은 건너뜁니다.");
                      return;
                  }
                  // 링크 생성
                  const linkElement = document.createElement("a");
                  const url = `\${contextPath}/product/detail?pno=\${image.PRODUCT_NO}`;
                  console.log("생성된 URL:", url);
                  linkElement.href = url;
                  linkElement.target = "_self";
                  // 이미지 생성
                  const imgElement = document.createElement("img");
                  imgElement.src = image.IMAGE_URL || "default-image.jpg"; // 기본 이미지
                  imgElement.alt = image.PRODUCT_NAME[0] || "상품 이름 없음"; // 상품 이름
                  imgElement.style.width = "200px";
                  imgElement.style.margin = "30px";
                  linkElement.appendChild(imgElement);
                  container.appendChild(linkElement);
                  // 상품 이름
                  const nameElement = document.createElement("p");
                  nameElement.textContent = `상품명: \${image.PRODUCT_NAME}`;
                  // 상품 가격
                  const priceElement = document.createElement("p");
                  priceElement.textContent = `가격: \${image.PRODUCT_PRICE}원`;
                  console.log("생성된 링크:", linkElement.href); // 디버깅
              });
          } catch (error) {
              console.error("API 호출 실패:", error);
          }
      }
      // 예시로 함수 호출 (필요에 따라 호출 시점 조정)
      fetchImages("all");
document.querySelectorAll(".tab").forEach((tab) => {
    tab.addEventListener("click", (e) => {
        const category = e.target.dataset.category;
        // 탭 선택 상태 업데이트
        document.querySelectorAll(".tab").forEach((t) => t.classList.remove("clicked"));
        e.target.classList.add("clicked");
        // 이미지 가져오기
        fetchImages(category);
        // 행사식품(plus)일 경우 배너 표시
        const bannerContainer = document.getElementById("banner-container");
        if (category === "plus") {
            bannerContainer.style.display = "block"; // 배너 표시
        } else {
            bannerContainer.style.display = "none"; // 배너 숨기기
        }
    });
});
document.querySelector('.banner-button[data-subcategory="oneplus"]').addEventListener("click", () => {
    console.log("1+1 상품 버튼 클릭됨");
    fetchImages("plus", "oneplus");
});
document.querySelector('.banner-button[data-subcategory="twoplus"]').addEventListener("click", () => {
    console.log("2+1 상품 버튼 클릭됨");
    fetchImages("plus", "twoplus");
});
document.getElementById("searchButton").addEventListener("click", () => {
    const searchText = document.getElementById("searchText").value.trim();
    console.log("입력된 검색어:", searchText); // 디버깅용
    fetchImages("all","", searchText);
});
window.onload = () => {
    fetchImages("all");
};
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