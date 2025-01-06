package com.kh.user.model.dao;

import static com.kh.common.template.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailVerificationDao {

    private Connection conn;

    public EmailVerificationDao() {
        try {
            conn = getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 인증 토큰 저장
	public void saveVerificationToken(String email, String token) {
		String query = "INSERT INTO EMAIL_VERIFICATION (EMAIL, TOKEN, CREATED_AT) VALUES (?, ?, SYSDATE)";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, token);
	        int rows = pstmt.executeUpdate();
	        if (rows > 0) {
	            System.out.println("토큰 저장 성공: " + token);
	        } else {
	            System.out.println("토큰 저장 실패");
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		commit(conn);
	}
	
	public void removeExpiredTokens() {
		String query = "DELETE FROM EMAIL_VERIFICATION WHERE CREATED_AT < (SYSDATE - INTERVAL '30' MINUTE)";
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	           pstmt.executeUpdate();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	}

	// 토큰 검증
	public boolean verifyToken(String token) {
		String query = "SELECT * FROM EMAIL_VERIFICATION WHERE TOKEN = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		boolean result = false;
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, token);
			rset = pstmt.executeQuery();
			
	        if (rset.next()) {
	            result = true;
	            deleteToken(token);  // 토큰 재사용 방지 (삭제)
	            commit(conn);
	        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
		
	}
	
	// 토큰 삭제 메소드
	public void deleteToken(String token) {
		String query = "DELETE FROM EMAIL_VERIFICATION WHERE TOKEN = ?";
		
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, token);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
				
	}

}

























