package com.mahendratechnosoft.crm.repository;

import java.time.LocalDate;
import java.util.Map;

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
		        a.domainProvider,
		        a.assingedTo,
		        h.amcStartDate,
		        h.amcEndDate,
		        d.domainStartDate,      
		        d.domainRenewalDate,    
		        g.gsuitStartDate,       
		        g.gsuitRenewalDate      
		    )
		    FROM AMC a
		    
		    LEFT JOIN AMCHistory h 
		           ON h.amcId = a.amcId 
		          AND h.sequence = (
		                SELECT MAX(h2.sequence)
		                FROM AMCHistory h2
		                WHERE h2.amcId = a.amcId
		          )

		    LEFT JOIN AMCDomainHistory d
		           ON d.amcId = a.amcId
		          AND d.sequence = (
		                SELECT MAX(d2.sequence)
		                FROM AMCDomainHistory d2
		                WHERE d2.amcId = a.amcId
		          )

		    LEFT JOIN AMCGsuitHistory g
		           ON g.amcId = a.amcId
		          AND g.sequence = (
		                SELECT MAX(g2.sequence)
		                FROM AMCGsuitHistory g2
		                WHERE g2.amcId = a.amcId
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
	
	
	@Query("""
	        SELECT new map(
	            COUNT(h) as totalAMC,
	            COALESCE(SUM(CASE WHEN h.isPaid = true THEN 1 ELSE 0 END), 0) as renewedAMC,
	            COALESCE(SUM(CASE WHEN h.isPaid = false AND h.amcEndDate < CURRENT_DATE THEN 1 ELSE 0 END), 0) as overdueAMC,
	    
	            COUNT(d) as totalDomain,
	            COALESCE(SUM(CASE WHEN d.isPaid = true THEN 1 ELSE 0 END), 0) as renewedDomain,
	            COALESCE(SUM(CASE WHEN d.isPaid = false AND d.domainRenewalDate < CURRENT_DATE THEN 1 ELSE 0 END), 0) as overdueDomain,
	    
	            COUNT(g) as totalGsuit,
	            COALESCE(SUM(CASE WHEN g.isPaid = true THEN 1 ELSE 0 END), 0) as renewedGsuit,
	            COALESCE(SUM(CASE WHEN g.isPaid = false AND g.gsuitRenewalDate < CURRENT_DATE THEN 1 ELSE 0 END), 0) as overdueGsuit
	        )
	        FROM AMC a
	        
	        LEFT JOIN AMCHistory h 
	               ON h.amcId = a.amcId 
	              AND h.sequence = (SELECT MAX(h2.sequence) FROM AMCHistory h2 WHERE h2.amcId = a.amcId)
	        
	        LEFT JOIN AMCDomainHistory d 
	               ON d.amcId = a.amcId 
	              AND d.sequence = (SELECT MAX(d2.sequence) FROM AMCDomainHistory d2 WHERE d2.amcId = a.amcId)
	        
	        LEFT JOIN AMCGsuitHistory g 
	               ON g.amcId = a.amcId 
	              AND g.sequence = (SELECT MAX(g2.sequence) FROM AMCGsuitHistory g2 WHERE g2.amcId = a.amcId)
	        
	        WHERE a.adminId = :adminId
	    """)
	    Map<String, Object> getDashboardCounts(@Param("adminId") String adminId);

}
