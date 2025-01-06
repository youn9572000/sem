package com.kh.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kh.admin.model.service.AdminDeleteService;

/**
 * Servlet implementation class ReportManagerDelete
 */
@WebServlet("/ReportManagerDelete")
public class ReportManagerDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportManagerDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String boardNo = request.getParameter("boardNo");
	        String replyNo = request.getParameter("replyNo");
	        String reviewNo = request.getParameter("reviewNo");

	        // 각 경우에 따라 처리
	        if (boardNo != null) {
	            // 게시글 삭제 로직 추가
	            System.out.println("Deleting board: " + boardNo);
	          int result =   new AdminDeleteService().deleteBoard(Integer.parseInt(boardNo));
	        } else if (replyNo != null) {
	            // 댓글 삭제 로직 추가
	            System.out.println("Deleting reply: " + replyNo);
	          int result =   new AdminDeleteService().deleteReply(Integer.parseInt(replyNo));
	        } else if (reviewNo != null) {
	             //리뷰 삭제 로직 추가
	            System.out.println("Deleting review: " + reviewNo);
	             new AdminDeleteService().deleteReview(Integer.parseInt(reviewNo));
	        }
	        
	        if (boardNo != null) {
	            int result = new AdminDeleteService().deleteBoard(Integer.parseInt(boardNo));
	            if (result > 0) {
	                System.out.println("게시글 삭제 성공: " + boardNo);
	            } else {
	                System.out.println("게시글 삭제 실패: " + boardNo);
	            }
	        }

	        // 삭제 후 페이지 리다이렉트
	        response.sendRedirect(request.getContextPath() + "/admin/report");
	    }

}
