package com.mahendratechnosoft.crm.entity;

import java.time.LocalDate;

import org.hibernate.annotations.UuidGenerator;

import com.mahendratechnosoft.crm.enums.PayerType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
@Entity
public class AMCGsuitHistory {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String acmGsuitHistoryId;
	private String amcId;
	
	private String domainName;
    private String platform;
    private LocalDate gsuitStartDate;
	private LocalDate gsuitRenewalDate;
	private String adminEmail;
    private String adminPassword;
    private Integer totalLicenses;
	private String gsuitAmount;
	
	@Enumerated(EnumType.STRING)
    private PayerType paidBy;
	
	private boolean isPurchasedViaReseller;
    private String resellerName;
	private String gsuitRenewalCycle;
    private int sequence;
    
    private boolean isPaid;
    
	public String getAcmGsuitHistoryId() {
		return acmGsuitHistoryId;
	}
	public void setAcmGsuitHistoryId(String acmGsuitHistoryId) {
		this.acmGsuitHistoryId = acmGsuitHistoryId;
	}
	public String getAmcId() {
		return amcId;
	}
	public void setAmcId(String amcId) {
		this.amcId = amcId;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public LocalDate getGsuitStartDate() {
		return gsuitStartDate;
	}
	public void setGsuitStartDate(LocalDate gsuitStartDate) {
		this.gsuitStartDate = gsuitStartDate;
	}
	public LocalDate getGsuitRenewalDate() {
		return gsuitRenewalDate;
	}
	public void setGsuitRenewalDate(LocalDate gsuitRenewalDate) {
		this.gsuitRenewalDate = gsuitRenewalDate;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public Integer getTotalLicenses() {
		return totalLicenses;
	}
	public void setTotalLicenses(Integer totalLicenses) {
		this.totalLicenses = totalLicenses;
	}
	public String getGsuitAmount() {
		return gsuitAmount;
	}
	public void setGsuitAmount(String gsuitAmount) {
		this.gsuitAmount = gsuitAmount;
	}
	public PayerType getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(PayerType paidBy) {
		this.paidBy = paidBy;
	}
	public boolean isPurchasedViaReseller() {
		return isPurchasedViaReseller;
	}
	public void setPurchasedViaReseller(boolean isPurchasedViaReseller) {
		this.isPurchasedViaReseller = isPurchasedViaReseller;
	}
	public String getResellerName() {
		return resellerName;
	}
	public void setResellerName(String resellerName) {
		this.resellerName = resellerName;
	}
	public String getGsuitRenewalCycle() {
		return gsuitRenewalCycle;
	}
	public void setGsuitRenewalCycle(String gsuitRenewalCycle) {
		this.gsuitRenewalCycle = gsuitRenewalCycle;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	
}
