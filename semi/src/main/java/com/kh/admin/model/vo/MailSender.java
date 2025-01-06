package com.kh.admin.model.vo;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	 public static void main(String[] args) {
	        // 발신자 계정 정보
	        final String senderEmail = "yb12140613@gmail.com";  // 발신자 이메일
	        final String senderPassword = "ouzheqgrrkucyxbm";  // 앱 비밀번호 또는 Gmail 비밀번호
	        final String recipientEmail = "kibin1214@naver.com";  // 수신자 이메일

	        // SMTP 설정
	        Properties props = new Properties();
	        props.put("mail.smtp.host", "smtp.gmail.com");  // Gmail SMTP 서버
	        props.put("mail.smtp.port", "587");  // TLS 포트
	        props.put("mail.smtp.auth", "true");  // 인증 사용
	        props.put("mail.smtp.starttls.enable", "true");  // TLS 사용

	        // 인증 정보 설정
	        Session session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(senderEmail, senderPassword);
	            }
	        });

	        try {
	            // 메일 작성
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(senderEmail));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
	            message.setSubject("이메일 테스트");
	            message.setText("안녕하세요. 자바에서 보내는 이메일입니다.");

	            // 메일 전송
	            Transport.send(message);
	            System.out.println("메일 발송 성공!");

	        } catch (MessagingException e) {
	            e.printStackTrace();
	            System.out.println("메일 발송 실패...");
	        }
	    }

}
