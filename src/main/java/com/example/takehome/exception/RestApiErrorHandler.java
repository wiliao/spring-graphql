package com.example.takehome.exception;

import java.time.Instant;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @Author: William Liao
 * @Email: liaotoca@yahoo.com
 */
@ControllerAdvice
public class RestApiErrorHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Error> handleException(HttpServletRequest request, Exception ex, Locale locale) {
		Error error = ErrorUtils
				.createError(ErrorCode.GENERIC_ERROR.getErrMsgKey(), ErrorCode.GENERIC_ERROR.getErrCode(),
						HttpStatus.INTERNAL_SERVER_ERROR.value())
				.setUrl(request.getRequestURL().toString()).setReqMethod(request.getMethod())
				.setTimestamp(Instant.now());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { JsonParseException.class, JsonProcessingException.class })
	public ResponseEntity<Error> handleJsonParseException(HttpServletRequest request, JsonParseException ex,
			Locale locale) {
		Error error = ErrorUtils
				.createError(ErrorCode.JSON_PARSE_ERROR.getErrMsgKey(), ErrorCode.JSON_PARSE_ERROR.getErrCode(),
						HttpStatus.NOT_ACCEPTABLE.value())
				.setUrl(request.getRequestURL().toString()).setReqMethod(request.getMethod())
				.setTimestamp(Instant.now());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Error> handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
			HttpRequestMethodNotSupportedException ex, Locale locale) {
		Error error = ErrorUtils
				.createError(ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getErrMsgKey(),
						ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getErrCode(), HttpStatus.NOT_IMPLEMENTED.value())
				.setUrl(request.getRequestURL().toString()).setReqMethod(request.getMethod())
				.setTimestamp(Instant.now());
		return new ResponseEntity<>(error, HttpStatus.NOT_IMPLEMENTED);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Error> handleResourceNotFoundException(HttpServletRequest request,
			ResourceNotFoundException ex, Locale locale) {
		Error error = ErrorUtils
				.createError(String.format("%s %s", ErrorCode.RESOURCE_NOT_FOUND.getErrMsgKey(), ex.getMessage()),
						ex.getErrorCode(), HttpStatus.NOT_FOUND.value())
				.setUrl(request.getRequestURL().toString()).setReqMethod(request.getMethod())
				.setTimestamp(Instant.now());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Error> handleHIllegalArgumentException(
			HttpServletRequest request,
			IllegalArgumentException ex,
			Locale locale) {
		Error error = ErrorUtils
				.createError(String
								.format("%s %s", ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getErrMsgKey(), ex.getMessage()),
						ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getErrCode(),
						HttpStatus.BAD_REQUEST.value()).setUrl(request.getRequestURL().toString())
				.setReqMethod(request.getMethod())
				.setTimestamp(Instant.now());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
