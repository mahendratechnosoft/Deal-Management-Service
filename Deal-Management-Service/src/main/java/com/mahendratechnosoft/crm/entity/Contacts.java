package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Contacts {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String id;
	private String customerId;
	private String name;
	private String email;
	private String phone;
	private String position;
	private boolean status;
	
	
	public Contacts(String id, String companyId, String customerId, String name, String email, String phone,
			String position, boolean status) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.position = position;
		this.status = status;
	}
	public Contacts() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
}
