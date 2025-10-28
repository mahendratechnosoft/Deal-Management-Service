package com.mahendratechnosoft.crm.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Deals {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String dealId;
	private String adminId;
	private String employeeId;
	private String createdBy;
	private LocalDateTime createdDateTime;
	private String dealName;
	private String companyName;
	private String customerId;
	private double expectedRevenue;
	private double acctualRevenue;
	private Date startDate;
	private Date closingDate;
	private String type;
	private String source;
	private String stage;
	private String  description;
	
	public Deals() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Deals(String dealId, String adminId, String employeeId, String createdBy, LocalDateTime createdDateTime,
			String dealName, String companyName, String customerId, double expectedRevenue, double acctualRevenue,
			Date startDate, Date closingDate, String type, String source, String stage, String description) {
		super();
		this.dealId = dealId;
		this.adminId = adminId;
		this.employeeId = employeeId;
		this.createdBy = createdBy;
		this.createdDateTime = createdDateTime;
		this.dealName = dealName;
		this.companyName = companyName;
		this.customerId = customerId;
		this.expectedRevenue = expectedRevenue;
		this.acctualRevenue = acctualRevenue;
		this.startDate = startDate;
		this.closingDate = closingDate;
		this.type = type;
		this.source = source;
		this.stage = stage;
		this.description = description;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
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

	public double getExpectedRevenue() {
		return expectedRevenue;
	}

	public void setExpectedRevenue(double expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}

	public double getAcctualRevenue() {
		return acctualRevenue;
	}

	public void setAcctualRevenue(double acctualRevenue) {
		this.acctualRevenue = acctualRevenue;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
