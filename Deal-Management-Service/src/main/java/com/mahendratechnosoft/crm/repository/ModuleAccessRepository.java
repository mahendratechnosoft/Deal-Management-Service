package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.ModuleAccess;

public interface ModuleAccessRepository extends JpaRepository<ModuleAccess, String>{

	ModuleAccess findByAdminIdAndEmployeeIdAndCustomerId(String adminId,String employeeId,String customerId);
	
	List<ModuleAccess> findByAdminIdAndCustomerIdIsNotNull(String adminId);
}
