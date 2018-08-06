/**
 * 
 */
package com.indream.fundoo.exceptionhandler;

/**
 * @author rootuser
 *
 */
public class LabelException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public LabelException(String message, Throwable cause) {
	super(message, cause);
    }

    public LabelException(String message) {
	super(message);
    }

    public LabelException(Throwable cause) {
	super(cause);
    }

}
