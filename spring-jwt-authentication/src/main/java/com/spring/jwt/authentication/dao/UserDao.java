package com.spring.jwt.authentication.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.jwt.authentication.entity.UserEntity;
import com.spring.jwt.authentication.repository.UserRepository;

@Repository
public class UserDao {
	
	@Autowired private UserRepository userRepository;
	
	public UserEntity insertUser(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}
}
