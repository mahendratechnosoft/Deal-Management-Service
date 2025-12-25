package com.mahendratechnosoft.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Esic;

@Repository
public interface EsicRepository extends JpaRepository<Esic, String>{
	
	@Query("SELECT e FROM Esic e WHERE " +
	       "(:adminId IS NULL OR e.adminId = :adminId) AND " +
	       "(:employeeId IS NULL OR e.employeeId = :employeeId) AND " +
	       "(:customerId IS NULL OR e.customerId = :customerId) AND " +
	       "(:contactId IS NULL OR e.contactId = :contactId) AND " +
	       "(:searchName IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :searchName, '%')))")
	Page<Esic> findEsicsByHierarchyAndName(
	       @Param("adminId") String adminId,
	       @Param("employeeId") String employeeId,
	       @Param("customerId") String customerId,
	       @Param("contactId") String contactId,
	       @Param("searchName") String searchName,
	       Pageable pageable
	);
}
