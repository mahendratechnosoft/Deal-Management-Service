package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;

import com.mahendratechnosoft.crm.enums.FORMAT;
import com.mahendratechnosoft.crm.enums.TYPE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
@Entity
public class FinanceSetting {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String financeSettingId;
	private String adminId;
	@Enumerated(EnumType.STRING)
	private TYPE type;
	private String prefix;
	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
	private String notes;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
	private String termsAndConditions;
	private int dueDays;
	@Enumerated(EnumType.STRING)
	private FORMAT numberFormat;
	
	public FinanceSetting() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FinanceSetting(String financeSettingId, String adminId, TYPE type, String prefix, String notes,
			String termsAndConditions, int dueDays, FORMAT numberFormat) {
		super();
		this.financeSettingId = financeSettingId;
		this.adminId = adminId;
		this.type = type;
		this.prefix = prefix;
		this.notes = notes;
		this.termsAndConditions = termsAndConditions;
		this.dueDays = dueDays;
		this.numberFormat = numberFormat;
	}
	public String getFinanceSettingId() {
		return financeSettingId;
	}
	public void setFinanceSettingId(String financeSettingId) {
		this.financeSettingId = financeSettingId;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public TYPE getType() {
		return type;
	}
	public void setType(TYPE type) {
		this.type = type;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getTermsAndConditions() {
		return termsAndConditions;
	}
	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}
	public int getDueDays() {
		return dueDays;
	}
	public void setDueDays(int dueDays) {
		this.dueDays = dueDays;
	}
	public FORMAT getNumberFormat() {
		return numberFormat;
	}
	public void setNumberFormat(FORMAT numberFormat) {
		this.numberFormat = numberFormat;
	}
}
