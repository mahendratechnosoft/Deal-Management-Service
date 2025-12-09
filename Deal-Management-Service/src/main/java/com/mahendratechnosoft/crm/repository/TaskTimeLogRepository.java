package com.mahendratechnosoft.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.TaskTimeLog;
import com.mahendratechnosoft.crm.enums.TimeLogStatus;

public interface TaskTimeLogRepository extends JpaRepository<TaskTimeLog, String>{

	boolean existsByTaskIdAndEmployeeIdAndStatus(String taskId, String employeeId, TimeLogStatus active);

	Optional<TaskTimeLog> findByTaskIdAndEmployeeIdAndStatus(String taskId, String employeeId, TimeLogStatus active);
	
	List<TaskTimeLog> findByTaskIdAndEmployeeIdOrderByStartTimeDesc(String taskId, String employeeId);
	List<TaskTimeLog> findByTaskIdAndAdminIdOrderByStartTimeDesc(String taskId, String adminId);

	Optional<TaskTimeLog> findByEmployeeIdAndStatus(String employeeId, TimeLogStatus active);

}
