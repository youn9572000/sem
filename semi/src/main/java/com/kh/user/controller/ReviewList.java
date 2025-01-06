package com.kh.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.user.model.service.ReviewService;
import com.kh.user.model.vo.Review;

@WebServlet("/review/list")
public class ReviewList extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReviewList() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 상품 번호 가져오기
            String productNoStr = request.getParameter("productNo");
            if (productNoStr == null || productNoStr.trim().isEmpty()) {
                throw new IllegalArgumentException("상품 번호가 유효하지 않습니다.");
            }

            int productNo = Integer.parseInt(productNoStr);

            // 리뷰 목록 조회
            List<Review> reviewList = new ReviewService().selectReviewList(productNo);
            request.setAttribute("reviewList", reviewList);

            // 포워드 처리
            request.getRequestDispatcher("/views/product/product.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "상품 번호가 유효하지 않습니다.");
            request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "리뷰 목록을 불러오는 중 오류가 발생했습니다.");
            request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
        }
    }
}
