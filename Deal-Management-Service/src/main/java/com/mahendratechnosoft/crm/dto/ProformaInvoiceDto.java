package com.mahendratechnosoft.crm.dto;

import java.util.List;

import com.mahendratechnosoft.crm.entity.PaymentProfile;
import com.mahendratechnosoft.crm.entity.ProformaInvoice;
import com.mahendratechnosoft.crm.entity.ProformaInvoiceContent;

public class ProformaInvoiceDto {
	
	private ProformaInvoice proformaInvoiceInfo;
	private List<ProformaInvoiceContent> proformaInvoiceContents;
	private List<PaymentProfile> paymentProfiles;
	
	public ProformaInvoiceDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProformaInvoiceDto(ProformaInvoice ProformaInvoiceInfo,
			List<ProformaInvoiceContent> proformaInvoiceContents) {
		super();
		this.proformaInvoiceInfo = ProformaInvoiceInfo;
		this.proformaInvoiceContents = proformaInvoiceContents;
	}

	public ProformaInvoice getProformaInvoiceInfo() {
		return proformaInvoiceInfo;
	}

	public void setProformaInvoiceInfo(ProformaInvoice proformaInvoiceInfo) {
		this.proformaInvoiceInfo = proformaInvoiceInfo;
	}

	public List<ProformaInvoiceContent> getProformaInvoiceContents() {
		return proformaInvoiceContents;
	}

	public void setProformaInvoiceContents(List<ProformaInvoiceContent> proformaInvoiceContents) {
		this.proformaInvoiceContents = proformaInvoiceContents;
	}

	public List<PaymentProfile> getPaymentProfiles() {
		return paymentProfiles;
	}

	public void setPaymentProfiles(List<PaymentProfile> paymentProfiles) {
		this.paymentProfiles = paymentProfiles;
	}
	
}
