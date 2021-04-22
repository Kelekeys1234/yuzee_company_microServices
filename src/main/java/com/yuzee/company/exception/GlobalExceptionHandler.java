package com.yuzee.company.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {
	

	@Override
	public Response toResponse(Exception exception) {
		ErrorWrapper errorWrapper = new ErrorWrapper();
		HttpStatus status = null;
		if (exception instanceof NotFoundException) {
			log.error("Exception occured {}",exception);
			status = HttpStatus.NOT_FOUND;
			errorWrapper.setErrorCode(status.value());
			errorWrapper.setErrorMessage(exception.getMessage());
		} else if (exception instanceof BadRequestException) {
			log.error("Exception occured {}",exception);
			status = HttpStatus.BAD_REQUEST;
			errorWrapper.setErrorCode(status.value());
			errorWrapper.setErrorMessage(exception.getMessage());
		} else {
			log.error("Exception occured {}",exception);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			errorWrapper.setErrorCode(status.value());
			errorWrapper.setErrorMessage(exception.getMessage() != null ? exception.getMessage() : "Internal Server Error" );
		}
		return Response.status(status.value()).entity(errorWrapper).build();
	}
}
