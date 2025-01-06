package com.kh.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.user.model.service.MemberService;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/member/changePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ChangePassword() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String) request.getSession().getAttribute("email");
		String newPassword = request.getParameter("newPassword");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		if(email == null || newPassword == null || newPassword.trim().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("{\"message\": \"요청이 잘못되었습니다.\"}");
            return;			
		}
		try {
			boolean isUpdated = MemberService.updatePassword(email, newPassword);
			if(isUpdated){
				 response.setStatus(HttpServletResponse.SC_OK);
	                response.getWriter().write("{\"message\": \"비밀번호가 성공적으로 변경되었습니다.\"}");
	
			}
		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			 response.getWriter().write("{\"message\": \"오류가 발생했습니다.\"}");
	            e.printStackTrace();
		}
	}

}
