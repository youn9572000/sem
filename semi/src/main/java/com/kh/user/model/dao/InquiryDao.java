package com.kh.user.model.dao;

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
import com.kh.common.model.vo.PageInfo;
import com.kh.user.model.dto.BoardDTO;
import com.kh.user.model.vo.Category;

public class InquiryDao {
	
	private Properties prop = new Properties();

	public InquiryDao() {
		String path = BoardDao.class.getResource("/sql/board/inquiry-mapper.xml").getPath();

		try {
			prop.loadFromXML(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int selectInquiryCount(Connection conn, int boardType) {
		
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectInquiryCount");

		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				listCount = rset.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	public List<Board> selectInquiry(Connection conn, PageInfo pi, String sort) {
		
		List<Board> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectInquriy");
		

		try {
			pstmt = conn.prepareStatement(sql);

			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;

			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				Board b = Board.builder().boardNo(rset.getInt("BOARD_NO"))
						.category(new AdminNoticeCategory(0, rset.getString("BOARD_CATEGORY")))
						.boardTitle(rset.getString("BOARD_TITLE")).boardWriter(rset.getString("BOARD_WRITER"))
						.puls(rset.getInt("PLUS")).count(rset.getInt("COUNT")).createDate(rset.getDate("CREATE_DATE"))
						.build();
				list.add(b);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public BoardDTO selectInquiryBoard(Connection conn, int bno) {
		
		BoardDTO b = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectInquiryBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				b = BoardDTO.builder()
							.b(Board.builder()
									.boardNo(bno)
									.boardTitle(rset.getString("BOARD_TITLE"))
									.boardContent(rset.getString("BOARD_CONTENT"))
									.boardWriter(rset.getString("USER_ID"))
									.createDate(rset.getDate("CREATE_DATE"))
									.build()
							  )
							.build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return b;
	}

	public List<Category> selectCategory(Connection conn) {
		
		List<Category> list = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectCategory");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Category c = Category.builder()
									 .boardCategory(rset.getInt("BOARD_CATEGORY"))
									 .categoryName(rset.getString("CATEGORY_NAME"))
									 .build();
				list.add(c);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int insertBoard(Connection conn, Board b) {
		
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(b.getBoardWriter()));
			pstmt.setInt(2, b.getCategory().getCategoryNo());
			pstmt.setString(3, b.getBoardTitle());
			pstmt.setString(4, b.getBoardContent());
			
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	public int inquiryDelete(Connection conn, int boardNo) {
		
		int result = 0;
	    PreparedStatement pstmt = null;
	    String sql = prop.getProperty("inquiryDelete");

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardNo);

	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }

	    return result;
	}

}
