package com.mahendratechnosoft.crm.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.mahendratechnosoft.crm.entity.Customer;

public class CustomerDto {
	
	@JsonUnwrapped
	private Customer customer;
	private String password;
	private String loginEmail;
	private boolean isActive;
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLoginEmail() {
		return loginEmail;
	}
	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
