package com.kh.admin.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.admin.model.service.VisitorService;

/**
 * Servlet implementation class VisitorController
 */
@WebServlet("/visitor")
public class VisitorController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	  String action = request.getParameter("action");
          if ("increment".equals(action)) {
              handleIncrement(request, response);
          } else if ("display".equals(action)) {
              handleDisplay(request, response);
          } else {
              response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
              response.getWriter().write("{\"message\": \"잘못된 요청\"}");
          }
      }

      private void handleIncrement(HttpServletRequest request, HttpServletResponse response) throws IOException {
          String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          VisitorService visitorService = new VisitorService();

          visitorService.incrementTodayVisitor(today);
          response.getWriter().write("{\"message\": \"방문자 수 증가 성공\"}");
      }

      private void handleDisplay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	  response.setContentType("application/json; charset=UTF-8");

    	    String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	    String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    	    VisitorService visitorService = new VisitorService();
    	    int totalVisitors = visitorService.getTotalVisitors();
    	    int todayVisitors = visitorService.getVisitorCount(today);
    	    int yesterdayVisitors = visitorService.getVisitorCount(yesterday);

    	    // JSON 응답 생성
    	    String jsonResponse = String.format(
    	        "{\"totalVisitors\": %d, \"todayVisitors\": %d, \"yesterdayVisitors\": %d}",
    	        totalVisitors, todayVisitors, yesterdayVisitors
    	    );

    	    response.getWriter().write(jsonResponse);
    	}
  

    @Override
    public void destroy() {
        System.out.println("VisitorController 자원 해제");
        super.destroy();
    }


	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
