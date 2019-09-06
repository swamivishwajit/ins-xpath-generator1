package com.xpath.insxpathgenerator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.sun.org.apache.xml.internal.utils.WrappedRuntimeException;

@ControllerAdvice
public class ErrorHandler {
	
	
	@ExceptionHandler(WrappedRuntimeException.class)
	public ResponseEntity<String> handleInvalidFileException(WrappedRuntimeException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity <String> handleMissingServletRequestPartException(MissingServletRequestPartException me){
		return new ResponseEntity<>("Please select file", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> getErrorMessage(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
