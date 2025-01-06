package com.kh.user.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.user.model.service.MemberService;
import com.kh.user.model.vo.Member;

@WebServlet("/member/update")
public class UpdateMember extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateMember() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/userPage/member/mypage.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set request encoding for UTF-8
        request.setCharacterEncoding("UTF-8");

        // Get the session and loginUser object
        HttpSession session = request.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        // If not logged in, redirect to login page
        if (loginUser == null) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>");
            response.getWriter().println("alert('로그인이 필요합니다.');");
            response.getWriter().println("location.href='" + request.getContextPath() + "/login.jsp';");
            response.getWriter().println("</script>");
            return;
        }

        // Get the email and password parameters from the form
        String email = request.getParameter("email");
        String currentPwd = request.getParameter("password");
        String newPwd = request.getParameter("new_password");
        String confirmPwd = request.getParameter("confirm_password");

        // Check if new password meets length requirement
        if (newPwd != null && newPwd.length() < 6) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>");
            response.getWriter().println("alert('비밀번호는 6자리 이상이어야 합니다.');");
            response.getWriter().println("location.href='" + request.getContextPath() + "/member/update';");
            response.getWriter().println("</script>");
            return;
        }

        // If passwords match, proceed
        if (!newPwd.isEmpty() && newPwd.equals(confirmPwd)) {
            if (loginUser.getUserPwd().equals(currentPwd)) {
                // Create Member object with updated info (email)
                Member updateMember = Member.builder()
                        .userNo(loginUser.getUserNo())
                        .email(email)
                        .build();

                int result = new MemberService().updateMemberAndPwd(updateMember, newPwd, currentPwd);

                // Handle result
                if (result > 0) {
                    response.setContentType("text/html; charset=UTF-8");
                    response.getWriter().println("<script>");
                    response.getWriter().println("alert('회원 정보 수정 성공.');");
                    response.getWriter().println("location.href='" + request.getContextPath() + "';");
                    response.getWriter().println("</script>");
                } else {
                    response.setContentType("text/html; charset=UTF-8");
                    response.getWriter().println("<script>");
                    response.getWriter().println("alert('회원 정보 수정 실패. 다시 시도해주세요.');");
                    response.getWriter().println("location.href='" + request.getContextPath() + "/views/common/errorPage.jsp';");
                    response.getWriter().println("</script>");
                }
            } else {
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().println("<script>");
                response.getWriter().println("alert('현재 비밀번호가 일치하지 않습니다.');");
                response.getWriter().println("location.href='" + request.getContextPath() + "/member/update';");
                response.getWriter().println("</script>");
            }
        } else {
            // Only update member info (email)
            Member updateMember = Member.builder()
                    .userNo(loginUser.getUserNo())
                    .email(email)
                    .build();
            
            int result = new MemberService().updateMember(updateMember);
            
            // Handle result
            if (result > 0) {
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().println("<script>");
                response.getWriter().println("alert('회원 정보 수정 성공. 다시 로그인 해주세요.');");
                response.getWriter().println("location.href='" + request.getContextPath() + "';");
                response.getWriter().println("</script>");
            } else {
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().println("<script>");
                response.getWriter().println("alert('회원 정보 수정 실패. 다시 시도해주세요.');");
                response.getWriter().println("location.href='" + request.getContextPath() + "/views/common/errorPage.jsp';");
                response.getWriter().println("</script>");
            }
        }
    }
}
