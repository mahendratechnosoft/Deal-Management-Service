package com.mahendratechnosoft.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.entity.Hospital.SampleReport;

public interface SampleReportRepository extends JpaRepository<SampleReport, String >{
	
	
	@Query("""
		    SELECT p FROM SampleReport p
		    WHERE p.sampleId = :sampleId 
		      AND (:search IS NULL OR LOWER(p.media) LIKE LOWER(CONCAT('%', :search, '%'))) 
		    ORDER BY p.sampleReportId DESC
		""")
		Page<SampleReport> findByDonorId(
		        @Param("sampleId") String sampleId,
		        @Param("search") String search,
		        Pageable pageable);

}
