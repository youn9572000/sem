package com.kh.admin.model.service;

import com.kh.admin.model.dao.AdminSearchDao;
import com.kh.admin.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

import static com.kh.common.template.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

public class AdminSearchService {

	
	private AdminSearchDao dao = new AdminSearchDao();
    public List<Board> searchBoards(String boardType, String keyword, int currentPage, int boardLimit) {
        Connection conn = getConnection();
        List<Board> list = dao.searchBoards(conn, boardType, keyword, currentPage, boardLimit);
        close(conn);
        return list;
    }

    public int getBoardCount(String boardType, String keyword) {
        Connection conn = getConnection();
        int count = dao.getBoardCount(conn, boardType, keyword);
        close(conn);
        return count;
    }
}
