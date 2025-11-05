package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.ActivityLogs;

public interface ActivityLogsRepository extends JpaRepository<ActivityLogs, String > {
	
	

	List<ActivityLogs> findByModuleIdAndModuleTypeOrderByCreatedDateTimeDesc(String moduleId,String moduleType);
}
