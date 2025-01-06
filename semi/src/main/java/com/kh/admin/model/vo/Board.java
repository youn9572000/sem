package com.kh.admin.model.vo;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
	private int boardNo;
	private String boardWriter;
	private AdminNoticeCategory category;
	private String boardTitle;
	private String boardContent;
	private int puls;
	private int count;
	private Date createDate;
	private String boardSatus;
	private String boardDec;
	
}
