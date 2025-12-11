package com.mahendratechnosoft.crm.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.entity.Task;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;
import com.mahendratechnosoft.crm.enums.TaskStatus;

public interface TaskRepository extends JpaRepository<Task,String> {
	
	@Query("""
		    SELECT t FROM Task t
		    WHERE t.adminId = :adminId 
			  AND (:status IS NULL OR t.status = :status)
		      AND (:search IS NULL OR LOWER(t.subject) LIKE LOWER(CONCAT('%', :search, '%'))) 
		    ORDER BY t.createdAt DESC
		""")
		Page<Task> findByAdminId(
		        @Param("adminId") String adminId,
		        @Param("status") TaskStatus status,
		        @Param("search") String search,
		        Pageable pageable);
	
	
	@Query("""
	        SELECT DISTINCT t FROM Task t
	        LEFT JOIN t.assignedEmployees ae
	        LEFT JOIN t.followersEmployees fe
	        WHERE t.adminId = :adminId
	          AND (
		           (:listType = 'ASSIGNED' AND (t.employeeId = :employeeId OR ae.employeeId = :employeeId))
		           OR
		           (:listType = 'FOLLOWER' AND fe.employeeId = :employeeId)
		           OR
		           (:listType IS NULL AND (t.employeeId = :employeeId OR ae.employeeId = :employeeId OR fe.employeeId = :employeeId))
		      )
	          AND (:status IS NULL OR t.status = :status)
	          AND (:search IS NULL OR LOWER(t.subject) LIKE LOWER(CONCAT('%', :search, '%'))) 
	        ORDER BY t.createdAt DESC
	    """)
	    Page<Task> findTasksForEmployee(
	            @Param("adminId") String adminId,
	            @Param("employeeId") String employeeId,
	            @Param("status") TaskStatus status,
	            @Param("search") String search,
	            @Param("listType") String listType,
	            Pageable pageable);
	
	
	@Query("""
	        SELECT t.status, COUNT(t) 
	        FROM Task t
	        WHERE t.adminId = :adminId
	        GROUP BY t.status
	    """)
	    List<Object[]> countStatusByAdminId(
	        @Param("adminId") String adminId
	    );

	    @Query("""
	        SELECT t.status, COUNT(DISTINCT t) 
	        FROM Task t
	        LEFT JOIN t.assignedEmployees ae
	        LEFT JOIN t.followersEmployees fe
	        WHERE t.adminId = :adminId
	          AND (
	               (:listType = 'ASSIGNED' AND (t.employeeId = :employeeId OR ae.employeeId = :employeeId))
	               OR
	               (:listType = 'FOLLOWER' AND fe.employeeId = :employeeId)
	               OR
	               (:listType IS NULL AND (t.employeeId = :employeeId OR ae.employeeId = :employeeId OR fe.employeeId = :employeeId))
	          )
	        GROUP BY t.status
	    """)
	    List<Object[]> countStatusForEmployee(
	        @Param("adminId") String adminId,
	        @Param("employeeId") String employeeId,
	        @Param("listType") String listType
	    );
	

}
