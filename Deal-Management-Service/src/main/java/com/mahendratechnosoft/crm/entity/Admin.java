package com.mahendratechnosoft.crm.entity;


import java.util.Arrays;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
@Entity
public class Admin {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String adminId;
	private String userId;
	private String loginEmail;
	private String name;
	private String phone;
	private String address;
	private String companyName;
	
	@Column(length = 500)
	private String description;
	
	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] logo;
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(String adminId, String userId, String loginEmail, String name, String phone, String address,
			String companyName, String description, byte[] logo) {
		super();
		this.adminId = adminId;
		this.userId = userId;
		this.loginEmail = loginEmail;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.companyName = companyName;
		this.description = description;
		this.logo = logo;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
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

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", userId=" + userId + ", loginEmail=" + loginEmail + ", name=" + name
				+ ", phone=" + phone + ", address=" + address + ", companyName=" + companyName + ", description="
				+ description + ", logo=" + Arrays.toString(logo) + "]";
	}

}
