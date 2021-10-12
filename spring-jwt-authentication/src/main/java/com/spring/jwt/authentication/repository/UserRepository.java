package com.spring.jwt.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.jwt.authentication.entity.UserEntity;

@Repository
public interface UserRepository extends  JpaRepository<UserEntity, Long>{
	
	@Query(value = " SELECT u FROM UserEntity u WHERE u.userName = ?1 ")
	UserEntity findByUserName(String userName);

}
