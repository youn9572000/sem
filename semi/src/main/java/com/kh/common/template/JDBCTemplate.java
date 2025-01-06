package com.kh.common.template;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

public class JDBCTemplate {
	static BasicDataSource dataSource = new BasicDataSource();
	
	static {
		// 커넥션풀을 통해 생성하고자 하는 커넥션 옵션 기술
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setUsername("C##SEMI");
		dataSource.setPassword("1234");
		dataSource.setInitialSize(10);// 초기 커넥션풀(커넥션 관리하는 저장소) 사이즈
		dataSource.setMaxTotal(50);
		dataSource.setDefaultAutoCommit(false);// 자동커밋 false
		dataSource.setMaxWaitMillis(10000);
		dataSource.setRemoveAbandonedTimeout(300);		
	}
	
	// 1. Connection 반환 메서드
		public static Connection getConnection() {	
			Connection conn = null;
			try {
				conn = dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return conn;
			
		}
		
		
		// 2. closer기능들
		public static void close(Connection conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void close(Statement stmt) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void close(ResultSet rset) {
			try {
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
		// 3. commit, rollback
		public static void commit(Connection conn) {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void rollback(Connection conn) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
		}
}