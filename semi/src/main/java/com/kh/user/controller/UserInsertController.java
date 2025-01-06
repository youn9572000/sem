package com.kh.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.admin.model.vo.Member;
import com.kh.user.model.service.MemberSignupService;

/**
 * Servlet implementation class UserInsert
 */
@WebServlet("/user/userInsert")
public class UserInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/userPage/member/Signup.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean emailVerified = (Boolean) request.getSession().getAttribute("emailVerified");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String email = request.getParameter("email");
		String confirmPwd = request.getParameter("confirmPwd");
		
	    if (emailVerified == null || !emailVerified) {
	        out.println("<script>");
	        out.println("alert('이메일 인증을 완료해야 합니다.');");
	        out.println("location.href='" + request.getContextPath() + "/views/userPage/member/Signup.jsp';");
	        out.println("</script>");
	        return;
	    }
		
		// 정규식 패턴
	    String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{8,15}$";
	    
	    if(!userPwd.matches(passwordPattern)) {
			out.println("<script>");
			out.println("alert('비밀번호 형식이 맞지 않습니다. 다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
	    	return;
	    }
	    
	    if (!userPwd.equals(confirmPwd)) {
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않습니다. 다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
	        return;
	    }
		
		
		
		Member m = Member.builder()
						.userId(userId)
						.userPwd(userPwd)
						.email(email)
						.build();
		int result = new MemberSignupService().insert(m);
		

		
		if(result > 0) {
			out.println("<script>");
			out.println("alert('회원가입에 성공했습니다! 해당 계정으로 로그인 해주세요.');");
			out.println("location.href='" + request.getContextPath() + "';");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('회원가입에 실패했습니다. 다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
		}
		out.close();
		
	}

}














