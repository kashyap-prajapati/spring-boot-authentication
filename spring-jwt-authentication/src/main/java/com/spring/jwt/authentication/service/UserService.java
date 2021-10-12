package com.spring.jwt.authentication.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.jwt.authentication.bean.User;
import com.spring.jwt.authentication.dao.UserDao;
import com.spring.jwt.authentication.entity.UserEntity;

@Service
public class UserService {
	
	@Autowired private UserDao userDao;
	@Autowired private PasswordEncoder passwordEncoder;
	
	
	public UserEntity insertUser(User user) {
		UserEntity userEntity =  new UserEntity();
		userEntity.setUserName(user.getUserName());
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
		userEntity.setDateOfBirth(user.getDateOfBirth());
		userEntity.setCreatedOn(new Date());
		return userDao.insertUser(userEntity);
	}

}
