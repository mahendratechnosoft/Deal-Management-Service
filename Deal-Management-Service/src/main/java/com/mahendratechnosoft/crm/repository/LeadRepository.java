package com.mahendratechnosoft.crm.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Leads;
@Repository
public interface LeadRepository extends JpaRepository<Leads, String> {

	@Query("SELECT l FROM Leads l WHERE l.adminId = :adminId AND (:status IS NULL OR l.status = :status)"
			+ "AND ("
			+ "          :search IS NULL "
			+ "          OR LOWER(l.clientName) LIKE LOWER(CONCAT('%', :search, '%'))"
			+ "          OR LOWER(l.companyName) LIKE LOWER(CONCAT('%', :search, '%'))"
			+ "      )"
			+ "AND (:fromDate IS NULL OR l.createdDate >= :fromDate)"
			+ "AND (:toDate   IS NULL OR l.createdDate <= :toDate)"
			+ "order By l.createdDate desc"
			)
	Page<Leads> findByAdminIdAndOptionalStatus( @Param("adminId") String adminId, @Param("status") String status,
			@Param("search") String search, @Param("fromDate") LocalDateTime fromDate,@Param("toDate") LocalDateTime toDate,
			Pageable pageable);

	
	@Query("SELECT l FROM Leads l WHERE l.employeeId = :employeeId AND (:status IS NULL OR l.status = :status) "
			+  "AND ("
			+ "          :search IS NULL "
			+ "          OR LOWER(l.clientName) LIKE LOWER(CONCAT('%', :search, '%'))"
			+ "          OR LOWER(l.companyName) LIKE LOWER(CONCAT('%', :search, '%'))"
			+ "      )")
			
	Page<Leads> findByEmployeeIdAndOptionalStatus( @Param("employeeId") String employeeId, @Param("status") String status,@Param("search") String search, Pageable pageable);

	
	List<Leads> findByAdminId(String companyId);
	
	@Query("SELECT l.status, COUNT(l) " + "FROM Leads l " + "WHERE l.adminId = :adminId " + "GROUP BY l.status")
	List<Object[]> countLeadsByStatus(@Param("adminId") String adminId);
	
	@Query("SELECT l.status, COUNT(l) " + "FROM Leads l " + "WHERE l.employeeId = :employeeId " + "GROUP BY l.status")
	List<Object[]> countLeadsByStatusByEmployeeId(@Param("employeeId") String employeeId);
	
	
	@Query("SELECT e.id, e.clientName FROM Leads e WHERE e.adminId = :adminId AND LOWER(e.status) <> 'converted'")
	List<Object[]> LeadNameAndIdByAdminId(@Param("adminId") String adminId);
	
	@Query("SELECT e.id, e.clientName FROM Leads e WHERE e.adminId = :adminId ")
	List<Object[]> LeadNameAndIdByAdminIdWithConverted(@Param("adminId") String adminId);
	
}
