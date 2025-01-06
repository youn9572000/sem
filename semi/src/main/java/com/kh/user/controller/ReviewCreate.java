package com.kh.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.user.model.service.ReviewService;
import com.kh.user.model.vo.Review;
import com.kh.user.model.vo.Member;

@WebServlet("/review/create")
public class ReviewCreate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReviewCreate() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            session.setAttribute("alertMsg", "로그인 후 이용해주세요.");
            response.sendRedirect(request.getContextPath() + "/member/login");
            return;
        }

        try {
            int productNo = Integer.parseInt(request.getParameter("productNo"));
            String reviewContent = request.getParameter("reviewContent");
            int reviewScore = Integer.parseInt(request.getParameter("reviewScore"));

            // `reviewWriter`를 숫자로 변환
            int reviewWriter = loginUser.getUserNo(); // 로그인 사용자 ID가 숫자라면 사용 가능

            // 유효성 검증
            if (reviewScore < 1 || reviewScore > 5) {
                session.setAttribute("alertMsg", "별점은 1에서 5 사이여야 합니다.");
                response.sendRedirect(request.getContextPath() + "/product/detail?pno=" + productNo);
                return;
            }

            Review review = Review.builder()
                    .reviewWriter(String.valueOf(reviewWriter)) // 숫자를 문자열로 변환
                    .productNo(productNo)
                    .reviewContent(reviewContent)
                    .reviewScore(reviewScore)
                    .reviewDec("N")
                    .build();

            ReviewService reviewService = new ReviewService();
            int result = reviewService.insertReview(review);

            if (result > 0) {
                session.setAttribute("alertMsg", "리뷰가 성공적으로 등록되었습니다.");
            } else {
                session.setAttribute("alertMsg", "리뷰 등록에 실패했습니다.");
            }

            response.sendRedirect(request.getContextPath() + "/product/detail?pno=" + productNo);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            session.setAttribute("alertMsg", "입력값이 잘못되었습니다.");
            response.sendRedirect(request.getContextPath() + "/product/list");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("alertMsg", "리뷰 작성 중 오류가 발생했습니다.");
            response.sendRedirect(request.getContextPath() + "/product/list");
        }
    }
}
