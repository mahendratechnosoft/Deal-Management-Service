package com.mahendratechnosoft.crm.dto;

import java.util.List;
import java.util.Map;

import com.mahendratechnosoft.crm.entity.LeadColumn;

public class LeadWithColumnsDTO {
	private List<LeadColumn.ColumnDefinition> columns;
	private Map<String, Object> fields;
	private String status;
	private String source;
	private String employeeId;
	private String assignTo;
	private String clientName;
	private Double revenue;

	public List<LeadColumn.ColumnDefinition> getColumns() {
		return columns;
	}

	public void setColumns(List<LeadColumn.ColumnDefinition> columns) {
		this.columns = columns;
	}

	public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
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
