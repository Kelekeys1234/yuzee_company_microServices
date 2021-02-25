package com.yuzee.company.exception;

public class ServiceInvokeException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ServiceInvokeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceInvokeException(String message) {
		super(message);
	}

	public ServiceInvokeException(Throwable cause) {
		super(cause);
	}

}
