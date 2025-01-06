package com.kh.admin.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import com.kh.common.template.JDBCTemplate;

import static com.kh.common.template.JDBCTemplate.*;

public class VisitorDao {
	
private Properties prop = new Properties();
	
	public VisitorDao() {
		String path = BlockDao.class.getResource("/sql/admin/admin-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	public void incrementVisitorCount(String date, Connection conn) {
	    String query = prop.getProperty("incrementVisitorCount");
	    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, date);
	        pstmt.setString(2, date);
	        int result = pstmt.executeUpdate();
	        System.out.println("MERGE INTO 실행 결과: " + result); // 실행 결과 출력
	        conn.commit();
	    } catch (SQLException e) {
	        System.out.println("MERGE INTO 실행 중 오류 발생: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("SQL 실행 중 오류 발생", e);
	    }finally {
	        if (conn != null) close(conn);
	    }
	}




	    // 특정 날짜의 방문자 수 조회
	  public int getVisitorCount(String date, Connection conn) {
		    int count = 0;
		    String query = "SELECT visit_count\r\n"
		    		+ "FROM VISITER\r\n"
		    		+ "WHERE TRUNC(visit_date) = TO_DATE(?, 'YYYY-MM-DD')";
		    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
		        pstmt.setString(1, date);
		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                count = rs.getInt("visit_count");
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return count;
		}

	    // 전체 방문자 수 조회
	  public int getTotalVisitorCount(Connection conn) {
		    int total = 0;
		    String query = "SELECT SUM(visit_count) AS total FROM VISITER";
		    try (PreparedStatement pstmt = conn.prepareStatement(query);
		         ResultSet rs = pstmt.executeQuery()) {
		        if (rs.next()) {
		            total = rs.getInt("total");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return total;
		}
}