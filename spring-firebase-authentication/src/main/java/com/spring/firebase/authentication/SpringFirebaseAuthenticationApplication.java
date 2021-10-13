package com.spring.firebase.authentication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class SpringFirebaseAuthenticationApplication {
	

	private static final Logger logger = LoggerFactory.getLogger(SpringFirebaseAuthenticationApplication.class);
	public static void main(String[] args) throws IOException {
		
		
		SpringApplication.run(SpringFirebaseAuthenticationApplication.class, args);
		ClassLoader classLoader = SpringFirebaseAuthenticationApplication.class.getClassLoader();
		
		File file = new File(classLoader.getResource("serviceAccountKey.json").getFile());
		FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
		
		FirebaseOptions options = new FirebaseOptions.Builder()
				  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				  .setDatabaseUrl("https://fir-authentication-db.firebaseio.com")
				  .build();

				FirebaseApp.initializeApp(options);
				
		logger.info("Instance -->" +FirebaseApp.getInstance().getName());
	}

}
