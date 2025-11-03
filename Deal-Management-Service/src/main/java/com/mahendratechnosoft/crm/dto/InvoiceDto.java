package com.mahendratechnosoft.crm.dto;

import java.util.List;

import com.mahendratechnosoft.crm.entity.Invoice;
import com.mahendratechnosoft.crm.entity.InvoiceContent;

public class InvoiceDto {
	
	private Invoice invoiceInfo;
	private List<InvoiceContent> invoiceContent;
	


	public Invoice getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(Invoice invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

	public List<InvoiceContent> getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(List<InvoiceContent> invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	
	

}
