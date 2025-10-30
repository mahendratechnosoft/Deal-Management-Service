package com.mahendratechnosoft.crm.dto;

import java.util.List;

import com.mahendratechnosoft.crm.entity.Proposal;
import com.mahendratechnosoft.crm.entity.ProposalContent;

public class ProposalDto {
	
	private Proposal proposalInfo;
	private List<ProposalContent> proposalContent;
	
	
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
	
	

}
