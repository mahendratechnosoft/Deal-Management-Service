package com.mahendratechnosoft.crm.dto;

public class ProformaInvoiceSummaryCountDTO {
	
	 private Double totalPaidAmount;
	    private Double totalInvoiceAmount;
	    private Double unpaidAmountBeforeDueDate;
	    private Double unpaidAmountAfterDueDate;
	    private Long overdueCount;
	    private Long paidCount;
	    private Long unpaidCount;
	    private Long partiallyPaidCount;
	    
		public ProformaInvoiceSummaryCountDTO() {
			super();
			// TODO Auto-generated constructor stub
		}
		public ProformaInvoiceSummaryCountDTO(Double totalPaidAmount, Double totalInvoiceAmount,
				Double unpaidAmountBeforeDueDate, Double unpaidAmountAfterDueDate, Long overdueCount, Long paidCount,
				Long unpaidCount, Long partiallyPaidCount) {
			super();
			this.totalPaidAmount = totalPaidAmount;
			this.totalInvoiceAmount = totalInvoiceAmount;
			this.unpaidAmountBeforeDueDate = unpaidAmountBeforeDueDate;
			this.unpaidAmountAfterDueDate = unpaidAmountAfterDueDate;
			this.overdueCount = overdueCount;
			this.paidCount = paidCount;
			this.unpaidCount = unpaidCount;
			this.partiallyPaidCount = partiallyPaidCount;
		}
		public Double getTotalPaidAmount() {
			return totalPaidAmount;
		}
		public void setTotalPaidAmount(Double totalPaidAmount) {
			this.totalPaidAmount = totalPaidAmount;
		}
		public Double getTotalInvoiceAmount() {
			return totalInvoiceAmount;
		}
		public void setTotalInvoiceAmount(Double totalInvoiceAmount) {
			this.totalInvoiceAmount = totalInvoiceAmount;
		}
		public Double getUnpaidAmountBeforeDueDate() {
			return unpaidAmountBeforeDueDate;
		}
		public void setUnpaidAmountBeforeDueDate(Double unpaidAmountBeforeDueDate) {
			this.unpaidAmountBeforeDueDate = unpaidAmountBeforeDueDate;
		}
		public Double getUnpaidAmountAfterDueDate() {
			return unpaidAmountAfterDueDate;
		}
		public void setUnpaidAmountAfterDueDate(Double unpaidAmountAfterDueDate) {
			this.unpaidAmountAfterDueDate = unpaidAmountAfterDueDate;
		}
		public Long getOverdueCount() {
			return overdueCount;
		}
		public void setOverdueCount(Long overdueCount) {
			this.overdueCount = overdueCount;
		}
		public Long getPaidCount() {
			return paidCount;
		}
		public void setPaidCount(Long paidCount) {
			this.paidCount = paidCount;
		}
		public Long getUnpaidCount() {
			return unpaidCount;
		}
		public void setUnpaidCount(Long unpaidCount) {
			this.unpaidCount = unpaidCount;
		}
		public Long getPartiallyPaidCount() {
			return partiallyPaidCount;
		}
		public void setPartiallyPaidCount(Long partiallyPaidCount) {
			this.partiallyPaidCount = partiallyPaidCount;
		}
	    
	    

}
