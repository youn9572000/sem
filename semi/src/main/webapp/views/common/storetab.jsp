<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>CU 상품탭입니다.</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/common.css">
</head>
<body>
	<div id="app">
		<div class="tab-bar">
			<div class="tab" data-category="all">전체</div>
			<div class="tab" data-category="snack">간식</div>
			<div class="tab" data-category="food">식품</div>
			<div class="tab" data-category="drink">음료수</div>
			<div class="tab" data-category="ice">아이스크림</div>
			<div class="tab" data-category="noodle">라면</div>
			<div class="tab" data-category="fast">즉석 식품</div>
			<div class="tab" data-category="plus">행사 상품</div>
		</div>

		<!-- 배너 컨테이너 -->
		<div class="banners" id="banners">
			<div id="banner-containersnack" class="banner-category" data-category="snack">
				<button class="banner-button1" data-subcategory="Snack">과자</button>
				<button class="banner-button1" data-subcategory="Cake">케이크,빵</button>
				<button class="banner-button1" data-subcategory="Choco">초콜릿,푸딩</button>
			</div>
			
			<div id="banner-containerdrink" class="banner-category" data-category="drink">
				<button class="banner-button1" data-subcategory="DrinkDlfqks">음료수</button>
				<button class="banner-button1" data-subcategory="DrinkMilk">유제품</button>
				<button class="banner-button1" data-subcategory="Drinkcoffie">커피</button>
			</div>
			<div id="banner-containernoodle" class="banner-category" data-category="noodle">
				<button class="banner-button1" data-subcategory="Cupnoodle">컵라면</button>
				<button class="banner-button1" data-subcategory="NoodleDlfqks">일반라면</button>
			</div>
			<div id="banner-containerfast" class="banner-category" data-category="fast">
				<button class="banner-button1" data-subcategory="LunchBox">도시락</button>
				<button class="banner-button1" data-subcategory="Gimbap">김밥</button>
				<button class="banner-button1" data-subcategory="Salad">샐러드,기타</button>
				<button class="banner-button1" data-subcategory="Burger">햄버거</button>
				<button class="banner-button1" data-subcategory="Hot">핫도그,샌드위치</button>
			</div>
			<div id="banner-containerplus" class="banner-category" data-category="plus">
				<button class="banner-button" data-subcategory="oneplus">1+1 상품</button>
				<button class="banner-button" data-subcategory="twoplus">2+1 상품</button>
			</div>
		</div>

		<div class="container">
			<div class="row" style="justify-content: center; margin-top: 30px; position: relative">
				<form id="searchForm" style="display: flex; align-items: center; gap: 0; width: 50%; height: 15%">
					<input type="text" class="form-control" placeholder="상품을 입력해주세요" id="searchText"
						style="flex: 1; color: green; font-weight: bold; border-radius: 4px 0 0 4px; border: 1px solid #ccc; height: 38px; margin-bottom: 5px" />
					<button type="button" class="btn btn-success" id="searchButton"
						style="border-radius: 0 4px 4px 0; border: 1px solid #28a745; height: 38px; padding: 0 20px; background-color: #D0C1E3; color: white;">
						상품 검색</button>
				</form>
			</div>
		</div>

		<div class="content" id="image-container"></div>
	</div>
	</body>