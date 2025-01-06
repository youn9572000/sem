package com.kh.admin.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    private int reviewNo;         // 리뷰 번호
    private String reviewWriter;  // 리뷰 작성자
    private int productNo;        // 상품 번호
    private String productName;
    private String reviewContent; // 리뷰 내용
    private Date reviewDate;      // 리뷰 작성일
    private int reviewScore;      // 리뷰 점수
}
