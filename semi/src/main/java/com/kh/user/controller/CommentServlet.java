package com.kh.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kh.user.model.service.ReplyService;
import com.kh.user.model.vo.Member;
import com.kh.user.model.vo.Reply;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/member/myComments")
public class CommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReplyService replyService = new ReplyService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int userNo = loginUser.getUserNo();
        List<String> replies = replyService.getCommentsByUser(userNo);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        for (String reply : replies) {
            out.println("<li>" + reply + "</li>");
        }
        out.close();
    }
}
