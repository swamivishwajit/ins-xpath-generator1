package com.xpath.insxpathgenerator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> getErrorMessage(Exception e) {
		return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
