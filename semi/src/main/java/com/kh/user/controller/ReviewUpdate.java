package com.kh.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kh.user.model.service.ReviewService;
import com.kh.user.model.vo.Review;

/**
 * Servlet implementation class ReviewUpdate
 */
@WebServlet("/review/update")
public class ReviewUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewUpdate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo")); // reviewNo 파라미터를 정수로 변환

		Review r = new ReviewService().selectOne(reviewNo); // reviewNo로 특정 리뷰 조회

		if (r != null) {
			request.setAttribute("review", r); // 조회된 리뷰 데이터를 request에 저장
			request.getRequestDispatcher("/views/product/product.jsp").forward(request, response);

		} else {
			request.setAttribute("errorMsg", "해당 리뷰를 찾을 수 없습니다.");
			request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");

		try {
			// 파라미터 가져오기
			int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
			String content = request.getParameter("content");
			int score = Integer.parseInt(request.getParameter("reviewScore"));

			// Review 객체 생성 (빌더 패턴 사용)
			Review review = Review.builder().reviewNo(reviewNo).reviewContent(content).reviewScore(score).build();

			// 수정 로직
			int result = new ReviewService().updateReview(review);

			// 결과 반환
			if (result > 0) {
				response.getWriter().write("{\"success\":true, \"message\":\"리뷰가 성공적으로 수정되었습니다.\"}");
				
			} else {
				response.getWriter().write("{\"success\":false, \"message\":\"리뷰 수정에 실패했습니다.\"}");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.getWriter().write("{\"success\":false, \"message\":\"숫자 형식이 잘못되었습니다.\"}");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"success\":false, \"message\":\"서버 오류가 발생했습니다.\"}");
		}
	}

}
