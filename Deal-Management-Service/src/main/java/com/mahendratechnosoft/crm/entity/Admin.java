package com.mahendratechnosoft.crm.entity;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
@Entity
public class Admin {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String adminId;
//	private String userId;
	private String loginEmail;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "userId", unique = true)
	private User user;
	
	private String name;
	private String phone;
	private String address;
	private String companyName;
	
	@Column(length = 500)
	private String description;
	
	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] logo;
	
	@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Employee> employees = new HashSet<>();
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Admin(String adminId, String loginEmail, User user, String name, String phone, String address,
			String companyName, String description, byte[] logo, Set<Employee> employees) {
		super();
		this.adminId = adminId;
		this.loginEmail = loginEmail;
		this.user = user;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.companyName = companyName;
		this.description = description;
		this.logo = logo;
		this.employees = employees;
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

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	
	public String getLoginEmail() {
		return loginEmail;
	}


	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", loginEmail=" + loginEmail + ", user=" + user + ", name=" + name
				+ ", phone=" + phone + ", address=" + address + ", companyName=" + companyName + ", description="
				+ description + ", logo=" + Arrays.toString(logo) + ", employees=" + employees + "]";
	}


}
