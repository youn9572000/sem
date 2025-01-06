<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이페이지</title>

</head>
<body>


	<body>
	<header>
		<div class="header-container">
			<h1 class="logo">
                <a href="/semi/index.jsp">
                    <img src="<%=request.getContextPath()%>/resources/img/logo.png"
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
					<li><a href="#" class="active">회원정보</a></li>
					<li><a href="#">등록한 게시글</a></li>
					<li><a href="#">등록한 댓글</a></li>
					<li><a href="#">찜 목록</a></li>
				</ul>
			</nav>

			<div class="mypage-content">
				<%@ include file="/views/common/myReplyContent.jsp"%>
			</div>
		</section>
	</main><script>
const commentContainer = document.querySelector('.comment-container');
const selectAllCheckbox = document.getElementById('select-all');
const deleteSelectedBtn = document.getElementById('delete-selected');

// 서버로부터 댓글 불러오기
async function fetchComments() {
    try {
        const response = await fetch('/api/comments');
        const comments = await response.json();
        renderComments(comments);
    } catch (error) {
        console.error('댓글 불러오기 실패:', error);
    }
}

// 댓글 렌더링
function renderComments(comments) {
    commentContainer.innerHTML = '';
    comments.forEach(comment => {
        commentContainer.innerHTML += `
            <div class="comment-item">
                <input type="checkbox" class="comment-select" data-id="${comment.id}">
                <div class="comment-details">
                    <span>${comment.title}</span>
                    <small>${comment.date}</small>
                    <p>${comment.content}</p>
                </div>
            </div>
        `;
    });
}

// 전체 선택/해제
selectAllCheckbox.addEventListener('change', (e) => {
    document.querySelectorAll('.comment-select').forEach(checkbox => {
        checkbox.checked = e.target.checked;
    });
});

// 선택 삭제
deleteSelectedBtn.addEventListener('click', async () => {
    const selectedCheckboxes = document.querySelectorAll('.comment-select:checked');
    if (selectedCheckboxes.length === 0) {
        alert('삭제할 댓글을 선택해주세요.');
        return;
    }

    if (confirm('선택한 댓글을 삭제하시겠습니까?')) {
        try {
            for (const checkbox of selectedCheckboxes) {
                const commentId = checkbox.dataset.id;
                await fetch(`/api/comments/${commentId}`, {
                    method: 'DELETE',
                });
            }
            fetchComments();
        } catch (error) {
            console.error('댓글 삭제 실패:', error);
        }
    }
});

// 초기 데이터 로딩
fetchComments();


</script>

</body>


</html>

