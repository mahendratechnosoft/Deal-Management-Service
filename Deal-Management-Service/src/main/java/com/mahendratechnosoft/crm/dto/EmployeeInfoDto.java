package com.mahendratechnosoft.crm.dto;

import java.util.Arrays;

import com.mahendratechnosoft.crm.entity.Employee;

public class EmployeeInfoDto {
	// 1. Employee's own details
    private String employeeId;
    private String name;
    private String phone;
    private String address;
    private String gender;
    private String description;
    private byte[] profileImage;
    
    // 2. Employee's User details
    private UserInfoDto user;
    
    // 3. Employee's Admin details
    private AdminInfoDto admin;

    // Constructor to map from the entity
    public EmployeeInfoDto(Employee employee) {
        // Map Employee fields
        this.employeeId = employee.getEmployeeId();
        this.name = employee.getName();
        this.phone = employee.getPhone();
        this.address = employee.getAddress();
        this.gender = employee.getGender();
        this.description = employee.getDescription();
        this.profileImage = employee.getProfileImage();
        
        // Map related User (if it exists)
        if (employee.getUser() != null) {
            this.user = new UserInfoDto(employee.getUser());
        }
        
        // Map related Admin (if it exists)
        // This re-uses your existing DTO!
        if (employee.getAdmin() != null) {
            this.admin = new AdminInfoDto(employee.getAdmin());
        }
    }

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public UserInfoDto getUser() {
		return user;
	}

	public void setUser(UserInfoDto user) {
		this.user = user;
	}

	public AdminInfoDto getAdmin() {
		return admin;
	}

	public void setAdmin(AdminInfoDto admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "EmployeeInfoDto [employeeId=" + employeeId + ", name=" + name + ", phone=" + phone + ", address="
				+ address + ", gender=" + gender + ", description=" + description + ", profileImage="
				+ Arrays.toString(profileImage) + ", user=" + user + ", admin=" + admin + "]";
	}
    
    
}
