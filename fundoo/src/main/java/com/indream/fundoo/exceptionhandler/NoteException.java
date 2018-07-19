package com.indream.fundoo.exceptionhandler;

public class NoteException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoteException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoteException(String message) {
		super(message);
	}

	public NoteException(Throwable cause) {
		super(cause);
	}

	
	
	
}
