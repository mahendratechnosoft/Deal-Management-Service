package com.mahendratechnosoft.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mahendratechnosoft.crm.entity.TaskTimeLog;
import com.mahendratechnosoft.crm.enums.TimeLogStatus;

public interface TaskTimeLogRepository extends JpaRepository<TaskTimeLog, String>{

	boolean existsByTaskIdAndEmployeeIdAndStatus(String taskId, String employeeId, TimeLogStatus active);

	Optional<TaskTimeLog> findByTaskIdAndEmployeeIdAndStatus(String taskId, String employeeId, TimeLogStatus active);
	
	List<TaskTimeLog> findByTaskIdAndEmployeeIdOrderByStartTimeDesc(String taskId, String employeeId);
	List<TaskTimeLog> findByTaskIdAndAdminIdOrderByStartTimeDesc(String taskId, String adminId);

	Optional<TaskTimeLog> findByEmployeeIdAndStatus(String employeeId, TimeLogStatus active);
	
	@Query("SELECT t FROM TaskTimeLog t WHERE t.adminId = :adminId AND t.employeeId IS NULL AND t.status = :status")
    Optional<TaskTimeLog> findActiveLogForAdmin(@Param("adminId") String adminId, @Param("status") TimeLogStatus status);

    @Query("SELECT t FROM TaskTimeLog t WHERE t.taskId = :taskId AND t.adminId = :adminId AND t.employeeId IS NULL AND t.status = :status")
    Optional<TaskTimeLog> findActiveLogForAdminByTask(@Param("taskId") String taskId, @Param("adminId") String adminId, @Param("status") TimeLogStatus status);

}
