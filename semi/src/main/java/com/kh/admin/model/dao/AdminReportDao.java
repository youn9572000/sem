package com.kh.admin.model.dao;

import static com.kh.common.template.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.admin.model.vo.AdminNoticeCategory;
import com.kh.admin.model.vo.Board;
import com.kh.admin.model.vo.Reply;
import com.kh.admin.model.vo.Review;
import com.kh.common.model.vo.PageInfo;

public class AdminReportDao {
	private Properties prop = new Properties();

	public AdminReportDao() {
		String path = AdminNoticeDao.class.getResource("/sql/admin/admin-mapper.xml").getPath();

		try {
			prop.loadFromXML(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public List<Board> selectBaordList(Connection conn, PageInfo pi) {
		List<Board>boardlist = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectBaordList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() +1;
			int endRow = startRow + pi.getBoardLimit()-1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
			//번호 제목 작성자 작성일 조회수 
				Board b = Board.builder()
							   .boardNo(rset.getInt("BOARD_NO"))
							   .boardWriter(rset.getString("BOARD_WRITER"))
							   .category(new AdminNoticeCategory(0, rset.getString("BOARD_CATEGORY")))
							   .boardTitle(rset.getString("BOARD_TITLE"))
							   .createDate(rset.getDate("CREATE_DATE"))
							   .count(rset.getInt("COUNT"))
							   .build();
				boardlist.add(b);
			
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return boardlist;
		
	}

	public List<Reply> selectReplyList(Connection conn, PageInfo pi) {
		List<Reply>replylist = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectReplyList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() +1;
			int endRow = startRow + pi.getBoardLimit()-1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
			// 제목 날짜 내용
				Reply r = Reply.builder()
						       .replyNo(rset.getInt("REPLY_NO"))
						       .boardNo(rset.getInt("BOARD_NO"))
						       .replyWriter(rset.getString("REPLY_WRITER"))
						       .rboardTitle(rset.getString("BOARD_TITLE"))
						       .replyContent(rset.getString("REPLY_CONTENT"))
						       .createDate(rset.getDate("CREATE_DATE"))
						       .build();
				replylist.add(r);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return replylist;
	}

	public List<Review> selectReviewList(Connection conn, PageInfo pi) {
		List<Review>reviewlist = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectReviewList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() +1;
			int endRow = startRow + pi.getBoardLimit()-1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Review rv = Review.builder()
						          .reviewNo(rset.getInt("REVIEW_NO"))
						          .reviewWriter(rset.getString("REVIEW_WRITER"))
						          .productName(rset.getString("PRODUCT_NAME"))
						          .reviewContent(rset.getString("REVIEW_CONTENT"))
						          .reviewDate(rset.getDate("REVIEW_DATE"))
						          .build();
				reviewlist.add(rv);
				
				
						
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return reviewlist;
	}


	public int ReportReplyCount(Connection conn, int boardCategory) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("ReportReplyCount");

		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	public int ReportBoardCount(Connection conn, int boardCategory) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("ReportBoardCount");

		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	public int ReportReivewCount(Connection conn, int boardCategory) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("ReportReivewCount");

		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

}
