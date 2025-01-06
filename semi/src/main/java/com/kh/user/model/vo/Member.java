package com.kh.user.model.vo;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class Member {
	private int userNo;
	private String userId;
	private String userPwd;
	private String email;
	private Date enrollDate;
	private String memberStatus;
	private String blockNo;
}


