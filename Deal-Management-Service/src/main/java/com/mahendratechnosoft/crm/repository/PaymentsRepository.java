package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.entity.Payments;

public interface PaymentsRepository extends JpaRepository<Payments, String> {
	
	@Query("""
		    SELECT p FROM Payments p
		    WHERE p.adminId = :adminId 
		      AND (:search IS NULL OR LOWER(p.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		    ORDER BY p.paymentId DESC
		""")
		Page<Payments> findByAdminId(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        Pageable pageable);
	

	@Query("""
		    SELECT p FROM Payments p 
		    WHERE p.employeeId = :employeeId 
		      AND (:search IS NULL OR LOWER(p.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		    ORDER BY p.paymentId DESC
		""")
		Page<Payments> findByEmployeeId(
		        @Param("employeeId") String employeeId,
		        @Param("search") String search,
		        Pageable pageable);
	
	List<Payments> findByProformaInvoiceId(String proformaInvoiceId);

}
