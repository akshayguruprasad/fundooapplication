package com.indream.fundoo.util;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.indream.fundoo.userservice.model.MailEntity;

@Component
public class MailListener {

	@Autowired
	MessageService springMessage;

	public void sendEmail(String message) {
MailEntity mail=Utility.convertFromJSONString(message,MailEntity.class);		
		
try {
	springMessage.sendMessage(mail.getTo(), mail.getSubject(), mail.getMessage());
} catch (AddressException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (MessagingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		
	}
	
}
