package com.mahendratechnosoft.crm.repository.Hospital;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Invoice;
import com.mahendratechnosoft.crm.entity.Hospital.SampleReport;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;

@Repository
public interface DonorsRepository extends JpaRepository<Donors, String>{
	
	@Query("""
		    SELECT p FROM Donors p
		    WHERE p.adminId = :adminId 
		      AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) 
		     AND (:status IS NULL OR LOWER(p.status) = LOWER(:status))
		    ORDER BY p.donorId DESC
		""")
		Page<Donors> findByAdminId(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        @Param("status") String status,
		        Pageable pageable);
	
	
	@Query("""
		    SELECT p FROM Donors p
		    WHERE p.employeeId = :employeeId 
		      AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) 
		     AND (:status IS NULL OR LOWER(p.status) = LOWER(:status))
		    ORDER BY p.donorId DESC
		""")
		Page<Donors> findByEmployeeId(
		        @Param("employeeId") String employeeId,
		        @Param("search") String search,
		        @Param("status") String status,
		        Pageable pageable);
	
	
	
}
