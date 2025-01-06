package com.kh.admin.model.service;

import com.kh.admin.model.dao.VisitorDao;
import static com.kh.common.template.JDBCTemplate. *;

import java.sql.Connection;

public class VisitorService {
    private VisitorDao visitorDAO = new VisitorDao();

    // 오늘 방문자 수 증가
    public void incrementTodayVisitor(String today) {
        Connection conn = getConnection();
        visitorDAO.incrementVisitorCount(today, conn);
        close(conn);
    }

    // 전체 방문자 수 조회
    public int getTotalVisitors() {
        Connection conn = getConnection();
        int result = visitorDAO.getTotalVisitorCount(conn);
        close(conn);
        return result;
    }

    // 특정 날짜 방문자 수 조회
    public int getVisitorCount(String date) {
        Connection conn = getConnection();
        int result = visitorDAO.getVisitorCount(date, conn);
        close(conn);
        return result;
    }
}
