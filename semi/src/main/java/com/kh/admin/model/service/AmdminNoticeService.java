package com.kh.admin.model.service;

import com.kh.admin.model.dao.AdminNoticeDao;
import com.kh.admin.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

import java.sql.Connection;
import java.util.List;

import static com.kh.common.template.JDBCTemplate.*;

public class AmdminNoticeService {
	private AdminNoticeDao dao = new AdminNoticeDao();

	public List<Board> selectBoardFList(PageInfo pi) {
		Connection conn = getConnection();
		
		List<Board> list = dao.selectFList(conn, pi);
		
		close(conn);
		
		return list;
	}
	public List<Board> selectBoardEList(PageInfo pi) {
			Connection conn = getConnection();
		
		List<Board> list = dao.selectEList(conn, pi);
		
		close(conn);
		
		return list;
	}
	public List<Board> selectBoardMList(PageInfo pi) {
		Connection conn = getConnection();

		List<Board> list = dao.selectMList(conn, pi);

		close(conn);

		return list;
	}
	public int selectFListCount(int boardCategory) {
		Connection conn = getConnection();
		
		int listCount = dao.selectFListCount(conn,boardCategory);
		
		close(conn);
		
		
		return listCount;
	}
	public int selectEListCount(int boardCategory) {
			Connection conn = getConnection();
		
		int listCount = dao.selectEListCount(conn,boardCategory);
		
		close(conn);
		
		
		return listCount;
	}
	public int selectMListCount(int boardCategory) {
	Connection conn = getConnection();
		
		int listCount = dao.selectMListCount(conn,boardCategory);
		
		close(conn);
		return listCount;
	}


}
