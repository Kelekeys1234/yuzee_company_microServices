package com.yuzee.company.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author SeekADegree
 *
 */
public class UnauthorizeException extends BaseException {

	/**
	 *
	 */
	private static final long serialVersionUID = 8569404917204068340L;
	private static final HttpStatus status = HttpStatus.UNAUTHORIZED;

	/**
	 *
	 */
	public UnauthorizeException() {
	}

	/**
	 *
	 * @param status
	 * @param message
	 * @param cause
	 */
	public UnauthorizeException(final String message, final Throwable cause) {
		super(status, message, cause);
	}

	/**
	 *
	 * @param status
	 * @param message
	 */
	public UnauthorizeException(final String message) {
		super(status, message);
	}

	/**
	 *
	 * @param status
	 * @param cause
	 */
	public UnauthorizeException(final Throwable cause) {
		super(status, cause);
	}

}
