package com.kh.user.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import com.kh.user.model.service.EmailService;
import com.kh.user.model.vo.Member;

import static com.kh.common.template.JDBCTemplate.*;

public class MemberDao {

	private Properties prop = new Properties();

	public MemberDao() {
		String fileName = MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath();

		try {
			// XML 파일 경로 설정
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Member login(Connection conn, String userId, String userPwd) {
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("login");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				m = Member.builder().userNo(rset.getInt("USER_NO")) // INTEGER → int
						.userId(rset.getString("USER_ID")) // VARCHAR → String
						.userPwd(rset.getString("USER_PWD")) // VARCHAR → String
						.email(rset.getString("EMAIL")) // VARCHAR → String
						.enrollDate(rset.getDate("ENROLL_DATE")) // DATE → java.sql.Date
						.memberStatus(rset.getString("MEMBER_STATUS")) // VARCHAR → String
						.build();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return m;
	}

	public int updateMember(Connection conn, Member updateMember) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("update"); // properties 파일에서 SQL 쿼리 가져오기 (회원정보 수정)

		try {
			// SQL 쿼리 준비 (이메일 수정)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, updateMember.getEmail()); // 이메일
			pstmt.setInt(2, updateMember.getUserNo()); // 사용자 번호 (수정된 사용자 번호)

			// 쿼리 실행 및 결과 업데이트된 행의 수 반환
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return updateCount; // 업데이트된 행의 수 반환
	}

	public int updateMemberPwd(Connection conn, Map<String, Object> param) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updatePwd"); // 비밀번호 업데이트 쿼리 가져오기

		try {
			pstmt = conn.prepareStatement(sql);

			// 파라미터 설정: 새 비밀번호, 사용자 번호, 현재 비밀번호
			pstmt.setString(1, (String) param.get("updatePwd")); // 새 비밀번호
			pstmt.setInt(2, (Integer) param.get("userNo")); // 사용자 번호
			pstmt.setString(3, (String) param.get("userPwd")); // 현재 비밀번호

			// 쿼리 실행
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
		} finally {
			close(pstmt); // PreparedStatement 종료
		}

		return updateCount; // 업데이트된 행 수 반환
	}

	public int delete(Connection conn, String userPwd, int userNo) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("delete");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			pstmt.setString(2, userPwd);

			updateCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return updateCount;
	}

	public String findUserId(Connection conn, String email) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String userId = null;

		String sql = prop.getProperty("findId");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);

			rset = pstmt.executeQuery();
			if (rset.next()) {
				userId = rset.getString("USER_ID");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return userId;
	}

	public boolean updatePasswrod(Connection conn, String email, String newPassword) {
		PreparedStatement pstmt =null;
		boolean result = false;
		String sql = "UPDATE MEMBER SET USER_PWD = ? WHERE EMAIL = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, email);
			result = pstmt.executeUpdate()>0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public boolean validateUser(Connection conn, String email, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		boolean isValid = false;
		String sql = "SELECT COUNT(*) FROM MEMBER WHERE EMAIL = ? AND USER_ID = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, userId);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				isValid = rset.getInt(1)>0;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return isValid;

	}
	 public boolean sendVerificationCode(String email, String verificationCode) {
	        return EmailService.sendVerificationCode(email, verificationCode);
	    }
}