package com.spring.firebase.authentication.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.firebase.authentication.exception.TokenException;
import com.spring.firebase.authentication.model.ErrorResponse;

@Component
public class AppExceptionHandler {
	
	@Autowired ObjectMapper mapper;

	public void handleInvalidToken(HttpServletRequest request,HttpServletResponse response, TokenException ex) throws IOException{
		ErrorResponse errorResponse =  new ErrorResponse(401, ex.getMessage());
		response.getWriter().print(mapper.writeValueAsString(errorResponse));
		response.setStatus(401);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	}

}
