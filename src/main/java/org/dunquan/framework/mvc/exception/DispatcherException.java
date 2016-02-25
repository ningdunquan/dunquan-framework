package org.dunquan.framework.mvc.exception;

/**
 * dispatcher异常
 * @author ningdq
 *
 */
public class DispatcherException extends Exception {

	private static final long serialVersionUID = 1L;

	public DispatcherException() {
		super();
	}

	public DispatcherException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DispatcherException(String message, Throwable cause) {
		super(message, cause);
	}

	public DispatcherException(String message) {
		super(message);
	}

	public DispatcherException(Throwable cause) {
		super(cause);
	}

}
