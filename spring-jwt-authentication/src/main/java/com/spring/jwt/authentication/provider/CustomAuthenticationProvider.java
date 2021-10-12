package com.spring.jwt.authentication.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.spring.jwt.authentication.entity.UserEntity;
import com.spring.jwt.authentication.repository.UserRepository;
import org.springframework.security.core.AuthenticationException;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	
	@Autowired private UserRepository userRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String userName  =  (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		if(validUsernamePassword(userName, password)) {
			return new UsernamePasswordAuthenticationToken(userName,password,AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
		}
		throw  new BadCredentialsException("Invalid Username and Password");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}
	
	
	public boolean validUsernamePassword(String username, String password) {
		UserEntity entity = userRepository.findByUserName(username);
		if(passwordEncoder.matches(password,entity.getPassword())) {
			return true;
		}else {
			return false;
		}
	}

}
	