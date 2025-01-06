package com.kh.product.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.product.model.vo.Product;
import com.kh.user.model.vo.Review;

import static com.kh.common.template.JDBCTemplate.close;

public class productDao {
    private Properties prop = new Properties();

    public productDao() {
        try {
            // product.xml 경로를 가져와 로드합니다.
            String fileName = productDao.class.getResource("/sql/product/product.xml").getPath();
            prop.loadFromXML(new FileInputStream(fileName)); // XML 파일 로드
        } catch (Exception e) {
            System.err.println("XML 파일 로드 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 상품 세부 정보 및 리뷰 목록 조회
    public Product productReview(Connection conn, int productNo) {
        Product product = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            // 상품 세부 정보 조회
            String productSql = prop.getProperty("selectProductDetail");
            pstmt = conn.prepareStatement(productSql);
            pstmt.setInt(1, productNo);

            rset = pstmt.executeQuery();
            if (rset.next()) {
                product = new Product();
                product.setProductNo(rset.getInt("PRODUCT_NO"));          // 상품 번호 (NUMBER → int)
                product.setStoreNo(rset.getInt("STORE_NO"));              // 편의점 번호 (NUMBER → int)
                product.setProductName(rset.getString("PRODUCT_NAME"));   // 상품명 (VARCHAR2 → String)
                product.setProductPrice(rset.getInt("PRODUCT_PRICE"));    // 상품 가격 (NUMBER → int)
                product.setImageUrl(rset.getString("IMAGE_URL"));         // 상품 이미지 URL (VARCHAR2 → String)
                product.setCATEGORY_NAME(rset.getString("CATEGORY_NAME"));// 카테고리명 (VARCHAR2 → String)
                product.setProductStatus(rset.getString("PRODUCT_STATUS"));// 상품 상태 (VARCHAR2 → String)
            }
            close(rset);
            close(pstmt);

            // 리뷰 목록 조회
            String reviewSql = prop.getProperty("selectReviewsByProductNo");
            pstmt = conn.prepareStatement(reviewSql);
            pstmt.setInt(1, productNo);

            rset = pstmt.executeQuery();
            List<Review> reviews = new ArrayList<>();
            while (rset.next()) {
                Review review = Review.builder()
                        .reviewNo(rset.getInt("REVIEW_NO"))              // 리뷰 번호 (NUMBER → int)
                        .reviewWriter(rset.getString("REVIEW_WRITER"))   // 작성자 ID (VARCHAR2 → String)
                        .productNo(rset.getInt("PRODUCT_NO"))            // 상품 번호 (NUMBER → int)
                        .reviewContent(rset.getString("REVIEW_CONTENT")) // 리뷰 내용 (VARCHAR2 → String)
                        .reviewDate(rset.getTimestamp("REVIEW_DATE"))    // 리뷰 작성일 (DATE → Timestamp)
                        .reviewScore(rset.getInt("REVIEW_SCORE"))        // 리뷰 점수 (NUMBER → int)
                        .build();
                reviews.add(review);
            }
            if (product != null) {
                product.setReviews(reviews); // 리뷰 목록을 상품 객체에 설정
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }

        return product;
    }
}
