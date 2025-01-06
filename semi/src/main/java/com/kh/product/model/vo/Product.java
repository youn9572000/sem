package com.kh.product.model.vo;

import java.util.List;
import com.kh.user.model.vo.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Product {

	private int productNo;
	private int storeNo;
	private String productName;
	private int productPrice;
	private String imageUrl;
	private String CATEGORY_NAME;
	private List<Review> reviews;
	private String productStatus;

}
