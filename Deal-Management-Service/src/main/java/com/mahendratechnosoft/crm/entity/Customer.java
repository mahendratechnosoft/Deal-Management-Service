package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String customerId;
	private String adminId;
	private String employeeId;
	private String companyName;
	private String phone;
	private String mobile;
    private String email;
	private String website;
	private String industry;
	private String revenue;
	private String gstin;
	private String panNumber;
	private String billingStreet;
	private String billingCity;
	private String billingState;
	private String billingCountry;
	private String billingZipCode;
	private String shippingStreet;
	private String shippingCity;
	private String shippingState;
	private String shippingCountry;
	private String shippingZipCode;
	private String description;
	private boolean status;
	private String userId;
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Customer(String customerId, String adminId, String employeeId, String phone, String mobile, String website,
			String industry, String revenue, String billingStreet, String billingCity, String billingState,
			String billingCountry, String billingZipCode, String shippingStreet, String shippingCity,
			String shippingState, String shippingCountry, String shippingZipCode, String description) {
		super();
		this.customerId = customerId;
		this.adminId = adminId;
		this.employeeId = employeeId;
		this.phone = phone;
		this.mobile = mobile;
		this.website = website;
		this.industry = industry;
		this.revenue = revenue;
		this.billingStreet = billingStreet;
		this.billingCity = billingCity;
		this.billingState = billingState;
		this.billingCountry = billingCountry;
		this.billingZipCode = billingZipCode;
		this.shippingStreet = shippingStreet;
		this.shippingCity = shippingCity;
		this.shippingState = shippingState;
		this.shippingCountry = shippingCountry;
		this.shippingZipCode = shippingZipCode;
		this.description = description;
	}



	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getRevenue() {
		return revenue;
	}
	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}
	public String getBillingStreet() {
		return billingStreet;
	}
	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	public String getBillingState() {
		return billingState;
	}
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	public String getBillingCountry() {
		return billingCountry;
	}
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}
	public String getBillingZipCode() {
		return billingZipCode;
	}
	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}
	public String getShippingStreet() {
		return shippingStreet;
	}
	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}
	public String getShippingCity() {
		return shippingCity;
	}
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	public String getShippingState() {
		return shippingState;
	}
	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}
	public String getShippingCountry() {
		return shippingCountry;
	}
	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}
	public String getShippingZipCode() {
		return shippingZipCode;
	}
	public void setShippingZipCode(String shippingZipCode) {
		this.shippingZipCode = shippingZipCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
