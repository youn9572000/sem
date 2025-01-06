package com.kh.user.model.dto;

public class ReplyDTO {
	
	private int boardNo;
    private int writer;
    private String content;
    private int parentReplyNo;

    public ReplyDTO(int boardNo, int writer, String content, int parentReplyNo) {
        this.boardNo = boardNo;
        this.writer = writer;
        this.content = content;
        this.parentReplyNo = parentReplyNo;
    }

    public int getBoardNo() { return boardNo; }
    public int getWriter() { return writer; }
    public String getContent() { return content; }
    public int getParentReplyNo() { return parentReplyNo; }

}
