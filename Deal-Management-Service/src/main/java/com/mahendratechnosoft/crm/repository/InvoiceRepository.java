package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
	
  Invoice findByInvoiceId(String invoiceId);	
  
  
	@Query("""
		    SELECT p FROM Invoice p
		    WHERE p.adminId = :adminId 
		      AND (:search IS NULL OR LOWER(p.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		    ORDER BY p.proposalId DESC
		""")
		Page<Invoice> findByAdminId(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        Pageable pageable);
	
	
	@Query("""
		    SELECT p FROM Invoice p 
		    WHERE p.employeeId = :employeeId 
		      AND (:search IS NULL OR LOWER(p.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		    ORDER BY p.proposalId DESC
		""")
		Page<Invoice> findByEmployeeId(
		        @Param("employeeId") String employeeId,
		        @Param("search") String search,
		        Pageable pageable);
	
	List<Invoice> findByRelatedToAndRelatedId(String releatedTo,String relatedId);

}
