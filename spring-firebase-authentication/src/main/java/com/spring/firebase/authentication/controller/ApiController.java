package com.spring.firebase.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

	@GetMapping("/hello")
	public ResponseEntity<Object> sayHello() {
		return new ResponseEntity<Object>("Hello World !!!",HttpStatus.OK);
	}
}
