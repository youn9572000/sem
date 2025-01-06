package com.kh.admin.model.service;

import static com.kh.common.template.JDBCTemplate. *;

import java.sql.Connection;
import java.util.List;

import com.kh.admin.model.dao.DeleteDao;
import com.kh.admin.model.dto.BlockMemberDTO;
import com.kh.admin.model.vo.Member;
import com.kh.common.model.vo.PageInfo;

public class DeleteService {
	
	private DeleteDao dao = new DeleteDao();

	public int selectListCount(String searchId) {
		Connection conn = getConnection();
		
		int listCount = dao.selectListCount(conn, searchId);
		
		close(conn);
		
		return listCount;
		
	}

	public List<Member> searchIdList(String searchId, PageInfo pi) {
		Connection conn = getConnection();
		
		List<Member> list = dao.searchIdList(conn, searchId ,pi);
	
		close(conn);
		
		return list;
		
	}

	public int deleteUsers(String userId) {
		Connection conn = getConnection();
		 
		int result = dao.deleteUsers(conn, userId);
		System.out.println("result: " + result);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
		
	}

	public List<Member> selectAllList(PageInfo pi) {
		Connection conn = getConnection();
		
		List<Member> list = dao.selectAllList(conn, pi);
		
		close(conn);
		
		return list;
		
	}

}
