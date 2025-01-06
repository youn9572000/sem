package com.kh.user.model.vo;

import java.util.Date;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter

@NoArgsConstructor


	public class Reply {

	    // Fields
	    private int replyNo;        // 댓글 번호
	    private int boardNo;        // 게시판 번호
	    private int replyWriter;    // 댓글 작성자
	    private int cReplyNo;       // 하위 댓글 번호
	    private String replyContent; // 댓글 내용
	    private Date createDate;    // 댓글 작성일
	    private String replyStatus; // 상태값 ('Y'/'N')
	    private String replyDec;    // 댓글 신고 ('Y'/'N')

}
