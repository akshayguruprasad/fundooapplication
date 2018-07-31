/**
 * 
 */
package com.indream.fundoo.util;

import com.indream.fundoo.exceptionhandler.GenericException;

/**
 * @author rootuser
 *
 */
public class PreConditions {

    /**
     * @param resource
     * @return
     * @throws UserExceptionHandler
     */
    public static <T> T CheckNotNull(T resource) throws GenericException {
	if (resource != null) {
	    throw new GenericException(("emailis already registered"));
	}
	return resource;
    }

    public static <T> T CheckNull(T resource) throws GenericException {
	if (resource == null) {
	    throw new GenericException(("invalid email id"));
	}
	return resource;
    }

    /**
     * @param resource
     * @return
     * @throws UserExceptionHandler
     */
    public static <T> T CheckPassword(T resource) throws GenericException {
	if (resource == null) {
	    throw new GenericException(("invalid password"));
	}
	return resource;
    }
}