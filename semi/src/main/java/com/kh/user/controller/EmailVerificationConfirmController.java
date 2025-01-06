package com.kh.user.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.user.model.dao.EmailVerificationDao;

/**
 * Servlet implementation class EmailVerificationConfirmController
 */
@WebServlet("/user/verifyEmail")
public class EmailVerificationConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailVerificationConfirmController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		
		EmailVerificationDao dao = new EmailVerificationDao();
		boolean isVerified = dao.verifyToken(token);
		dao.deleteToken(token);
		
		if(isVerified) {
			request.getSession().setAttribute("emailVerified", true);  // 세션에 인증 상태 저장
			response.sendRedirect(request.getContextPath() + "/views/userPage/member/SeconSignup.jsp?verified=true");
		}else {
			response.sendRedirect(request.getContextPath() + "/views/userPage/member/SeconSignup.jsp?verified=false");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}