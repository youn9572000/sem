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

import static com.kh.common.template.JDBCTemplate.*;

import com.kh.admin.model.vo.AdminNoticeCategory;
import com.kh.admin.model.vo.Board;

public class AdminSearchDao {
	private Properties prop = new Properties();

	public AdminSearchDao() {
		String path = AdminNoticeDao.class.getResource("/sql/admin/admin-mapper.xml").getPath();

		try {
			prop.loadFromXML(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	  // 이벤트 게시판 검색
	public List<Board> searchBoards(Connection conn, String boardType, String keyword, int currentPage, int boardLimit) {
		 List<Board> list = new ArrayList<>();
	        PreparedStatement pstmt = null;
	        ResultSet rset = null;
	        String sql = prop.getProperty("searchBaord");

	        int startRow = (currentPage - 1) * boardLimit + 1; // 시작 행
	        int endRow = currentPage * boardLimit;           // 끝 행

	        try {
	            pstmt = conn.prepareStatement(sql);

	            pstmt.setInt(1, getCategoryCode(boardType)); // 게시판 카테고리 매핑
	            pstmt.setString(2, "%" + keyword + "%");
	            pstmt.setString(3, "%" + keyword + "%");
	            pstmt.setString(4, "%" + keyword + "%");
	            pstmt.setInt(5, startRow);
	            pstmt.setInt(6, endRow);

	            System.out.println("Executing query: " + pstmt.toString());

	            rset = pstmt.executeQuery();
	            while (rset.next()) {
	                Board b = Board.builder()
	                               .boardNo(rset.getInt("BOARD_NO"))
	                               .boardTitle(rset.getString("BOARD_TITLE"))
	                               .boardWriter(rset.getString("BOARD_WRITER"))
	                               .boardContent(rset.getString("BOARD_CONTENT"))
	                               .count(rset.getInt("COUNT"))
	                               .createDate(rset.getDate("CREATE_DATE"))
	                               .build();
	                list.add(b);
	            }
	        } catch (SQLException e) {
	            System.err.println("Error executing searchBoards: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            close(rset);
	            close(pstmt);
	        }
	        return list;
	    }

	    // 전체 게시글 수 계산
	    public int getBoardCount(Connection conn, String boardType, String keyword) {
	        int count = 0;
	        PreparedStatement pstmt = null;
	        ResultSet rset = null;
	        String sql = prop.getProperty("searchBoardList");

	        try {
	            pstmt = conn.prepareStatement(sql);

	            pstmt.setInt(1, getCategoryCode(boardType));
	            pstmt.setString(2, "%" + keyword + "%");
	            pstmt.setString(3, "%" + keyword + "%");
	            pstmt.setString(4, "%" + keyword + "%");

	            System.out.println("Executing query: " + pstmt.toString());

	            rset = pstmt.executeQuery();
	            if (rset.next()) {
	                count = rset.getInt(1);
	            }
	        } catch (SQLException e) {
	            System.err.println("Error executing getBoardCount: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            close(rset);
	            close(pstmt);
	        }
	        return count;
	    }

	    // 게시판 카테고리 매핑
	    private int getCategoryCode(String boardType) {
	        switch (boardType) {
	            case "event": return 2;
	            case "free": return 1;
	            case "notice": return 3;
	            default: return 0;
	        }
	    }
	}