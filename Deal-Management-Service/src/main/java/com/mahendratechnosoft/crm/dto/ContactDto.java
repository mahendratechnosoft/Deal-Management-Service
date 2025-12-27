package com.mahendratechnosoft.crm.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.mahendratechnosoft.crm.entity.Contacts;

public class ContactDto {
	
	@JsonUnwrapped
	private Contacts contacts;
	private String password;
	private String loginEmail;
	private boolean isActive;
	
	public Contacts getContacts() {
		return contacts;
	}
	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
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
