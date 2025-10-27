package com.mahendratechnosoft.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, String> {
	Optional<Employee> findByUser_LoginEmail(String loginEmail);
}
