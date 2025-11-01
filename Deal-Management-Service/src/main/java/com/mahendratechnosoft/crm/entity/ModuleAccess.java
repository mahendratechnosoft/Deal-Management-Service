package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class ModuleAccess {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String moduleAccessId;
	private String adminId;
	private String employeeId;

	private boolean leadViewAll;
	private boolean leadCreate;
	private boolean leadDelete;
	private boolean leadEdit;
	
	private boolean accountViewAll;
	private boolean accountCreate;
	private boolean accountDelete;
	private boolean accountEdit;
	
	private boolean dealViewAll;
	private boolean dealCreate;
	private boolean dealDelete;
	private boolean dealEdit;
	
	private boolean contactViewAll;
	private boolean contactCreate;
	private boolean contactDelete;
	private boolean contactEdit;
	

	public String getModuleAccessId() {
		return moduleAccessId;
	}



	public void setModuleAccessId(String moduleAccessId) {
		this.moduleAccessId = moduleAccessId;
	}


	public boolean isLeadViewAll() {
		return leadViewAll;
	}



	public void setLeadViewAll(boolean leadViewAll) {
		this.leadViewAll = leadViewAll;
	}



	public boolean isLeadCreate() {
		return leadCreate;
	}



	public void setLeadCreate(boolean leadCreate) {
		this.leadCreate = leadCreate;
	}



	public boolean isLeadDelete() {
		return leadDelete;
	}



	public void setLeadDelete(boolean leadDelete) {
		this.leadDelete = leadDelete;
	}



	public boolean isLeadEdit() {
		return leadEdit;
	}



	public void setLeadEdit(boolean leadEdit) {
		this.leadEdit = leadEdit;
	}



	public boolean isAccountViewAll() {
		return accountViewAll;
	}



	public void setAccountViewAll(boolean accountViewAll) {
		this.accountViewAll = accountViewAll;
	}



	public boolean isAccountCreate() {
		return accountCreate;
	}



	public void setAccountCreate(boolean accountCreate) {
		this.accountCreate = accountCreate;
	}



	public boolean isAccountDelete() {
		return accountDelete;
	}



	public void setAccountDelete(boolean accountDelete) {
		this.accountDelete = accountDelete;
	}



	public boolean isAccountEdit() {
		return accountEdit;
	}



	public void setAccountEdit(boolean accountEdit) {
		this.accountEdit = accountEdit;
	}



	public boolean isDealViewAll() {
		return dealViewAll;
	}



	public void setDealViewAll(boolean dealViewAll) {
		this.dealViewAll = dealViewAll;
	}



	public boolean isDealCreate() {
		return dealCreate;
	}



	public void setDealCreate(boolean dealCreate) {
		this.dealCreate = dealCreate;
	}



	public boolean isDealDelete() {
		return dealDelete;
	}



	public void setDealDelete(boolean dealDelete) {
		this.dealDelete = dealDelete;
	}



	public boolean isDealEdit() {
		return dealEdit;
	}



	public void setDealEdit(boolean dealEdit) {
		this.dealEdit = dealEdit;
	}



	public boolean isContactViewAll() {
		return contactViewAll;
	}



	public void setContactViewAll(boolean contactViewAll) {
		this.contactViewAll = contactViewAll;
	}



	public boolean isContactCreate() {
		return contactCreate;
	}



	public void setContactCreate(boolean contactCreate) {
		this.contactCreate = contactCreate;
	}



	public boolean isContactDelete() {
		return contactDelete;
	}



	public void setContactDelete(boolean contactDelete) {
		this.contactDelete = contactDelete;
	}



	public boolean isContactEdit() {
		return contactEdit;
	}



	public void setContactEdit(boolean contactEdit) {
		this.contactEdit = contactEdit;
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
