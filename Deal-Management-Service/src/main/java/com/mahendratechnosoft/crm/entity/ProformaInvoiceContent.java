package com.mahendratechnosoft.crm.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class ProformaInvoiceContent {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String proformaInvoiceContentId;
	private String proformaInvoiceId;
	@Column(length = 1000)
	private String item;
	@Column(length = 5000)
	private String description;
	private int quantity;
	private double rate;
	private String sacCode;
	private String itemId;
	
	private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }
	
	public ProformaInvoiceContent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProformaInvoiceContent(String proformaInvoiceContentId, String proformaInvoiceId, String item,
			String description, int quantity, double rate, String sacCode) {
		super();
		this.proformaInvoiceContentId = proformaInvoiceContentId;
		this.proformaInvoiceId = proformaInvoiceId;
		this.item = item;
		this.description = description;
		this.quantity = quantity;
		this.rate = rate;
		this.sacCode = sacCode;
	}

	public String getProformaInvoiceContentId() {
		return proformaInvoiceContentId;
	}

	public void setProformaInvoiceContentId(String proformaInvoiceContentId) {
		this.proformaInvoiceContentId = proformaInvoiceContentId;
	}

	public String getProformaInvoiceId() {
		return proformaInvoiceId;
	}

	public void setProformaInvoiceId(String proformaInvoiceId) {
		this.proformaInvoiceId = proformaInvoiceId;
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
