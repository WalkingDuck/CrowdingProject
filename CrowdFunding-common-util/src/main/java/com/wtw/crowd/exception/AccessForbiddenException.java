package com.wtw.crowd.exception;

/**
 *	没登录访问非共有资源时的异常
 */
public class AccessForbiddenException extends RuntimeException {
	
	private static final long serialVersionUID = 1368435628777878045L;

	public AccessForbiddenException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public AccessForbiddenException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AccessForbiddenException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AccessForbiddenException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
