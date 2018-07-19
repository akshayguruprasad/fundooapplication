package com.indream.fundoo.util;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface MessageService {
	public void sendMessage(String userEmail, String subject, String messageInput)
			throws AddressException, MessagingException;
}
