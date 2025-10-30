package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,String> {

	List<Department> findAllByAdminId(String adminId);
	
	List<Department> findAllByAdminIdAndNameContainingIgnoreCase(String adminId, String name);

}
