package com.digitalbooks.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

public class BaseController {

	public BaseController() {
		super();
	}

	@ExceptionHandler(Unauthorized.class)
	String handleException(Unauthorized ex) {
		return ex.getMessage();
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	void handleOrderNotFound(Exception ex) {

	}
}