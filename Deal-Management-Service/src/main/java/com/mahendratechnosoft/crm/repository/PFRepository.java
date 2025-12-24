package com.mahendratechnosoft.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.PF;

@Repository
public interface PFRepository extends JpaRepository<PF, String>{
	
	@Query("SELECT p FROM PF p WHERE " +
	           "(:adminId IS NULL OR p.adminId = :adminId) AND " +
	           "(:employeeId IS NULL OR p.employeeId = :employeeId) AND " +
	           "(:customerId IS NULL OR p.customerId = :customerId) AND " +
	           "(:contactId IS NULL OR p.contactId = :contactId) AND " +
	           "(:searchName IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :searchName, '%')))")
    Page<PF> findPFsByHierarchyAndName(
            @Param("adminId") String adminId,
            @Param("employeeId") String employeeId,
            @Param("customerId") String customerId,
            @Param("contactId") String contactId,
            @Param("searchName") String searchName,
            Pageable pageable
    );
}
