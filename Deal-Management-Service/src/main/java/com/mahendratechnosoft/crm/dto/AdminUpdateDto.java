package com.mahendratechnosoft.crm.dto;

public class AdminUpdateDto {
	private String name;
    private String phone;
    private String address;
    private String companyName;
    private String description;
    
    // Add this field
    private String logoBase64;
    
    private String gstNumber;
    private String bankName;
    private String accountHolderName;
    private String accountNumber;
    private String ifscCode;
    
    private String signatureBase64;
    private String stampBase64;
    private String companyEmail;
    private String panNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogoBase64() {
		return logoBase64;
	}

	public void setLogoBase64(String logoBase64) {
		this.logoBase64 = logoBase64;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
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

	public String getSignatureBase64() {
		return signatureBase64;
	}

	public void setSignatureBase64(String signatureBase64) {
		this.signatureBase64 = signatureBase64;
	}

	public String getStampBase64() {
		return stampBase64;
	}

	public void setStampBase64(String stampBase64) {
		this.stampBase64 = stampBase64;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
    
    
}
