package com.mahendratechnosoft.crm.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.JoinColumn;

@Entity
public class ProformaInvoice {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String proformaInvoiceId;
	private String adminId;
	private String employeeId;
	private String proposalId;
	private String assignTo;
	private int proformaInvoiceNumber;
	private String currencyType;
	private double discount;
	private String taxType;
	private double taxPercentage;
	private double cgstPercentage;
	private double sgstPercentage;
	private Date dueDate;
	private Date proformaInvoiceDate;
	private double totalAmount;
	private double paidAmount;
	private String status;
	private String relatedTo; // lead,customer
	private String relatedId;
	private String companyName;
	private String mobileNumber;
	private String gstin;
	private String panNumber;
	private String email;
	private String billingStreet;
	private String billingCity;
	private String billingState;
	private String billingCountry;
	private String billingZipCode;
	private String shippingStreet;
	private String shippingCity;
	private String shippingState;
	private String shippingCountry;
	private String shippingZipCode;
	@Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String notes;
    
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String termsAndConditions;
    
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] companySignature;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] companyStamp;
    private LocalDateTime createdAt;
    
    private int invoiceNumber;
    private Date invoiceDate;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "proforma_invoice_payment_mode",
        joinColumns = @JoinColumn(name = "proforma_invoice_id")
    )
    @Column(name = "payment_profile_id")
    private List<String> paymentProfileIds = new ArrayList<>();
    
    private String acmHistoryId;
    private String acmDomainHistoryId;
    private String acmGsuitHistoryId; 
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }
	
	public ProformaInvoice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProformaInvoice(String proformaInvoiceId, String adminId, String employeeId, String proposalId,
			String assignTo, int proformaInvoiceNumber, String currencyType, double discount, String taxType,
			Date dueDate, Date proformaInvoiceDate, double totalAmount, double paidAmount, String status, String relatedTo,
			String relatedId, String companyName, String mobileNumber, String gstin, String panNumber, String email,
			String billingStreet, String billingCity, String billingState, String billingCountry, String billingZipCode,
			String shippingStreet, String shippingCity, String shippingState, String shippingCountry,
			String shippingZipCode, String notes, String termsAndConditions, byte[] companySignature, double taxPercentage,
			byte[] companyStamp) {
		super();
		this.proformaInvoiceId = proformaInvoiceId;
		this.adminId = adminId;
		this.employeeId = employeeId;
		this.proposalId = proposalId;
		this.assignTo = assignTo;
		this.proformaInvoiceNumber = proformaInvoiceNumber;
		this.currencyType = currencyType;
		this.discount = discount;
		this.taxType = taxType;
		this.dueDate = dueDate;
		this.proformaInvoiceDate = proformaInvoiceDate;
		this.totalAmount = totalAmount;
		this.paidAmount = paidAmount;
		this.status = status;
		this.relatedTo = relatedTo;
		this.relatedId = relatedId;
		this.companyName = companyName;
		this.mobileNumber = mobileNumber;
		this.gstin = gstin;
		this.panNumber = panNumber;
		this.email = email;
		this.billingStreet = billingStreet;
		this.billingCity = billingCity;
		this.billingState = billingState;
		this.billingCountry = billingCountry;
		this.billingZipCode = billingZipCode;
		this.shippingStreet = shippingStreet;
		this.shippingCity = shippingCity;
		this.shippingState = shippingState;
		this.shippingCountry = shippingCountry;
		this.shippingZipCode = shippingZipCode;
		this.notes = notes;
		this.termsAndConditions = termsAndConditions;
		this.companySignature = companySignature;
		this.companyStamp = companyStamp;
		this.taxPercentage = taxPercentage;
	}



	public String getProformaInvoiceId() {
		return proformaInvoiceId;
	}

	public void setProformaInvoiceId(String proformaInvoiceId) {
		this.proformaInvoiceId = proformaInvoiceId;
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

	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

   

	public Date getProformaInvoiceDate() {
		return proformaInvoiceDate;
	}

	public void setProformaInvoiceDate(Date proformaInvoiceDate) {
		this.proformaInvoiceDate = proformaInvoiceDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRelatedTo() {
		return relatedTo;
	}

	public void setRelatedTo(String relatedTo) {
		this.relatedTo = relatedTo;
	}

	public String getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(String relatedId) {
		this.relatedId = relatedId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBillingStreet() {
		return billingStreet;
	}

	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}

	public String getBillingCity() {
		return billingCity;
	}

	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	public String getBillingState() {
		return billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	public String getBillingCountry() {
		return billingCountry;
	}

	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}

	public String getBillingZipCode() {
		return billingZipCode;
	}

	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}

	public String getShippingStreet() {
		return shippingStreet;
	}

	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getShippingState() {
		return shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getShippingCountry() {
		return shippingCountry;
	}

	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	public String getShippingZipCode() {
		return shippingZipCode;
	}

	public void setShippingZipCode(String shippingZipCode) {
		this.shippingZipCode = shippingZipCode;
	}


	public int getProformaInvoiceNumber() {
		return proformaInvoiceNumber;
	}

	public void setProformaInvoiceNumber(int proformaInvoiceNumber) {
		this.proformaInvoiceNumber = proformaInvoiceNumber;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public byte[] getCompanySignature() {
		return companySignature;
	}

	public void setCompanySignature(byte[] companySignature) {
		this.companySignature = companySignature;
	}

	public byte[] getCompanyStamp() {
		return companyStamp;
	}

	public void setCompanyStamp(byte[] companyStamp) {
		this.companyStamp = companyStamp;
	}

	public double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public List<String> getPaymentProfileIds() {
		return paymentProfileIds;
	}

	public void setPaymentProfileIds(List<String> paymentProfileIds) {
		this.paymentProfileIds = paymentProfileIds;
	}
	
	public double getCgstPercentage() {
		return cgstPercentage;
	}

	public void setCgstPercentage(double cgstPercentage) {
		this.cgstPercentage = cgstPercentage;
	}

	public double getSgstPercentage() {
		return sgstPercentage;
	}

	public void setSgstPercentage(double sgstPercentage) {
		this.sgstPercentage = sgstPercentage;
	}

	public String getAcmHistoryId() {
		return acmHistoryId;
	}

	public void setAcmHistoryId(String acmHistoryId) {
		this.acmHistoryId = acmHistoryId;
	}

	public String getAcmDomainHistoryId() {
		return acmDomainHistoryId;
	}

	public void setAcmDomainHistoryId(String acmDomainHistoryId) {
		this.acmDomainHistoryId = acmDomainHistoryId;
	}

	public String getAcmGsuitHistoryId() {
		return acmGsuitHistoryId;
	}

	public void setAcmGsuitHistoryId(String acmGsuitHistoryId) {
		this.acmGsuitHistoryId = acmGsuitHistoryId;
	}

	@Override
	public String toString() {
		return "ProformaInvoice [discount=" + discount + ", taxType=" + taxType + ", taxPercentage=" + taxPercentage
				+ ", dueDate=" + dueDate + ", proformaInvoiceDate=" + proformaInvoiceDate + ", totalAmount="
				+ totalAmount + ", paidAmount=" + paidAmount + ", status=" + status + ", companyName=" + companyName
				+ "]";
	}

	
	
	
}
