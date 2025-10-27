package com.mahendratechnosoft.crm.entity;


import java.util.Arrays;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
@Entity
public class Employee {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String employeeId;
	private String userId;
	private String adminId;
	private String loginEmail;
	private String name;
	private String phone;
	private String address;
	private String gender;
	
	@Column(length = 500)
	private String description;
	
	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] profileImage;
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(String employeeId, String userId, String adminId, String loginEmail, String name, String phone,
			String address, String gender, String description, byte[] profileImage) {
		super();
		this.employeeId = employeeId;
		this.userId = userId;
		this.adminId = adminId;
		this.loginEmail = loginEmail;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.description = description;
		this.profileImage = profileImage;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", userId=" + userId + ", adminId=" + adminId + ", loginEmail="
				+ loginEmail + ", name=" + name + ", phone=" + phone + ", address=" + address + ", gender=" + gender
				+ ", description=" + description + ", profileImage=" + Arrays.toString(profileImage) + "]";
	}

	
}
