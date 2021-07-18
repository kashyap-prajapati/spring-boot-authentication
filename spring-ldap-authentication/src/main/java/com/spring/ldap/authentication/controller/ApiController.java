package com.spring.ldap.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	@GetMapping("/")
	public String index() {
		return "Index";
	}
	
	@GetMapping("/success")
	public String success() {
		return "Success";
	}
}
