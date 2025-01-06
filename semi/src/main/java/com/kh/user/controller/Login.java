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

@WebServlet("/member/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Login() {
        super();
    }

    // GET : 로그인 페이지로 이동
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // views/userPage/member/login.jsp 로 포워딩
        request.getRequestDispatcher("/views/userPage/member/login.jsp").forward(request, response);
    }

    // POST : 로그인 처리


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String userId = request.getParameter("userId");
        String userPwd = request.getParameter("password");

        MemberService service = new MemberService();
        Member m = service.login(userId, userPwd);

        response.setContentType("text/html; charset=UTF-8");

        if (m == null) {
            // 아이디 또는 비밀번호가 틀린 경우
            response.getWriter().println("<script>");
            response.getWriter().println("alert('아이디 또는 비밀번호가 틀렸습니다.');");
            response.getWriter().println("location.href='" + request.getContextPath() + "/views/userPage/member/login.jsp';");
            response.getWriter().println("</script>");
        } else if ("N".equals(m.getMemberStatus())) {
            // 차단된 계정인 경우
            response.getWriter().println("<script>");
            response.getWriter().println("alert('차단된 계정입니다. 관리자에게 문의하세요.');");
            response.getWriter().println("location.href='" + request.getContextPath() + "/views/userPage/member/login.jsp';");
            response.getWriter().println("</script>");
        } else if ("Y".equals(m.getMemberStatus())) {
            // 로그인 성공
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", m);

            response.getWriter().println("<script>");
            response.getWriter().println("alert('로그인 성공!');");
            response.getWriter().println("location.href='" + request.getContextPath() + "/index.jsp';");
            response.getWriter().println("</script>");
        } else {
            // 기타 상태
            response.getWriter().println("<script>");
            response.getWriter().println("alert('알 수 없는 오류가 발생했습니다. 다시 시도해주세요.');");
            response.getWriter().println("location.href='" + request.getContextPath() + "/views/userPage/member/login.jsp';");
            response.getWriter().println("</script>");
        }
       
        int userNo = m.getUserNo(); // Member 객체에서 userNo를 가져옴
        if (userNo >= 1 && userNo <= 5) {
            // 특정 번호 범위에 해당하는 사용자를 이동
            response.sendRedirect(request.getContextPath() + "/views/adminPage/admin/MainPage.jsp");
        } else {
            // 일반 사용자는 메인 페이지로 이동
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
        
        
    }
}
