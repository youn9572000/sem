package com.kh.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.user.model.service.SaveListService;
import com.kh.user.model.vo.Member;
import com.kh.user.model.vo.Save;

@WebServlet("/kart/savelist")
public class SaveListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SaveListServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 응답 인코딩 설정
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        // 로그인 확인
        if (loginUser == null) {
            session.setAttribute("alertMsg", "로그인이 필요합니다.");
            response.sendRedirect(request.getContextPath() + "/kart/savelist.jsp");
            return;
        }

        int userId = loginUser.getUserNo(); // 로그인한 사용자 ID 가져오기
        SaveListService service = new SaveListService();
        List<Save> saveList = service.selectSaveListList(userId); // userId 전달

        if (saveList == null || saveList.isEmpty()) {
            request.setAttribute("message", "찜 목록이 비어 있습니다.");
        } else {
            request.setAttribute("saveList", saveList);
        }

        request.getRequestDispatcher("/kart/savelist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // POST 요청은 GET 요청과 동일하게 처리
        doGet(request, response);
    }
    
}
