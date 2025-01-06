package com.kh.user.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.user.model.dao.EmailVerificationDao;

/**
 * Servlet implementation class EmailVerificationController
 */
@WebServlet("/user/sendVerification")
public class EmailVerificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailVerificationController() {
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String token = UUID.randomUUID().toString(); // 인증 토큰 생성
		
		// 인증 토큰을 DB에 저장 (이메일과 매핑)
		EmailVerificationDao dao = new EmailVerificationDao();
		dao.saveVerificationToken(email, token);
		
		// 이메일 발송 설정
		String subject = "회원가입 이메일 인증";
		String verificationUrl = "http://localhost:8083"+request.getContextPath()+"/user/verifyEmail?token=" + token;
	
		String content = "<h1>이메일 인증</h1>"
				+ "<p>아래 링크를 클릭하여 이메일 인증을 완료해주세요:</p>"
				+ "<a href='" + verificationUrl + "'>이메일 인증하기</a>";
		
		boolean isSent = sendEmail(email, subject, content);
		
		if(isSent) {
			response.getWriter().write("success");
		}else {
			response.getWriter().write("fail");
		}
		
	}

	private boolean sendEmail(String email, String subject, String content) {
		final String senderEmail = "kibin1214@naver.com"; // 이메일 발신자
		final String senderPassword = "F5TLY1G3KY27"; // 이메일 비밀번호
		
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.naver.com"); // SMTP 서버
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		
		// 타임아웃 설정 추가
		prop.put("mail.smtp.timeout", "10000");  // SMTP 서버 응답 대기 시간 (10초)
		prop.put("mail.smtp.connectiontimeout", "10000");  // 서버 연결 타임아웃 (10초)
		prop.put("mail.smtp.writetimeout", "10000");  // 데이터 쓰기 타임아웃 (10초)
		
		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderEmail, "편리하조 관리자"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setContent(content, "text/html;charset=UTF-8");
            
            Transport.send(message);
            return true;
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		
				
	}

}