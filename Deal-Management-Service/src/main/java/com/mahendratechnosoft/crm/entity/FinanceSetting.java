package com.mahendratechnosoft.crm.entity;

import org.hibernate.annotations.UuidGenerator;

import com.mahendratechnosoft.crm.enums.FORMAT;
import com.mahendratechnosoft.crm.enums.TYPE;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class FinanceSetting {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private String financeSettingId;
	private String adminId;
	private TYPE type;
	private String prefix;
	private String termsAndCondition;
	private int dueDays;
	private FORMAT numberFormat;
	
	
	public FinanceSetting(String financeSettingId, String adminId, TYPE type, String prefix, String termsAndCondition,
			int dueDays, FORMAT numberFormat) {
		super();
		this.financeSettingId = financeSettingId;
		this.adminId = adminId;
		this.type = type;
		this.prefix = prefix;
		this.termsAndCondition = termsAndCondition;
		this.dueDays = dueDays;
		this.numberFormat = numberFormat;
	}

	public FinanceSetting() {
		super();
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

	public String getTermsAndCondition() {
		return termsAndCondition;
	}

	public void setTermsAndCondition(String termsAndCondition) {
		this.termsAndCondition = termsAndCondition;
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
