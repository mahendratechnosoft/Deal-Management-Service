package com.mahendratechnosoft.crm.dto;

import java.time.LocalDateTime;
import java.util.Base64;

import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.User;

public class AdminInfoDto {
	// Fields from Admin
    private String adminId;
    private String name;
    private String phone;
    private String address;
    private String companyName;
    private String description;
    private String logoBase64;

    // Fields from User
    private String userId;
    private String loginEmail;
    private String role;
    private LocalDateTime expiryDate;

    // Constructor to map from the entity
    public AdminInfoDto(Admin admin) {
        // Map Admin fields
        this.adminId = admin.getAdminId();
        this.name = admin.getName();
        this.phone = admin.getPhone();
        this.address = admin.getAddress();
        this.companyName = admin.getCompanyName();
        this.description = admin.getDescription();
        if (admin.getLogo() != null) {
            this.logoBase64 = Base64.getEncoder().encodeToString(admin.getLogo());
        }

        // Map User fields
        User user = admin.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.loginEmail = user.getLoginEmail();
            this.role = user.getRole();
            this.expiryDate = user.getExpiryDate();
        }
    }

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getLogoBase64() {
		return logoBase64;
	}

	public void setLogoBase64(String logoBase64) {
		this.logoBase64 = logoBase64;
	}

	@Override
	public String toString() {
		return "AdminInfoDto [adminId=" + adminId + ", name=" + name + ", phone=" + phone + ", address=" + address
				+ ", companyName=" + companyName + ", description=" + description + ", logoBase64=" + logoBase64
				+ ", userId=" + userId + ", loginEmail=" + loginEmail + ", role=" + role + ", expiryDate=" + expiryDate
				+ "]";
	}

	 
}
