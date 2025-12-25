package com.mahendratechnosoft.crm.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.annotations.UuidGenerator;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;

@Entity
public class Esic {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String esicId;
    private String adminId;
    private String employeeId;
    private String customerId;
    private String contactId;

    private String name;
    private LocalDate dateOfJoining;
    private String esicNumber;
    private LocalDate dateOfBirth;
    private String fatherName;
    private String phone;

    private String gender;
    private boolean isMarried;
    private String aadhaarNumber;

    private String accountHolderName;
    private String bankName;
    private String accountNumber;
    private String ifsc;
    
    private String presentAddress;
    private String permantAddress;
    
    private String nomineeName;
    private String nomineeRelation;
    private String nomineeAdhaar;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] aadhaarPhoto;
    
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] passportPhoto;
    
    private LocalDateTime createdAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	}

	public String getEsicId() {
		return esicId;
	}

	public void setEsicId(String esicId) {
		this.esicId = esicId;
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getEsicNumber() {
		return esicNumber;
	}

	public void setEsicNumber(String esicNumber) {
		this.esicNumber = esicNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isMarried() {
		return isMarried;
	}

	public void setMarried(boolean isMarried) {
		this.isMarried = isMarried;
	}

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getPresentAddress() {
		return presentAddress;
	}

	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}

	public String getPermantAddress() {
		return permantAddress;
	}

	public void setPermantAddress(String permantAddress) {
		this.permantAddress = permantAddress;
	}

	public String getNomineeName() {
		return nomineeName;
	}

	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	public String getNomineeRelation() {
		return nomineeRelation;
	}

	public void setNomineeRelation(String nomineeRelation) {
		this.nomineeRelation = nomineeRelation;
	}

	public String getNomineeAdhaar() {
		return nomineeAdhaar;
	}

	public void setNomineeAdhaar(String nomineeAdhaar) {
		this.nomineeAdhaar = nomineeAdhaar;
	}

	public byte[] getAadhaarPhoto() {
		return aadhaarPhoto;
	}

	public void setAadhaarPhoto(byte[] aadhaarPhoto) {
		this.aadhaarPhoto = aadhaarPhoto;
	}

	public byte[] getPassportPhoto() {
		return passportPhoto;
	}

	public void setPassportPhoto(byte[] passportPhoto) {
		this.passportPhoto = passportPhoto;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
