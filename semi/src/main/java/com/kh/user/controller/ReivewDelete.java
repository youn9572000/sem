package com.kh.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.user.model.service.ReviewService;
import com.kh.user.model.vo.Member;

/**
 * Servlet implementation class ReivewDelete
 */
@WebServlet("/review/delete")
public class ReivewDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReivewDelete() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member loginUser = (Member) session.getAttribute("loginUser");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userPwd = request.getParameter("userPwd");
		HttpSession session = request.getSession();
		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) {
			session.setAttribute("alertMsg", "로그인 후 이용해주세요.");
			response.sendRedirect(request.getContextPath() + "/member/login");
			return;
		}
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		int productNo = Integer.parseInt(request.getParameter("productNo"));

		int result = new ReviewService().deleteReview(reviewNo);

		if (result > 0) {
			// 삭제 성공 시 페이지 리다이렉트
			response.sendRedirect(request.getContextPath() + "/product/detail?pno=" + productNo);
		} else {
			// 삭제 실패 시 에러 페이지로 이동
			request.setAttribute("errorMsg", "리뷰 삭제에 실패했습니다.");
			request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
		}
	}

}
