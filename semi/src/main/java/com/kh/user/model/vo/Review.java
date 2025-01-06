package com.kh.user.model.vo;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Builder

public class Review {
    private int reviewNo;
    private String reviewWriter;
    private int productNo;
    private String reviewContent;
    private Timestamp reviewDate;
    private int reviewScore;
    private String reviewDec = "N"; // 기본값 설정
	}
    // Getters and Setters
