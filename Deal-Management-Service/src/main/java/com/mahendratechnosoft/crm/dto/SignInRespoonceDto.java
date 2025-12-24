package com.mahendratechnosoft.crm.dto;

import java.time.LocalDateTime;

import com.mahendratechnosoft.crm.entity.ModuleAccess;

public class SignInRespoonceDto {
	
	private String jwtToken;
	private String userId;
	private String loginEmail;
	private String role;
	private LocalDateTime expiryDate;
	private String loginUserName;
	private String employeeId;
	private String adminId;
	private String customerId;
	private byte[] logo;
	private ModuleAccess moduleAccess;
	public SignInRespoonceDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SignInRespoonceDto(String jwtToken, String userId, String loginEmail, String role,
			LocalDateTime expiryDate,String loginUserName,String employeeId,String adminId ,ModuleAccess moduleAccess,String customerId,byte[] logo) {
		super();
		this.jwtToken = jwtToken;
		this.userId = userId;
		this.loginEmail = loginEmail;
		this.role = role;
		this.expiryDate = expiryDate;
		this.loginUserName=loginUserName;
		this.employeeId=employeeId;
		this.adminId=adminId;
		this.moduleAccess=moduleAccess;
		this.customerId = customerId;
		this.logo = logo;
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
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	
	public ModuleAccess getModuleAccess() {
		return moduleAccess;
	}

	public void setModuleAccess(ModuleAccess moduleAccess) {
		this.moduleAccess = moduleAccess;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "SignInRespoonceDto [jwtToken=" + jwtToken + ", userId=" + userId + ", loginEmail=" + loginEmail
				+ ", role=" + role + ", expiryDate=" + expiryDate + ", loginUserName=" + loginUserName + "]";
	}
	
}
