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

	private boolean leadAccess;
	private boolean leadViewAll;
	private boolean leadCreate;
	private boolean leadDelete;
	private boolean leadEdit;
	
	private boolean customerAccess;
	private boolean customerViewAll;
	private boolean customerCreate;
	private boolean customerDelete;
	private boolean customerEdit;
	
	private boolean proposalAccess;
	private boolean proposalViewAll;
	private boolean proposalCreate;
	private boolean proposalDelete;
	private boolean proposalEdit;
	
	private boolean proformaInvoiceAccess;
	private boolean proformaInvoiceViewAll;
	private boolean proformaInvoiceCreate;
	private boolean proformaInvoiceDelete;
	private boolean proformaInvoiceEdit;
	
	
	private boolean invoiceAccess;
	private boolean invoiceViewAll;
	private boolean invoiceCreate;
	private boolean invoiceDelete;
	private boolean invoiceEdit;
	
	private boolean paymentAccess;
	private boolean paymentViewAll;
	private boolean paymentcreate;
	private boolean paymentDelete;
	private boolean paymentEdit;
	

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



	public boolean iscustomerViewAll() {
		return customerViewAll;
	}



	public void setcustomerViewAll(boolean customerViewAll) {
		this.customerViewAll = customerViewAll;
	}



	public boolean iscustomerCreate() {
		return customerCreate;
	}



	public void setcustomerCreate(boolean customerCreate) {
		this.customerCreate = customerCreate;
	}



	public boolean iscustomerDelete() {
		return customerDelete;
	}



	public void setcustomerDelete(boolean customerDelete) {
		this.customerDelete = customerDelete;
	}



	public boolean iscustomerEdit() {
		return customerEdit;
	}



	public void setcustomerEdit(boolean customerEdit) {
		this.customerEdit = customerEdit;
	}

	public boolean isproposalCreate() {
		return proposalCreate;
	}



	public void setproposalCreate(boolean proposalCreate) {
		this.proposalCreate = proposalCreate;
	}



	public boolean isproposalDelete() {
		return proposalDelete;
	}



	public void setproposalDelete(boolean proposalDelete) {
		this.proposalDelete = proposalDelete;
	}



	public boolean isproposalEdit() {
		return proposalEdit;
	}



	public void setproposalEdit(boolean proposalEdit) {
		this.proposalEdit = proposalEdit;
	}



	public boolean isproformaInvoiceViewAll() {
		return proformaInvoiceViewAll;
	}



	public void setproformaInvoiceViewAll(boolean proformaInvoiceViewAll) {
		this.proformaInvoiceViewAll = proformaInvoiceViewAll;
	}



	public boolean isproformaInvoiceCreate() {
		return proformaInvoiceCreate;
	}



	public void setproformaInvoiceCreate(boolean proformaInvoiceCreate) {
		this.proformaInvoiceCreate = proformaInvoiceCreate;
	}



	public boolean isproformaInvoiceDelete() {
		return proformaInvoiceDelete;
	}



	public void setproformaInvoiceDelete(boolean proformaInvoiceDelete) {
		this.proformaInvoiceDelete = proformaInvoiceDelete;
	}



	public boolean isproformaInvoiceEdit() {
		return proformaInvoiceEdit;
	}



	public void setproformaInvoiceEdit(boolean proformaInvoiceEdit) {
		this.proformaInvoiceEdit = proformaInvoiceEdit;
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



	public boolean isLeadAccess() {
		return leadAccess;
	}



	public void setLeadAccess(boolean leadAccess) {
		this.leadAccess = leadAccess;
	}



	public boolean isCustomerAccess() {
		return customerAccess;
	}



	public void setCustomerAccess(boolean customerAccess) {
		this.customerAccess = customerAccess;
	}



	public boolean isCustomerViewAll() {
		return customerViewAll;
	}



	public void setCustomerViewAll(boolean customerViewAll) {
		this.customerViewAll = customerViewAll;
	}



	public boolean isCustomerCreate() {
		return customerCreate;
	}



	public void setCustomerCreate(boolean customerCreate) {
		this.customerCreate = customerCreate;
	}



	public boolean isCustomerDelete() {
		return customerDelete;
	}



	public void setCustomerDelete(boolean customerDelete) {
		this.customerDelete = customerDelete;
	}



	public boolean isCustomerEdit() {
		return customerEdit;
	}



	public void setCustomerEdit(boolean customerEdit) {
		this.customerEdit = customerEdit;
	}



	public boolean isProposalAccess() {
		return proposalAccess;
	}



	public void setProposalAccess(boolean proposalAccess) {
		this.proposalAccess = proposalAccess;
	}



	public boolean isProposalViewAll() {
		return proposalViewAll;
	}



	public void setProposalViewAll(boolean proposalViewAll) {
		this.proposalViewAll = proposalViewAll;
	}



	public boolean isProposalCreate() {
		return proposalCreate;
	}



	public void setProposalCreate(boolean proposalCreate) {
		this.proposalCreate = proposalCreate;
	}



	public boolean isProposalDelete() {
		return proposalDelete;
	}



	public void setProposalDelete(boolean proposalDelete) {
		this.proposalDelete = proposalDelete;
	}



	public boolean isProposalEdit() {
		return proposalEdit;
	}



	public void setProposalEdit(boolean proposalEdit) {
		this.proposalEdit = proposalEdit;
	}



	public boolean isProformaInvoiceAccess() {
		return proformaInvoiceAccess;
	}



	public void setProformaInvoiceAccess(boolean proformaInvoiceAccess) {
		this.proformaInvoiceAccess = proformaInvoiceAccess;
	}



	public boolean isProformaInvoiceViewAll() {
		return proformaInvoiceViewAll;
	}



	public void setProformaInvoiceViewAll(boolean proformaInvoiceViewAll) {
		this.proformaInvoiceViewAll = proformaInvoiceViewAll;
	}



	public boolean isProformaInvoiceCreate() {
		return proformaInvoiceCreate;
	}



	public void setProformaInvoiceCreate(boolean proformaInvoiceCreate) {
		this.proformaInvoiceCreate = proformaInvoiceCreate;
	}



	public boolean isProformaInvoiceDelete() {
		return proformaInvoiceDelete;
	}



	public void setProformaInvoiceDelete(boolean proformaInvoiceDelete) {
		this.proformaInvoiceDelete = proformaInvoiceDelete;
	}



	public boolean isProformaInvoiceEdit() {
		return proformaInvoiceEdit;
	}



	public void setProformaInvoiceEdit(boolean proformaInvoiceEdit) {
		this.proformaInvoiceEdit = proformaInvoiceEdit;
	}



	public boolean isInvoiceAccess() {
		return invoiceAccess;
	}



	public void setInvoiceAccess(boolean invoiceAccess) {
		this.invoiceAccess = invoiceAccess;
	}



	public boolean isInvoiceViewAll() {
		return invoiceViewAll;
	}



	public void setInvoiceViewAll(boolean invoiceViewAll) {
		this.invoiceViewAll = invoiceViewAll;
	}



	public boolean isInvoiceCreate() {
		return invoiceCreate;
	}



	public void setInvoiceCreate(boolean invoiceCreate) {
		this.invoiceCreate = invoiceCreate;
	}



	public boolean isInvoiceDelete() {
		return invoiceDelete;
	}



	public void setInvoiceDelete(boolean invoiceDelete) {
		this.invoiceDelete = invoiceDelete;
	}



	public boolean isInvoiceEdit() {
		return invoiceEdit;
	}



	public void setInvoiceEdit(boolean invoiceEdit) {
		this.invoiceEdit = invoiceEdit;
	}



	public boolean isPaymentAccess() {
		return paymentAccess;
	}



	public void setPaymentAccess(boolean paymentAccess) {
		this.paymentAccess = paymentAccess;
	}



	public boolean isPaymentViewAll() {
		return paymentViewAll;
	}



	public void setPaymentViewAll(boolean paymentViewAll) {
		this.paymentViewAll = paymentViewAll;
	}



	public boolean isPaymentcreate() {
		return paymentcreate;
	}



	public void setPaymentcreate(boolean paymentcreate) {
		this.paymentcreate = paymentcreate;
	}



	public boolean isPaymentDelete() {
		return paymentDelete;
	}



	public void setPaymentDelete(boolean paymentDelete) {
		this.paymentDelete = paymentDelete;
	}



	public boolean isPaymentEdit() {
		return paymentEdit;
	}



	public void setPaymentEdit(boolean paymentEdit) {
		this.paymentEdit = paymentEdit;
	}

	
	
}
