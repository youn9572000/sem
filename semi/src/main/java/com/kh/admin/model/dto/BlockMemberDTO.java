package com.kh.admin.model.dto;

import com.kh.admin.model.vo.Block;
import com.kh.admin.model.vo.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockMemberDTO {
	private Block block;
	private Member member;

}
