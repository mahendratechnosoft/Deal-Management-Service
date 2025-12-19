package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class AMC {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String amcId;
	private String adminId;
	private String employeeId;
	private String assingedTo;
	private String clientName;
	private String companyName;
	private String contactPersonName;
	private String email;
	private String phoneNumber;
	private String websiteURL;
	private String technology;
	private String hostingProvider;
	private String domainProvider;
	private String customerId;

	
	public AMC(String amcId, String adminId, String employeeId, String clientName, String companyName,
			String contactPersonName, String email, String phoneNumber, String websiteURL, String technology,
			String hostingProvider, String domainProvider, String assingedTo) {
		super();
		this.amcId = amcId;
		this.adminId = adminId;
		this.employeeId = employeeId;
		this.clientName = clientName;
		this.companyName = companyName;
		this.contactPersonName = contactPersonName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.websiteURL = websiteURL;
		this.technology = technology;
		this.hostingProvider = hostingProvider;
		this.domainProvider = domainProvider;
		this.assingedTo = assingedTo;
	}
	public AMC() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAmcId() {
		return amcId;
	}
	public void setAmcId(String amcId) {
		this.amcId = amcId;
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
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getWebsiteURL() {
		return websiteURL;
	}
	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getHostingProvider() {
		return hostingProvider;
	}
	public void setHostingProvider(String hostingProvider) {
		this.hostingProvider = hostingProvider;
	}
	public String getDomainProvider() {
		return domainProvider;
	}
	public void setDomainProvider(String domainProvider) {
		this.domainProvider = domainProvider;
	}
	public String getAssingedTo() {
		return assingedTo;
	}
	public void setAssingedTo(String assingedTo) {
		this.assingedTo = assingedTo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
