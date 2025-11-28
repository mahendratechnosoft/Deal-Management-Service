package com.mahendratechnosoft.crm.dto;

import java.time.LocalDateTime;

import com.mahendratechnosoft.crm.entity.ModuleAccess;

public class AdminInfoDto {
	
	
	private String adminId;
	private String loginEmail;
	private String companyEmail;
	private String name;
	private String phone;
	private String address;
	private String companyName;
    private String gstNumber;
    private String password;
    private LocalDateTime expiryDate;
    private ModuleAccess moduleAccess;

    
    
	public AdminInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AdminInfoDto(String adminId, String loginEmail, String companyEmail, String name, String phone,
			String address, String companyName, String gstNumber) {
		super();
		this.adminId = adminId;
		this.loginEmail = loginEmail;
		this.companyEmail = companyEmail;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.companyName = companyName;
		this.gstNumber = gstNumber;
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
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
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
	public String getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}
	public ModuleAccess getModuleAccess() {
		return moduleAccess;
	}
	public void setModuleAccess(ModuleAccess moduleAccess) {
		this.moduleAccess = moduleAccess;
	}

    
    
    
    
    

}
