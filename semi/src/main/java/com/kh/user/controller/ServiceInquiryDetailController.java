package com.kh.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.user.model.dto.BoardDTO;
import com.kh.user.model.service.BoardService;
import com.kh.user.model.service.InquiryService;
import com.kh.user.model.vo.Attachment;

/**
 * Servlet implementation class ServiceInquiryDetailController
 */
@WebServlet("/service/detail")
public class ServiceInquiryDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceInquiryDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String referer = request.getHeader("Referer");
		request.setAttribute("referer", referer);
		
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		InquiryService service = new InquiryService();
		BoardDTO board = service.selectInquiryBoard(boardNo);
		
		request.setAttribute("board", board);
		
		request.getRequestDispatcher("/views/userPage/board/InquiryDetail.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
