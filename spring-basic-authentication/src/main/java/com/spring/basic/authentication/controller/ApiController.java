package com.spring.basic.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	@GetMapping("/home")
	public String getHome() {
		return "Successfully authenticated";
	}

}
