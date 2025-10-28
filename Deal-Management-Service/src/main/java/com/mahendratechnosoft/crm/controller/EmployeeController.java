package com.mahendratechnosoft.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.repository.EmployeeRepository;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
    private EmployeeRepository employeeRepository; // Create this repository interface

    // Helper method to get the currently logged-in Employee
    @ModelAttribute("employee")
    public Employee getCurrentlyLoggedInEmployee(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("User not authenticated");
        }
        
        String loginEmail = authentication.getName();
        return employeeRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new RuntimeException("Employee profile not found for user: " + loginEmail));
    }

    // Endpoint to get the employee's own info
    @GetMapping("/getEmployeeInfo")
    public ResponseEntity<Employee> getEmployeeInfo(@ModelAttribute("employee") Employee employee) {
        return ResponseEntity.ok(employee);
    }
}
