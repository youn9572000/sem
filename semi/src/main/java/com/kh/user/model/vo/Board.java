
package com.kh.user.model.vo;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data // Lombok: Getter, Setter, toString, equals, hashCode 자동 생성
@NoArgsConstructor // Lombok: 기본 생성자 자동 생성
@AllArgsConstructor // Lombok: 모든 필드를 포함하는 생성자 자동 생성
@Getter@Setter@Builder
public class Board {

    private int boardNo;          // 게시판 번호
    private int boardWriter;      // 게시글 작성자
    private int boardCategory;    // 게시판 타입 (카테고리)
    private String boardTitle;    // 게시판 제목
    private String boardContent;  // 게시판 내용
    private int plus;             // 추천수
    private int count;            // 조회수
    private Date createDate;      // 게시글 작성일
    private String boardStatus;   // 상태값 ('Y': 활성, 'N': 비활성)
    private String boardDec;      // 게시글 신고 여부 ('Y': 신고, 'N': 신고 없음)
}