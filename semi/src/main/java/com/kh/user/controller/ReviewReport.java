package com.kh.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.user.model.service.ReviewService;
import com.kh.user.model.vo.Member;

@WebServlet("/review/report") // URL 매핑
public class ReviewReport extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        String reviewNoStr = request.getParameter("reviewNo");
        if (reviewNoStr == null || reviewNoStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"Review number is missing.\"}");
            return;
        }

        try {
            int reviewNo = Integer.parseInt(reviewNoStr);

            // 리뷰 상태 조회
            char status = new ReviewService().getReviewStatus(reviewNo);

            // 상태를 JSON 형식으로 반환
            response.getWriter().write("{\"success\": true, \"status\": \"" + status + "\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"Invalid review number format.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Server error occurred.\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        // 로그인 확인
        if (loginUser == null) {
            session.setAttribute("alertMsg", "로그인이 필요합니다.");
            response.sendRedirect(request.getContextPath() + "/views/userPage/member/login.jsp");
            return;
        }

        String reviewNoStr = request.getParameter("reviewNo");
        String newStatus = request.getParameter("newStatus");

        // 입력 값 검증
        if (reviewNoStr == null || reviewNoStr.isEmpty() || newStatus == null || newStatus.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("fail: invalid input");
            return;
        }

        int reviewNo = Integer.parseInt(reviewNoStr);
        ReviewService reviewService = new ReviewService(); // ReviewService 인스턴스 생성
        char status = newStatus.charAt(0); // 'Y' 또는 'N'

        // 상태 업데이트
        boolean isUpdated = reviewService.updateReviewStatus(reviewNo, status);

        if (isUpdated) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("fail: update failed");
        }
    }
}
