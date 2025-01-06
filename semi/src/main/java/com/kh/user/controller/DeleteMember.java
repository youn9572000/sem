package com.kh.user.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.user.model.service.MemberService;
import com.kh.user.model.vo.Member;

/**
 * Servlet implementation class DeleteMember
 */
@WebServlet("/member/delete")
public class DeleteMember extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeleteMember() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 요청 인코딩 설정
        request.setCharacterEncoding("UTF-8");

        // 2. 세션에서 로그인 사용자 정보 가져오기
        HttpSession session = request.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        // 3. 로그인 확인
        if (loginUser == null) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>");
            response.getWriter().println("alert('로그인이 필요합니다.');");
            response.getWriter().println("location.href='" + request.getContextPath() + "/login.jsp';");
            response.getWriter().println("</script>");
            return;
        }

        // 4. 클라이언트로부터 전달받은 비밀번호 가져오기
        String inputPwd = request.getParameter("userPwd");

        // 5. 비밀번호 검증
        if (inputPwd == null || inputPwd.isEmpty()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>");
            response.getWriter().println("alert('비밀번호를 입력해주세요.');");
            response.getWriter().println("location.href='" + request.getContextPath() + "/mypage.jsp';");
            response.getWriter().println("</script>");
            return;
        }

        // 6. 데이터베이스에서 비밀번호 검증
        MemberService memberService = new MemberService();
        boolean isValidPassword = memberService.validatePassword(inputPwd, loginUser.getUserPwd());

        if (!isValidPassword) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>");
            response.getWriter().println("alert('비밀번호가 일치하지 않습니다. 다시 시도해주세요.');");
            response.getWriter().println("location.href='" + request.getContextPath() + "/mypage.jsp';");
            response.getWriter().println("</script>");
            return;
        }

        // 7. 회원탈퇴 처리
        int result = memberService.delete(inputPwd, loginUser.getUserNo());

        if (result > 0) {
            session.invalidate(); // 세션 무효화
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>");
            response.getWriter().println("alert('회원탈퇴가 완료되었습니다. 이용해 주셔서 감사합니다.');");
            response.getWriter().println("location.href='" + request.getContextPath() + "/index.jsp';");
            response.getWriter().println("</script>");
        } else {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>");
            response.getWriter().println("alert('회원탈퇴 중 문제가 발생했습니다. 다시 시도해주세요.');");
            response.getWriter().println("location.href='" + request.getContextPath() + "/mypage.jsp';");
            response.getWriter().println("</script>");
        }
    }
}