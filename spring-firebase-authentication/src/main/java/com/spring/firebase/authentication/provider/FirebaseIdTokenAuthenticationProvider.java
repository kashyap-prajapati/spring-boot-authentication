package com.spring.firebase.authentication.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.spring.firebase.authentication.exception.TokenException;
import com.spring.firebase.authentication.token.FirebaseAuthenticationToken;

@Component
public class FirebaseIdTokenAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		FirebaseAuthenticationToken token = (FirebaseAuthenticationToken )authentication;
		try {
			FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token.getIdToken(),true);
			String uid = firebaseToken.getUid();
			UserRecord userRecord =  FirebaseAuth.getInstance().getUser(uid);
			return new UsernamePasswordAuthenticationToken(userRecord.getUid(),null);
		}
		catch (FirebaseAuthException e) {
			if(e.getAuthErrorCode().equals("id-token-revoked")) {
				throw new TokenException("User token has been revoked, Please sign in again !");
			}else {
				throw new TokenException("Authentication Failed");
			}
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.isAssignableFrom(FirebaseAuthenticationToken.class);
	}

}
