package org.dunquan.framework.ioc.exception;

public class IOCException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IOCException() {
	}
	
	public IOCException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IOCException(String message, Throwable cause) {
		super(message, cause);
	}

	public IOCException(String message) {
		super(message);
	}

	public IOCException(Throwable cause) {
		super(cause);
	}
}
