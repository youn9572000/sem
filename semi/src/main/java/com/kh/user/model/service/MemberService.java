package com.kh.user.model.service;

import static com.kh.common.template.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.jasper.tagplugins.jstl.core.Catch;
import com.kh.user.model.dao.MemberDao;
import com.kh.user.model.vo.Member;

public class MemberService {
	private static MemberDao dao = new MemberDao();

	public Member login(String userId, String userPwd) {
		// dao가 database에 접속할수 있도록 Connection 생성하기
		Connection conn = getConnection();

		// dao에게 connection과 id, pwd를 전달하여 로그인 기능 수행
		Member m = dao.login(conn, userId, userPwd);

		close(conn);

		// Member 객체를 그대로 반환 (상태 확인은 컨트롤러에서 처리)
		if (m == null) {
			System.out.println("로그인 실패: Member 객체가 null");
		} else {
			System.out.println("로그인 성공: " + m.getUserId() + ", 상태: " + m.getMemberStatus());
		}
		return m;
	}

	public int updateMember(Member updateMember) {
		Connection conn = getConnection(); // DB 연결
		int result = 0;

		try {
			// 트랜잭션 시작
			conn.setAutoCommit(false);

			// 회원정보 수정 호출
			result = new MemberDao().updateMember(conn, updateMember);

			if (result > 0) {
				commit(conn); // 성공하면 커밋
			} else {
				rollback(conn); // 실패하면 롤백
			}
		} catch (SQLException e) {
			rollback(conn); // 예외 발생 시 롤백
			e.printStackTrace();
		} finally {
			close(conn); // 연결 종료
		}

		return result; // 수정된 행 수 반환
	}

	public int updateMemberAndPwd(Member updateMember, String newPwd, String currentPwd) {
		Connection conn = getConnection(); // DB 연결
		int result = 0;

		try {
			// 트랜잭션 시작
			conn.setAutoCommit(false);

			// 회원정보 수정 호출
			int memberUpdateResult = new MemberDao().updateMember(conn, updateMember); // 이메일 수정
			System.out.println("Member update result: " + memberUpdateResult);

			if (memberUpdateResult > 0) {
				// 비밀번호 수정 호출
				Map<String, Object> param = new HashMap<>();
				param.put("updatePwd", newPwd); // 새 비밀번호
				param.put("userNo", updateMember.getUserNo()); // 사용자 번호
				param.put("userPwd", currentPwd); // 현재 비밀번호

				int passwordUpdateResult = new MemberDao().updateMemberPwd(conn, param); // 비밀번호 수정
				System.out.println("Password update result: " + passwordUpdateResult);

				if (passwordUpdateResult > 0) {
					// 트랜잭션 성공적으로 처리
					result = 1; // 성공 시 result 값을 1로 설정
				} else {
					result = 0; // 비밀번호 변경 실패 시 result 값을 0으로 설정
				}
			} else {
				result = 0; // 회원정보 수정 실패 시 result 값을 0으로 설정
			}

			// 트랜잭션 결과에 따라 커밋 또는 롤백
			if (result > 0) {
				commit(conn); // 모든 작업이 성공하면 커밋
			} else {
				rollback(conn); // 실패한 경우 롤백
			}
		} catch (SQLException e) {
			rollback(conn); // 예외 발생 시 롤백
			e.printStackTrace();
		} finally {
			close(conn); // 연결 종료
		}

		return result; // 수정된 행 수 반환
	}

	public int delete(String userPwd, int userNo) {
		Connection conn = getConnection();
		int result = 0;

		try {
			// 트랜잭션 시작
			conn.setAutoCommit(false);

			// DAO에서 실제 삭제 작업을 처리
			result = dao.delete(conn, userPwd, userNo);

			if (result > 0) {
				// 성공적으로 삭제된 경우 커밋
				commit(conn);
			} else {
				// 삭제가 되지 않은 경우 롤백
				rollback(conn);
			}

		} catch (SQLException e) {
			// 예외가 발생하면 롤백
			rollback(conn);
			e.printStackTrace();
		} finally {
			// Connection을 닫기
			close(conn);
		}

		return result;
	}

	public static String findUserIdByEmail(String email) {
		Connection conn = null;
		String userId = null;

		try {
			// DB 연결 생성
			conn = getConnection();

			// 트랜잭션 설정: 자동 커밋 비활성화
			conn.setAutoCommit(false);

			// DAO를 통해 이메일로 사용자 ID 조회
			userId = dao.findUserId(conn, email);

			if (userId != null) {
				// 사용자 ID가 존재하면 트랜잭션 커밋
				commit(conn);
			} else {
				// 사용자 ID가 없는 경우 트랜잭션 롤백
				rollback(conn);
			}
		} catch (SQLException e) {
			// 예외 발생 시 롤백 처리
			rollback(conn);
			e.printStackTrace();
		} finally {
			// 연결 자원 닫기
			close(conn);
		}

		return userId; // 사용자 ID 반환
	}

	public static boolean updatePassword(String email, String newPassword) {
		Connection conn = getConnection();
		boolean result = dao.updatePasswrod(conn, email, newPassword);
		if (result) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public static boolean validateUser(String email, String userId) {
		Connection conn = getConnection();
		boolean isValid = dao.validateUser(conn, email, userId);
		close(conn);
		return isValid;
	}

	public boolean validatePassword(String inputPwd, String userPwd) {
    // 간단한 문자열 비교로 비밀번호 검증
    return inputPwd.equals(userPwd);
}

	

}