package com.mahendratechnosoft.crm.entity;

import java.time.LocalDate;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class AMCHistory {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String acmHistoryId;
	private String amcId;
	private LocalDate amcStartDate;
	private LocalDate amcEndDate;
	private double amcAmount;
	private  String amcScope;
	private String amcRecycleType;
	private int sequence;
	private boolean isPaid;
	private boolean isDeleted;
	
	
	public AMCHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AMCHistory(String acmHistoryId, String amcId, LocalDate amcStartDate, LocalDate amcEndDate, double amcAmount,
			String amcScope, String amcRecycleType) {
		super();
		this.acmHistoryId = acmHistoryId;
		this.amcId = amcId;
		this.amcStartDate = amcStartDate;
		this.amcEndDate = amcEndDate;
		this.amcAmount = amcAmount;
		this.amcScope = amcScope;
		this.amcRecycleType = amcRecycleType;
	}
	public String getAcmHistoryId() {
		return acmHistoryId;
	}
	public void setAcmHistoryId(String acmHistoryId) {
		this.acmHistoryId = acmHistoryId;
	}
	public String getAmcId() {
		return amcId;
	}
	public void setAmcId(String amcId) {
		this.amcId = amcId;
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
	public double getAmcAmount() {
		return amcAmount;
	}
	public void setAmcAmount(double amcAmount) {
		this.amcAmount = amcAmount;
	}
	public String getAmcScope() {
		return amcScope;
	}
	public void setAmcScope(String amcScope) {
		this.amcScope = amcScope;
	}
	public String getAmcRecycleType() {
		return amcRecycleType;
	}
	public void setAmcRecycleType(String amcRecycleType) {
		this.amcRecycleType = amcRecycleType;
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
	
	
}
