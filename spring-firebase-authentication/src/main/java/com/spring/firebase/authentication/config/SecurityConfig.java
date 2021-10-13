package com.spring.firebase.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.firebase.authentication.entrypoint.AuthenticationEntryPoint;
import com.spring.firebase.authentication.filter.SecurityFilter;
import com.spring.firebase.authentication.handler.AppExceptionHandler;
import com.spring.firebase.authentication.handler.CustomAuthentionFailureHandler;
import com.spring.firebase.authentication.provider.FirebaseIdTokenAuthenticationProvider;

@EnableWebSecurity
@Component
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final FirebaseIdTokenAuthenticationProvider firebaseIdTokenAuthenticationProvider;
	
	@Autowired private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired private CustomAuthentionFailureHandler customAuthentionFailureHandler;
	@Autowired private AppExceptionHandler appExceptionHandler;
	
	@Autowired
	public SecurityConfig(FirebaseIdTokenAuthenticationProvider firebaseIdTokenAuthenticationProvider) {
		this.firebaseIdTokenAuthenticationProvider=firebaseIdTokenAuthenticationProvider;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new SecurityFilter(authenticationManager(),appExceptionHandler),BasicAuthenticationFilter.class)
		.authorizeRequests()
		.antMatchers("/hello").authenticated()
		.and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(customAuthentionFailureHandler);
		
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(firebaseIdTokenAuthenticationProvider);
	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean 
	public ObjectMapper getMapper() {
		return new ObjectMapper();
	}
	
}
