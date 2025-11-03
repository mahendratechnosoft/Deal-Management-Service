package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class InvoiceContent {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String proposalContentId;
	private String invoiceId;
	@Column(length = 1000)
	private String item;
	@Column(length = 5000)
	private String description;
	private int quantity;
	private double rate;
	private String sacCode;
	
	public InvoiceContent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvoiceContent(String proposalContentId, String invoiceId, String item, String description, int quantity,
			double rate, String sacCode) {
		super();
		this.proposalContentId = proposalContentId;
		this.invoiceId = invoiceId;
		this.item = item;
		this.description = description;
		this.quantity = quantity;
		this.rate = rate;
		this.sacCode = sacCode;
	}

	public String getProposalContentId() {
		return proposalContentId;
	}

	public void setProposalContentId(String proposalContentId) {
		this.proposalContentId = proposalContentId;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
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

	public String getSacCode() {
		return sacCode;
	}

	public void setSacCode(String sacCode) {
		this.sacCode = sacCode;
	}
	
	

}
