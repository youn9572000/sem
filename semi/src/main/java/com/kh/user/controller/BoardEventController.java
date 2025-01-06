package com.kh.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.admin.model.vo.Board;
import com.kh.common.model.vo.PageInfo;
import com.kh.user.model.service.BoardService;

/**
 * Servlet implementation class BoardEventController
 */
@WebServlet("/board/event")
public class BoardEventController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardEventController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int listCount; // 게시글 총 개수
		int currentPage; // 요청한 페이지
		int pageLimit; // 페이빙바에 표시할 최대 갯수
		int boardLimit; // 한 페이지당 보여질 게시글의 개수
		
		int startPage; // 페이징바의 시작수
		int endPage; // 페이징바의 끝수
		int maxPage; // 가장 마지막 페이지
		
		listCount = new BoardService().selectEventCount(2);
		
		currentPage = request.getParameter("cpage") == null ? 1 : Integer.parseInt(request.getParameter("cpage"));
		String sort = request.getParameter("sort") == null ? "date" : request.getParameter("sort");
		
		pageLimit = 10;
		boardLimit = 10;
		
		maxPage = (int) Math.ceil(listCount / (double) boardLimit);
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		endPage = startPage + pageLimit - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage);
		
		List<Board> list = new BoardService().selectEvent(pi, sort);
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		
		request.getRequestDispatcher("/views/userPage/board/boardEvent.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
