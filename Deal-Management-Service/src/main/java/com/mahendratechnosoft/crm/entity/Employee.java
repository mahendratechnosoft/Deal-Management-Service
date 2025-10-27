package com.mahendratechnosoft.crm.entity;


import java.util.Arrays;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
@Entity
public class Employee {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String employeeId;
//	private String userId;
//	private String adminId;
//	private String loginEmail;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "userId", unique = true)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id", referencedColumnName = "adminId")
	private Admin admin;
	
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

	

	public Employee(String employeeId, User user, Admin admin, String name, String phone, String address, String gender,
			String description, byte[] profileImage) {
		super();
		this.employeeId = employeeId;
		this.user = user;
		this.admin = admin;
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



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Admin getAdmin() {
		return admin;
	}



	public void setAdmin(Admin admin) {
		this.admin = admin;
	}



	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", user=" + user + ", admin=" + admin + ", name=" + name
				+ ", phone=" + phone + ", address=" + address + ", gender=" + gender + ", description=" + description
				+ ", profileImage=" + Arrays.toString(profileImage) + "]";
	}

	

	
}
