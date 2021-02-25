package com.yuzee.company.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends BaseException {
	

	private static final long serialVersionUID = 4444292779589575016L;
	private static final HttpStatus status = HttpStatus.NOT_FOUND;

	/**
	 *
	 */
	public InternalServerErrorException() {
	}

	/**
	 *
	 * @param status
	 * @param message
	 * @param cause
	 */
	public InternalServerErrorException(final String message, final Throwable cause) {
		super(status, message, cause);
	}

	/**
	 *
	 * @param status
	 * @param message
	 */
	public InternalServerErrorException(final String message) {
		super(status, message);
	}

	/**
	 *
	 * @param status
	 * @param cause
	 */
	public InternalServerErrorException(final Throwable cause) {
		super(status, cause);
	}
}