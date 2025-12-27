package com.mahendratechnosoft.crm.dto;

public class EmployeeInfo {

	private String employeeId;
	private String loginEmail;
	private String name;
	
	

	public EmployeeInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeInfo(String employeeId, String loginEmail, String name) {
		super();
		this.employeeId = employeeId;
		this.loginEmail = loginEmail;
		this.name = name;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
