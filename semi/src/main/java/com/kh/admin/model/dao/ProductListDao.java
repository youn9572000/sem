package com.kh.admin.model.dao;

import static com.kh.common.template.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.kh.common.model.vo.PageInfo;
import com.kh.product.model.vo.Product;
import com.kh.user.model.dao.MemberDao;

public class ProductListDao {

		private Properties prop = new Properties();
		
		public ProductListDao() {
			String path = MemberDao.class.getResource("/sql/admin/admin-mapper.xml").getPath();
			
			try {
				prop.loadFromXML(new FileInputStream(path));
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}

	public List<Product> selectAllProductList(Connection conn, PageInfo pi) {
		List<Product> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAllProductList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Product p = Product.builder()
						.productNo(rset.getInt("PRODUCT_NO"))
						.storeNo(rset.getInt("STORE_NO"))
						.productPrice(rset.getInt("PRODUCT_PRICE"))
						.productName(rset.getString("PRODUCT_NAME"))
						.imageUrl(rset.getString("IMAGE_URL"))
						.build();
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
		
	}

	public int selectAllProductListCount(Connection conn) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAllProductListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	}

	public List<Product> getProductsByStore(Connection conn, String storeName) {
		List<Product> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("getProductsByStore");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, storeName);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Product p = Product.builder()
						.productNo(rset.getInt("PRODUCT_NO"))
						.productName(rset.getString("PRODUCT_NAME"))
						.build();
				list.add(p);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
		
	}

	public boolean deleteProduct(Connection conn, List<Integer> productNos) {
		PreparedStatement pstmt = null;
		boolean result = false;
		String sql = prop.getProperty("deleteProduct");
		
		try {
			
	        String inClause = productNos.stream()
                    .map(p -> "?")
                    .collect(Collectors.joining(", "));
	        
	        sql = sql.replace("( ? )", "(" + inClause + ")");  // ?를 동적으로 여러 개로 변경
			
			pstmt = conn.prepareStatement(sql);
			
			for(int i = 0; i < productNos.size(); i++) {
				pstmt.setInt(i + 1, productNos.get(i));				
			}
			
			int rows = pstmt.executeUpdate();
			
			if(rows > 0) {
				result = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			
		}
		
		return result;
		
	}

	public boolean addProduct(Connection conn, Product product) {
		PreparedStatement pstmt = null;
		boolean result = false;
		String sql = prop.getProperty("addProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, product.getProductNo());
			
			int rows = pstmt.executeUpdate();
			
			if(rows > 0) {
				result = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
		
	}

	public int selectProductListCount(Connection conn, String storeNo) {
		PreparedStatement pstmt = null;
		int listCount = 0;
		ResultSet rset = null;
		String sql = prop.getProperty("selectProductListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, storeNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	
	}

	public List<Product> selectProductList(Connection conn, PageInfo pi, String storeNo) {
		PreparedStatement pstmt = null;
		List<Product> list = new ArrayList<>();
		ResultSet rset = null;
		String sql = prop.getProperty("selectProductList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setString(1, storeNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();

			while(rset.next()) {
				Product p = Product.builder()
						.productNo(rset.getInt("PRODUCT_NO"))
						.storeNo(rset.getInt("STORE_NO"))
						.productPrice(rset.getInt("PRODUCT_PRICE"))
						.productName(rset.getString("PRODUCT_NAME"))
						.imageUrl(rset.getString("IMAGE_URL"))
						.build();
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int searchProductListCountByName(Connection conn, String searchProduct) {
		PreparedStatement pstmt = null;
		int listCount = 0;
		ResultSet rset = null;
		String sql = prop.getProperty("searchProductListCountByName");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, searchProduct);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	
	}

	public int searchProductListCountByStoreAndName(Connection conn, String storeNo, String searchProduct) {
		PreparedStatement pstmt = null;
		int listCount = 0;
		ResultSet rset = null;
		String sql = prop.getProperty("searchProductListCountByStoreAndName");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, searchProduct);
			pstmt.setString(2, storeNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	}

	public List<Product> searchProductListByName(Connection conn, PageInfo pi, String searchProduct) {
		PreparedStatement pstmt = null;
		List<Product> list = new ArrayList<>();
		ResultSet rset = null;
		String sql = prop.getProperty("searchProductListByName");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setString(1, searchProduct);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();

			while(rset.next()) {
				Product p = Product.builder()
						.productNo(rset.getInt("PRODUCT_NO"))
						.storeNo(rset.getInt("STORE_NO"))
						.productPrice(rset.getInt("PRODUCT_PRICE"))
						.productName(rset.getString("PRODUCT_NAME"))
						.imageUrl(rset.getString("IMAGE_URL"))
						.build();
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public List<Product> searchProductListByStoreAndName(Connection conn, PageInfo pi, String storeNo,
			String searchProduct) {
		PreparedStatement pstmt = null;
		List<Product> list = new ArrayList<>();
		ResultSet rset = null;
		String sql = prop.getProperty("searchProductListByStoreAndName");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setString(1, searchProduct);
			pstmt.setString(2, storeNo);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			
			rset = pstmt.executeQuery();

			while(rset.next()) {
				Product p = Product.builder()
						.productNo(rset.getInt("PRODUCT_NO"))
						.storeNo(rset.getInt("STORE_NO"))
						.productPrice(rset.getInt("PRODUCT_PRICE"))
						.productName(rset.getString("PRODUCT_NAME"))
						.imageUrl(rset.getString("IMAGE_URL"))
						.build();
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	


}






















