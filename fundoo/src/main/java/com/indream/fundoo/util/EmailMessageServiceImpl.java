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

/**
 * EMAIL SERVICE IMPL FOR THE SIMPLE JAVA MAIL SENDING
 * 
 * @author Akshay
 *
 */
public class EmailMessageServiceImpl implements MessageService {
	final Logger LOG = Logger.getLogger(EmailMessageServiceImpl.class);

	@Autowired
	PasswordEncoder passwordEncoder;//PASSWORD ENCODER DEFINED IN THE APPCONFIG

	/* @purpose
	 * SEND MAIL TO THE RECIPIENT VIA SMTP PROTOCOL
	 *
	 * @author akshay
	 * @com.indream.fundoo.util
	 * @since Jul 24, 2018
	 *
	 */
	@Override
	public void sendMessage(String userEmail, String subject, String messageInput)
			throws AddressException, MessagingException {
		LOG.info("Enter [EmailMessageServiceImpl][sendMessage]");
		Session session = null;
		Transport transportor = null;
		Multipart multiPart = null;
		BodyPart part = null;
		Message message = null;

		Properties props = new Properties();//SET ALL THE NECESSARY PROPERTIES
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Authenticator auth = new Authenticator() {
		};//SET AUTH 
		session = Session.getDefaultInstance(props, auth);//CREATE A SESSION TO SEND THE MAIL

		message = new MimeMessage(session);//BUILD A MIME MESSAGE FORMAT PACKET
		message.setFrom(new InternetAddress("studentportal.manager@gmail.com"));//SET THE SENDER DATA
		message.setSubject(subject);//ADD THE SUBJECT
		message.addRecipient(RecipientType.TO, new InternetAddress(userEmail));//SET THE RECIVER DATA
		multiPart = new MimeMultipart();//MULTIPART FOR THE BODY
		part = new MimeBodyPart();//CREATE A SINGLE PART
		part.setContent(messageInput, "text/html");//HTML CONTEXT SET
		multiPart.addBodyPart(part);//ADD TO THE MULTIPART
		message.setContent(multiPart);//ADD THE MULTIPART TO THE MAIL MESSAGE
		transportor = session.getTransport("smtp");
		transportor.connect("studentportal.manager@gmail.com", "ABC12345six");//SET THE SENDER CREDENTIALS
		transportor.sendMessage(message, message.getAllRecipients());//SEND MESSAGE

	}

}
