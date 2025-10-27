package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
