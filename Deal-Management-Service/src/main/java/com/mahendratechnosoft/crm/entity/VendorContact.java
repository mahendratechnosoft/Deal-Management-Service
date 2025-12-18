package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;


@Entity
public class VendorContact {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String vendorContactId;
	private String vendorId;
	private String adminId;
	private String employeeId;
	
	private String contactPersonName;
    private String emailAddress;
    private String phone;
    private String position;
    private Boolean isActive;
    private LocalDateTime createdAt;
	private String createdBy;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	}
    
	public VendorContact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VendorContact(String vendorContactId, String contactPersonName, String emailAddress, String phone,
			String position, Boolean isActive, String vendorId) {
		super();
		this.vendorContactId = vendorContactId;
		this.contactPersonName = contactPersonName;
		this.emailAddress = emailAddress;
		this.phone = phone;
		this.position = position;
		this.isActive = isActive;
		this.vendorId = vendorId;
	}

	public String getVendorContactId() {
		return vendorContactId;
	}

	public void setVendorContactId(String vendorContactId) {
		this.vendorContactId = vendorContactId;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
}
