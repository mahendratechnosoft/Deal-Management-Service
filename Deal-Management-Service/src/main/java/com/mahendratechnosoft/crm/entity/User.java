package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class User {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String userId;
	private String loginEmail;
	private String password;
	@Column(length = 500)
	private String description;
	private String role;
	private LocalDateTime expiryDate;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userId, String loginEmail, String password, String description, String role,
			LocalDateTime expiryDate) {
		super();
		this.userId = userId;
		this.loginEmail = loginEmail;
		this.password = password;
		this.description = description;
		this.role = role;
		this.expiryDate = expiryDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", loginEmail=" + loginEmail + ", password=" + password + ", description="
				+ description + ", role=" + role + ", expiryDate=" + expiryDate + "]";
	}
}
