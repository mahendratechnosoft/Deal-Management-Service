package com.mahendratechnosoft.crm.entity;


import org.hibernate.annotations.UuidGenerator;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
@Entity
public class Admin {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String adminId;
	@Column(unique = true, nullable = false)
	private String loginEmail;
	private String companyEmail;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;
	
	private String name;
	private String phone;
	private String address;
	private String companyName;
	
	@Column(length = 500)
	private String description;
	
	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] logo;
	
    private String gstNumber;
    private String bankName;
    private String accountHolderName;
    private String accountNumber;
    private String ifscCode;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] companySignature;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] companyStamp;
    
    private String panNumber;
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Admin(String adminId, String loginEmail, User user, String name, String phone, String address,
			String companyName, String description, byte[] logo) {
		super();
		this.adminId = adminId;
		this.loginEmail = loginEmail;
		this.user = user;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.companyName = companyName;
		this.description = description;
		this.logo = logo;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

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

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getLoginEmail() {
		return loginEmail;
	}


	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
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
