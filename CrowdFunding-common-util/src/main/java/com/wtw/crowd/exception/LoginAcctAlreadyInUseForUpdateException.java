package com.wtw.crowd.exception;

/**
 * 检测到loginAcct已经被使用时 抛出本异常(本异常用于更新用户请求)
 */
public class LoginAcctAlreadyInUseForUpdateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -224851561330731702L;

	public LoginAcctAlreadyInUseForUpdateException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseForUpdateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LoginAcctAlreadyInUseForUpdateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
