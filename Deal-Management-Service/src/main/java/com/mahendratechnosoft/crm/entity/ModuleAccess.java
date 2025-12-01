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
	private boolean paymentCreate;
	private boolean paymentDelete;
	private boolean paymentEdit;
	
	
	private boolean timeSheetAccess;
	private boolean timeSheetViewAll;
	private boolean timeSheetCreate;
	private boolean timeSheetDelete;
	private boolean timeSheetEdit;
	
	private boolean donorAccess;
	private boolean donorViewAll;
	private boolean donorCreate;
	private boolean donorDelete;
	private boolean donorEdit;
	
	private boolean itemAccess;
	private boolean itemViewAll;
	private boolean itemCreate;
	private boolean itemDelete;
	private boolean itemEdit;
	
	private boolean employeeAccess;
    private boolean setting;	
	public String getModuleAccessId() {
		return moduleAccessId;
	}
	public void setModuleAccessId(String moduleAccessId) {
		this.moduleAccessId = moduleAccessId;
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
	public boolean isPaymentCreate() {
		return paymentCreate;
	}
	public void setPaymentcreate(boolean paymentCreate) {
		this.paymentCreate = paymentCreate;
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
	public boolean isTimeSheetAccess() {
		return timeSheetAccess;
	}
	public void setTimeSheetAccess(boolean timeSheetAccess) {
		this.timeSheetAccess = timeSheetAccess;
	}
	public boolean isTimeSheetViewAll() {
		return timeSheetViewAll;
	}
	public void setTimeSheetViewAll(boolean timeSheetViewAll) {
		this.timeSheetViewAll = timeSheetViewAll;
	}
	public boolean isTimeSheetCreate() {
		return timeSheetCreate;
	}
	public void setTimeSheetCreate(boolean timeSheetCreate) {
		this.timeSheetCreate = timeSheetCreate;
	}
	public boolean isTimeSheetDelete() {
		return timeSheetDelete;
	}
	public void setTimeSheetDelete(boolean timeSheetDelete) {
		this.timeSheetDelete = timeSheetDelete;
	}
	public boolean isTimeSheetEdit() {
		return timeSheetEdit;
	}
	public void setTimeSheetEdit(boolean timeSheetEdit) {
		this.timeSheetEdit = timeSheetEdit;
	}
	public boolean isDonorAccess() {
		return donorAccess;
	}
	public void setDonorAccess(boolean donorAccess) {
		this.donorAccess = donorAccess;
	}
	public boolean isDonorViewAll() {
		return donorViewAll;
	}
	public void setDonorViewAll(boolean donorViewAll) {
		this.donorViewAll = donorViewAll;
	}
	public boolean isDonorCreate() {
		return donorCreate;
	}
	public void setDonorCreate(boolean donorCreate) {
		this.donorCreate = donorCreate;
	}
	public boolean isDonorDelete() {
		return donorDelete;
	}
	public void setDonorDelete(boolean donorDelete) {
		this.donorDelete = donorDelete;
	}
	public boolean isDonorEdit() {
		return donorEdit;
	}
	public void setDonorEdit(boolean donorEdit) {
		this.donorEdit = donorEdit;
	}
	public boolean isItemAccess() {
		return itemAccess;
	}
	public void setItemAccess(boolean itemAccess) {
		this.itemAccess = itemAccess;
	}
	public boolean isItemViewAll() {
		return itemViewAll;
	}
	public void setItemViewAll(boolean itemViewAll) {
		this.itemViewAll = itemViewAll;
	}
	public boolean isItemCreate() {
		return itemCreate;
	}
	public void setItemCreate(boolean itemCreate) {
		this.itemCreate = itemCreate;
	}
	public boolean isItemDelete() {
		return itemDelete;
	}
	public void setItemDelete(boolean itemDelete) {
		this.itemDelete = itemDelete;
	}
	public boolean isItemEdit() {
		return itemEdit;
	}
	public void setItemEdit(boolean itemEdit) {
		this.itemEdit = itemEdit;
	}
	public boolean isEmployeeAccess() {
		return employeeAccess;
	}
	public void setEmployeeAccess(boolean employeeAccess) {
		this.employeeAccess = employeeAccess;
	}
	public boolean isSetting() {
		return setting;
	}
	public void setSetting(boolean setting) {
		this.setting = setting;
	}
	public void setPaymentCreate(boolean paymentCreate) {
		this.paymentCreate = paymentCreate;
	}
	
	
	
}
