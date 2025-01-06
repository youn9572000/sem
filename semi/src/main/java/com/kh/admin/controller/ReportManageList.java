package com.kh.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.admin.model.service.AmdminReportService;
import com.kh.admin.model.vo.Board;
import com.kh.admin.model.vo.Reply;
import com.kh.admin.model.vo.Review;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class ReportManageList
 */
@WebServlet("/admin/report")
public class ReportManageList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportManageList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardListCount; // 게시글 총 개수
		int replyListCount; // 댓글 총 개수
		int reviewListCount; // 리뷰 총 개수

		int boardCurrentPage; // 게시글 요청 페이지
		int replyCurrentPage; // 댓글 요청 페이지
		int reviewCurrentPage; // 리뷰 요청 페이지

		int pageLimit = 3; // 페이징바에 표시할 최대 갯수
		int boardLimit = 3; // 한 페이지당 보여질 게시글, 댓글, 리뷰 최대 갯수

		// 게시글 페이징 처리
		boardListCount = new AmdminReportService().ReportBoardCount(1); // 게시글 총 개수
		boardCurrentPage = request.getParameter("boardPage") == null ? 1 : Integer.parseInt(request.getParameter("boardPage"));
		int boardMaxPage = (int) Math.ceil(boardListCount / (double) boardLimit);
		int boardStartPage = (boardCurrentPage - 1) / pageLimit * pageLimit + 1;
		int boardEndPage = boardStartPage + pageLimit - 1;
		if (boardEndPage > boardMaxPage) boardEndPage = boardMaxPage;

		PageInfo boardPi = new PageInfo(boardListCount, boardCurrentPage, pageLimit, boardLimit, boardStartPage, boardEndPage, boardMaxPage);

		// 댓글 페이징 처리
		replyListCount = new AmdminReportService().ReportReplyCount(1); // 댓글 총 개수
		replyCurrentPage = request.getParameter("replyPage") == null ? 1 : Integer.parseInt(request.getParameter("replyPage"));
		int replyMaxPage = (int) Math.ceil(replyListCount / (double) boardLimit);
		int replyStartPage = (replyCurrentPage - 1) / pageLimit * pageLimit + 1;
		int replyEndPage = replyStartPage + pageLimit - 1;
		if (replyEndPage > replyMaxPage) replyEndPage = replyMaxPage;

		PageInfo replyPi = new PageInfo(replyListCount, replyCurrentPage, pageLimit, boardLimit, replyStartPage, replyEndPage, replyMaxPage);

		// 리뷰 페이징 처리
		reviewListCount = new AmdminReportService().ReportReivewCount(1); // 리뷰 총 개수
		reviewCurrentPage = request.getParameter("reviewPage") == null ? 1 : Integer.parseInt(request.getParameter("reviewPage"));
		int reviewMaxPage = (int) Math.ceil(reviewListCount / (double) boardLimit);
		int reviewStartPage = (reviewCurrentPage - 1) / pageLimit * pageLimit + 1;
		int reviewEndPage = reviewStartPage + pageLimit - 1;
		if (reviewEndPage > reviewMaxPage) reviewEndPage = reviewMaxPage;

		PageInfo reviewPi = new PageInfo(reviewListCount, reviewCurrentPage, pageLimit, boardLimit, reviewStartPage, reviewEndPage, reviewMaxPage);

		// 목록 조회
		List<Board> boardList = new AmdminReportService().selectBoardList(boardPi);
		List<Reply> replyList = new AmdminReportService().selectReplyList(replyPi);
		List<Review> reviewList = new AmdminReportService().selectReviewList(reviewPi);

		// JSP로 데이터 전달
		request.setAttribute("boardList", boardList);
		request.setAttribute("replyList", replyList);
		request.setAttribute("reviewList", reviewList);

		request.setAttribute("boardPi", boardPi);
		request.setAttribute("replyPi", replyPi);
		request.setAttribute("reviewPi", reviewPi);

		// View 포워딩
		request.getRequestDispatcher("/views/adminPage/report/ReprotManagement.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
