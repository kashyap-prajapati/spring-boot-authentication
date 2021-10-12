package com.spring.jwt.authentication.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name = "user_register")
public class UserEntity {

	@Id // Specify The Primary key of Table
	@Column(name = "userid") // Name of Columns
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
	private Long userId;
	
	@Column(name = "username")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "dob")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonFormat(shape = Shape.STRING,pattern = "dd-MM-yyyy")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@JsonFormat(shape = Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	
}
