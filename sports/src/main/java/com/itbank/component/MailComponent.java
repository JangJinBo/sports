package com.itbank.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailComponent {

	private final String host = "smtp.naver.com";
	private final int port = 465;
	private String serverId = ""; // 메일계정 아이디
	private String serverPw = ""; // 메일계정 비번

	private Properties props;

	@Value("classpath:mailForm.html")
	private Resource mailForm;

	public MailComponent() {

		props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
	}

	public int sendMimeMessage(HashMap<String, String> param) {

		Session mailSession = Session.getDefaultInstance(props, new Authenticator() {
			String un = serverId;
			String pw = serverPw;

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(un, pw);
			}

		});

		mailSession.setDebug(true);
		// 메일 발송시 진행 상황을 콘솔창에 출력하도록 설정한다.

		Message message = new MimeMessage(mailSession);
		String tag = "";

		try {
			// 파일 내용 불러와서 태그에 넣어두기
			Scanner sc = new Scanner(mailForm.getFile());
			while (sc.hasNextLine()) {
				tag += sc.nextLine();
			}
			sc.close();

			message.setFrom(new InternetAddress(serverId + "@naver.com"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(param.get("receiver")));
			message.setSubject(param.get("subject"));
			message.setContent(String.format(tag, param.get("content")), "text/html; charset=utf-8");
			Transport.send(message);
			return 1;
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
