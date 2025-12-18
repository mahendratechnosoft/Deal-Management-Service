package com.mahendratechnosoft.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.entity.VendorContact;

public interface VendorContactRepository extends JpaRepository<VendorContact, String>{
	
	@Query("""
	    SELECT v FROM VendorContact v
	    WHERE v.vendorId = :vendorId
	      AND (:search IS NULL 
	           OR LOWER(v.contactPersonName) LIKE LOWER(CONCAT('%', :search, '%')))
	""")
	Page<VendorContact> findAllByAdminAndEmployeeAndName(
	        @Param("vendorId") String vendorId,
	        @Param("search") String search,
	        Pageable pageable);
}
