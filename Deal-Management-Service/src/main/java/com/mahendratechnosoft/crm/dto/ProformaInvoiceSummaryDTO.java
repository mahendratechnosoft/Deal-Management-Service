package com.mahendratechnosoft.crm.dto;

public class ProformaInvoiceSummaryDTO {
	
	 private String proformaInvoiceId;
	    private String porformaInvoiceNumber;
	    private String relatedTo;
	    private String relatedId;

	    public ProformaInvoiceSummaryDTO(String proformaInvoiceId, String porformaInvoiceNumber, String relatedTo, String relatedId) {
	        this.proformaInvoiceId = proformaInvoiceId;
	        this.porformaInvoiceNumber = porformaInvoiceNumber;
	        this.relatedTo = relatedTo;
	        this.relatedId = relatedId;
	    }

	    // Getters
	    public String getProformaInvoiceId() { return proformaInvoiceId; }
	    public String getPorformaInvoiceNumber() { return porformaInvoiceNumber; }
	    public String getRelatedTo() { return relatedTo; }
	    public String getRelatedId() { return relatedId; }

}
