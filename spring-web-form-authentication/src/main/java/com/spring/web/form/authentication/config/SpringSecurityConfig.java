package com.spring.web.form.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.web.form.authentication.handler.WebAccessDeniedHandler;


/**
 * 
 * @author KASHYAP
 *
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private WebAccessDeniedHandler webAccessDeniedHandler;

	
	/**
	 *  Disable CSRF.
	 *  Request Mapping ["/","/home","about"] accessed by any user and no authentication required..
	 *  Request Mapping ["/admin"] accessed by the user who has a role of admin.
	 *  Request Mapping ["/user"] accessed by the user who has a role of user.
	 *  Login Page URL ["/login"]
	 *  Default Success URL ["/home"]
	 *  Access Denied exception handle by MyAccessDeniedHandler 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/","/home","/about").permitAll()
		.antMatchers("/admin/**").hasAnyRole("ADMIN")
		.antMatchers("/user/**").hasAnyRole("USER")
		.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/home")
			.permitAll()
			.and()
		.logout()
			.permitAll()
			.and()
		.exceptionHandling().accessDeniedHandler(webAccessDeniedHandler);
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	            .ignoring()
	            .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**");
	}

	
	
	/**
	 * Create Two user in Memory
	 * 1. username: user , password: user and ROLE: "USER"
	 * 2. username: admin, password: admin  and ROLE: "ADMIN"
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("user").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
    }
	
	
	 
	/**
	 * Spring Security Doesn't work with plain password by default.
	 * So we have implement password encoder interface in such way that it will work with plain password.
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
