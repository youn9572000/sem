package com.kh.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.admin.model.service.ProductListService;
import com.kh.common.model.vo.PageInfo;
import com.kh.product.model.vo.Product;

/**
 * Servlet implementation class ProductListController
 */
@WebServlet("/admin/productManage")
public class ProductListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이징 처리에 필요한 변수
		int listCount = 0; // 게시글 총 개수
		int currentPage;// 요청한 페이지
		int pageLimit; // 페이징바에 표시할 최대 갯수
		int boardLimit; // 한 페이지당 보여질 게시글의 최대 갯수.
				
		int startPage; // 페이징바의 시작 수
		int endPage; // 페이징바의 끝 수
		int maxPage; // 가장 마지막 페이지
		
		List<Product> list = new ArrayList<>();
		ProductListService pl = new ProductListService();
		String productName = request.getParameter("productName");
		String storeNo = request.getParameter("storeNo");
		
		System.out.println("storeNo 값 : " + storeNo);
		System.out.println("productName 값 : " + productName);
		
		currentPage = request.getParameter("cpage") == null ? 1 : Integer.parseInt(request.getParameter("cpage"));
		
		listCount = pl.selectProductListCount(storeNo, productName);

		pageLimit = 10;
		boardLimit = 10;
		
		maxPage = (int) Math.ceil(listCount / (double)boardLimit);
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		endPage = startPage + pageLimit - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage);
		
		list = pl.selectProductList(pi, storeNo, productName);
		
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		request.setAttribute("productName", productName);
		request.setAttribute("storeNo", storeNo);
		
		request.getRequestDispatcher("/views/adminPage/admin/productManagement.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}