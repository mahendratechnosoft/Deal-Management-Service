package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;

import com.mahendratechnosoft.crm.enums.PaymentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class PaymentProfile {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
    private String paymentProfileId;
	private String adminId;

    @Column(nullable = false)
    private String profileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;
    
    private String accountHolderName;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String branchName;

    private String upiId;
    @Lob
    @Column(columnDefinition = "LONGBLOB") 
    private byte[] qrCodeImage;
    
    
    private boolean isDefault = false; 
    private boolean forInvoice = true; 
    private boolean forExpense = false;
    private boolean isActive = true;
    
    
	public String getPaymentProfileId() {
		return paymentProfileId;
	}
	public void setPaymentProfileId(String paymentProfileId) {
		this.paymentProfileId = paymentProfileId;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public PaymentType getType() {
		return type;
	}
	public void setType(PaymentType type) {
		this.type = type;
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
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getUpiId() {
		return upiId;
	}
	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}
	public byte[] getQrCodeImage() {
		return qrCodeImage;
	}
	public void setQrCodeImage(byte[] qrCodeImage) {
		this.qrCodeImage = qrCodeImage;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public boolean isForInvoice() {
		return forInvoice;
	}
	public void setForInvoice(boolean forInvoice) {
		this.forInvoice = forInvoice;
	}
	public boolean isForExpense() {
		return forExpense;
	}
	public void setForExpense(boolean forExpense) {
		this.forExpense = forExpense;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	} 
    
}
