package com.kh.user.model.service;

import static com.kh.common.template.JDBCTemplate.*;

import java.sql.Connection;

import com.kh.admin.model.vo.Member;
import com.kh.user.model.dao.MemberSignupDao;

public class MemberSignupService {
	
	MemberSignupDao dao = new MemberSignupDao();
	
	public int insert(Member m) {
		// 1) Connection객체생성
		Connection conn = getConnection(); // conn으로 db와 연결함
		
		// 2) connection과 m을 dao에게 전달
		int result = dao.insert(conn, m);
		
		// 3) 커밋 rollback
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		// 4) 자원반납
		close(conn);
		
		return result;
	}

	public int idCheck(String userId) {
		Connection conn = getConnection();
		
		int count = dao.idCheck(conn, userId);
		
		close(conn);
		
		return count;
	}

	public boolean emailCheck(String email) {
		Connection conn = getConnection();
		
		boolean exists = dao.emilCheck(conn, email);
		
		close(conn);
		
		return exists;
	}
	

}
