package com.kh.user.model.service;

import java.sql.Connection;
import java.util.List;

import com.kh.admin.model.vo.Board;
import com.kh.common.model.vo.PageInfo;
import com.kh.user.model.dao.InquiryDao;
import com.kh.user.model.dto.BoardDTO;
import com.kh.user.model.vo.Attachment;
import com.kh.user.model.vo.Category;

import static com.kh.common.template.JDBCTemplate.*;

public class InquiryService {
	
	private InquiryDao dao = new InquiryDao();

	public int selectInquiryCount(int boardType) {
		Connection conn = getConnection();

		int listCount = dao.selectInquiryCount(conn, boardType);

		close(conn);

		return listCount;
	}

	public List<Board> selectInquiry(PageInfo pi, String sort) {
		
		Connection conn = getConnection();

		List<Board> list = dao.selectInquiry(conn, pi, sort);

		close(conn);

		return list;
	}

	public BoardDTO selectInquiryBoard(int bno) {
		
		Connection conn = getConnection();
		
		BoardDTO b = dao.selectInquiryBoard(conn, bno);
		
		close(conn);
		
		return b;
	}

	public List<Category> selectCategory() {
		
		Connection conn = getConnection();
		
		List<Category> list = dao.selectCategory(conn);
		
		close(conn);

		return list;
	}

	public int insertBoard(Board b) {
		
		Connection conn = getConnection();
		
		int result = dao.insertBoard(conn, b);
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	public int inquriyDelete(int boardNo) {
		
		Connection conn = getConnection();
	    int result = dao.inquiryDelete(conn, boardNo);

	    if (result > 0) {
	        commit(conn);
	    } else {
	        rollback(conn);
	    }

	    close(conn);
	    return result;
	}

	
	
	

}
