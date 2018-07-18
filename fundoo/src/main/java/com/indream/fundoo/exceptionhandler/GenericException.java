package com.indream.fundoo.exceptionhandler;

public class GenericException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GenericException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public GenericException(String arg0) {
		super(arg0);
	}

	public GenericException(Throwable arg0) {
		super(arg0);
	}

}
