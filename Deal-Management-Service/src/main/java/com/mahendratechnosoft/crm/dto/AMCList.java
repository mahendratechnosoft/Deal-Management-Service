package com.mahendratechnosoft.crm.dto;

import java.time.LocalDate;

public class AMCList {
	private String amcId;
	private String clientName;
	private String companyName;
	private String websiteURL;
	private LocalDate amcStartDate;
	private LocalDate amcEndDate;
	private String domainProvider;
	private String assingedTo;
	
    private LocalDate domainStartDate;
    private LocalDate domainRenewalDate;

    private LocalDate gsuitStartDate;
    private LocalDate gsuitRenewalDate;
	
	
	
    public AMCList(String amcId, String clientName, String companyName, String websiteURL, 
            String domainProvider, String assingedTo, 
            LocalDate amcStartDate, LocalDate amcEndDate,
            LocalDate domainStartDate, LocalDate domainRenewalDate,
            LocalDate gsuitStartDate, LocalDate gsuitRenewalDate) {
		 this.amcId = amcId;
		 this.clientName = clientName;
		 this.companyName = companyName;
		 this.websiteURL = websiteURL;
		 this.domainProvider = domainProvider;
		 this.assingedTo = assingedTo;
		 this.amcStartDate = amcStartDate;
		 this.amcEndDate = amcEndDate;
		 this.domainStartDate = domainStartDate;
		 this.domainRenewalDate = domainRenewalDate;
		 this.gsuitStartDate = gsuitStartDate;
		 this.gsuitRenewalDate = gsuitRenewalDate;
	}
    
	public AMCList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAmcId() {
		return amcId;
	}
	public void setAmcId(String amcId) {
		this.amcId = amcId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getWebsiteURL() {
		return websiteURL;
	}
	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}
	public LocalDate getAmcStartDate() {
		return amcStartDate;
	}
	public void setAmcStartDate(LocalDate amcStartDate) {
		this.amcStartDate = amcStartDate;
	}
	public LocalDate getAmcEndDate() {
		return amcEndDate;
	}
	public void setAmcEndDate(LocalDate amcEndDate) {
		this.amcEndDate = amcEndDate;
	}
	public String getDomainProvider() {
		return domainProvider;
	}
	public void setDomainProvider(String domainProvider) {
		this.domainProvider = domainProvider;
	}
	public String getAssingedTo() {
		return assingedTo;
	}
	public void setAssingedTo(String assingedTo) {
		this.assingedTo = assingedTo;
	}
	public LocalDate getDomainStartDate() {
		return domainStartDate;
	}
	public void setDomainStartDate(LocalDate domainStartDate) {
		this.domainStartDate = domainStartDate;
	}
	public LocalDate getDomainRenewalDate() {
		return domainRenewalDate;
	}
	public void setDomainRenewalDate(LocalDate domainRenewalDate) {
		this.domainRenewalDate = domainRenewalDate;
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
	
	
}
