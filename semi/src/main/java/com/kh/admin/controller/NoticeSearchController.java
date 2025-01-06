package com.kh.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.admin.model.service.AdminSearchService;
import com.kh.admin.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class NoticeSearchController
 */
@WebServlet("/admin/search")
public class NoticeSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoticeSearchController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String boardType = request.getParameter("type");
		if (boardType == null || boardType.trim().isEmpty()) {
			boardType = "notice"; // 기본값 설정
		}

		String keyword = request.getParameter("keyword");
		if (keyword == null) {
			keyword = ""; // 검색어가 없으면 빈 문자열로 처리
		}

		int currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
		int boardLimit = 10;

		AdminSearchService searchService = new AdminSearchService();
		List<Board> list = searchService.searchBoards(boardType, keyword, currentPage, boardLimit);
		int listCount = searchService.getBoardCount(boardType, keyword);

		int pageLimit = 5;
		int maxPage = (int) Math.ceil((double) listCount / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage);

		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		request.setAttribute("keyword", keyword);

		String viewPath = switch (boardType) {
		case "event" -> "/views/adminPage/notice/ManageEventBoard.jsp";
		case "free" -> "/views/adminPage/notice/ManageFreeBoard.jsp";
		case "notice" -> "/views/adminPage/notice/ManageNotice.jsp";
		default -> "/views/errorPage.jsp";
		};
		request.getRequestDispatcher(viewPath).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
