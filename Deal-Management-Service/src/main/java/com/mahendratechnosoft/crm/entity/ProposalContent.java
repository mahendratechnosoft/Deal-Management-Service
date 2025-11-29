package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class ProposalContent {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String proposalContentId;
	private String proposalId;
	@Column(length = 1000)
	private String item;
	@Column(length = 5000)
	private String description;
	private int quantity;
	private double rate;
	private LocalDateTime createdAt;
	
	private String itemId;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }
    
	public ProposalContent(String proposalContentId, String proposalId, String item, String description, int quantity,
			double rate) {
		super();
		this.proposalContentId = proposalContentId;
		this.proposalId = proposalId;
		this.item = item;
		this.description = description;
		this.quantity = quantity;
		this.rate = rate;
	}
	public ProposalContent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getProposalContentId() {
		return proposalContentId;
	}
	public void setProposalContentId(String proposalContentId) {
		this.proposalContentId = proposalContentId;
	}
	public String getProposalId() {
		return proposalId;
	}
	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	
}
