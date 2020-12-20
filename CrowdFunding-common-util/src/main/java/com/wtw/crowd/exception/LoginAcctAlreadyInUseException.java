package com.wtw.crowd.exception;

/**
 * 检测到loginAcct已经被使用时 抛出本异常(本异常用于新增用户请求)
 *
 */
public class LoginAcctAlreadyInUseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8954514595417108119L;

	public LoginAcctAlreadyInUseException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
