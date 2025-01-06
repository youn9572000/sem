package com.kh.user.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static com.kh.common.template.JDBCTemplate.*;
import com.kh.user.model.vo.Reply;

public class ReplyDao {

	public List<String> selectRepliesByUser(Connection conn, int userNo) {
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    List<String> replies = new ArrayList<>();

	    String sql = "SELECT REPLY_CONTENT " +
	                 "FROM REPLY WHERE REPLY_WRITER = ? ORDER BY CREATE_DATE DESC";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, userNo);
	        rset = pstmt.executeQuery();

	        while (rset.next()) {
	            replies.add(rset.getString("REPLY_CONTENT")); // 댓글 내용만 리스트에 추가
	            System.out.println("가져온 댓글 내용: " + rset.getString("REPLY_CONTENT"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rset);
	        close(pstmt);
	    }

	    return replies;
	}
}
