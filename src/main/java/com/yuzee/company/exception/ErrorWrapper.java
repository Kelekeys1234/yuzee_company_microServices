package com.yuzee.company.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error_wrapper")
public class ErrorWrapper {

	@XmlElement(name = "status")
	private int errorStatus = 0;
	@XmlElement(name = "message")
	private String errorMessage = null;
	
	public ErrorWrapper() {}

	public ErrorWrapper(int errorCode, String errorMessage) {
		super();
		this.errorStatus = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * Error code
	 * 
	 * @return errorCode
	 */
	public int getErrorCode() {
		return errorStatus;
	}

	public void setErrorCode(int errorCode) {
		this.errorStatus = errorCode;
	}

	public ErrorWrapper errorCode(int errorCode) {
		this.errorStatus = errorCode;
		return this;
	}

	/**
	 * Error message
	 * 
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ErrorWrapper errorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}
}