package com.mahendratechnosoft.crm.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.mahendratechnosoft.crm.dto.AdminUpdateDto;
import com.mahendratechnosoft.crm.dto.EmployeeRegistrationDto;
import com.mahendratechnosoft.crm.dto.EmployeeUpdateDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.repository.AdminRepository;
import com.mahendratechnosoft.crm.service.EmployeeService;


@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
    private AdminRepository adminRepository;
	
	@Autowired
	private EmployeeService employeeService;
    
    @ModelAttribute("admin")
    public Admin getCurrentlyLoggedInAdmin(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("User not authenticated");
        }
        
        String loginEmail = authentication.getName();
        return adminRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new RuntimeException("Admin profile not found for user: " + loginEmail));
    }
    
    @GetMapping("/getAdminInfo")
    public ResponseEntity<Admin> getAdminInfo(@ModelAttribute("admin") Admin admin) {
        return ResponseEntity.ok(admin);
    }
    
    @PutMapping("/updateAdminInfo") // Note: Different path
    public ResponseEntity<Admin> updateAdminInfo(
            @ModelAttribute("admin") Admin adminToUpdate,
            @RequestBody AdminUpdateDto updateDto) {

        // Update entity from DTO
        adminToUpdate.setName(updateDto.getName());
        adminToUpdate.setPhone(updateDto.getPhone());
        adminToUpdate.setAddress(updateDto.getAddress());
        adminToUpdate.setCompanyName(updateDto.getCompanyName());
        adminToUpdate.setDescription(updateDto.getDescription());
        
        String base64Image = updateDto.getLogoBase64();
        if (base64Image != null && !base64Image.isEmpty()) {
        	try {
        		byte[] logoBytes = Base64.getDecoder().decode(base64Image);
        		adminToUpdate.setLogo(logoBytes);
        	} catch (IllegalArgumentException e) {
        		throw new RuntimeException("Invalid Base64 image format", e);
        	}
        }

        Admin updatedAdmin = adminRepository.save(adminToUpdate);
        return ResponseEntity.ok(updatedAdmin);
    }
    
    @PostMapping("/createEmployee")
    public ResponseEntity<Employee> createEmployee(
            @ModelAttribute("admin") Admin admin,
            @RequestBody EmployeeRegistrationDto employeeRequest) {
        
    	Employee newEmployee = employeeService.createEmployee(employeeRequest, admin);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }
    
    @PutMapping("/updateEmployee")
    public ResponseEntity<Employee> updateEmployeeByAdmin(
    		@ModelAttribute("admin") Admin admin,
            @RequestBody Employee updateRequest) {
        updateRequest.setAdmin(admin);
        Employee updatedEmployee = employeeService.updateEmployee(updateRequest);
        return ResponseEntity.ok(updatedEmployee);
    }

}
