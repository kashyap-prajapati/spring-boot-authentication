package com.spring.jwt.authentication.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.spring.jwt.authentication.bean.ErrorResponse;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AppExceptionHandler {

	
	@ExceptionHandler(value = BadCredentialsException.class)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ResponseEntity<Object> handleBadCredential(AuthenticationException ex){
		ErrorResponse response =  new ErrorResponse("Invalid username and password","909");
		return new ResponseEntity<Object>(response,HttpStatus.OK);
	}


}
