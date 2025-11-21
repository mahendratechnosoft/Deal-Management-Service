package com.mahendratechnosoft.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.entity.Items;

public interface ItemsRepository  extends JpaRepository<Items, String>{
	
	
	@Query("""
		    SELECT p FROM Items p
		    WHERE p.adminId = :adminId 
		      AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) 
		    ORDER BY p.itemId DESC
		""")
		Page<Items> findByAdminId(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        Pageable pageable);
	
	@Query("""
		    SELECT p FROM Items p
		    WHERE p.employeeId = :employeeId 
		      AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) 
		    ORDER BY p.itemId DESC
		""")
		Page<Items> findByEmployeeId(
		        @Param("employeeId") String admiemployeeIdnId,
		        @Param("search") String search,
		        Pageable pageable);

}
