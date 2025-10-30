package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.mahendratechnosoft.crm.entity.Department;
import com.mahendratechnosoft.crm.entity.Role;

public interface RoleRepository extends JpaRepository<Role,String> {

	List<Role> findAllByDepartmentId(String departmentId);
	
	@Modifying
    void deleteByDepartmentId(String departmentId);

}
