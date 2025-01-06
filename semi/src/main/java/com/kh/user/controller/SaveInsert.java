package com.kh.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.user.model.service.SaveListService;
import com.kh.user.model.vo.Member;
import com.kh.user.model.vo.Save;

@WebServlet("/kart/insert")
public class SaveInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SaveInsert() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html; charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
	        
	    HttpSession session = request.getSession();
	    Member loginUser = (Member) session.getAttribute("loginUser");

	    // 로그인 확인
	    if (loginUser == null) {
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().println("<script>");
	        response.getWriter().println("alert('로그인이 필요합니다.');");
	        response.getWriter().println("location.href='" + request.getContextPath() + "/views/userPage/member/login.jsp';");
	        response.getWriter().println("</script>");
	        return;
	    }

	    // 상품 번호 및 이름 가져오기
	    String productNoStr = request.getParameter("productNo");
	    String productName = request.getParameter("productName");

	    if (productNoStr == null || productNoStr.trim().isEmpty()) {
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().println("<script>");
	        response.getWriter().println("alert('상품 번호가 유효하지 않습니다.');");
	        response.getWriter().println("location.href='" + request.getContextPath() + "/product/list';");
	        response.getWriter().println("</script>");
	        return;
	    }

	    int productNo;
	    try {
	        productNo = Integer.parseInt(productNoStr);
	    } catch (NumberFormatException e) {
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().println("<script>");
	        response.getWriter().println("alert('상품 번호가 잘못되었습니다.');");
	        response.getWriter().println("location.href='" + request.getContextPath() + "/product/list';");
	        response.getWriter().println("</script>");
	        return;
	    }

	    if (productName == null || productName.trim().isEmpty()) {
	        productName = "이름 없음"; // 기본값 설정
	    }

	    // Save 객체 생성 및 데이터베이스 처리
	    Save save = Save.builder().userId(loginUser.getUserNo()).productNo(productNo).productName(productName).build();

	    // 중복 체크
	    SaveListService saveService = new SaveListService();
	    boolean isDuplicate = saveService.isDuplicateSave(save);

	    if (isDuplicate) {
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().println("<script>");
	        response.getWriter().println("alert('이미 상품이 찜 목록에 존재합니다.');");
	        response.getWriter().println("location.href='" + request.getContextPath() + "/product/detail?pno=" + productNo + "';");
	        response.getWriter().println("</script>");
	        return;
	    }

	    // 중복이 없으면 데이터베이스에 추가
	    int result = saveService.insertSaveList(save);

	    if (result > 0) {
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().println("<script>");
	        response.getWriter().println("alert('상품이 찜 목록에 추가되었습니다.');");
	        response.getWriter().println("location.href='" + request.getContextPath() + "/kart/savelist';");
	        response.getWriter().println("</script>");
	    } else {
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().println("<script>");
	        response.getWriter().println("alert('찜 등록에 실패했습니다. 다시 시도해주세요.');");
	        response.getWriter().println("location.href='" + request.getContextPath() + "/product/detail?pno=" + productNo + "';");
	        response.getWriter().println("</script>");
	    }
	}

}
