package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.EmailConfiguration;

public interface EmailConfigurationRepository extends JpaRepository<EmailConfiguration, String> {

	EmailConfiguration findByAdminId(String adminId);
	
	
}
