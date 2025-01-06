package com.kh.user.model.service;

import static com.kh.common.template.JDBCTemplate.close;
import static com.kh.common.template.JDBCTemplate.commit;
import static com.kh.common.template.JDBCTemplate.getConnection;
import static com.kh.common.template.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.kh.user.model.dao.SavelistDao;
import com.kh.user.model.vo.Save;

public class SaveListService {
	private SavelistDao dao = new SavelistDao();

	public List<Save> selectSaveListList(int userId) {
		Connection conn = getConnection();
		List<Save> list = dao.selectSaveList(conn, userId); // userId 전달
		close(conn);
		return list;
	}

	public int insertSaveList(Save s) {
		Connection conn = getConnection();

		int result = dao.insertSaveList(conn, s);

		if (result > 0) {
			int saveno = dao.selectinsertSaveList(conn);
			s.setNo(saveno);
			System.out.println("찜 목록 추가 성공: " + s);

			commit(conn);
		} else {
			rollback(conn);
			System.out.println("찜 목록 추가 실패");
		}
		close(conn);
		return result;

	}

	public int deleteSaveList(Save d) {
		Connection conn = getConnection();
		int result = dao.deleteSaveList(conn, d);
		if (result > 0) {
			commit(conn);

		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public boolean isDuplicateSave(Save save) {
		Connection conn = getConnection();
		boolean isDuplicate = dao.isDuplicateSave(conn, save);
		close(conn);
		return isDuplicate;
	}

	public int deleteAllSaveList(int userId) {
		Connection conn = getConnection();
		int result = 0;

		try {
			result = dao.deleteAllSaveList(conn, userId); // DAO 호출
			if (result > 0)
				commit(conn);
			else
				rollback(conn);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
		} finally {
			close(conn);
		}

		return result;
	}

}
