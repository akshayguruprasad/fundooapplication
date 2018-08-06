/**
 * 
 */
package com.indream.fundoo.exceptionhandler;

/**
 * @author bridgeit
 *
 */
public class ElasticNoteException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public ElasticNoteException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ElasticNoteException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ElasticNoteException(Throwable cause) {
		super(cause);
	}

}
