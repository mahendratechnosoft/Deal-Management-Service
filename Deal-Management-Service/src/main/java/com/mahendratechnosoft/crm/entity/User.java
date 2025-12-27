package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;
import org.hibernate.annotations.UuidGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String userId;
	@Column(unique = true, nullable = false)
	private String loginEmail;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String role;
	private LocalDateTime expiryDate;
	@Column(columnDefinition = "boolean default true") 
    private boolean isActive = true;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userId, String loginEmail, String password, String role, LocalDateTime expiryDate) {
		super();
		this.userId = userId;
		this.loginEmail = loginEmail;
		this.password = password;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
