package com.kh.user.model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.kh.admin.model.vo.Board;
import com.kh.common.model.vo.PageInfo;
import com.kh.user.model.dao.BoardDao;
import com.kh.user.model.dto.BoardDTO;
import com.kh.user.model.vo.Attachment;
import com.kh.user.model.vo.Category;

import static com.kh.common.template.JDBCTemplate.*;

public class BoardService {

	private BoardDao dao = new BoardDao();

	public int selectNoticeCount(int boardType) {

		Connection conn = getConnection();

		int listCount = dao.selectNoticeCount(conn, boardType);

		close(conn);

		return listCount;

	}

	public List<Board> selectNotice(PageInfo pi, String sort) {

		Connection conn = getConnection();

		List<Board> list = dao.selectNotice(conn, pi, sort);

		close(conn);

		return list;

	}
	
	public int selectFreeCount(int boardType) {
		
		Connection conn = getConnection();
		
		int listCount = dao.selectFreeCount(conn, boardType);
		
		close(conn);
		
		return listCount;
	}
	
	public List<Board> selectFree(PageInfo pi, String sort) {
		
		Connection conn = getConnection();
		
		List<Board> list = dao.selectFree(conn, pi, sort);
		
		close(conn);
		
		return list;
	}
	
	public int selectEventCount(int boardType) {
		
		Connection conn = getConnection();
		
		int listCount = dao.selectEventCount(conn, boardType);
		
		close(conn);
		
		return listCount;
	}
	
	public List<Board> selectEvent(PageInfo pi, String sort) {
		
		Connection conn = getConnection();
		
		List<Board> list = dao.selectEvent(conn, pi, sort);
		
		close(conn);
		
		return list;
	}

	public BoardDTO selectBoard(int boardNo) {
		Connection conn = getConnection();

		BoardDTO board = dao.selectBoard(conn, boardNo);

		close(conn);

		return board;
	}

	public List<Attachment> selectAttachment(int boardNo) {

		Connection conn = getConnection();

		List<Attachment> a = dao.selectAttachment(conn, boardNo);

		close(conn);

		return a;

	}

	public List<Category> selectCategory() {
		
		Connection conn = getConnection();
		
		List<Category> list = dao.selectCategory(conn);
		
		close(conn);

		return list;
		
	}

	public int insertBoard(Board b, Attachment at) {
		
		Connection conn = getConnection();
		
		int result = dao.insertBoard(conn, b);
		
		if (result>0 && at != null) {
			result = dao.insertAttachment(conn, at);
		}
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
		
	}

	public List<String> getBoardTitlesByUser(int boardWriter) {
	    Connection conn = getConnection();
	    List<String> titles = dao.getBoardTitlesByUser(conn, boardWriter);
	    close(conn);
	    return titles;
	}
}
