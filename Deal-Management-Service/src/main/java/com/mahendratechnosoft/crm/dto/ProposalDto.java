package com.mahendratechnosoft.crm.dto;

import java.util.List;

import com.mahendratechnosoft.crm.entity.PaymentProfile;
import com.mahendratechnosoft.crm.entity.Proposal;
import com.mahendratechnosoft.crm.entity.ProposalContent;

public class ProposalDto {
	
	private Proposal proposalInfo;
	private List<ProposalContent> proposalContent;
	private List<PaymentProfile> paymentProfiles;
	
	public Proposal getProposalInfo() {
		return proposalInfo;
	}
	public void setProposalInfo(Proposal proposalInfo) {
		this.proposalInfo = proposalInfo;
	}
	public List<ProposalContent> getProposalContent() {
		return proposalContent;
	}
	public void setProposalContent(List<ProposalContent> proposalContent) {
		this.proposalContent = proposalContent;
	}
	public List<PaymentProfile> getPaymentProfiles() {
		return paymentProfiles;
	}
	public void setPaymentProfiles(List<PaymentProfile> paymentProfiles) {
		this.paymentProfiles = paymentProfiles;
	}
	
}
