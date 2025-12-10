package com.mahendratechnosoft.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.entity.AMC;

public interface AMCRepository extends JpaRepository<AMC, String> {
	
	

	@Query("""
		    SELECT c FROM AMC c 
		    WHERE c.adminId = :adminId 
		      AND (:employeeId IS NULL OR c.employeeId = :employeeId)
		      AND (:search IS NULL OR LOWER(c.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		    ORDER BY c.amcId DESC
		""")
		Page<AMC> findByAMC(
		        @Param("adminId") String adminId,
		        @Param("employeeId") String employeeId,
		        @Param("search") String search,
		        Pageable pageable);

}
