package com.mahendratechnosoft.crm.controller;

import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mahendratechnosoft.crm.dto.AdminUpdateDto;
import com.mahendratechnosoft.crm.dto.EmployeeRegistrationDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Deals;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.LeadStatus;
import com.mahendratechnosoft.crm.entity.Leads;
import com.mahendratechnosoft.crm.repository.AdminRepository;
import com.mahendratechnosoft.crm.service.DealsService;
import com.mahendratechnosoft.crm.service.EmployeeService;
import com.mahendratechnosoft.crm.service.LeadService;


@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
    private AdminRepository adminRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DealsService dealsService;
	
	@Autowired
	private LeadService leadService;
	
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
    
    
    // deals APIs
    
	@PostMapping("/createDeals")
	public ResponseEntity<?> createDeals(@ModelAttribute("admin") Admin admin, @RequestBody Deals requestBody) {
		requestBody.setAdminId(admin.getAdminId());

		return dealsService.createDeals(requestBody);

	}
	
	
	@PostMapping("/updateDeals")
	public ResponseEntity<?> updateDeals(@ModelAttribute("admin") Admin admin, @RequestBody Deals requestBody) {
		requestBody.setAdminId(admin.getAdminId());

		return dealsService.updateDeals(requestBody);

	}
	
	@GetMapping("/getAllDeals/{page}/{size}")
	public ResponseEntity<?> getAllDeals(@ModelAttribute("admin") Admin admin, @PathVariable int page,@PathVariable int size) {

		return dealsService.getAllDeals(page ,size,admin);

	}
	
	
	// lead APis
	
	@PostMapping("/createLead")
	public ResponseEntity<?> createLead(@ModelAttribute("admin") Admin admin,@RequestBody Leads dto) {
		dto.setAdminId(admin.getAdminId());
		return leadService.createLead(dto);
	}
	
	
	
	@GetMapping("/getAllLeads/{page}/{size}")
	public ResponseEntity<?> getAllLeads(@ModelAttribute("admin") Admin admin,@PathVariable int page ,@PathVariable int size) {

	  return  leadService.getAllLeads(page, size,admin);
	}
    
	@PutMapping("/updateLead")
	public ResponseEntity<?> updateLead( @ModelAttribute("admin") Admin admin,@RequestBody Leads lead) {
		lead.setAdminId(admin.getAdminId());
		return leadService.updateLead(lead);
	}
	
	
	// lead status APIs

	@PostMapping("/addLeadStatus")
	public ResponseEntity<?> addLeadStatus(@ModelAttribute("admin") Admin admin, @RequestBody LeadStatus leadStatus) {

		try {

			leadStatus.setAdminId(admin.getAdminId());

			return leadService.addLeadStatus(leadStatus);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());

		}
	}

	@GetMapping("/getLeadStaus")
	public ResponseEntity<?> getLeadStatus(@ModelAttribute("admin") Admin admin) {

		try {

			return leadService.getLeadStatus(admin);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}
	}

	@DeleteMapping("/deleteLeadStatus/{leadStatusId}")
	public ResponseEntity<?> deleteLeadStatus(@PathVariable String leadStatusId) {

		try {
			return leadService.deleteLeadStatus(leadStatusId);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}
	}
		
	
    
    @GetMapping("/getAllEmployees/{page}/{size}")
    public ResponseEntity<?> getEmployees(
            @ModelAttribute("admin") Admin admin,
            @PathVariable int page,
            @PathVariable int size,
            @RequestParam(name = "search", required = false) String searchTerm
            ) {
    	Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "employeeId"));
        Page<Employee> employeePage = employeeService.getEmployeesByAdmin(admin, searchTerm, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("employeeList", employeePage.getContent());
        response.put("currentPage", employeePage.getNumber());
        response.put("totalItems", employeePage.getTotalElements());
        response.put("totalPages", employeePage.getTotalPages());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/getEmployeeById/{employeeId}")
    public ResponseEntity<?> getEmployeeByIdByAdmin(
            @ModelAttribute("admin") Admin admin,
            @PathVariable String employeeId) {
        try {
            Employee employee = employeeService.getEmployeeById(employeeId);

            // Optional: Check if employee belongs to the same admin (security measure)
            if (employee.getAdmin() != null && !employee.getAdmin().getAdminId().equals(admin.getAdminId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to access this employee's details.");
            }
    
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while fetching employee details.");
        }
    }
    
}
