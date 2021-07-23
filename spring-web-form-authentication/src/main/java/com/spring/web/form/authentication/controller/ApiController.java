package com.spring.web.form.authentication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * @author KASHYAP PRAJAPATI
 *
 */
@Controller
public class ApiController {



	    @GetMapping(value = {"/home","/"})
	    public String home() {
	        return "/home";
	    }

	    @GetMapping("/admin")
	    public String admin() {
	        return "/admin";
	    }

	    @GetMapping("/user")
	    public String user() {
	        return "/user";
	    }

	    @GetMapping("/about")
	    public String about() {
	        return "/about";
	    }

	    @GetMapping("/login")
	    public String login() {
	        return "/login";
	    }

	    @GetMapping("/error")
	    public String error() {
	        return "/error";
	    }

}
