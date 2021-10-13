package com.spring.firebase.authentication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.firebase.authentication.exception.TokenException;
import com.spring.firebase.authentication.handler.AppExceptionHandler;
import com.spring.firebase.authentication.token.FirebaseAuthenticationToken;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	private AuthenticationManager authenticationManager;
	private AppExceptionHandler appExceptionHandler;
	
	public SecurityFilter(AuthenticationManager authenticationManager,AppExceptionHandler appExceptionHandler) {
		this.authenticationManager = authenticationManager;
		this.appExceptionHandler =appExceptionHandler;
	}
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
		try {
		String authorization = request.getHeader("Authorization");
		String idToken =  authorization.replace("Bearer ", "");
			Authentication authentication =  authenticationManager.authenticate(new FirebaseAuthenticationToken(idToken));
			if(authentication!=null) {
				SecurityContextHolder.getContext().setAuthentication(new FirebaseAuthenticationToken(idToken));		
				filterChain.doFilter(request, response);	
			}		
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		}catch (TokenException e) {
			this.appExceptionHandler.handleInvalidToken(request, response, e);
		}
		
	}

}
