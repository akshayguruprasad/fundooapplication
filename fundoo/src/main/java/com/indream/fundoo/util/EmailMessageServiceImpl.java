package com.indream.fundoo.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EmailMessageServiceImpl implements MessageService {
	final Logger LOG = Logger.getLogger(EmailMessageServiceImpl.class);

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void sendMessage(String userEmail, String subject, String messageInput)
			throws AddressException, MessagingException {
		LOG.info("Enter [EmailMessageServiceImpl][sendMessage]");
		Session session = null;
		Transport transportor = null;
		Multipart multiPart = null;
		BodyPart part = null;
		Message message = null;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Authenticator auth = new Authenticator() {
		};
		session = Session.getDefaultInstance(props, auth);

		message = new MimeMessage(session);
		message.setFrom(new InternetAddress("studentportal.manager@gmail.com"));
		message.setSubject(subject);
		message.addRecipient(RecipientType.TO, new InternetAddress(userEmail));
		multiPart = new MimeMultipart();
		part = new MimeBodyPart();
		part.setContent(messageInput, "text/html");
		multiPart.addBodyPart(part);
		message.setContent(multiPart);
		transportor = session.getTransport("smtp");
		transportor.connect("studentportal.manager@gmail.com", "ABC12345six");
		transportor.sendMessage(message, message.getAllRecipients());

	}

}
