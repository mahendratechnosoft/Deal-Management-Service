package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Items {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String itemId;
	private String adminId;
	private String employeeId;
	@Column(length = 1000)
	private String name;
	@Column(length = 5000)
	private String description;
	private String code;
	private int quantity;
	private double rate;
	
	public Items() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Items(String itemId, String name, String description, String code, int quantity, double rate) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.description = description;
		this.code = code;
		this.quantity = quantity;
		this.rate = rate;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
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
}
