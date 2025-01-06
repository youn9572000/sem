package com.kh.user.model.service;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

	private static final String SMTP_HOST = "smtp.naver.com"; // SMTP 서버
	private static final String SMTP_USER = "jovision00@naver.com"; // 발신 이메일 주소
	private static final String SMTP_PASSWORD = "5K23KTHH6X8S"; // 이메일 비밀번호 (2단계 인증 비밀번호)

	public static boolean sendVerificationCode(String email, String verificationCode) {
		boolean isSent = false;

		// SMTP 설정
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", SMTP_HOST);

		// SMTP 세션 생성
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
			}
		});

		try {
			// 이메일 메시지 생성
			Message message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(SMTP_USER, "편리하조", "UTF-8")); // 발신자
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException("인코딩 오류 발생: 발신자 이름 설정 실패");
			}
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(email)); // 수신자
			message.setSubject("편리하조: 인증번호 발송"); // 제목
			message.setText("안녕하세요, 인증번호는 [" + verificationCode + "] 입니다."); // 내용

			// 이메일 발송
			Transport.send(message);
			isSent = true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return isSent;
	}
}
