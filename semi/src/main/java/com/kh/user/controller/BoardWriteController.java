package com.kh.user.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.admin.model.vo.AdminNoticeCategory;
import com.kh.admin.model.vo.Board;
import com.kh.admin.model.vo.Member;
import com.kh.common.rename.FileRenamePolicy;
import com.kh.user.model.service.BoardService;
import com.kh.user.model.vo.Attachment;
import com.kh.user.model.vo.Category;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/board/write")
public class BoardWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardWriteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> list = new BoardService().selectCategory();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/views/userPage/board/boardWrite.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if(ServletFileUpload.isMultipartContent(request)) {
			
			long maxSize = 1024*1024*10;
			String filePath = request.getServletContext().getRealPath("/upload/free/");
			MultipartRequest multiRequest = new MultipartRequest(request, filePath,  maxSize, "UTF-8", new FileRenamePolicy());
			
			int categoryNo = Integer.parseInt(multiRequest.getParameter("category"));
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			Member loginUser = (Member) session.getAttribute("loginUser");
			String boardWriter = String.valueOf(loginUser.getUserNo());
			
			int userNo = loginUser.getUserNo();
			session.setAttribute("loginUserNo", userNo);
			
			Board b = Board.builder()
					   .boardTitle(boardTitle)
					   .boardContent(boardContent)
					   .boardWriter(boardWriter)
					   .category(AdminNoticeCategory.builder().categoryNo(categoryNo).build())
					   .build();
			
			Attachment at = null;
			if(multiRequest.getOriginalFileName("upfile") != null) {
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("upfile")); //파일의 원본명
				at.setChangeName(multiRequest.getFilesystemName("upfile")); // 수정된 파일명
				at.setFilePath("/upload/free/");
			}
			
			int result = new BoardService().insertBoard(b,at);
			
			if(result >0) {
				//서비스 성공시 목록페이지로 이동
				session.setAttribute("alertMsg", "게시글 등록 성공!");
				response.sendRedirect(request.getContextPath()+"/board/free");
			}else {
				//실패시 errorPage로 포워딩
				//테이블에 등록 실패시 서버상에 업로드된 파일을 보관할 이유가 없으므로 삭제처리
			if(at != null) {
					new File(filePath+at.getChangeName()).delete();
			}}
		}
		
	}

}
