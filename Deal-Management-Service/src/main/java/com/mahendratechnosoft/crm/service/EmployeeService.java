package com.mahendratechnosoft.crm.service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.dto.EmployeeRegistrationDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.repository.EmployeeRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Transactional
    public Employee createEmployee(EmployeeRegistrationDto request, Admin admin) {
        
        // 1. Check if user email already exists
        if (userRepository.existsByLoginEmail(request.getLoginEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // 2. Create and save the new User
        User newUser = new User();
        newUser.setLoginEmail(request.getLoginEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole("ROLE_EMPLOYEE"); // Or "EMPLOYEE"
        newUser.setExpiryDate(LocalDateTime.now().plusYears(1)); // Example expiry
        
        User savedUser = userRepository.save(newUser);

        // 3. Create the new Employee
        Employee newEmployee = new Employee();
        newEmployee.setLoginEmail(request.getLoginEmail());
        newEmployee.setName(request.getName());
        newEmployee.setPhone(request.getPhone());
        newEmployee.setAddress(request.getAddress());
        newEmployee.setGender(request.getGender());
        newEmployee.setDescription(request.getDescription());
        newEmployee.setDepartmentId(request.getDepartmentId());
        newEmployee.setDepartmentName(request.getDepartmentName());
        newEmployee.setRoleId(request.getRoleId());
        newEmployee.setRoleName(request.getRoleName());
        newEmployee.setAdmin(admin); // The logged-in admin

        // 5. Handle the profile image (same as your Admin controller)
        String base64Image = request.getProfileImageBase64();
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                newEmployee.setProfileImage(imageBytes);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid Base64 image format", e);
            }
        }

        // 6. Save the Employee
        Employee savedEmployee = employeeRepository.save(newEmployee);
        
        // 7. Return the safe DTO
        return savedEmployee;
    }
	
	public Employee updateEmployee(Employee employee) {
	    Employee savedEmployee = employeeRepository.save(employee);
	    return savedEmployee;
	}
	
	public Page<Employee> getEmployeesByAdmin(Admin admin, String searchTerm, Pageable pageable) {
        
        // Simply call the new repository method
        return employeeRepository.findByAdminWithPagination(admin, searchTerm, pageable);
    }
	
	public Employee getEmployeeById(String employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));
    }
	
	public boolean emailExists(String email) {
        return employeeRepository.existsByLoginEmail(email);
    }
}
	