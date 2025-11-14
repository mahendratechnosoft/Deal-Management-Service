package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Leads {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String id;
	private String adminId;
	private String employeeId;
	private String assignTo;
	private String status;
	private String source;
	private String clientName;
	private String companyName;
	private String customerId;
	private Double revenue;
	private String mobileNumber;
	private String phoneNumber;
	private String email;
	private String website;
	private String industry;
	private String priority;
	private boolean converted;
	private String street;
	private String country;
	private String state;
	private String city;
	private String zipCode;
	private String description;
	private LocalDateTime followUp;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	@Column(columnDefinition = "json")
	@JdbcTypeCode(SqlTypes.JSON)
	private Map<String, Object> fields;
	
	@Transient
	private List<LeadColumn.ColumnDefinition> columns;
	

	public Leads(String id, String adminId, String employeeId, String assignTo, String status, String source,
			LocalDateTime createdDate, LocalDateTime updatedDate, Map<String, Object> fields) {
		super();
		this.id = id;
		this.adminId=adminId;
		this.employeeId = employeeId;
		this.assignTo = assignTo;
		this.status = status;
		this.source = source;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.fields = fields;
	}

	public Leads() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
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

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Double getRevenue() {
		return revenue;
	}

	public void setRevenue(Double revenue) {
		this.revenue = revenue;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public boolean isConverted() {
		return converted;
	}

	public void setConverted(boolean converted) {
		this.converted = converted;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<LeadColumn.ColumnDefinition> getColumns() {
		return columns;
	}

	public void setColumns(List<LeadColumn.ColumnDefinition> columns) {
		this.columns = columns;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public LocalDateTime getFollowUp() {
		return followUp;
	}

	public void setFollowUp(LocalDateTime followUp) {
		this.followUp = followUp;
	}

	
	

}