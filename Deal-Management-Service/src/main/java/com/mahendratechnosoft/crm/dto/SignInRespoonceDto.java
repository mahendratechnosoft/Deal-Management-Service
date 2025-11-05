package com.mahendratechnosoft.crm.dto;

import java.time.LocalDateTime;

public class SignInRespoonceDto {
	
	private String jwtToken;
	private String userId;
	private String loginEmail;
	private String role;
	private LocalDateTime expiryDate;
	private String loginUserName;
	public SignInRespoonceDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SignInRespoonceDto(String jwtToken, String userId, String loginEmail, String role,
			LocalDateTime expiryDate,String loginUserName ) {
		super();
		this.jwtToken = jwtToken;
		this.userId = userId;
		this.loginEmail = loginEmail;
		this.role = role;
		this.expiryDate = expiryDate;
		this.loginUserName=loginUserName;
	}
	
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
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

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	@Override
	public String toString() {
		return "SignInRespoonceDto [jwtToken=" + jwtToken + ", userId=" + userId + ", loginEmail=" + loginEmail
				+ ", role=" + role + ", expiryDate=" + expiryDate + ", loginUserName=" + loginUserName + "]";
	}
	
	
	
}
