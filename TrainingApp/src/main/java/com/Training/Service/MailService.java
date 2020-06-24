package com.Training.Service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Training.Configuration.MailConfig;
import com.Training.Request.SendMailRequest;

@Service
public class MailService {

	@Autowired
	MailConfig mailConfig;

	public void sendMail(SendMailRequest request) {

		Properties properties = new Properties();
		properties.put("mail.smtp.host", mailConfig.getHost());
		properties.put("mail.smtp.port", mailConfig.getPort());
		properties.put("mail.smtp.auth", String.valueOf(mailConfig.isAuthEnabled()));
		properties.put("mail.smtp.starttls.enable", String.valueOf(mailConfig.isStarttlsEnabled()));

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailConfig.getUserAddress(), mailConfig.getUserPwd());
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setSubject(request.getSubject());
			message.setContent(request.getContent(), "text/html; charset=UTF-8");
			message.setFrom(new InternetAddress(mailConfig.getUserAddress(), mailConfig.getUserDisplayName()));
			for (String address : request.getReceivers()) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
			}

			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
