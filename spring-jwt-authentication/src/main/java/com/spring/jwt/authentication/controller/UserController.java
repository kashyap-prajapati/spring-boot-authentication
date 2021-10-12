package com.spring.jwt.authentication.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jwt.authentication.bean.User;
import com.spring.jwt.authentication.entity.UserEntity;
import com.spring.jwt.authentication.service.UserService;

@RestController
public class UserController {

	@Autowired private UserService userService;
	
	@PostMapping("/post-user")
	public ResponseEntity<Object> postUser(@RequestBody User user){
		JSONObject json = new JSONObject();
		UserEntity entity = userService.insertUser(user);
		if(entity.getUserId()>0) {
			return new ResponseEntity<>(entity,HttpStatus.OK);
		}else {
			json.put("msg", "User Creation failed");
			return new ResponseEntity<>(json,HttpStatus.OK);
		}
		
	}
	
	
	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}
	
	
}
