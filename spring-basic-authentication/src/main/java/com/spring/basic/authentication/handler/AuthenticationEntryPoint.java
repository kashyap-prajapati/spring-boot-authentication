package com.spring.basic.authentication.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;


/**
 * 
 * @author Kashyap Prajapati
 * If Authentication fails controls goes to Authentication Entry Point and return response to client.
 */
@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint{

	@Override
	public void afterPropertiesSet() {
		setRealmName("admin");
		super.afterPropertiesSet();
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		response.addHeader("Authorization", "Basic "+getRealmName());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter writer = response.getWriter();
		writer.print("401 - UNAUTHORIZED");
	}

	
}
