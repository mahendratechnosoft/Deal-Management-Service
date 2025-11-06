package com.mahendratechnosoft.crm.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
@Entity
public class Payments {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String paymentId;
	private String adminId;
	private String employeeId;
	private String createdBy;
	private String proformaInvoiceNo;
	private String proformaInvoiceId;
	private String transactionId;
	private String companyName;
	private String customerId;
	private double amount;
	private Date paymentDate;
	private LocalDateTime createdDateTime;
	
	@Transient
	private double totalProformaInvoicePaidAmount;
	
	public Payments() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payments(String paymentId, String adminId, String employeeId, String createdBy, String proformaInvoiceNo,
			String proformaInvoiceId, String transactionId, String companyName, String customerId, double amount,
			Date paymentDate, LocalDateTime createdDateTime) {
		super();
		this.paymentId = paymentId;
		this.adminId = adminId;
		this.employeeId = employeeId;
		this.createdBy = createdBy;
		this.proformaInvoiceNo = proformaInvoiceNo;
		this.proformaInvoiceId = proformaInvoiceId;
		this.transactionId = transactionId;
		this.companyName = companyName;
		this.customerId = customerId;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.createdDateTime = createdDateTime;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getProformaInvoiceNo() {
		return proformaInvoiceNo;
	}

	public void setProformaInvoiceNo(String proformaInvoiceNo) {
		this.proformaInvoiceNo = proformaInvoiceNo;
	}

	public String getProformaInvoiceId() {
		return proformaInvoiceId;
	}

	public void setProformaInvoiceId(String proformaInvoiceId) {
		this.proformaInvoiceId = proformaInvoiceId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public double getTotalProformaInvoicePaidAmount() {
		return totalProformaInvoicePaidAmount;
	}

	public void setTotalProformaInvoicePaidAmount(double totalProformaInvoicePaidAmount) {
		this.totalProformaInvoicePaidAmount = totalProformaInvoicePaidAmount;
	}


	
	
	
}
