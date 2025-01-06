package com.kh.admin.model.service;

import static com.kh.common.template.JDBCTemplate. *;

import java.sql.Connection;
import java.util.List;

import com.kh.admin.model.dao.ProductListDao;
import com.kh.common.model.vo.PageInfo;
import com.kh.product.model.vo.Product;

public class ProductListService {

	private ProductListDao dao = new ProductListDao();
	
	public List<Product> selectProductList(PageInfo pi, String storeNo, String searchProduct) {
		Connection conn = getConnection();
		List<Product> list = null;
		
		if(storeNo == null || storeNo.equals("all") && (searchProduct == null || searchProduct.isEmpty())) {
			list = dao.selectAllProductList(conn, pi);			
			
		}else if(storeNo != null && !storeNo.equals("all") && (searchProduct == null || searchProduct.isEmpty())) {
			list = dao.selectProductList(conn, pi, storeNo);
		}else if ((storeNo == null || storeNo.equals("all")) && searchProduct != null && !searchProduct.isEmpty()){
            // 3. 상품명으로만 검색
            list = dao.searchProductListByName(conn, pi, searchProduct);
		} else if(storeNo != null && !storeNo.equals("all") && searchProduct != null && !searchProduct.isEmpty()) {
            // 4. 특정 편의점에서 상품명으로 검색
            list = dao.searchProductListByStoreAndName(conn, pi, storeNo, searchProduct);
        }
		
		close(conn);
		
		return list;
		
	}

	public int selectProductListCount(String storeNo, String searchProduct) {
		Connection conn = getConnection();
		int listCount = 0;
		
		if(storeNo == null || storeNo.equals("all") && (searchProduct == null || searchProduct.isEmpty())) {
			// 1. 전체상품으로 검색
			listCount = dao.selectAllProductListCount(conn);
			
		}else if(storeNo != null && !storeNo.equals("all") && (searchProduct == null || searchProduct.isEmpty())) {
			//2. 특정 편의점으로만 검색
			listCount = dao.selectProductListCount(conn, storeNo);
			
		}else if ((storeNo == null || storeNo.equals("all")) && searchProduct != null && !searchProduct.isEmpty()) {
            // 3. 상품명으로만 검색
            listCount = dao.searchProductListCountByName(conn, searchProduct);
            
		} else if(storeNo != null && !storeNo.equals("all") && searchProduct != null && !searchProduct.isEmpty()) {
            // 4. 특정 편의점에서 상품명으로 검색
            listCount = dao.searchProductListCountByStoreAndName(conn, storeNo, searchProduct);
        }
		close(conn);
		
		return listCount;
		
	}

	public List<Product> getProductsByStore(String storeName) {
		Connection conn = getConnection();
		
		List<Product> list = dao.getProductsByStore(conn, storeName);
		
		close(conn);
		
		return list;
		
	}

	public boolean deleteProducts(List<Integer> productNos) {
		Connection conn = getConnection();
		
		boolean result = dao.deleteProduct(conn, productNos);
		
		if(result) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	public boolean addProduct(Product product) {
		Connection conn = getConnection();
		
		boolean result = dao.addProduct(conn, product);
		
		if(result) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

}









