package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Leads {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String id;
	private String companyId;
	private String employeeId;
	private String assignTo;
	private String status;
	private String source;
	private String clientName;
	private Double revenue;
	

	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	@Column(columnDefinition = "json")
	@JdbcTypeCode(SqlTypes.JSON)
	private Map<String, Object> fields;

	public Leads(String id, String companyId, String employeeId, String assignTo, String status, String source,
			LocalDateTime createdDate, LocalDateTime updatedDate, Map<String, Object> fields) {
		super();
		this.id = id;
		this.companyId = companyId;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	
	

}