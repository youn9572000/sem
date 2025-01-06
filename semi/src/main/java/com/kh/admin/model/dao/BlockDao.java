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

import com.kh.admin.model.dto.BlockMemberDTO;
import com.kh.admin.model.vo.Block;
import com.kh.admin.model.vo.Member;
import com.kh.common.model.vo.PageInfo;

public class BlockDao {
	
	private Properties prop = new Properties();
	
	public BlockDao() {
		String path = BlockDao.class.getResource("/sql/admin/admin-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public int selectListCount(Connection conn) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
		
	}

	public List<BlockMemberDTO> selectAllList(Connection conn, PageInfo pi) {
		List<BlockMemberDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAllList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Block b = Block.builder()
						.reason(rset.getString("BLOCK_REASON"))
						.blockDay(rset.getInt("BLOCK_DAY"))
						.build();
				Member m = Member.builder()
						.userNo(rset.getInt("USER_NO"))
						.userId(rset.getString("USER_ID"))
						.blockDate(rset.getDate("BLOCK_DATE"))
						.build();
				list.add(new BlockMemberDTO(b, m));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;

	}

	public int updateBlock(Connection conn, int blockDays, String userId, String blockReason) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateBlock");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);

                
                pstmt.setString(1, blockReason);
                pstmt.setInt(2, blockDays);
                pstmt.setString(3, userId);
                pstmt.setString(4, userId);
                
                result = pstmt.executeUpdate();
                
    			
            
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            close(pstmt);
        }
		
		return result;
	}
	


	public List<BlockMemberDTO> selectSearchId(String searchId, Connection conn, PageInfo pi) {
		List<BlockMemberDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("blockSearchIdList");
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setString(1, searchId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Block b = Block.builder()
						.reason(rset.getString("BLOCK_REASON"))
						.blockDay(rset.getInt("BLOCK_DAY"))
						.build();
				Member m = Member.builder()
						.userNo(rset.getInt("USER_NO"))
						.userId(rset.getString("USER_ID"))
						.blockDate(rset.getDate("BLOCK_DATE"))
						.build();
				list.add(new BlockMemberDTO(b, m));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}

	public List<BlockMemberDTO> selectBlockList(Connection conn, PageInfo pi) {
		List<BlockMemberDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectBlockList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Block b = Block.builder()
						.reason(rset.getString("BLOCK_REASON"))
						.blockDay(rset.getInt("BLOCK_DAY"))
						.build();
				Member m = Member.builder()
						.userNo(rset.getInt("USER_NO"))
						.userId(rset.getString("USER_ID"))
						.blockDate(rset.getDate("BLOCK_DATE"))
						.build();
				list.add(new BlockMemberDTO(b, m));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public int selectBlockCount(Connection conn) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectBlockCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
		
		
	}

	public List<BlockMemberDTO> selectUnblockList(Connection conn, PageInfo pi) {
		List<BlockMemberDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectUnblockList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Block b = Block.builder()
						.reason(rset.getString("BLOCK_REASON"))
						.blockDay(rset.getInt("BLOCK_DAY"))
						.build();
				Member m = Member.builder()
						.userNo(rset.getInt("USER_NO"))
						.userId(rset.getString("USER_ID"))
						.blockDate(rset.getDate("BLOCK_DATE"))
						.build();
				list.add(new BlockMemberDTO(b, m));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public int selectUnblockCount(Connection conn) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectUnblockCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	}

	public String searchAllList(Connection conn) {
		String result = null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("searchAllList");
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
	        if (rset.next()) {
	            result = rset.getString(1);
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}

	public int releaseUser(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("releaseUser");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
		
	}

	public int selectCombinedListCount(Connection conn, String searchId, String filter) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT COUNT(*) FROM MEMBER WHERE 1=1";
		
        if (searchId != null && !searchId.isEmpty()) {
            query += " AND USER_ID LIKE ?";
        }
        
        if ("blocked".equals(filter)) {
            query += " AND BLOCK_NO IS NOT NULL";
        } else if ("unblocked".equals(filter)) {
            query += " AND BLOCK_NO IS NULL";
        }
		
		try {
			pstmt = conn.prepareStatement(query);
			int paramIndex = 1;
			
			if(searchId != null && !searchId.isEmpty()) {
				pstmt.setString(paramIndex++, "%" + searchId + "%");
			}
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				count = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return count;
	}

	public List<BlockMemberDTO> selectCombinedList(Connection conn, String searchId, String filter, PageInfo pi) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<BlockMemberDTO> list = new ArrayList<>();
		
		String query = "SELECT * FROM (" +
				" SELECT M.USER_ID, M.USER_NO, B.BLOCK_REASON, B.BLOCK_DAY, M.BLOCK_DATE, ROW_NUMBER() OVER (ORDER BY M.USER_NO) AS RNUM " +
                "FROM MEMBER M " +
                "LEFT JOIN BLOCK B ON M.BLOCK_NO = B.BLOCK_NO " +
                "WHERE 1=1";
		
		if(searchId != null && !searchId.isEmpty()) {
			query += " AND M.USER_ID LIKE ?";
		}
		if("blocked".equals(filter)) {
			query += " AND B.BLOCK_NO IS NOT NULL";
		}else if("unblocked".equals(filter)) {
            query += " AND B.BLOCK_NO IS NULL";
        }
		
		query += ") WHERE RNUM BETWEEN ? AND ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			int paramIndex = 1;
			
			if(searchId != null && !searchId.isEmpty()) {
				pstmt.setString(paramIndex++, "%" + searchId + "%");
			}
			pstmt.setInt(paramIndex++, (pi.getCurrentPage() - 1) * pi.getBoardLimit());
			pstmt.setInt(paramIndex, pi.getBoardLimit());
			
			rset = pstmt.executeQuery();

			while(rset.next()) {
				Block b = Block.builder()
						.reason(rset.getString("BLOCK_REASON"))
						.blockDay(rset.getInt("BLOCK_DAY"))
						.build();
				Member m = Member.builder()
						.userNo(rset.getInt("USER_NO"))
						.userId(rset.getString("USER_ID"))
						.blockDate(rset.getDate("BLOCK_DATE"))
						.build();
				list.add(new BlockMemberDTO(b, m));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
		
	}
	
	
	
	

}























