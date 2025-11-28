package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.ModuleAccess;

public interface ModuleAccessRepository extends JpaRepository<ModuleAccess, String>{

	ModuleAccess findByAdminIdAndEmployeeId(String adminId,String employeeId);
}
