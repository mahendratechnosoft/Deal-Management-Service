package com.mahendratechnosoft.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahendratechnosoft.crm.entity.Deals;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.repository.EmployeeRepository;
import com.mahendratechnosoft.crm.service.DealsService;
import com.mahendratechnosoft.crm.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
    private EmployeeRepository employeeRepository; // Create this repository interface
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DealsService dealsService;

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
    
    @PutMapping("/updateProfile")
    public ResponseEntity<Employee> updateOwnProfile(
            @ModelAttribute("employee") Employee currentEmployee,
            @RequestBody Employee updateRequest) {
    	updateRequest.setLoginEmail(currentEmployee.getLoginEmail());
    	updateRequest.setEmployeeId(currentEmployee.getEmployeeId());
    	updateRequest.setAdmin(currentEmployee.getAdmin());
        Employee updatedEmployee = employeeService.updateEmployee(updateRequest);
        return ResponseEntity.ok(updatedEmployee);
    }// deals APIs
    
	@PostMapping("/createDeals")
	public ResponseEntity<?> createDeals(@ModelAttribute("employee") Employee employee, @RequestBody Deals requestBody) {
		requestBody.setAdminId(employee.getAdmin().getAdminId());
		requestBody.setEmployeeId(employee.getEmployeeId());
		return dealsService.createDeals(requestBody);

	}
	
	
	@PostMapping("/updateDeals")
	public ResponseEntity<?> updateDeals(@ModelAttribute("employee") Employee employee, @RequestBody Deals requestBody) {
		requestBody.setAdminId(employee.getAdmin().getAdminId());
		
		return dealsService.updateDeals(requestBody);

	}
	
	@GetMapping("/getAllDeals/{page}/{size}")
	public ResponseEntity<?> getAllDeals(@ModelAttribute("employee") Employee employee, @PathVariable int page,@PathVariable int size) {

		return dealsService.getAllDeals(page ,size,employee);

	}

    
    
    
}
