package com.indream.fundoo.exceptionhandler;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class AynchExceptionHandler extends RuntimeException implements AsyncUncaughtExceptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AynchExceptionHandler() {
		super();
	}

	public AynchExceptionHandler(String message, Throwable cause) {
		super(message, cause);
	}

	public AynchExceptionHandler(String message) {
		super(message);
	}

	public AynchExceptionHandler(Throwable cause) {
		super(cause);
	}

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		throw new AynchExceptionHandler(method.getName() + "Exception has occured ", ex);

	}

}
