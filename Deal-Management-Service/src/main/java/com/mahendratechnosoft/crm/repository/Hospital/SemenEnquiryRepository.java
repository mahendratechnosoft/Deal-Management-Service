package com.mahendratechnosoft.crm.repository.Hospital;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Hospital.SemenEnquiry;

@Repository
public interface SemenEnquiryRepository extends JpaRepository<SemenEnquiry, String>{
	
	@Query("""
	    SELECT p FROM SemenEnquiry p
	    WHERE p.adminId = :adminId
	      AND (:employeeId IS NULL OR p.employeeId = :employeeId)
	      AND (
	           :search IS NULL OR :search = '' 
	           OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
	      )
	    ORDER BY p.createdAt DESC
	""")
	Page<SemenEnquiry> getAllSemenEnquiry(
	        @Param("adminId") String adminId,
	        @Param("employeeId") String employeeId,
	        @Param("search") String search,
	        Pageable pageable
	);

}
