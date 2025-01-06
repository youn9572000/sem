package com.kh.user.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Category {
	
	private int boardCategory;
	private String categoryName;

}
