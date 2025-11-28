package com.mahendratechnosoft.crm.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.dto.ProformaInvoiceSummaryCountDTO;
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
		    ORDER BY p.createdAt DESC
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
		    ORDER BY p.createdAt DESC
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
		      
		    ORDER BY p.createdAt DESC
		""")
		List<ProformaInvoiceSummaryDTO> getAllPerfromaByAdmin(@Param("adminId") String adminIdd);

	
	@Query("SELECT COALESCE(MAX(p.proformaInvoiceNumber), 0) FROM ProformaInvoice p WHERE p.adminId = :adminId")
	int findMaxProposalNumberByAdminId(String adminId);

	@Query("SELECT COUNT(p) > 0 FROM ProformaInvoice p WHERE p.adminId = :adminId AND p.proformaInvoiceNumber = :proformaNumber")
	boolean existsByAdminIdAndProformaNumber(String adminId, int proformaNumber);
	
	@Query("SELECT COALESCE(MAX(p.invoiceNumber), 0) FROM ProformaInvoice p WHERE p.adminId = :adminId")
	int findMaxInvoiceNumberByAdminId(String adminId);
	
	@Query("""
			SELECT p FROM ProformaInvoice p 
	        WHERE p.adminId = :adminId 
	        AND p.invoiceNumber > 0 
	        AND LOWER(p.status) = 'paid'
		    AND (:search IS NULL OR LOWER(p.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		    Order BY p.invoiceNumber desc
			""")
	Page<ProformaInvoice> findPaidInvoicesByAdminId(
			@Param("adminId") String adminId, 
			@Param("search") String search,
	        Pageable pageable);
	
	@Query("""
			SELECT p FROM ProformaInvoice p 
	        WHERE p.employeeId = :employeeId 
	        AND p.invoiceNumber > 0 
	        AND LOWER(p.status) = 'paid'
		    AND (:search IS NULL OR LOWER(p.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		    Order BY p.invoiceNumber desc
			""")
	Page<ProformaInvoice> findPaidInvoicesByEmployee(
			@Param("employeeId") String employeeId, 
			@Param("search") String search,
	        Pageable pageable);


	Optional<ProformaInvoice> findByProposalId(String proposalId);
	
	@Query("SELECT new com.mahendratechnosoft.crm.dto.ProformaInvoiceSummaryCountDTO(" +
	        "SUM(p.paidAmount), " +
	        "SUM(p.totalAmount), " +
	        "SUM(CASE WHEN p.dueDate IS NULL OR p.dueDate >= CURRENT_DATE THEN (p.totalAmount - p.paidAmount) ELSE 0 END), " +
	        "SUM(CASE WHEN p.dueDate < CURRENT_DATE THEN (p.totalAmount - p.paidAmount) ELSE 0 END), " +
	        "SUM(CASE WHEN p.dueDate < CURRENT_DATE AND LOWER(p.status) <> 'paid' THEN 1 ELSE 0 END), " +
	        "SUM(CASE WHEN LOWER(p.status) = 'paid' THEN 1 ELSE 0 END), " +
	        "SUM(CASE WHEN (p.dueDate IS NULL OR p.dueDate >= CURRENT_DATE) AND LOWER(p.status) = 'unpaid' THEN 1 ELSE 0 END), " +
	        "SUM(CASE WHEN (p.dueDate IS NULL OR p.dueDate >= CURRENT_DATE) AND LOWER(p.status) = 'partially paid' THEN 1 ELSE 0 END) " +
	        ") " +
	        "FROM ProformaInvoice p " +
	        "WHERE p.adminId = :adminId AND (:employeeId IS NULL OR p.employeeId = :employeeId )" +
	        "AND p.proformaInvoiceDate BETWEEN :startDate AND :endDate")
	ProformaInvoiceSummaryCountDTO getInvoiceSummary(@Param("adminId") String adminId,@Param("employeeId") String employeeId,
	                                                 @Param("startDate") Date startDate,
	                                                 @Param("endDate") Date endDate);
	
	
	
	@Query("SELECT " +
		       "SUM(p.totalAmount), " +
		       "SUM(p.totalAmount / (1 + (p.taxPercentage / 100))), " +
		       "SUM(p.totalAmount - (p.totalAmount / (1 + (p.taxPercentage / 100)))) " +
		       "FROM ProformaInvoice p " +
		       "WHERE p.adminId = :adminId " +
		       "AND (:employeeId IS NULL OR p.employeeId = :employeeId)"+
		       "And p.status = 'Paid'"+
		       "AND p.invoiceDate BETWEEN :startDate AND :endDate")
		List<Object[]> fetchTotals(@Param("adminId") String adminId,
		                           @Param("employeeId") String employeeId,
		                           @Param("startDate") Date startDate,
                                   @Param("endDate") Date endDate);


}
