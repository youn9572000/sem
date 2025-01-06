package com.kh.user.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import com.kh.user.model.dao.ReviewDao;
import com.kh.user.model.vo.Review;
import static com.kh.common.template.JDBCTemplate.*;

public class ReviewService {
	private ReviewDao dao = new ReviewDao(); // ReviewDao 객체 생성

	// 리뷰 목록 조회
	public List<Review> selectReviewList(int productNo) {
		Connection conn = getConnection();
		List<Review> reviewList = new ArrayList<>();

		try {
			reviewList = dao.selectList(conn, productNo);
			for (Review review : reviewList) {
				System.out.println("리뷰 작성일: " + review.getReviewDate()); // 디버깅용
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}

		return reviewList;
	}

	// 리뷰 총 개수 조회
	public int selectReviewCount(int productNo) {
		Connection conn = getConnection(); // 데이터베이스 연결 가져오기
		int listCount = dao.selectReviewCount(conn, productNo); // DAO 메서드 호출
		close(conn); // 연결 닫기
		return listCount; // 총 리뷰 개수 반환
	}

	// 리뷰 삽입
	public int insertReview(Review r) {
		Connection conn = null;
		int result = 0;

		try {
			conn = getConnection(); // DB 연결
			result = dao.insertReview(conn, r); // 리뷰 삽입

			if (result > 0) {
				commit(conn); // 성공 시 커밋
				int rno = dao.selectReviewListNo(conn); // 삽입된 리뷰 번호 조회
				if (rno > 0) {
					r.setReviewNo(rno); // 리뷰 객체에 번호 설정
				} else {
					throw new RuntimeException("리뷰 번호 조회 실패");
				}
			} else {
				rollback(conn); // 삽입 실패 시 롤백
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null)
				rollback(conn); // 롤백 처리
		} finally {
			if (conn != null)
				close(conn); // 연결 닫기
		}
		return result; // 삽입 결과 반환
	}

	// 특정 리뷰 조회
	public Review selectOne(int reviewNo) {
		Connection conn = getConnection();
		Review r = dao.selectOne(conn, reviewNo); // 특정 리뷰 조회
		close(conn); // 연결 닫기
		return r; // 리뷰 반환
	}

	// 리뷰 업데이트
	public int updateReview(Review r) {
		Connection conn = null;
		int result = 0;

		try {
			conn = getConnection();
			result = dao.updateReview(conn, r); // 리뷰 업데이트
			if (result > 0) {
				commit(conn); // 성공 시 커밋
			} else {
				rollback(conn); // 실패 시 롤백
			}
		} catch (Exception e) {
			e.printStackTrace(); // 예외 처리
			if (conn != null)
				rollback(conn); // 롤백 처리
		} finally {
			if (conn != null)
				close(conn); // 연결 닫기
		}

		return result; // 업데이트 결과 반환
	}

	// 리뷰 삭제
	public int deleteReview(int reviewNo) {
		Connection conn = null;
		int result = 0;

		try {
			conn = getConnection(); // 데이터베이스 연결
			result = dao.deleteReview(conn, reviewNo); // 리뷰 삭제
			if (result > 0) {
				commit(conn); // 성공 시 커밋
			} else {
				rollback(conn); // 실패 시 롤백
			}
		} catch (Exception e) {
			e.printStackTrace(); // 예외 처리
			if (conn != null)
				rollback(conn); // 롤백 처리
		} finally {
			if (conn != null)
				close(conn); // 연결 닫기
		}

		return result; // 삭제 결과 반환
	}

	// 상품 번호로 리뷰 목록 조회
	public List<Review> getReviewsByProductNo(int productNo) {
		Connection conn = null;
		List<Review> reviewList = null;

		try {
			conn = getConnection(); // 데이터베이스 연결
			reviewList = dao.selectReviewListByProductNo(conn, productNo); // DAO 호출
		} catch (Exception e) {
			e.printStackTrace(); // 예외 처리
		} finally {
			close(conn); // 연결 종료
		}

		return reviewList; // 리뷰 목록 반환
	}

	public boolean updateReviewStatus(int reviewNo, char newStatus) {
		Connection conn = null;
		int result = 0;

		try {
			conn = getConnection();
			result = new ReviewDao().updateReviewStatus(conn, reviewNo, newStatus);

			if (result > 0) {
				commit(conn);
				return true;
			} else {
				rollback(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			close(conn);
		}

		return false;
	}

	public char getReviewStatus(int reviewNo) {
		Connection conn = null;
		char status = 'N'; // 기본 상태를 'N'(신고되지 않음)으로 설정

		try {
			conn = getConnection(); // 데이터베이스 연결
			status = new ReviewDao().getReviewStatus(conn, reviewNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}

		return status; // 조회된 상태 반환
	}

	public int getReviewWriter(int reviewNo) {
		Connection conn = null;
		int writerId = -1;

		try {
			conn = getConnection();
			writerId = new ReviewDao().getReviewWriter(conn, reviewNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}

		return writerId;
	}

	public boolean isUserReporter(int reviewNo, int userId) {
		Connection conn = null;
		boolean isReporter = false; // 기본값: 신고자가 아님

		try {
			conn = getConnection(); // DB 커넥션 생성
			ReviewDao reviewDao = new ReviewDao(); // DAO 객체 생성
		} catch (Exception e) {
			e.printStackTrace(); // 예외 발생 시 출력
		} finally {
			close(conn); // DB 연결 닫기
		}

		return isReporter; // 신고자 여부 반환
	}


}
