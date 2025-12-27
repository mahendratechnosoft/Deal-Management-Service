package com.mahendratechnosoft.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, String>{

	@Query("""
	    SELECT v FROM Vendor v
	    WHERE v.adminId = :adminId
	      AND (:employeeId IS NULL OR v.employeeId = :employeeId)
	      AND (:vendorName IS NULL 
	           OR LOWER(v.vendorName) LIKE LOWER(CONCAT('%', :vendorName, '%')))
	""")
	Page<Vendor> findAllByAdminAndEmployeeAndName(
	        @Param("adminId") String adminId,
	        @Param("employeeId") String employeeId,
	        @Param("vendorName") String vendorName,
	        Pageable pageable);
	
	
	@Query("SELECT COALESCE(MAX(v.vendorCodeNumber), 0) FROM Vendor v WHERE v.adminId = :adminId")
    int findMaxVendorNumberByAdminId(String adminId);
}
