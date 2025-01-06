package com.kh.admin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.admin.model.service.ProductListService;
import com.kh.product.model.vo.Product;

/**
 * Servlet implementation class DeleteProducts
 */
@WebServlet("/admin/deleteProducts")
public class DeleteProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProducts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line;
		
		while((line = reader.readLine()) != null) {
			sb.append(line);
		}
		
		String json = sb.toString();
		Gson gson = new Gson();
		Product[] product = gson.fromJson(json, Product[].class);
		
		List<Integer> productNos = new ArrayList<>();
		for(Product p : product) {
			productNos.add(p.getProductNo());
		}
        
        ProductListService service = new ProductListService();
        boolean isDeleted = service.deleteProducts(productNos);
        
        response.setContentType("application/json; charset=UTF-8");
        if(isDeleted) {
        	response.getWriter().write("{\"success\":true}");
        }else {
        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	response.getWriter().write("{\"success\": false, \"message\": \"상품 삭제 실패\"}");
        }
		
	}

}

































