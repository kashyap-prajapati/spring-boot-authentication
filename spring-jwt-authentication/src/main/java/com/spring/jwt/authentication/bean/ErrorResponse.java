package com.spring.jwt.authentication.bean;

import java.io.Serializable;


public class ErrorResponse implements  Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 924438984149875706L;
	private String errorMessage;
	private String errorCode;
	public ErrorResponse() {
		
	}
	
	public ErrorResponse(String errorMessgae, String errorCode) {
		this.errorMessage=errorMessgae;
		this.errorCode=errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
	
}
