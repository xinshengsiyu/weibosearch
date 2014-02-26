package com.augmentum.weibo.exception;

public class WeiboException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WeiboException() {
		super();
	}

	public WeiboException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeiboException(String message) {
		super(message);
	}

	public WeiboException(Throwable cause) {
		super(cause);
	}

}
