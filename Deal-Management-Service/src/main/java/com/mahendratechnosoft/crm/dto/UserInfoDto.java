package com.mahendratechnosoft.crm.dto;

import java.time.LocalDateTime;

import com.mahendratechnosoft.crm.entity.User;

public class UserInfoDto {
	private String userId;
    private String loginEmail;
    private String role;
    private LocalDateTime expiryDate;

    public UserInfoDto(User user) {
        if (user != null) {
            this.userId = user.getUserId();
            this.loginEmail = user.getLoginEmail();
            this.role = user.getRole();
            this.expiryDate = user.getExpiryDate();
        }
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

	@Override
	public String toString() {
		return "UserInfoDto [userId=" + userId + ", loginEmail=" + loginEmail + ", role=" + role + ", expiryDate="
				+ expiryDate + "]";
	}
    
    
}
