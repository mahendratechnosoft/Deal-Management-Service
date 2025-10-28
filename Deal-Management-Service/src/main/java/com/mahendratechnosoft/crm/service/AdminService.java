package com.mahendratechnosoft.crm.service;

import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.dto.EmployeeRegistrationDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.repository.AdminRepository;
import com.mahendratechnosoft.crm.repository.EmployeeRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
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
}
