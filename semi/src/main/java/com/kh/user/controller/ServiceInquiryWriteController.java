package com.kh.user.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.kh.admin.model.vo.AdminNoticeCategory;
import com.kh.admin.model.vo.Board;
import com.kh.user.model.vo.Member;
import com.kh.user.model.service.InquiryService;
import com.kh.user.model.vo.Category;

/**
 * Servlet implementation class ServiceInquiryWriteController
 */
@WebServlet("/service/write")
public class ServiceInquiryWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceInquiryWriteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> list = new InquiryService().selectCategory();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/views/userPage/board/InquiryWrite.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Member loginUser = (Member) session.getAttribute("loginUser");
		String boardWriter = String.valueOf(loginUser.getUserNo());
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		Board b = Board.builder()
				   .boardTitle(title)
				   .boardContent(content)
				   .boardWriter(boardWriter)
				   .category(AdminNoticeCategory.builder().categoryNo(4).build())
				   .build();
		
		int result = new InquiryService().insertBoard(b);
		
		response.getWriter().print(result);
		
		if(result >0) {
			//서비스 성공시 목록페이지로 이동
			session.setAttribute("alertMsg", "게시글 등록 성공!");
			response.sendRedirect(request.getContextPath()+"/service/inquiry");
		}
	}

}
