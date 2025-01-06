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
public class Member {
	private int userNo;
	private String userId;
	private String userPwd;
	private String email;
	private Date enrollDate;
	private String memberStatus;
	private String blockNo;
	private Date blockDate;

}
