package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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
	
	

}
