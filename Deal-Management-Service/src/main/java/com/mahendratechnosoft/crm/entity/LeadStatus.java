package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LeadStatus {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String id;
	private String adminId;
	private String leadStatus;
    private int sequence;
    
	public LeadStatus(String id, String adminId, String leadStatus, int sequence) {
		super();
		this.id = id;
		this.adminId = adminId;
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
	
	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
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
