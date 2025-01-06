package com.kh.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.admin.model.service.ProductListService;
import com.kh.product.model.vo.Product;

/**
 * Servlet implementation class GetProductsByStore
 */
@WebServlet("/admin/getProductsByStore")
public class GetProductsByStore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProductsByStore() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String storeName = request.getParameter("storeName");
		
		System.out.println("서블릿 호출됨, storeName: " + storeName);  // 서블릿 호출 확인
	        
		
		ProductListService service = new ProductListService();
		List<Product> products = service.getProductsByStore(storeName);
		
		response.setContentType("application/json; charset=UTF-8");
		
	    // JSON 반환 전 서버 콘솔에 출력
	    System.out.println("서버에서 반환하는 데이터: " + toJson(products));  // <-- 여기에 추가
	    
	    // 상품이 없는 경우 처리
	    if (products == null || products.isEmpty()) {
	        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	        response.getWriter().write("{\"error\": \"상품이 없습니다.\"}");
	        return;
	    }
	
	    response.getWriter().write(toJson(products));
	
	}

	private String toJson(List<Product> products) {
		StringBuilder json = new StringBuilder("[");
		for(int i = 0; i < products.size(); i++) {
			Product p = products.get(i);
			json.append("{")
				.append("\"productNo\":").append(p.getProductNo()).append(",")
				.append("\"productName\":\"").append(p.getProductName()).append("\"")
				.append("}");
			if(i < products.size() - 1) json.append(",");
		}
		json.append("]");
		return json.toString();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
