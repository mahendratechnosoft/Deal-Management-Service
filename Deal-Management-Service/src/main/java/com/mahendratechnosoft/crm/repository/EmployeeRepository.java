package com.mahendratechnosoft.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mahendratechnosoft.crm.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	@Query("SELECT e FROM Employee e JOIN FETCH e.admin a JOIN FETCH a.user u WHERE e.loginEmail = :loginEmail")
	Optional<Employee> findByLoginEmail(String loginEmail);
}
