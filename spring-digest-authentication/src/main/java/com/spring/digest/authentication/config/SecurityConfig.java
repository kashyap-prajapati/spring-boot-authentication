package com.spring.digest.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

/**
 * 
 * @author Admin
 *
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilter(digestAuthenticationFilter())
		.exceptionHandling().authenticationEntryPoint(digestEntryPoint())
		.and()
		.httpBasic()
		.and()
		.authorizeRequests()
		.antMatchers("/home").permitAll()
		.anyRequest().authenticated();
	}

	
	DigestAuthenticationFilter digestAuthenticationFilter() {
		DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
		digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
		digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
		return digestAuthenticationFilter;
	}
	
	@Bean
	public UserDetailsService userDetailsServiceBean() {
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		inMemoryUserDetailsManager.createUser(User.withUsername("admin").password("admin").roles("ADMIN").build());
		return inMemoryUserDetailsManager;
	}
	
	@Bean
	DigestAuthenticationEntryPoint digestEntryPoint() {
		DigestAuthenticationEntryPoint bauth = new DigestAuthenticationEntryPoint();
		bauth.setRealmName("ADMIN");
		bauth.setKey("MySecretKey");
		bauth.setNonceValiditySeconds(10);
		return bauth;
	}

	
	
	/**
	 * SPRING DIGEST AUTHENTICATION DOESN'T WORK WITH ENCRYPTED PASSWORD.
	 * AND SPRING DOES NOT ALLOW PASSWORD TO PLAIN.
	 * SO WE WORK AROUND PASSWORD ENCODER INTERFACE AND IMPLEMENT IT.
	 * THIS PASSWORD ENCODER RETURN PLAIN PASSWORD.
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new PasswordEncoder() {
	        @Override
	        public String encode(CharSequence rawPassword) {
	            return rawPassword.toString();
	        }
	        @Override
	        public boolean matches(CharSequence rawPassword, String encodedPassword) {
	            return rawPassword.toString().equals(encodedPassword);
	        }
	    };
	}
}
