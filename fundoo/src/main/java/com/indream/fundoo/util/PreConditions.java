/**
 * 
 */
package com.indream.fundoo.util;

import java.lang.reflect.Constructor;

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
    public static <T, E extends RuntimeException> T checkNotNull(T resource, String message, Class<E> exception) {
	if (resource != null) {
	    throwException(exception, message);
	}
	return resource;
    }

    public static <T, E extends RuntimeException> T checkNull(T resource, String message, Class<E> exception) {
	if (resource == null) {
	    throwException(exception, message);
	}
	return resource;
    }

    /**
     * @param resource
     * @return
     * @throws UserExceptionHandler
     */
    public static <T, E extends RuntimeException> T checkPassword(T resource, String message, Class<E> exception) {
	if (resource == null) {
	    throwException(exception, message);
	}
	return resource;
    }

    public static <T, E extends RuntimeException> void checkFalse(T resource, String message, Class<E> exception) {
	if (resource.equals(false)) {
	    throwException(exception, message);

	}

    }

    public static <T , E extends RuntimeException> void checkNotMatch(T resource1, T resource2, String message,
	    Class<E> exception) {
	if (!resource1.equals(resource2)) {
	    throwException(exception, message);
	}
    }

    public static <T extends Number, E extends RuntimeException> void checkNotPositive(T number, String message,
	    Class<E> exception) {
	if (number.hashCode() < 1) {
	    throwException(exception, message);
	}
    }

    /*
     * @purpose
     *
     *
     * @author akshay
     * 
     * @com.indream.fundoo.util
     * 
     * @since Jul 27, 2018
     *
     */
    public static <E extends RuntimeException> void throwException(Class<E> exception, String message) {

	try {
	    Constructor<E> constructor = exception.getConstructor(String.class);

	    E value = constructor.newInstance(message);

	    throw value;

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

}