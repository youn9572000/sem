package com.kh.admin.model.service;

import com.kh.admin.model.dao.AdminReportDao;
import java.sql.Connection;
import java.util.List;

import com.kh.admin.model.vo.Board;
import com.kh.admin.model.vo.Reply;
import com.kh.admin.model.vo.Review;
import com.kh.common.model.vo.PageInfo;
import static com.kh.common.template.JDBCTemplate.*;

public class AmdminReportService {
	private AdminReportDao dao = new AdminReportDao();
	public List<Board> selectBoardList(PageInfo pi) {
		Connection conn = getConnection();
		
		List<Board> list = dao.selectBaordList(conn, pi);
		
		close(conn);
		return list;
	}

	public List<Reply> selectReplyList(PageInfo pi) {
		Connection conn = getConnection();
		
		List<Reply> replyList = dao.selectReplyList(conn, pi);
		
		close(conn);
		return replyList;
	}

	public List<Review> selectReviewList(PageInfo pi) {
		Connection conn = getConnection();
		
		List<Review> reviewList = dao.selectReviewList(conn,pi);
		
		close(conn);
		return reviewList;
	}

	public int ReportReplyCount(int boardCategory) {
		Connection conn = getConnection();
		
		int listCount = dao.ReportReplyCount(conn,boardCategory);
		
		close(conn);
		return listCount;
	}

	public int ReportBoardCount(int boardCategory) {
		Connection conn = getConnection();
		
		int listCount = dao.ReportBoardCount(conn,boardCategory);
		
		close(conn);
		return listCount;
	}

	public int ReportReivewCount(int boardCategory) {
		Connection conn = getConnection();
		
		int listCount = dao. ReportReivewCount(conn,boardCategory);
		
		close(conn);
		return listCount;
	}

}
