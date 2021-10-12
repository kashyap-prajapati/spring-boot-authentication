package com.spring.jwt.authentication.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.authentication.bean.ErrorResponse;
import com.spring.jwt.authentication.util.JwtTokenUtil;


/**
 * Kashyap Prajapati
 * @author Admin
 *
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired private JwtTokenUtil jwtTokenUtil;
	@Autowired private AuthenticationManager authenticationManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			
		if(postToAuthenticate(request)) {
			 JSONParser parser =  new JSONParser(getRequestBody(request));
			 try {
				 Map<String,Object> map = parser.parseObject();
				 checkAuthentication(response, map.get("username").toString(), map.get("password").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			 return;
		}
	
		
		final String requestTokenHeader = request.getHeader("Authorization");
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			String jwtToken = requestTokenHeader.substring(7);
			String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			if(username!=null && jwtTokenUtil.validateToken(jwtToken, username)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		} else {
			ErrorResponse errorResponse = new ErrorResponse("Authorization Token Not Fount","401");
			ObjectMapper objectMapper = new ObjectMapper();
			response.getWriter().print(objectMapper.writeValueAsString(errorResponse));
			response.setStatus(401);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			return;
		}
			
		filterChain.doFilter(request, response);
	}
	
	private boolean postToAuthenticate(HttpServletRequest request) {
		return request.getRequestURL().toString().endsWith("/authenticate") &&  request.getMethod().equalsIgnoreCase("POST"); 
	}
	
	private void checkAuthentication(HttpServletResponse response, String username, String password) throws IOException {
		UsernamePasswordAuthenticationToken auth =  new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication =  tryToAuthenticate(auth);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		JSONObject tokenJson =  new JSONObject();
		tokenJson.put("token", jwtTokenUtil.generateToken(username));
		response.setContentType("application/json");
		response.getWriter().print(tokenJson);
	}
	

    private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Invalid Credentials Found !!");
        }
        return responseAuthentication;
    }
    
    
    private String getRequestBody(HttpServletRequest request) throws IOException {
		 InputStream is = request.getInputStream();
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();

		 byte buf[] = new byte[1024];
		 int letti;

		 while ((letti = is.read(buf)) > 0)
			 baos.write(buf, 0, letti);

		 return new String(baos.toByteArray());
		 
    }
	

}
