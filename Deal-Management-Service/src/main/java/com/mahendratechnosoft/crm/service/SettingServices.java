package com.mahendratechnosoft.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.entity.Department;
import com.mahendratechnosoft.crm.entity.Role;
import com.mahendratechnosoft.crm.repository.DepartmentRepository;
import com.mahendratechnosoft.crm.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class SettingServices {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Department createDepartment(Department department) {
		Department save = departmentRepository.save(department);
		return save;
	}
	
	public List<Department> getAllDepartmentByAdmin(String adminId, String name) {
	    if (name != null && !name.trim().isEmpty()) {
	        return departmentRepository.findAllByAdminIdAndNameContainingIgnoreCase(adminId, name);
	    } else {
	        return departmentRepository.findAllByAdminId(adminId);
	    }
	}
	
	public Role createRole(Role role) {
		Role save = roleRepository.save(role);
		return save;
	}
	
	public List<Role> getAllRoleByDepartemntId(String departmentId){
		List<Role> allByDepartmentId = roleRepository.findAllByDepartmentId(departmentId);
		return allByDepartmentId;
	}
	
	@Transactional
    public void deleteDepartmentAndRoles(String departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new RuntimeException("Department not found with id: " + departmentId);
        }
        roleRepository.deleteByDepartmentId(departmentId);
        departmentRepository.deleteById(departmentId);
    }
	
    public void deleteRole(String roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new RuntimeException("Role not found with id: " + roleId);
        }
        roleRepository.deleteById(roleId);
    }
}
