package com.mahendratechnosoft.crm.repository.Hospital;


import java.util.List;

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
	
	
	@Query("""
		    SELECT p FROM Donors p
		    WHERE p.adminId = :adminId 
		    AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) 
		    AND (:bloodGroup IS NULL OR LOWER(p.bloodGroup) = LOWER(:bloodGroup))
		    AND (:city IS NULL OR LOWER(p.city) = LOWER(:city))
		    
		  
		    AND (:height = 0 OR p.height = :height)
		    AND (:weight = 0 OR p.weight = :weight)
		    
		    
		    AND (:skinColor IS NULL OR LOWER(p.skinColor) = LOWER(:skinColor))
		    AND (:eyeColor IS NULL OR LOWER(p.eyeColor) = LOWER(:eyeColor))
		    AND (:religion IS NULL OR LOWER(p.religion) = LOWER(:religion))
		    AND (:profession IS NULL OR LOWER(p.profession) = LOWER(:profession))
		    ORDER BY p.donorId DESC
		""")
		Page<Donors> findByMatchingDonorAdminId(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        @Param("bloodGroup") String bloodGroup,
		        @Param("city") String city,
		        @Param("height") double height,
		        @Param("weight") double weight,
		        @Param("skinColor") String skinColor,
		        @Param("eyeColor") String eyeColor,
		        @Param("religion") String religion,
		        @Param("profession") String profession,
		        Pageable pageable);
	
	@Query("SELECT COUNT(d) FROM Donors d WHERE d.uin LIKE :prefix%")
	int countUinByPrefix(@Param("prefix") String prefix);
	
	
	@Query("SELECT d.status, COUNT(d) FROM Donors d " +
		       "WHERE d.status IN ('New Donor', 'Donor', 'Qualified', 'Shortlisted') " +
		       "GROUP BY d.status")
		List<Object[]> getDonorCountByStatus();

}
