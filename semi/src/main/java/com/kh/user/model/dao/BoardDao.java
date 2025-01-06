package com.kh.user.model.dao;

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
import com.kh.user.model.vo.Attachment;
import com.kh.user.model.vo.Category;

import static com.kh.common.template.JDBCTemplate.*;

public class BoardDao {

	private Properties prop = new Properties();

	public BoardDao() {
		String path = BoardDao.class.getResource("/sql/board/board-mapper.xml").getPath();

		try {
			prop.loadFromXML(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<Board> selectNotice(Connection conn, PageInfo pi, String sort) {

		List<Board> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectNotice");

		switch (sort) {
		case "count":
			sql += " ORDER BY COUNT DESC";
			break;
		case "plus":
			sql += " ORDER BY PLUS DESC";
			break;
		default:
			sql += " ORDER BY BOARD_NO DESC";
		}

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

	public int selectNoticeCount(Connection conn, int boardType) {

		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectNoticeCount");

		try {
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, boardType);
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

	public int selectFreeCount(Connection conn, int boardType) {

		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectFreeCount");

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

	public List<Board> selectFree(Connection conn, PageInfo pi, String sort) {
		List<Board> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectFree");

		switch (sort) {
		case "count":
			sql += " ORDER BY COUNT DESC";
			break;
		case "plus":
			sql += " ORDER BY PLUS DESC";
			break;
		default:
			sql += " ORDER BY BOARD_NO DESC";
		}

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

	public int selectEventCount(Connection conn, int boardType) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectEventCount");

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

	public List<Board> selectEvent(Connection conn, PageInfo pi, String sort) {
		List<Board> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectEvent");

		switch (sort) {
		case "count":
			sql += " ORDER BY COUNT DESC";
			break;
		case "plus":
			sql += " ORDER BY PLUS DESC";
			break;
		default:
			sql += " ORDER BY BOARD_NO DESC";
		}

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

	public BoardDTO selectBoard(Connection conn, int boardNo) {

		BoardDTO board = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectBoard");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				board = BoardDTO.builder()
						.b(Board.builder().boardNo(boardNo).boardTitle(rset.getString("BOARD_TITLE"))
								.boardContent(rset.getString("BOARD_CONTENT")).boardWriter(rset.getString("USER_ID"))
								.createDate(rset.getDate("CREATE_DATE")).build())
						.at(Attachment.builder().fileNo(rset.getInt("FILE_NO"))
								.originName(rset.getString("ORIGINNAME_NAME")).changeName(rset.getString("CHANGE_NAME"))
								.filePath(rset.getString("FILE_PATH")).build())
						.build();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return board;

	}

	public List<Attachment> selectAttachment(Connection conn, int boardNo) {
		List<Attachment> a = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAttachment");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				Attachment at = Attachment.builder().fileNo(rset.getInt("FILE_NO"))
						.originName(rset.getString("ORIGINNAME_NAME")).changeName(rset.getString("CHANGE_NAME"))
						.filePath(rset.getString("FILE_PATH")).build();
				a.add(at);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return a;

	}

	public List<Category> selectCategory(Connection conn) {

		List<Category> list = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectCategory");

		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				Category c = Category.builder().boardCategory(rset.getInt("BOARD_CATEGORY"))
						.categoryName(rset.getString("CATEGORY_NAME")).build();
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
		} finally {
			close(pstmt);
		}

		return updateCount;

	}

	public int insertAttachment(Connection conn, Attachment at) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertAttachment");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());

			updateCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return updateCount;
	}

	public List<String> getBoardTitlesByUser(Connection conn, int boardWriter) {
	    List<String> titles = new ArrayList<>();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    String sql = "SELECT BOARD_TITLE FROM BOARD WHERE BOARD_WRITER = ? AND BOARD_STATUS = 'Y'";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardWriter);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            titles.add(rs.getString("BOARD_TITLE")); // 제목만 리스트에 추가
	            System.out.println("가져온 제목: " + rs.getString("BOARD_TITLE"));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rs);
	        close(pstmt);
	    }

	    return titles;
	}

}
