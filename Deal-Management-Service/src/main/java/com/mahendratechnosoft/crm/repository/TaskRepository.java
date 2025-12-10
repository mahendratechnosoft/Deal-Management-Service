package com.mahendratechnosoft.crm.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.entity.Task;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;

public interface TaskRepository extends JpaRepository<Task,String> {
	
	@Query("""
		    SELECT t FROM Task t
		    WHERE t.adminId = :adminId 
		      AND (:search IS NULL OR LOWER(t.subject) LIKE LOWER(CONCAT('%', :search, '%'))) 
		    ORDER BY t.createdAt DESC
		""")
		Page<Task> findByAdminId(
		        @Param("adminId") String adminId,
		        @Param("search") String search,
		        Pageable pageable);
	
	
	@Query("""
	        SELECT DISTINCT t FROM Task t
	        LEFT JOIN t.assignedEmployees ae
	        LEFT JOIN t.followersEmployees fe
	        WHERE t.adminId = :adminId
	          AND (
	               t.employeeId = :employeeId 
	               OR ae.employeeId = :employeeId 
	               OR fe.employeeId = :employeeId
	          )
	          AND (:search IS NULL OR LOWER(t.subject) LIKE LOWER(CONCAT('%', :search, '%'))) 
	        ORDER BY t.createdAt DESC
	    """)
	    Page<Task> findTasksForEmployee(
	            @Param("adminId") String adminId,
	            @Param("employeeId") String employeeId,
	            @Param("search") String search,
	            Pageable pageable);
	

}
