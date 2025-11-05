package com.mahendratechnosoft.crm.controller;

import java.util.Base64;
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
import com.mahendratechnosoft.crm.dto.InvoiceDto;
import com.mahendratechnosoft.crm.dto.ProformaInvoiceDto;
import com.mahendratechnosoft.crm.dto.ProposalDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Contacts;
import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.Deals;
import com.mahendratechnosoft.crm.entity.Department;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.LeadStatus;
import com.mahendratechnosoft.crm.entity.Leads;
import com.mahendratechnosoft.crm.entity.Role;
import com.mahendratechnosoft.crm.repository.AdminRepository;
import com.mahendratechnosoft.crm.service.ContactsService;
import com.mahendratechnosoft.crm.service.CustomerService;
import com.mahendratechnosoft.crm.service.DealsService;
import com.mahendratechnosoft.crm.service.EmployeeService;
import com.mahendratechnosoft.crm.service.LeadService;
import com.mahendratechnosoft.crm.service.SalesService;
import com.mahendratechnosoft.crm.service.SettingServices;


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
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ContactsService contactsService;
	
	@Autowired
	private SettingServices settingServices;
	
	@Autowired
    private SalesService salesService;	
	
	
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
        adminToUpdate.setGstNumber(updateDto.getGstNumber());
        adminToUpdate.setBankName(updateDto.getBankName());
        adminToUpdate.setAccountHolderName(updateDto.getAccountHolderName());
        adminToUpdate.setAccountNumber(updateDto.getAccountNumber());
        adminToUpdate.setIfscCode(updateDto.getIfscCode());
        

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
        
        if (updateRequest.getModuleAccess() != null) {
            updateRequest.getModuleAccess().setEmployeeId(updateRequest.getEmployeeId()); 
            updateRequest.getModuleAccess().setAdminId(admin.getAdminId()); 
        }
        
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
		return leadService.createLead(dto,admin.getCompanyName());
	}
	
	
	@GetMapping("/getLeadById/{leadId}")
	public ResponseEntity<?> getLeadById(@PathVariable String leadId) {
		
		return leadService.getLeadById(leadId);
	}
	

	@DeleteMapping("/deleteLead/{id}")
	public String deleteLead(@PathVariable String id) {
		
		return leadService.deleteLead(id);
	}
	
	
	@GetMapping("/getAllLeads/{page}/{size}")
	public ResponseEntity<?> getAllLeads(@ModelAttribute("admin") Admin admin,@PathVariable int page ,@PathVariable int size,@RequestParam(required = false) String search,@RequestParam(required = false) String leadStatus) {

	  return  leadService.getAllLeads(page, size,admin,leadStatus,search);
	}
    
	@PutMapping("/updateLead")
	public ResponseEntity<?> updateLead( @ModelAttribute("admin") Admin admin,@RequestBody Leads lead) {
		lead.setAdminId(admin.getAdminId());
		return leadService.updateLead(lead,admin.getCompanyName());
	}
	
	
	@PutMapping("/updateLeadStatus")
	public ResponseEntity<?> updateLeadStatus(@ModelAttribute("admin") Admin admin,@RequestBody Map<String, String> request) {
		String leadId = request.get("leadId");
		String status = request.get("status");
		return leadService.updateLeadStatus(leadId,status,admin.getCompanyName());
	}
	
	@GetMapping("/getModuleActivity/{moduleId}/{moduleType}")
	public ResponseEntity<?> getModuleActivity(@PathVariable String moduleId,@PathVariable String moduleType) {

		return leadService.getModuleActivit(moduleId,moduleType);
	}
	
	
	@GetMapping("/getLeadNameAndId")
	public ResponseEntity<?> getLeadNameAndId(@ModelAttribute("admin") Admin admin) {

	  return  leadService.getLeadNameAndId(admin);
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
    
    
    @GetMapping("/getEmployeeNameAndId")
    public ResponseEntity<?> getEmployeeByIdByAdmin( @ModelAttribute("admin") Admin admin)   {
   
    	return employeeService.getEmployeeNameAndId(admin);
    }
    
    
    @PostMapping("/createCustomer")
	public ResponseEntity<?> createCustomer(@ModelAttribute("admin") Admin admin,@RequestBody Customer customer) {

		try {
            customer.setAdminId(admin.getAdminId());
			return customerService.createCustomer(customer);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}
	}
    
    @GetMapping("/getCustomerById/{customerId}")
 	public ResponseEntity<?> getCustomerById(@PathVariable("customerId") String customerId) {

 		try {
            
 			return customerService.getCustomerById(customerId);

 		} catch (Exception e) {
 			e.printStackTrace();
 			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
 		}
 	}
    
    @PutMapping("/updateCustomer")
	public ResponseEntity<?> udpateCustomer(@ModelAttribute("admin") Admin admin,@RequestBody Customer customer) {

		try {
            customer.setAdminId(admin.getAdminId());
			return customerService.updateCustomer(customer);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}
	}
    
    
	@GetMapping("/getAllCustomer/{page}/{size}")
	public ResponseEntity<?> getAllCustomer(@ModelAttribute("admin") Admin admin, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search) {

		return customerService.getAllCustomer(page ,size,admin,search);

	}
	
	@GetMapping("/getCustomerListWithNameAndId")
	public ResponseEntity<?> getCustomerListWithNameAndId(@ModelAttribute("admin") Admin admin) {

		return customerService.getCustomerListWithNameAndId(admin);

	}
	
	
    @PutMapping("/updateCustomerStatus/{customerId}/{status}")
	public ResponseEntity<?> updateCustomerStatus(@PathVariable("customerId") String customerId,@PathVariable("status") boolean status) {

		try {
           
			return customerService.updateCustomerStatus(customerId,status);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}
	}
    
	@GetMapping("/getAllCustomerStatusAndCount")
	public ResponseEntity<?> getAllCustomerStatusAndCount(@ModelAttribute("admin") Admin admin) {

		return customerService.getAllCustomerStatusAndCount(admin);

	}
    
    
	
	@PostMapping("/createContact")
	public ResponseEntity<?> createContact(@RequestBody Contacts contacts) {
          
		return contactsService.createContact(contacts);
		
	}
	
	@PutMapping("/updateContact")
	public ResponseEntity<?> updateContact(@RequestBody Contacts contacts) {

		return contactsService.updateContact(contacts);

	}
    
	
	@GetMapping("/getContacts/{customerId}")
	public ResponseEntity<?> getContacts(@PathVariable String customerId,@RequestParam(defaultValue = "") String name) {
      
		return contactsService.getContacts(customerId, name);
	}
	
	
	@DeleteMapping("/deleteContacts/{contactId}")
	public ResponseEntity<?> deleteContacts(@PathVariable String contactId) {
		return contactsService.deleteContacts(contactId);
	}
	
	
    @PostMapping("/createDepartment")
    public ResponseEntity<?> createDepartment(
    		@ModelAttribute Admin admin,
    		@RequestBody Department department
    		){
    	department.setAdminId(admin.getAdminId());
    	Department responce = settingServices.createDepartment(department);
    	return ResponseEntity.ok(responce);
    }
    
    @GetMapping("/getAllDepartment")
    public ResponseEntity<?> getAllDepartmetn(
        @ModelAttribute Admin admin,
        @RequestParam(value = "name", required = false) String name
    ) {
        List<Department> allDepartmentByAdmin = settingServices.getAllDepartmentByAdmin(admin.getAdminId(), name);
        return ResponseEntity.ok(allDepartmentByAdmin);
    }
    
    @PostMapping("/createRole")
    public ResponseEntity<?> creteRole(@ModelAttribute Admin admin,
    		@RequestBody Role role){
    	role.setAdminId(admin.getAdminId());
    	Role responce = settingServices.createRole(role);
    	return ResponseEntity.ok(responce);
    }
    
    @GetMapping("/getRoleByDepartment/{departemntId}")
    public ResponseEntity<?> getAllRoleByDepartemntId(@PathVariable String departemntId){
    	List<Role> allRoleByDepartemntId = settingServices.getAllRoleByDepartemntId(departemntId);
    	return ResponseEntity.ok(allRoleByDepartemntId);
    }
    
    @DeleteMapping("/deleteDepartment/{departmentId}")
    public ResponseEntity<?> deleteDepartment(@PathVariable String departmentId) {
        settingServices.deleteDepartmentAndRoles(departmentId);
        return ResponseEntity.ok("Department deleted successfully..");
    }
    
    @DeleteMapping("/deleteRole/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable String roleId) {
        settingServices.deleteRole(roleId);
        return ResponseEntity.ok("Role deleted successfully..");
    }
	
	
	@PostMapping("/createProposal")
	public ResponseEntity<?> createProposal(@ModelAttribute("admin") Admin admin,@RequestBody ProposalDto request) {
		
		return salesService.createProposal(request,admin);
		
	}
	
	@PutMapping("/updateProposal")
	public ResponseEntity<?> updateProposal(@ModelAttribute("admin") Admin admin,@RequestBody ProposalDto request) {
		
		return salesService.updateProposal(request,admin);
		
	}
	
	
	@GetMapping("/getProposalById/{proposalId}")
	public ResponseEntity<?> getProposalById(@PathVariable String proposalId) {
		
		return salesService.getProposalById(proposalId);
		
	}
	
	@GetMapping("/getAllProposal/{page}/{size}")
	public ResponseEntity<?> getAllProposal(@ModelAttribute("admin") Admin admin, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search) {

		return salesService.getAllProposal(page ,size,admin,search);

	}
	
	@GetMapping("/getProposalByModuleType/{moduleTypeId}/{moduleType}")
	public ResponseEntity<?> getProposalByModuleType(@PathVariable String moduleTypeId,@PathVariable String moduleType) {

		return salesService.getProposalByModuleType(moduleTypeId,moduleType);

	}
	
	
	
	//Invoice API
	@PostMapping("/createInvoice")
	public ResponseEntity<?> createInvoice(@ModelAttribute("admin") Admin admin,@RequestBody InvoiceDto request) {
		
		return salesService.createInvoice(request,admin);
		
	}
	
	@PutMapping("/updateInvoice")
	public ResponseEntity<?> updateInvoice(@ModelAttribute("admin") Admin admin,@RequestBody InvoiceDto request) {
		
		return salesService.updateInvoice(request,admin);
		
	}
	
	@GetMapping("/getInvoiceById/{invoiceId}")
	public ResponseEntity<?> getInvoiceById(@PathVariable String invoiceId) {
		
		return salesService.getInvoiceById(invoiceId);
		
	}
	
	
	@GetMapping("/getAllInvoice/{page}/{size}")
	public ResponseEntity<?> getAllInvoice(@ModelAttribute("admin") Admin admin, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search) {

		return salesService.getAllInvoice(page ,size,admin,search);

	}
	
	
	@GetMapping("/getInvoiceByCustomerId/{customerId}")
	public ResponseEntity<?> getInvoiceByCustomerId( @PathVariable String customerId) {

		return salesService.getInvoiceByCustomerId(customerId);

	}
	
	
	@GetMapping("/checkCustomerIsExist")
	public ResponseEntity<?> getInvoiceByCustomerId(@RequestBody Map<String, String> request ) {

		String companyName = request.get("companyName");
        return customerService.checkCustomerExist(companyName);

	}
	
	
	@DeleteMapping("/deleteProposalContent")
	public ResponseEntity<?> deleteProposalContent(@RequestBody List<String> proposalContentIds) {
	    if (proposalContentIds == null || proposalContentIds.isEmpty()) {
	        return ResponseEntity.badRequest().body(Map.of("message", "No proposal content IDs provided"));
	    }
	    salesService.deleteProposalContent(proposalContentIds);
	    return ResponseEntity.ok("Proposals deleted successfully");
	}
	
	
	// proforma-invoice api
	
	@PostMapping("/createProformaInvoice")
	public ResponseEntity<?> createProformaInvoice(@ModelAttribute("admin") Admin admin,@RequestBody ProformaInvoiceDto request) {
		 
		return salesService.createProformaInvoice(request,admin);
		
	}
	
	
	@PutMapping("/updateProformaInvoice")
	public ResponseEntity<?> updateProformaInvoice(@ModelAttribute("admin") Admin admin,@RequestBody ProformaInvoiceDto request) {
		
		return salesService.updateProformaInvoice(request,admin);
		
	}
	
	@GetMapping("/getProformaInvoiceById/{proformaInvoiceId}")
	public ResponseEntity<?> getProformaInvoiceById(@PathVariable String proformaInvoiceId) {
		
		return salesService.getProformaInvoiceById(proformaInvoiceId);
		
	}
	
	
	
	@GetMapping("/getAllProformaInvoice/{page}/{size}")
	public ResponseEntity<?> getAllProformaInvoice(@ModelAttribute("admin") Admin admin, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search) {

		return salesService.getAllProformaInvoice(page ,size,admin,search);

	}
	
	
	@DeleteMapping("/deleteProformaInvoiceContent")
	public ResponseEntity<?> deleteProformaInvoiceContent(@RequestBody List<String> proformaInvoiceContentIds) {
	    if (proformaInvoiceContentIds == null || proformaInvoiceContentIds.isEmpty()) {
	        return ResponseEntity.badRequest().body(Map.of("message", "No Proforma Invoice content IDs provided"));
	    }
	    salesService.deleteProformaInvoiceContent(proformaInvoiceContentIds);
	    return ResponseEntity.ok("Proposals deleted successfully");
	}

}
