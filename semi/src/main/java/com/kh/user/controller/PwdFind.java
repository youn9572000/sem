package com.kh.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.user.model.service.EmailService;
import com.kh.user.model.service.MemberService;

@WebServlet("/member/pwdcheck")
public class PwdFind extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PwdFind() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String userId = request.getParameter("userId");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 입력값 검증
        if (email == null || email.trim().isEmpty() || userId == null || userId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"이메일과 아이디를 모두 입력해주세요.\"}");
            return;
        }

        try {
            // 사용자 유효성 검사
            boolean isValidUser = MemberService.validateUser(email, userId);
            if (!isValidUser) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"등록된 이메일 또는 아이디가 없습니다.\"}");
                return;
            }

            // 인증 코드 생성
            String verificationCode = generateVerificationCode();

            // 이메일 발송
            boolean isSent = EmailService.sendVerificationCode(email, verificationCode);
            if (isSent) {
                // 세션에 인증 코드 및 이메일 저장
                request.getSession().setAttribute("verificationCode", verificationCode);
                request.getSession().setAttribute("email", email);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"이메일이 성공적으로 발송되었습니다.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"message\": \"이메일 발송에 실패했습니다.\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"서버 내부 오류가 발생했습니다.\"}");
            e.printStackTrace();
        }
    }

    // 6자리 인증 코드 생성
    private String generateVerificationCode() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
}
