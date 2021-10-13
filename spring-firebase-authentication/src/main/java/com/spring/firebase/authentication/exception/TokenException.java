package com.spring.firebase.authentication.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2871443314673401611L;
	
	private String errorMsg;
	private int errorCode;
	
	public TokenException(String errorMsg) {
		super(errorMsg);
		this.errorMsg=errorMsg;
	}
	
	public TokenException(int errorCode, String errorMsg) {
		super("Error Code "+errorCode+ " Error Message : "+errorMsg);
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
	}

}
