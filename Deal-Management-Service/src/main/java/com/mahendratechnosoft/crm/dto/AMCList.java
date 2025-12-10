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
	
	
	public AMCList(String amcId, String clientName, String companyName, String websiteURL, LocalDate amcStartDate,
			LocalDate amcEndDate, String domainProvider, String assingedTo) {
		super();
		this.amcId = amcId;
		this.clientName = clientName;
		this.companyName = companyName;
		this.websiteURL = websiteURL;
		this.amcStartDate = amcStartDate;
		this.amcEndDate = amcEndDate;
		this.domainProvider = domainProvider;
		this.assingedTo = assingedTo;
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
	
	
}
