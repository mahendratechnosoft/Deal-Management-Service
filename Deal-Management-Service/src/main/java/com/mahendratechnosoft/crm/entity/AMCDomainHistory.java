package com.mahendratechnosoft.crm.entity;

import java.time.LocalDate;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class AMCDomainHistory {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String acmDomainHistoryId;
	private String amcId;
	private LocalDate domainStartDate;
	private LocalDate domainRenewalDate;
	private String domainAmount;
	private String domainRenewalCycle;
    private int sequence;
    private boolean isPaid;
    private boolean isDeleted;
    private String proformaInvoiceId;
	
	
	public AMCDomainHistory(String acmDomainHistoryId, String amcId, LocalDate domainStartDate, LocalDate domainRenewalDate,
			String domainAmount, String domainRenewalCycle) {
		super();
		this.acmDomainHistoryId = acmDomainHistoryId;
		this.amcId = amcId;
		this.domainStartDate = domainStartDate;
		this.domainRenewalDate = domainRenewalDate;
		this.domainAmount = domainAmount;
		this.domainRenewalCycle = domainRenewalCycle;
	}
	public AMCDomainHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String getAcmDomainHistoryId() {
		return acmDomainHistoryId;
	}
	public void setAcmDomainHistoryId(String acmDomainHistoryId) {
		this.acmDomainHistoryId = acmDomainHistoryId;
	}
	public String getAmcId() {
		return amcId;
	}
	public void setAmcId(String amcId) {
		this.amcId = amcId;
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
	public String getDomainAmount() {
		return domainAmount;
	}
	public void setDomainAmount(String domainAmount) {
		this.domainAmount = domainAmount;
	}
	public String getDomainRenewalCycle() {
		return domainRenewalCycle;
	}
	public void setDomainRenewalCycle(String domainRenewalCycle) {
		this.domainRenewalCycle = domainRenewalCycle;
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
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getProformaInvoiceId() {
		return proformaInvoiceId;
	}
	public void setProformaInvoiceId(String proformaInvoiceId) {
		this.proformaInvoiceId = proformaInvoiceId;
	}
	
}
