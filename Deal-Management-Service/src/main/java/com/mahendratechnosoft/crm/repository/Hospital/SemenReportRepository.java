package com.mahendratechnosoft.crm.repository.Hospital;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.entity.Hospital.SemenReport;

public interface SemenReportRepository extends JpaRepository<SemenReport, String >{
	
	
	@Query("""
		    SELECT p FROM SemenReport p
		    WHERE p.donorId = :donorId 
		      AND (:search IS NULL OR LOWER(p.media) LIKE LOWER(CONCAT('%', :search, '%'))) 
		    ORDER BY p.sampleReportId DESC
		""")
		Page<SemenReport> findByDonorId(
		        @Param("donorId") String donorId,
		        @Param("search") String search,
		        Pageable pageable);
	
	
	List<SemenReport> findByDonorId(String donorId);

}
