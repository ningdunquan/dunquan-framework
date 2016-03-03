package org.dunquan.framework.mvc.exception;

public class ActionException extends RuntimeException {
	private static final long serialVersionUID = 8763497650877766330L;

	public ActionException() {
	}
	
	public ActionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActionException(String message) {
		super(message);
	}

	public ActionException(Throwable cause) {
		super(cause);
	}
}
