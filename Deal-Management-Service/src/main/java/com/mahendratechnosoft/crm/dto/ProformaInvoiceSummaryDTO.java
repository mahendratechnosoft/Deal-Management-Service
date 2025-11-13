package com.mahendratechnosoft.crm.dto;

public class ProformaInvoiceSummaryDTO {
	
	 private String proformaInvoiceId;
	    private int proformaInvoiceNumber;
	    private String companyName;
	    private String relatedId;
	    private double totalAmount;
		private double paidAmount;
		
		
	    public ProformaInvoiceSummaryDTO(String proformaInvoiceId, int proformaInvoiceNumber, String companyName, String relatedId,double totalAmount,double paidAmount) {
	        this.proformaInvoiceId = proformaInvoiceId;
	        this.proformaInvoiceNumber = proformaInvoiceNumber;
	        this.companyName = companyName;
	        this.relatedId = relatedId;
	        this.totalAmount=totalAmount;
	        this.paidAmount=paidAmount;
	    }

		public String getProformaInvoiceId() {
			return proformaInvoiceId;
		}

		public void setProformaInvoiceId(String proformaInvoiceId) {
			this.proformaInvoiceId = proformaInvoiceId;
		}

		public int getProformaInvoiceNumber() {
			return proformaInvoiceNumber;
		}

		public void setProformaInvoiceNumber(int proformaInvoiceNumber) {
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

		public double getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(double totalAmount) {
			this.totalAmount = totalAmount;
		}

		public double getPaidAmount() {
			return paidAmount;
		}

		public void setPaidAmount(double paidAmount) {
			this.paidAmount = paidAmount;
		}

	   

}
