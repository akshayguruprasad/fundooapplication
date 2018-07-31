package com.indream.fundoo.userservice.model;

import java.io.Serializable;

public class MailEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String subject;
	private String to;
	private String message;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
