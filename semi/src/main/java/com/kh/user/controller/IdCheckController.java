package com.kh.user.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.user.model.service.MemberSignupService;

/**
 * Servlet implementation class IdCheckController
 */
@WebServlet("/user/idCheck")
public class IdCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IdCheckController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 클라이언트가 전달한 값 추출
		String userId = request.getParameter("userId");
		
		// 2. 서비스 요청(중복회원확인)
		int count = new MemberSignupService().idCheck(userId);
		
		// 3. 결과값 반환
		if(count > 0) {// 이미 회원이 존재하는 경우 ==> "NNNNN"
			response.getWriter().print("NNNNN");
		}else { // 존재하는 아이디가 없는 경우 ==> "YYYYY"
			response.getWriter().print("YYYYY");
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
