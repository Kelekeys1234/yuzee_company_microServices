package com.yuzee.company.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
    	StringBuilder message = new StringBuilder();
         for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
             message.append(cv.getPropertyPath() + " " + cv.getMessage() + "\n");
         }
    	ErrorWrapper errorWrapper = new ErrorWrapper();
    	log.error("Exception occured {}",exception);
		errorWrapper.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorWrapper.setErrorMessage(message.toString());
		return Response.status(HttpStatus.BAD_REQUEST.value()).entity(errorWrapper).build();
    }

}
