package com.kh.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/verifyCode")
public class VerifyCode extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public VerifyCode() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String inputCode = request.getParameter("code");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 세션에서 저장된 이메일과 인증번호 가져오기
        String savedCode = (String) request.getSession().getAttribute("verificationCode");
        String savedEmail = (String) request.getSession().getAttribute("email");

        // 요청 데이터 및 세션 데이터 검증
        if (email == null || inputCode == null || savedEmail == null || !email.equals(savedEmail)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"잘못된 요청입니다.\"}");
            return;
        }

        // 인증번호 확인
        if (savedCode != null && savedCode.equals(inputCode)) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"인증이 완료되었습니다.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"message\": \"인증번호가 일치하지 않습니다.\"}");
        }
    }
}
