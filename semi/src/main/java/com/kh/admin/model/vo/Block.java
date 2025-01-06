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
public class Block {
	private String blockNo;
	private String reason;
	private int blockDay;
	private Date blockDate;
}
