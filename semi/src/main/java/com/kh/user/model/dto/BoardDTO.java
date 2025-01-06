package com.kh.user.model.dto;

import com.kh.admin.model.vo.Board;
import com.kh.user.model.vo.Attachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {
	private Board b;
	private Attachment at;

}
