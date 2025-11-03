package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.ProposalContent;

public interface ProposalContentRepository extends JpaRepository<ProposalContent, String > {
	
	List<ProposalContent> findByProposalId(String proposalId);

}
