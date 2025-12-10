package com.mahendratechnosoft.crm.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.dto.AMCList;
import com.mahendratechnosoft.crm.entity.AMC;

public interface AMCRepository extends JpaRepository<AMC, String> {
	
	

	@Query("""
		    SELECT new com.mahendratechnosoft.crm.dto.AMCList(
		        a.amcId,
		        a.clientName,
		        a.companyName,
		        a.websiteURL,
		        h.amcStartDate,
		        h.amcEndDate,
		        a.domainProvider,
		        a.assingedTo
		    )
		    FROM AMC a
		    LEFT JOIN AMCHistory h 
		           ON h.amcId = a.amcId 
		          AND h.sequence = (
		                SELECT MAX(h2.sequence)
		                FROM AMCHistory h2
		                WHERE h2.amcId = a.amcId
		          )
		    WHERE a.adminId = :adminId
		      AND (:employeeId IS NULL OR a.employeeId = :employeeId)
		      AND (:search IS NULL OR LOWER(a.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
		      AND (:fromDate IS NULL OR h.amcEndDate >= :fromDate)
              AND (:toDate IS NULL OR h.amcEndDate <= :toDate)
		    ORDER BY a.amcId DESC
		    """)
		Page<AMCList> findByAMC(
		        @Param("adminId") String adminId,
		        @Param("employeeId") String employeeId,
		        @Param("search") String search,
		        @Param("fromDate") LocalDate fromDate,
		        @Param("toDate") LocalDate toDate,
		        Pageable pageable);


}
