package com.kh.admin.model.service;
import static com.kh.common.template.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.kh.admin.model.dao.AdminDeleteDao;
import com.kh.admin.model.vo.Member;

public class AdminDeleteService {
	
	private AdminDeleteDao dao = new AdminDeleteDao();
	
	public int deleteBoard(int boardNo) {
		Connection conn = getConnection();
	
		int result = dao.BoardDeleteDao(conn, boardNo);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	public int deleteReply(int replyNo) {
		Connection conn = getConnection();
		
		int result = dao.ReplyDeleteDao(conn, replyNo);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
		
	}

	public int deleteReview(int reviewNo) {
		Connection conn = getConnection();
		int result = dao.ReviewDeleteDao(conn, reviewNo);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	public int deleteEBoard(int eboardNo) {
		Connection conn = getConnection();
		int result = dao.EBoardDeleteDao(conn, eboardNo);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
		
	}

	public int deleteFBoard(int fboardNo) {
		Connection conn = getConnection();
		int result = dao.FBoardDeleteDao(conn, fboardNo);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
		
	}

	public int deleteMBoard(int mboardNo) {
		Connection conn = getConnection();
		int result = dao.MBoardDeleteDao(conn, mboardNo);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
		
	}

	public int getMemberCount() {
		Connection conn = getConnection();
		int count = dao.getMemberCount(conn);
	    close(conn);
		
		return count;
	}

	public List<Member> getMemberList(int startRow, int endRow) {
		 Connection conn = getConnection();
		    List<Member> list = dao.selectMemberList(conn, startRow, endRow);
		    close(conn);
			
			return list;
	}

}
