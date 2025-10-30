package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Department {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String departmentId;
	private String name;
	private String adminId;
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Department(String departmentId, String name, String adminId) {
		super();
		this.departmentId = departmentId;
		this.name = name;
		this.adminId = adminId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	@Override
	public String toString() {
		return "Department [departmentId=" + departmentId + ", name=" + name + ", adminId=" + adminId + "]";
	}
	
	
}
