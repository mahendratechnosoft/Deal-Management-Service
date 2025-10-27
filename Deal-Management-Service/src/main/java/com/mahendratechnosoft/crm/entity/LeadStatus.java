package com.mahendratechnosoft.crm.entity;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;

@Entity
public class LeadStatus {

	@Id
	private String id;
	private int companyId;
	private String leadStatus;
    private int sequence;
    
	public LeadStatus(String id, int companyId, String leadStatus, int sequence) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.leadStatus = leadStatus;
		this.sequence = sequence;
	}
	
	public LeadStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getLeadStatus() {
		return leadStatus;
	}
	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
    
    
    
    
}
