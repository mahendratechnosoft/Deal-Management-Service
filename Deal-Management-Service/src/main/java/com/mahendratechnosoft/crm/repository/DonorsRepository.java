package com.mahendratechnosoft.crm.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Invoice;
import com.mahendratechnosoft.crm.entity.Hospital.DonorSample;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;

@Repository
public interface DonorsRepository extends JpaRepository<Donors, String>{
	
	@Query("""
		    SELECT p FROM Donors p
		    WHERE p.adminId = :adminId 
		      AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) And p.status!='selected'
		    ORDER BY p.donorId DESC
		""")
		Page<Donors> findByAdminId(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        Pageable pageable);
	
	
	@Query("""
		    SELECT p FROM Donors p
		    WHERE p.adminId = :adminId 
		      AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) And p.status!='selected'
		    ORDER BY p.donorId DESC
		""")
		Page<Donors> findByEmployeeId(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        Pageable pageable);
	
	@Query("""
		    SELECT p FROM Donors p
		    WHERE p.adminId = :adminId 
		      AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) And p.status='selected'
		    ORDER BY p.donorId DESC
		""")
		Page<Donors> findByAdminIdSelected(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        Pageable pageable);
	
	
	@Query("""
		    SELECT p FROM Donors p
		    WHERE p.adminId = :adminId 
		      AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) And p.status='selected'
		    ORDER BY p.donorId DESC
		""")
		Page<Donors> findByEmployeeIdSelected(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        Pageable pageable);
	
	
	@Query("""
		    SELECT p FROM DonorSample p
		    WHERE p.sampleReportId = :sampleReportId 
		      AND (:search IS NULL OR LOWER(p.tankNo) LIKE LOWER(CONCAT('%', :search, '%'))) 
		    ORDER BY p.donorSampleId DESC
		""")
		Page<DonorSample> findBySampleReportId(
		        @Param("sampleReportId") String sampleReportId,
		        @Param("search") String search,
		        Pageable pageable);

}
