package com.mahendratechnosoft.crm.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.annotations.UuidGenerator;

@Entity
public class Vendor {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String vendorId;
	private String adminId;
	private String employeeId;

    private String vendorCode;
    private int vendorCodeNumber;
    private String vendorName;
    private String companyName;
    private String emailAddress;
    private String phone;
    private Double balance;
    private String pan;
    private String gstNumber;
    private Boolean isMSMERegister;
    private String udyamRegistrationType;
    private String udyamRegistrationNumber;
    @Column(length = 500)
    private String remark;
    private String accountHolderName;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String street;
    private String country;
    private String state;
    private String city;
    private String zipCode;
    private LocalDateTime createdAt;
	private String createdBy;
    
    @PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	}
    

	public Vendor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vendor(String vendorId, String vendorCode, String vendorName, String companyName, String emailAddress,
			String phone, Double balance, String pan, Boolean isMSMERegister, String udyamRegistrationType,
			String udyamRegistrationNumber, String remark, String accountHolderName, String bankName,
			String accountNumber, String ifscCode, String street, String country, String state, String city,
			String zipCode, LocalDateTime createdAt, String createdBy) {
		super();
		this.vendorId = vendorId;
		this.vendorCode = vendorCode;
		this.vendorName = vendorName;
		this.companyName = companyName;
		this.emailAddress = emailAddress;
		this.phone = phone;
		this.balance = balance;
		this.pan = pan;
		this.isMSMERegister = isMSMERegister;
		this.udyamRegistrationType = udyamRegistrationType;
		this.udyamRegistrationNumber = udyamRegistrationNumber;
		this.remark = remark;
		this.accountHolderName = accountHolderName;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.ifscCode = ifscCode;
		this.street = street;
		this.country = country;
		this.state = state;
		this.city = city;
		this.zipCode = zipCode;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
	}


	public String getVendorId() {
		return vendorId;
	}


	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	
	
	public String getAdminId() {
		return adminId;
	}


	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}


	public String getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}


	public String getVendorCode() {
		return vendorCode;
	}


	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}


	public String getVendorName() {
		return vendorName;
	}


	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Double getBalance() {
		return balance;
	}


	public void setBalance(Double balance) {
		this.balance = balance;
	}


	public String getPan() {
		return pan;
	}


	public void setPan(String pan) {
		this.pan = pan;
	}


	public Boolean getIsMSMERegister() {
		return isMSMERegister;
	}


	public void setIsMSMERegister(Boolean isMSMERegister) {
		this.isMSMERegister = isMSMERegister;
	}


	public String getUdyamRegistrationType() {
		return udyamRegistrationType;
	}


	public void setUdyamRegistrationType(String udyamRegistrationType) {
		this.udyamRegistrationType = udyamRegistrationType;
	}


	public String getUdyamRegistrationNumber() {
		return udyamRegistrationNumber;
	}


	public void setUdyamRegistrationNumber(String udyamRegistrationNumber) {
		this.udyamRegistrationNumber = udyamRegistrationNumber;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getAccountHolderName() {
		return accountHolderName;
	}


	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public String getIfscCode() {
		return ifscCode;
	}


	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getGstNumber() {
		return gstNumber;
	}


	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}


	public int getVendorCodeNumber() {
		return vendorCodeNumber;
	}


	public void setVendorCodeNumber(int vendorCodeNumber) {
		this.vendorCodeNumber = vendorCodeNumber;
	}
    
	
}