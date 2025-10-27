package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
@Entity
public class User {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String userId;
	private String loginEmail;
	private String password;
	private String role;
	private LocalDateTime expiryDate;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Admin admin;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Employee employee;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userId, String loginEmail, String password, String role, LocalDateTime expiryDate, Admin admin,
			Employee employee) {
		super();
		this.userId = userId;
		this.loginEmail = loginEmail;
		this.password = password;
		this.role = role;
		this.expiryDate = expiryDate;
		this.admin = admin;
		this.employee = employee;
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

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", loginEmail=" + loginEmail + ", password=" + password + ", role=" + role
				+ ", expiryDate=" + expiryDate + ", admin=" + admin + ", employee=" + employee + "]";
	}

}
