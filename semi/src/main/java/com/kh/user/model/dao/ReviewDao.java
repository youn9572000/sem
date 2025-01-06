package com.kh.user.model.dao;

import static com.kh.common.template.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.user.model.vo.Review;

public class ReviewDao {
    private Properties prop = new Properties(); // SQL 쿼리를 저장할 Properties 객체

    // 생성자: XML 파일에서 SQL 쿼리 로드
    public ReviewDao() {
        String fileName = ReviewDao.class.getResource("/sql/review/Review.xml").getPath();
        try {
            prop.loadFromXML(new FileInputStream(fileName)); // XML 파일 로드
        } catch (Exception e) {
            System.err.println("XML 파일 로드 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 리뷰 목록 조회 메서드
    public List<Review> selectList(Connection conn, int productNo) {
        List<Review> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = prop.getProperty("selectReviewList");

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productNo);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                Review review = Review.builder()
                        .reviewNo(rset.getInt("REVIEW_NO"))
                        .reviewWriter(rset.getString("REVIEW_WRITER")) // 문자열 처리
                        .productNo(rset.getInt("PRODUCT_NO"))
                        .reviewContent(rset.getString("REVIEW_CONTENT"))
                        .reviewDate(rset.getTimestamp("REVIEW_DATE"))
                        .reviewScore(rset.getObject("REVIEW_SCORE") != null ? rset.getInt("REVIEW_SCORE") : 0) // null 처리
                        .reviewDec(rset.getString("REVIEW_DEC") != null ? rset.getString("REVIEW_DEC") : "N") // 기본값 처리
                        .build();
                list.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return list;
    }

    // 리뷰 총 개수 조회 메서드
    public int selectReviewCount(Connection conn, int productNo) {
        int listCount = 0;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = prop.getProperty("selectReviewCount");

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productNo);
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

    // 리뷰 번호 조회 메서드
    public int selectReviewListNo(Connection conn) {
        int rno = 0;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = prop.getProperty("selectReivewListNo");

        try {
            pstmt = conn.prepareStatement(sql);
            rset = pstmt.executeQuery();

            if (rset.next()) {
                rno = rset.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return rno;
    }

    // 리뷰 추가 메서드
    public int insertReview(Connection conn, Review r) throws SQLException {
        int updateCount = 0;
        PreparedStatement pstmt = null;
        String sql = prop.getProperty("insertReview");

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(r.getReviewWriter())); // 문자열을 숫자로 변환
            pstmt.setInt(2, r.getProductNo());
            pstmt.setString(3, r.getReviewContent());
            pstmt.setInt(4, r.getReviewScore());
            updateCount = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return updateCount;
    }

    // 리뷰 단일 조회 메서드
    public Review selectOne(Connection conn, int reviewNo) {
        Review r = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = prop.getProperty("selectOneReview");

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewNo);
            rset = pstmt.executeQuery();

            if (rset.next()) {
                r = Review.builder()
                        .reviewContent(rset.getString("REVIEW_CONTENT"))
                        .reviewScore(rset.getInt("REVIEW_SCORE"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return r;
    }

    // 리뷰 삭제 메서드
    public int deleteReview(Connection conn, int reviewNo) {
        int updateCount = 0;
        PreparedStatement pstmt = null;
        String sql = prop.getProperty("deleteReviewByWriter");

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewNo);
            updateCount = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return updateCount;
    }

    // 리뷰 상태 업데이트 메서드
    public int updateReviewStatus(Connection conn, int reviewNo, char newStatus) {
        String sql = prop.getProperty("updateReviewStatus");
        int result = 0;

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(newStatus)); // char → String
            pstmt.setInt(2, reviewNo);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 리뷰 상태 조회 메서드
    public char getReviewStatus(Connection conn, int reviewNo) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        char status = 'N'; // 기본 상태

        String sql = prop.getProperty("selectReviewStatus");

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewNo);
            rset = pstmt.executeQuery();

            if (rset.next()) {
                status = rset.getString("REVIEW_DEC").charAt(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return status;
    }

	public int updateReview(Connection conn, Review r) {
		 String sql = prop.getProperty("updateReview"); // XML에서 SQL 쿼리 가져오기
		    PreparedStatement pstmt = null;
		    int result = 0;
		 
		    try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, r.getReviewContent());
				pstmt.setInt(2, r.getReviewScore());
				pstmt.setInt(3, r.getReviewNo());
				
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				close(pstmt);
			}
		    return result;
		
	}

	public List<Review> selectReviewListByProductNo(Connection conn, int productNo) {
		   List<Review> list = new ArrayList<>(); // 리뷰 목록을 저장할 리스트
		    PreparedStatement pstmt = null; // SQL 쿼리 실행을 위한 PreparedStatement 객체
		    ResultSet rset = null; // SQL 실행 결과를 저장할 ResultSet 객체
		    String sql = prop.getProperty("selectReviewListByProductNo"); // XML에서 SQL 쿼리 가져오기
		    
		    try {
				pstmt = conn.prepareStatement(sql);
				 pstmt.setInt(1, productNo); // 첫 번째 파라미터: 상품 번호
			        rset = pstmt.executeQuery(); // 쿼리 실행 및 결과 가져오기
			        
			        while (rset.next()) { // 결과를 반복 처리
			            // Review 객체 생성 및 값 설정
			            Review review = Review.builder()
			                    .reviewNo(rset.getInt("REVIEW_NO")) // 리뷰 번호
			                    .reviewWriter(rset.getString("REVIEW_WRITER")) // 작성자 ID
			                    .productNo(rset.getInt("PRODUCT_NO")) // 상품 번호
			                    .reviewContent(rset.getString("REVIEW_CONTENT")) // 리뷰 내용
			                    .reviewDate(rset.getTimestamp("REVIEW_DATE")) // 리뷰 작성일
			                    .reviewScore(rset.getInt("REVIEW_SCORE")) // 리뷰 점수
			                    .build();

			            list.add(review); // 생성된 Review 객체를 리스트에 추가
			        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				close(rset);
				close(pstmt);
			}
		    return list;
		    
	}

	public int getReviewWriter(Connection conn, int reviewNo) {
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    int writerId = -1; // 기본값을 -1로 설정 (조회 실패 시 반환)

	    String sql = "SELECT REVIEW_WRITER FROM REVIEW WHERE REVIEW_NO = ?"; // 작성자 ID 조회 SQL

	    try {
	        pstmt = conn.prepareStatement(sql); // SQL 준비
	        pstmt.setInt(1, reviewNo); // 첫 번째 파라미터로 리뷰 번호 설정
	        rset = pstmt.executeQuery(); // 쿼리 실행

	        if (rset.next()) {
	            writerId = rset.getInt("REVIEW_WRITER"); // 작성자 ID 가져오기
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
	    } finally {
	        close(rset); // ResultSet 닫기
	        close(pstmt); // PreparedStatement 닫기
	    }

	    return writerId; // 작성자 ID 반환
	}
}
