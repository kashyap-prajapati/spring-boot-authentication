package com.spring.jwt.authentication.entrypoint;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.authentication.bean.ErrorResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3695243399735097551L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		ErrorResponse errorRes =  new ErrorResponse(authException.toString(), "401");
		response.setStatus(401);
		response.getWriter().print(mapper.writeValueAsString(errorRes));
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	}	

}
