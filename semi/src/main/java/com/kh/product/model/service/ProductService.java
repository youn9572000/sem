package com.kh.product.model.service;

import static com.kh.common.template.JDBCTemplate.close;
import static com.kh.common.template.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import com.kh.product.model.dao.productDao;
import com.kh.product.model.vo.Product;
import com.kh.user.model.dao.ReviewDao;
import com.kh.user.model.vo.Review;

public class ProductService {

	private productDao dao = new productDao(); // Product 媛앹껜 ?앹꽦

	public Product getProductById(int productNo) {
		Connection conn = getConnection();

		Product product = dao.productReview(conn, productNo);

		close(conn);

		return product;

	}
	
}
