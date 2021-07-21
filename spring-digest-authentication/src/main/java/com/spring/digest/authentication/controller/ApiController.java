package com.spring.digest.authentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author KASHYAP PRAJAPATI
 *
 */
@RestController
public class ApiController {

	@RequestMapping(value = "/secure")
    public String secure() {
        return "You are authorize to access this page. This is secure page. ";
    }
	
	@RequestMapping(value = "/success")
    public String success() {
        return "You are authorize to access this page. This is success page. ";
    }
	
    @RequestMapping(value = "/home")
    public String home() {
        return "This is public page. No need of authentication";
    }	
}
