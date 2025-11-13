package com.mahendratechnosoft.crm.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.dto.ProformaInvoiceSummaryDTO;
import com.mahendratechnosoft.crm.entity.Payments;
import com.mahendratechnosoft.crm.entity.ProformaInvoice;

public interface ProformaInvoiceRepository extends JpaRepository<ProformaInvoice, String>{

  ProformaInvoice findByProformaInvoiceId(String proformaInvoiceId);
  
  
	@Query("""
		    SELECT p FROM ProformaInvoice p
		    WHERE p.adminId = :adminId 
		      AND (:search IS NULL OR LOWER(p.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		      AND (p.proformaInvoiceDate BETWEEN :startDate AND :endDate)
		    ORDER BY p.proposalId DESC
		""")
		Page<ProformaInvoice> findByAdminId(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        @Param("startDate") Date startDate,
		        @Param("endDate") Date endDate,
		        Pageable pageable);
	

	@Query("""
		    SELECT p FROM ProformaInvoice p 
		    WHERE p.employeeId = :employeeId 
		      AND (:search IS NULL OR LOWER(p.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		      AND (p.proformaInvoiceDate BETWEEN :startDate AND :endDate)
		    ORDER BY p.proposalId DESC
		""")
		Page<ProformaInvoice> findByEmployeeId(
		        @Param("employeeId") String employeeId,
		        @Param("search") String search,
		        @Param("startDate") Date startDate,
		        @Param("endDate") Date endDate,
		        Pageable pageable);
	
	
	
	
	@Query("""
		    SELECT new com.mahendratechnosoft.crm.dto.ProformaInvoiceSummaryDTO(
		        p.proformaInvoiceId,
		        p.proformaInvoiceNumber,
		        p.companyName,
		        p.relatedId,
		        p.totalAmount,
		        p.paidAmount
		    )
		    FROM ProformaInvoice p
		    WHERE p.adminId = :adminId
		      
		    ORDER BY p.proformaInvoiceId DESC
		""")
		List<ProformaInvoiceSummaryDTO> getAllPerfromaByAdmin(@Param("adminId") String adminIdd);

}
