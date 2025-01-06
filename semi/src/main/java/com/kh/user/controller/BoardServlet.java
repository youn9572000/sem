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

import com.kh.user.model.vo.Member;
import com.kh.user.model.service.BoardService;

/**
 * Servlet implementation class BoardServlet
 */
import com.google.gson.Gson;
import java.io.PrintWriter;

@WebServlet("/member/myBoardTitles")
public class BoardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BoardService boardService = new BoardService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int boardWriter = loginUser.getUserNo();
        List<String> titles = boardService.getBoardTitlesByUser(boardWriter);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        for (String title : titles) {
            out.println("<li>" + title + "</li>");
        }
        out.close();
    }
}

