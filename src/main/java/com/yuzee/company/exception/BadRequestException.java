package com.yuzee.company.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author SeekADegree
 *
 */
public class BadRequestException extends BaseException {

	/**
	 *
	 */
	private static final long serialVersionUID = 8569404917204068340L;
	private static final HttpStatus status = HttpStatus.BAD_REQUEST;

	/**
	 *
	 */
	public BadRequestException() {
	}

	/**
	 *
	 * @param status
	 * @param message
	 * @param cause
	 */
	public BadRequestException(final String message, final Throwable cause) {
		super(status, message, cause);
	}

	/**
	 *
	 * @param status
	 * @param message
	 */
	public BadRequestException(final String message) {
		super(status, message);
	}

	/**
	 *
	 * @param status
	 * @param cause
	 */
	public BadRequestException(final Throwable cause) {
		super(status, cause);
	}

}
