package com.kh.admin.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.admin.model.service.AdminDeleteService;

/**
 * Servlet implementation class NoticeDeleteController
 */
@WebServlet("/noticeDelete")
public class NoticeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private AdminDeleteService deleteService = new AdminDeleteService(); // AdminDeleteService 초기화
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDeleteController() {
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
		String[] eboardNos = request.getParameterValues("eboardNo");
        String[] fboardNos = request.getParameterValues("fboardNo");
        String[] mboardNos = request.getParameterValues("mboardNo");

        if (eboardNos != null) {
            for (String eboardNo : eboardNos) {
                System.out.println("Deleting Event Board: " + eboardNo);
                deleteService.deleteEBoard(Integer.parseInt(eboardNo));
            }
            response.sendRedirect(request.getContextPath() + "/admin/elist"); // 이벤트 게시판 목록으로 이동
            return;
        }

        // fboardNos 처리 (자유 게시판 삭제)
        if (fboardNos != null) {
            for (String fboardNo : fboardNos) {
                System.out.println("Deleting Free Board: " + fboardNo);
                deleteService.deleteFBoard(Integer.parseInt(fboardNo));
            }
            response.sendRedirect(request.getContextPath() + "/admin/flist"); // 자유 게시판 목록으로 이동
            return;
        }

        // mboardNos 처리 (공지사항 게시판 삭제)
        if (mboardNos != null) {
            for (String mboardNo : mboardNos) {
                System.out.println("Deleting Notice Board: " + mboardNo);
                deleteService.deleteMBoard(Integer.parseInt(mboardNo));
            }
            response.sendRedirect(request.getContextPath() + "/admin/mlist"); // 공지사항 목록으로 이동
            return;
        }

        // 삭제 대상이 없을 경우 기본 페이지로 이동
        response.sendRedirect(request.getContextPath() + "/admin/defaultList");
	}
}