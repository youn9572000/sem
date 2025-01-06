package com.kh.user.model.service;


import java.sql.Connection;
import java.util.List;
import static com.kh.common.template.JDBCTemplate.*;
import com.kh.user.model.dao.ReplyDao;
import com.kh.user.model.vo.Reply;

public class ReplyService {
	
	  private static ReplyDao dao = new ReplyDao();

	   public List<String> getCommentsByUser(int userNo) {
	        Connection conn = getConnection();
	        List<String> replies = dao.selectRepliesByUser(conn, userNo);
	        close(conn);
	        return replies;
	    }
}
