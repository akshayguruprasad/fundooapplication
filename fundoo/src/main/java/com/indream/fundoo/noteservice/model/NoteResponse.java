package com.indream.fundoo.noteservice.model;

import java.io.Serializable;

public class NoteResponse implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public NoteResponse() {
		super();
	}
	public NoteResponse(String message, int code) {
		super();
		this.message = message;
		this.code = code;
	}
	private String message;
	private int code;
	
}
