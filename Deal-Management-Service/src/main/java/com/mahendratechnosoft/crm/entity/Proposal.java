package com.mahendratechnosoft.crm.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;

@Entity
public class Proposal {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String proposalId;
	private String adminId;
	private String employeeId;
	private String assignTo;
	private int proposalNumber;
	private String currencyType;
	private double discount;
	private String taxType;
	private double taxPercentage;
	private double cgstPercentage;
	private double sgstPercentage;
	private Date dueDate;
	private Date proposalDate;
	private double totalAmmount;
	private String status;
	private String subject;
	private String relatedTo; // lead,customer
	private String relatedId;
	private String companyName;
	private String mobileNumber;
	private String email;
	private String street;
	private String city;
	private String state;
	private String country;
	private String zipCode;
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
	
	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] attachmentFile;
    private String attachmentFileName;
    private String attachmentFileType;

	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	}

	public Proposal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Proposal(String proposalId, String adminId, String employeeId, String assignTo, int proposalNumber,
			String currencyType, double discount, String taxType, Date dueDate, Date proposalDate, double totalAmmount,
			String status, String subject, String relatedTo, String relatedId, String companyName, String mobileNumber,
			String email, String street, String city, String state, String country, String zipCode) {
		super();
		this.proposalId = proposalId;
		this.adminId = adminId;
		this.employeeId = employeeId;
		this.assignTo = assignTo;
		this.proposalNumber = proposalNumber;
		this.currencyType = currencyType;
		this.discount = discount;
		this.taxType = taxType;
		this.dueDate = dueDate;
		this.proposalDate = proposalDate;
		this.totalAmmount = totalAmmount;
		this.status = status;
		this.subject = subject;
		this.relatedTo = relatedTo;
		this.relatedId = relatedId;
		this.companyName = companyName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
	}

	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
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

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public int getProposalNumber() {
		return proposalNumber;
	}

	public void setProposalNumber(int proposalNumber) {
		this.proposalNumber = proposalNumber;
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

	public Date getProposalDate() {
		return proposalDate;
	}

	public void setProposalDate(Date proposalDate) {
		this.proposalDate = proposalDate;
	}

	public double getTotalAmmount() {
		return totalAmmount;
	}

	public void setTotalAmmount(double totalAmmount) {
		this.totalAmmount = totalAmmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(double taxPercentage) {
		this.taxPercentage = taxPercentage;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public byte[] getAttachmentFile() {
		return attachmentFile;
	}

	public void setAttachmentFile(byte[] attachmentFile) {
		this.attachmentFile = attachmentFile;
	}

	public String getAttachmentFileName() {
		return attachmentFileName;
	}

	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}

	public String getAttachmentFileType() {
		return attachmentFileType;
	}

	public void setAttachmentFileType(String attachmentFileType) {
		this.attachmentFileType = attachmentFileType;
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
	
}
