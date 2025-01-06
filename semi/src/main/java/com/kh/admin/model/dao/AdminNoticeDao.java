package com.kh.admin.model.dao;


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
import com.kh.common.model.vo.PageInfo;
import static com.kh.common.template.JDBCTemplate.*;


public class AdminNoticeDao {
	private Properties prop = new Properties();

	public AdminNoticeDao() {
		String path = AdminNoticeDao.class.getResource("/sql/admin/admin-mapper.xml").getPath();

		try {
			prop.loadFromXML(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Board> selectFList(Connection conn, PageInfo pi) {
		List<Board>list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectFList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() +1;
			int endRow = startRow + pi.getBoardLimit()-1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
			    Board b = Board.builder()
			            .boardNo(rset.getInt("BOARD_NO"))
			            .category(new AdminNoticeCategory(0, rset.getString("BOARD_CATEGORY")))
			            .boardTitle(rset.getString("BOARD_TITLE"))
			            .boardWriter(rset.getString("BOARD_WRITER"))
			            .count(rset.getInt("COUNT"))
			            .createDate(rset.getDate("CREATE_DATE"))
			            .build();
			    list.add(b);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	public List<Board> selectEList(Connection conn, PageInfo pi) {
		List<Board>list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectEList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() +1;
			int endRow = startRow + pi.getBoardLimit()-1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
			    Board b = Board.builder()
			            .boardNo(rset.getInt("BOARD_NO"))
			            .category(new AdminNoticeCategory(0, rset.getString("BOARD_CATEGORY")))
			            .boardTitle(rset.getString("BOARD_TITLE"))
			            .boardWriter(rset.getString("BOARD_WRITER"))
			            .count(rset.getInt("COUNT"))
			            .createDate(rset.getDate("CREATE_DATE"))
			            .build();
			    list.add(b);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
		
		
	}
	public List<Board> selectMList(Connection conn, PageInfo pi) {
		List<Board>list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectMList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() +1;
			int endRow = startRow + pi.getBoardLimit()-1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
			    Board b = Board.builder()
			            .boardNo(rset.getInt("BOARD_NO"))
			            .category(new AdminNoticeCategory(0, rset.getString("BOARD_CATEGORY")))
			            .boardTitle(rset.getString("BOARD_TITLE"))
			            .boardWriter(rset.getString("BOARD_WRITER"))
			            .count(rset.getInt("COUNT"))
			            .createDate(rset.getDate("CREATE_DATE"))
			            .build();
			    list.add(b);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	
	
	
	public int selectFListCount(Connection conn, int boardCategory) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectBaordFListCount");

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

	public int selectEListCount(Connection conn, int boardCategory) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectBaordEListCount");

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

	public int selectMListCount(Connection conn, int boardCategory) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectBaordMListCount");

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
