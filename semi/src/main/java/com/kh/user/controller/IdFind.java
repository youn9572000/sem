package com.kh.user.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.user.model.service.MemberService;

/**
 * Servlet implementation class IdCheck
 */
@WebServlet("/member/idfind")
public class IdFind extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public IdFind() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 클라이언트에서 이메일 파라미터 받기
        String email = request.getParameter("email");

        // 응답 데이터 형식 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // 이메일 유효성 검사
            if (email == null || email.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"이메일을 입력해주세요.\"}");
                return;
            }

            // 서비스 호출
            String userId = new MemberService().findUserIdByEmail(email);

            if (userId == null) {
                // 이메일에 해당하는 ID가 없는 경우
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"등록된 이메일이 없습니다.\"}");
            } else {
                // 아이디가 성공적으로 조회된 경우
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"userId\": \"" + userId + "\"}");
            }
        } catch (Exception e) {
            // 서버 오류 처리
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"오류가 발생했습니다. 관리자에게 문의하세요.\"}");
            e.printStackTrace();
        }
    }
}
