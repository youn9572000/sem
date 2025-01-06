package com.kh.admin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kh.admin.model.service.DeleteService;
import com.kh.admin.model.vo.Member;

/**
 * Servlet implementation class DeleteUserController
 */
@WebServlet("/admin/DeleteUser")
public class DeleteUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line;
		
		while((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		
		System.out.println("수신된 데이터: " + sb.toString());  // 요청 데이터 로그 출력
		
		Gson gson = new Gson();
		Member[] users = gson.fromJson(sb.toString(), Member[].class);
		
		
		System.out.println("파싱된 데이터: " + Arrays.toString(users));  // 파싱 결과 로그
		
		DeleteService service = new DeleteService();
		boolean isSuccess = true;
		
		for(Member user : users) {
			int result = service.deleteUsers(user.getUserId());
			if(result <= 0) {
				isSuccess = false;
				break;
			}
		}
		
	    if (isSuccess) {
	        response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
	    } else {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
	    }
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		JsonObject json = new JsonObject();
		json.addProperty("success", isSuccess);
		response.getWriter().write(json.toString());
		
	}

}


























