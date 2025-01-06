package com.kh.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.admin.model.dto.BlockMemberDTO;
import com.kh.admin.model.service.BlockService;
import com.kh.admin.model.service.DeleteService;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class MemberBlockController
 */
@WebServlet("/admin/MemberBlock")
public class MemberBlockController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberBlockController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 페이징 처리에 필요한 변수
		int listCount = 0; // 게시글 총 개수
		int currentPage; // 요청한 페이지
		int pageLimit; // 페이징바에 표시할 최대 갯수
		int boardLimit; // 한 페이지당 보여질 게시글의 최대 갯수.
		
		int startPage; // 페이징바의 시작 수
		int endPage; // 페이징바의 끝 수
		int maxPage; // 가장 마지막 페이지
		
		String searchId = request.getParameter("searchId");
		String filter = request.getParameter("sort-select");
		
		List<BlockMemberDTO> list = new ArrayList<>();
		BlockService bs= new BlockService();
		DeleteService ds = new DeleteService();
		
		listCount = bs.selectCombinedListCount(searchId, filter);
		
		currentPage = request.getParameter("cpage") == null ? 1 : Integer.parseInt(request.getParameter("cpage"));
		
		pageLimit = 10;
		boardLimit = 10;
		
		maxPage = (int) Math.ceil(listCount / (double)boardLimit);
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		endPage = startPage + pageLimit - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage);
		
		list = bs.selectCombinedList(searchId, filter, pi);
		
				
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		request.setAttribute("filter", filter);
		request.setAttribute("searchId", searchId);

		request.getRequestDispatcher("/views/adminPage/admin/MemberBlock.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
