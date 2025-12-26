package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Proposal;
@Repository
public interface ProposalRepository extends JpaRepository<Proposal, String >{
	
	
	@Query("""
		    SELECT p FROM Proposal p
		    WHERE p.adminId = :adminId 
		      AND (:search IS NULL OR LOWER(p.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		    ORDER BY p.createdAt DESC
		""")
		Page<Proposal> findByAdminId(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        Pageable pageable);
	
	
	@Query("""
		    SELECT p FROM Proposal p 
		    WHERE p.employeeId = :employeeId 
		      AND (:search IS NULL OR LOWER(p.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		    ORDER BY p.createdAt DESC
		""")
		Page<Proposal> findByEmployeeId(
		        @Param("employeeId") String employeeId,
		        @Param("search") String search,
		        Pageable pageable);
	
	Proposal findByProposalId(String proposalId);
	
	List<Proposal> findByRelatedToAndRelatedId(String relatedTo,String RelatedId);
	
	@Query("SELECT COALESCE(MAX(p.proposalNumber), 0) FROM Proposal p WHERE p.adminId = :adminId")
    int findMaxProposalNumberByAdminId(String adminId);
	
	@Query("SELECT COUNT(p) > 0 FROM Proposal p WHERE p.adminId = :adminId AND p.proposalNumber = :proposalNumber")
	boolean existsByAdminIdAndProposalNumber(String adminId, int proposalNumber);
	
	@Query("SELECT p.proposalId, p.proposalNumber,p.formatedProposalNumber FROM Proposal p WHERE p.adminId = :adminId ORDER BY p.createdAt DESC")
	List<Object[]> proposalNumberAndIdByAdminId(@Param("adminId") String adminId);


}
