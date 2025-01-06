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
public class Reply {
    private int replyNo;          // 댓글 번호
    private int boardNo;          // 게시글 번호
    private String replyWriter;   // 작성자
    private int cReplyNo;         // 대댓글 번호 (참조 댓글 번호)
    private String replyContent;  // 댓글 내용
    private Date createDate;      // 작성일
    private String replyStatus;   // 상태 (예: 활성/삭제)
    private String replyDec;      // 추가 설명 또는 상태 정보
    private String rboardTitle;
}
