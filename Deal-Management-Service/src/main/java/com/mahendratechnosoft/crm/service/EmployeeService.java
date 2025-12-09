package com.mahendratechnosoft.crm.service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.dto.EmployeeRegistrationDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
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
        newEmployee.setCountry(request.getCountry());
        newEmployee.setState(request.getState());
        newEmployee.setCity(request.getCity());
        newEmployee.setAdmin(admin); // The logged-in admin
        if(request.getModuleAccess() != null) {
        	newEmployee.setModuleAccess(request.getModuleAccess());
        }
        
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
        
        if(savedEmployee.getModuleAccess() !=null) {
        	ModuleAccess access = savedEmployee.getModuleAccess();
        	access.setAdminId(admin.getAdminId());
        	access.setEmployeeId(savedEmployee.getEmployeeId());
        	employeeRepository.save(savedEmployee);
        }
        

        // 7. Return the safe DTO
        return savedEmployee;
    }
	
	public Employee updateEmployee(Employee employee) {
	    Employee savedEmployee = employeeRepository.save(employee);
	    return savedEmployee;
	}
	
	
	public String deleteEmployee(String employeeId) {
	   employeeRepository.deleteById(employeeId);
	    return "Delete Employee Successfully";
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
	
	public ResponseEntity<?> getEmployeeNameAndId(Object loginUser) {
	    try {
	        Admin admin = null;

	        if (loginUser instanceof Admin a) {
	            admin = a;
	        } else if (loginUser instanceof Employee employee) {
	            admin = employee.getAdmin();
	        }

	       

	        if (admin == null) {
	            return ResponseEntity.badRequest().body("Admin not found for the logged-in user");
	        }

	        List<Object[]> employees = employeeRepository.EmployeeNameAndIdByAdminId(admin);

	        List<Map<String, Object>> result = employees.stream()
	                .map(e -> Map.of("employeeId", e[0], "name", e[1]))
	                .toList();

	        return ResponseEntity.ok(result);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error: " + e.getMessage());
	    }
	}
	
	
	public ResponseEntity<String> updatePassword(String loginEmail, String password) {

		Optional<User> user = userRepository.findByLoginEmail(loginEmail);

		if(user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
		}
		
		user.get().setLoginEmail(loginEmail);
		user.get().setPassword(passwordEncoder.encode(password));

		userRepository.save(user.get());

		return ResponseEntity.ok("Password Updated !");

	}

}
	