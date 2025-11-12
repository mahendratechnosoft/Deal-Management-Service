package com.mahendratechnosoft.crm.dto;

public class ProformaInvoiceSummaryDTO {
	
	 private String proformaInvoiceId;
	    private String proformaInvoiceNumber;
	    private String companyName;
	    private String relatedId;

	    public ProformaInvoiceSummaryDTO(String proformaInvoiceId, String proformaInvoiceNumber, String companyName, String relatedId) {
	        this.proformaInvoiceId = proformaInvoiceId;
	        this.proformaInvoiceNumber = proformaInvoiceNumber;
	        this.companyName = companyName;
	        this.relatedId = relatedId;
	    }

		public String getProformaInvoiceId() {
			return proformaInvoiceId;
		}

		public void setProformaInvoiceId(String proformaInvoiceId) {
			this.proformaInvoiceId = proformaInvoiceId;
		}

		public String getProformaInvoiceNumber() {
			return proformaInvoiceNumber;
		}

		public void setProformaInvoiceNumber(String proformaInvoiceNumber) {
			this.proformaInvoiceNumber = proformaInvoiceNumber;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getRelatedId() {
			return relatedId;
		}

		public void setRelatedId(String relatedId) {
			this.relatedId = relatedId;
		}

	   

}
