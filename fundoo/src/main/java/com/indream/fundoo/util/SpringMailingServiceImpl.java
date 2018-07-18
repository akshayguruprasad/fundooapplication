package com.indream.fundoo.util;

import javax.mail.MessagingException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;

public class SpringMailingServiceImpl implements MessageService {

	@Autowired
	private Environment env;

	@Autowired
	private JavaMailSenderImpl springMail;

	@Async(value = "threadpoolexec")
	@Override
	public void sendMessage(String userEmail, String subject, String message)
			throws IllegalStateException, MessagingException {

		springMail.setPassword(env.getProperty("mail.password"));
		springMail.setUsername(env.getProperty("mail.username"));
		SimpleMailMessage messageSimple = new SimpleMailMessage();
		messageSimple.setText(message);
		messageSimple.setTo(userEmail.trim());
		messageSimple.setSubject(subject);
		springMail.send(messageSimple);
	}

}
