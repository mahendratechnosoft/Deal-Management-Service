package com.mahendratechnosoft.crm.dto;

import com.mahendratechnosoft.crm.enums.PaymentType;

public class PaymentProfileDropdownDto {
	private String paymentProfileId;
    private String profileName;
    private PaymentType type;
    private boolean isDefault;
    
	public PaymentProfileDropdownDto(String paymentProfileId, String profileName, PaymentType type, boolean isDefault) {
		super();
		this.paymentProfileId = paymentProfileId;
		this.profileName = profileName;
		this.type = type;
		this.isDefault = isDefault;
	}
	public String getPaymentProfileId() {
		return paymentProfileId;
	}
	public void setPaymentProfileId(String paymentProfileId) {
		this.paymentProfileId = paymentProfileId;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public PaymentType getType() {
		return type;
	}
	public void setType(PaymentType type) {
		this.type = type;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
}
