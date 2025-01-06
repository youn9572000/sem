package com.kh.user.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Save {
	private int no;
	private int userId;
	private int productNo;
	private String productName;
	private int productPrice;
	private String productImage;
	
}
