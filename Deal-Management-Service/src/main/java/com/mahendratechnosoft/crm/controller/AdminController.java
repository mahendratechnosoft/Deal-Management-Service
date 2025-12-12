package com.mahendratechnosoft.crm.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.multipart.MultipartFile;
import com.mahendratechnosoft.crm.config.UserDetailServiceImp;
import com.mahendratechnosoft.crm.dto.AdminRegistrationDto;
import com.mahendratechnosoft.crm.dto.AdminUpdateDto;
import com.mahendratechnosoft.crm.dto.CreateAMC;
import com.mahendratechnosoft.crm.dto.EmployeeRegistrationDto;
import com.mahendratechnosoft.crm.dto.InvoiceDto;
import com.mahendratechnosoft.crm.dto.ProformaInvoiceDto;
import com.mahendratechnosoft.crm.dto.ProposalDto;
import com.mahendratechnosoft.crm.dto.TaskDto;
import com.mahendratechnosoft.crm.dto.UserCredential;
import com.mahendratechnosoft.crm.dto.Hospital.AllocationDetailsDTO;
import com.mahendratechnosoft.crm.entity.AMC;
import com.mahendratechnosoft.crm.entity.AMCDomainHistory;
import com.mahendratechnosoft.crm.entity.AMCHistory;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Attendance;
import com.mahendratechnosoft.crm.entity.Contacts;
import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.Deals;
import com.mahendratechnosoft.crm.entity.Department;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.Items;
import com.mahendratechnosoft.crm.entity.LeadStatus;
import com.mahendratechnosoft.crm.entity.Leads;
import com.mahendratechnosoft.crm.entity.Payments;
import com.mahendratechnosoft.crm.entity.Role;
import com.mahendratechnosoft.crm.entity.Task;
import com.mahendratechnosoft.crm.entity.TaskAttachment;
import com.mahendratechnosoft.crm.entity.TaskComments;
import com.mahendratechnosoft.crm.entity.Hospital.DonorBloodReport;
import com.mahendratechnosoft.crm.entity.Hospital.DonorFamilyInfo;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;
import com.mahendratechnosoft.crm.entity.Hospital.FamilyInfo;
import com.mahendratechnosoft.crm.entity.Hospital.FamilyVialAllocation;
import com.mahendratechnosoft.crm.entity.Hospital.SampleReport;
import com.mahendratechnosoft.crm.entity.Hospital.SemenReport;
import com.mahendratechnosoft.crm.enums.TaskStatus;
import com.mahendratechnosoft.crm.repository.AdminRepository;
import com.mahendratechnosoft.crm.repository.ProformaInvoiceContentRepository;
import com.mahendratechnosoft.crm.service.AMCService;
import com.mahendratechnosoft.crm.service.AttendanceService;
import com.mahendratechnosoft.crm.service.ContactsService;
import com.mahendratechnosoft.crm.service.CustomerService;
import com.mahendratechnosoft.crm.service.DealsService;
import com.mahendratechnosoft.crm.service.DonorService;
import com.mahendratechnosoft.crm.service.EmployeeService;
import com.mahendratechnosoft.crm.service.ExcelService;
import com.mahendratechnosoft.crm.service.LeadService;
import com.mahendratechnosoft.crm.service.SalesService;
import com.mahendratechnosoft.crm.service.SettingServices;
import com.mahendratechnosoft.crm.service.TaskService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ProformaInvoiceContentRepository proformaInvoiceContentRepository;

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
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
    private ExcelService excelService;
	
	@Autowired
	private DonorService donorService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private AMCService amcService;

    AdminController(ProformaInvoiceContentRepository proformaInvoiceContentRepository) {
        this.proformaInvoiceContentRepository = proformaInvoiceContentRepository;
    }
	
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
        adminToUpdate.setCompanyEmail(updateDto.getCompanyEmail());
        adminToUpdate.setPanNumber(updateDto.getPanNumber());
        

        String base64Image = updateDto.getLogoBase64();
        if (base64Image != null) {
        	try {
        		byte[] logoBytes = Base64.getDecoder().decode(base64Image);
        		adminToUpdate.setLogo(logoBytes);
        	} catch (IllegalArgumentException e) {
        		throw new RuntimeException("Invalid Base64 image format", e);
        	}
        }
        
        String signatureBase64 = updateDto.getSignatureBase64();
        if (signatureBase64 != null ) {
        	try {
        		byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);
        		adminToUpdate.setCompanySignature(signatureBytes);
        	} catch (IllegalArgumentException e) {
        		throw new RuntimeException("Invalid Base64 image format", e);
        	}
        }
        
        String stampBase64 = updateDto.getStampBase64();
        if (stampBase64 != null ) {
        	try {
        		byte[] stampBytes= Base64.getDecoder().decode(stampBase64);
        		adminToUpdate.setCompanyStamp(stampBytes);
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
    
	@DeleteMapping("/deleteEmployee/{employeeId}")
	public String deleteEmployee(@PathVariable String employeeId) {

		return employeeService.deleteEmployee(employeeId);

	}
	
	@PutMapping("/updateEmployeePassword")
	public ResponseEntity<String> updateEmployeePassword(@RequestBody UserCredential request) {

		return employeeService.updatePassword(request.getLoginEmail(),request.getPassword());

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
	public ResponseEntity<?> getLeadById(@ModelAttribute("admin") Admin admin,@PathVariable String leadId) {
		
		return leadService.getLeadById(leadId,admin);
	}
	

	@DeleteMapping("/deleteLead/{id}")
	public String deleteLead(@PathVariable String id) {
		
		return leadService.deleteLead(id);
	}
	
	
	@GetMapping("/getAllLeads/{page}/{size}")
	public ResponseEntity<?> getAllLeads(@ModelAttribute("admin") Admin admin,
			@PathVariable int page ,
			@PathVariable int size,
			@RequestParam(required = false) String search,
			@RequestParam(required = false) String leadStatus,
			@RequestParam(required = false) String startDate,
	        @RequestParam(required = false) String endDate,@RequestParam(required = false) String followUpDate) {

	  return  leadService.getAllLeads(page, size,admin,leadStatus,search,startDate, endDate,followUpDate);
	}
	
	@GetMapping("/getLeadStatusAndCount")
	public ResponseEntity<?> getLeadStatusAndCount(@ModelAttribute("admin") Admin admin) {

	  return  leadService.getLeadStatusAndCount(admin);
	}
	
	@GetMapping("/getLeadFollowUp")
	public ResponseEntity<?> getLeadFollowUp(@ModelAttribute("admin") Admin admin,@RequestParam(required = false) String followUpdate) {
	  return  leadService.getLeadFollowUp(admin,followUpdate);
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

		return salesService.getAllProformaAsInvoice(page ,size,admin,search);

	}
	
	
	@GetMapping("/getInvoiceByCustomerId/{customerId}")
	public ResponseEntity<?> getInvoiceByCustomerId( @PathVariable String customerId) {

		return salesService.getInvoiceByCustomerId(customerId);

	}
	
	
	@GetMapping("/checkCustomerIsExist/{companyName}")
	public ResponseEntity<?> checkCustomerIsExist(@ModelAttribute("admin") Admin admin, @PathVariable String companyName) {
        return customerService.checkCustomerExist(admin,companyName);

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
	public ResponseEntity<?> getAllProformaInvoice(@ModelAttribute("admin") Admin admin, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search,@RequestParam(required = false) Date startDate,@RequestParam(required = false) Date endDate) {

		return salesService.getAllProformaInvoice(page ,size,admin,search,startDate,endDate);

	}
	
	
	@DeleteMapping("/deleteProformaInvoiceContent")
	public ResponseEntity<?> deleteProformaInvoiceContent(@RequestBody List<String> proformaInvoiceContentIds) {
	    if (proformaInvoiceContentIds == null || proformaInvoiceContentIds.isEmpty()) {
	        return ResponseEntity.badRequest().body(Map.of("message", "No Proforma Invoice content IDs provided"));
	    }
	    salesService.deleteProformaInvoiceContent(proformaInvoiceContentIds);
	    return ResponseEntity.ok("Proposals deleted successfully");
	}
	
	// Payments API
	
	
	@PostMapping("/createPayment")
	public ResponseEntity<?> createPayment(@ModelAttribute("admin") Admin admin,@RequestBody Payments request) {
		 
		return salesService.createPayment(request,admin);
		
	}
	
	@PutMapping("/updatePayment")
	public ResponseEntity<?> updatePayment(@RequestBody Payments request) {
		 
		return salesService.updatePayment(request);
		
	}
	
	@GetMapping("/getPaymentById/{paymentId}")
	public ResponseEntity<?> getPaymentById(@PathVariable String paymentId) {
		
		return salesService.getPaymentById(paymentId);
		
	}
	
	
	@GetMapping("/getAllPayments/{page}/{size}")
	public ResponseEntity<?> getAllPayments(@ModelAttribute("admin") Admin admin, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search) {

		return salesService.getAllPayments(page ,size,admin,search);

	}
	
	@GetMapping("/getPaymentsByProformaInvoice/{proformcaInvoiceId}")
	public ResponseEntity<?> getPaymentByProformaInvoice(@PathVariable String proformcaInvoiceId) {
		
		return salesService.getPaymentByProformaInvoice(proformcaInvoiceId);
		
	}
	
	@GetMapping("/getNextProposalNumber")
    public ResponseEntity<Integer> getNextProposalNumber(@ModelAttribute Admin admin) {
        int nextNumber = salesService.getNextProposalNumberForAdmin(admin.getAdminId());
        return ResponseEntity.ok(nextNumber);
    }
	
	@GetMapping("/isProposalNumberUnique/{proposalNumber}")
    public ResponseEntity<Boolean> checkProposalNumberUnique(
    		@ModelAttribute Admin admin,
            @PathVariable int proposalNumber) {

        boolean isUnique = salesService.isProposalNumberUnique(admin.getAdminId(), proposalNumber);
        return ResponseEntity.ok(isUnique);
    }
	
	
	@GetMapping("/getAllPerforma")
    public ResponseEntity<?> getAllPerforma(@ModelAttribute Admin admin) {
     
        return ResponseEntity.ok(salesService.getAllPerforma(admin.getAdminId()));
    }
	
	@GetMapping("/getAttendanceBetween")
	public ResponseEntity<?> getAttendanceBetween(@ModelAttribute("admin") Admin admin, @RequestParam String fromDate,@RequestParam(required = false) String employeeId,
			@RequestParam String toDate) {
		return attendanceService.getAttendanceBetween(admin, fromDate, toDate,employeeId);
	}
	
	@PutMapping("/updateAttendance")
    public ResponseEntity<?> updateAttendance(@ModelAttribute("admin") Admin admin,@RequestBody Attendance attendance) {
     
        return  attendanceService.updateAttendance(admin ,attendance);
    }
	
	@DeleteMapping("/deleteAttendance/{attendanceId}")
    public ResponseEntity<?> updateAttendance(@PathVariable String attendanceId ) {
     
        return  attendanceService.deleteAttendance(attendanceId);
    }
	
	@GetMapping("/getLoginStatusAllEmployee/{date}")
    public ResponseEntity<?> getLoginStatusAllEmployee(@ModelAttribute Admin admin ,@PathVariable String date) {
      
        return attendanceService.getLoginStatusAllEmployee(admin.getAdminId(),date);
    }
	
	

	@GetMapping("/getAttendanceExcelExport")
	public ResponseEntity<?> getAttendanceBetweenExcelExport(@ModelAttribute("admin") Admin admin, @RequestParam String fromDate,
			@RequestParam String toDate,@RequestParam(required = false) String employeeId,String attendanceType,String monthName) {
		return attendanceService.getAttendanceExcelExport(admin, fromDate, toDate,employeeId,attendanceType,monthName);
	}
	
	@GetMapping("/getNextProformaNumber")
    public ResponseEntity<Integer> getNextProformaNumber(@ModelAttribute Admin admin) {
        int nextNumber = salesService.getNextProformaNumberForAdmin(admin.getAdminId());
        return ResponseEntity.ok(nextNumber);
    }
	
	@GetMapping("/isProformaNumberUnique/{proformaNumber}")
    public ResponseEntity<Boolean> checkProformaNumberUnique(
    		@ModelAttribute Admin admin,
            @PathVariable int proformaNumber) {

        boolean isUnique = salesService.isProformaNumberUnique(admin.getAdminId(), proformaNumber);
        return ResponseEntity.ok(isUnique);
    }
	
	@GetMapping("/leadTemplateExcel")
    public void downloadExcel(HttpServletResponse response) throws Exception {
        excelService.generateExcel(response);
    }
	
	
	@PostMapping("/importLeads")
	public ResponseEntity<?> importLeads(@RequestParam("file") MultipartFile file,@ModelAttribute("admin") Admin admin) {
	    try {
	        List<Leads> imported = excelService.importLeadsFromExcel(file,admin);
	        return ResponseEntity.ok("Successfully imported " + imported.size() + " leads.");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Failed to import: " + e.getMessage());
	    }
	}
	
	
	// donor code start
	
	@PostMapping("/createDonor")
	public ResponseEntity<?> createDonar(@ModelAttribute("admin") Admin admin,@RequestBody Donors request) {
		 request.setAdminId(admin.getAdminId());
		 request.setCreatedBy(admin.getName());
		return donorService.createDonor(request);
		
	}
	
	
	
	@GetMapping("/getAllDonorList/{page}/{size}")
	public ResponseEntity<?> getAllDonorList(@ModelAttribute("admin") Admin admin, @PathVariable int page,@PathVariable int size,
			@RequestParam(required = false) String search,@RequestParam(required = false) String status) {

		return donorService.getAllDonorList(page ,size,admin,search,status);

	}
	
	@GetMapping("/getDonorStatusCount")
    public ResponseEntity<?> getDonorStatusCount(@ModelAttribute("admin") Admin admin) {
     
        return  donorService.getDonorStatusCount(admin);
    }
	
	
	

	@GetMapping("/getDonorById/{donorId}")
    public ResponseEntity<?> getDonorById(@PathVariable String donorId ) {
     
        return  donorService.getDonarById(donorId);
    }
	
	@PutMapping("/updateDonor")
	public ResponseEntity<?> updateeDonor(@ModelAttribute("admin") Admin admin,@RequestBody Donors request) {
		return donorService.updateDonor(admin,request);
		
	}
	
	
	@PutMapping("/updateDonorStatus")
	public ResponseEntity<?> updateDonorStatus(@ModelAttribute("admin") Admin admin,@RequestBody Map<String,String> request) {
		
		return donorService.updateDonorStatus(request);
		
	}
	
	@GetMapping("/getDonorFamilyInfo/{donorId}")
    public ResponseEntity<?> getDonorFamilyInfo(@PathVariable String donorId ) {
     
        return  donorService.getDonorFamilyInfo(donorId);
    }
	
	
	@PutMapping("/updateDonorFamilyInfo")
	public ResponseEntity<?> updateDonorFamilyInfo(@RequestBody List<DonorFamilyInfo> request) {
		
		return donorService.updateDonorFamilyInfo(request);
		
	}
	
	@DeleteMapping("/deleteDonorFamilyInfo/{donorFamilyId}")
    public ResponseEntity<?> deleteDonorFamilyInfo(@PathVariable String donorFamilyId ) {
     
        return  donorService.deleteDonorFamilyInfo(donorFamilyId);
    }
	
	@PutMapping("/updateDonorBloodReport")
	public ResponseEntity<?> updateDonorBloodReport(@RequestBody List<DonorBloodReport> request) {
		
		return donorService.updateDonorBloodReport(request);
		
	}
	
	
	@GetMapping("/getDonorBloodReport/{donorId}")
    public ResponseEntity<?> getDonorBloodReport(@PathVariable String donorId ) {
     
        return  donorService.getDonorBloodReport(donorId);
    }
	
	@DeleteMapping("/deleteDonorBloodReport/{bloodReportId}")
    public ResponseEntity<?> deleteDonorBloodReport(@PathVariable String bloodReportId ) {
     
        return  donorService.deleteDonorBloodReport(bloodReportId);
    }
	
	
	@PutMapping("/updateSemenReport")
	public ResponseEntity<?> updateSemenReport(@RequestBody List<SemenReport> request) {
		
		return donorService.updateDonorSemenReport(request);
		
	}
	
	@PutMapping("/updateSampleReport")
	public ResponseEntity<?> updateSample(@RequestBody SampleReport request) {
		
		return donorService.updateDonorSample(request);
		
	}

	
	
	@GetMapping("/getSemenReportByDonorId/{donorId}")
    public ResponseEntity<?> getSemenReportByDonorId(@PathVariable String donorId ) {
     
        return  donorService.getSemenReportByDonorId(donorId);
    }

	
	@GetMapping("/getSampleReportByDonorId/{donorId}")
    public ResponseEntity<?> getSampleReportByDonorId(@PathVariable String donorId ) {
     
        return  donorService.getSampleReportByDonorId(donorId);
    }
	
	
	@PostMapping("/createFamilyInfo")
	public ResponseEntity<?> createFamilyInfo(@ModelAttribute("admin") Admin admin, @RequestBody FamilyInfo request) {
		request.setAdminId(admin.getAdminId());
		return donorService.createFamilyInfo(request);
		
	}
	
	
	
	@GetMapping("/getAllFamilyList/{page}/{size}")
	public ResponseEntity<?> getAllFamilyList(@ModelAttribute("admin") Admin admin, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search) {

		return donorService.getAllFamilyList(page ,size,admin,search);

	}
	
	
	
	@GetMapping("/getFamilyById/{familyInfoId}")
    public ResponseEntity<?> getFamilyById(@PathVariable String familyInfoId ) {
        return  donorService.getFamilyInfoById(familyInfoId);
    }

	
	@PutMapping("/updateFamilyInfo")
	public ResponseEntity<?> updateFamilyInfo(@ModelAttribute("admin") Admin admin, @RequestBody FamilyInfo request) {
		return donorService.updateFamilyInfo(admin,request);
		
	}
	
	
	@GetMapping("/getAllMatchingDonorList/{page}/{size}")
	public ResponseEntity<?> getAllMatchingDonorList(@ModelAttribute("admin") Admin admin, @PathVariable int page,
			@PathVariable int size, @RequestParam(required = false) String search,
			@RequestParam(required = false) String bloodGroup,@RequestParam(required = false) String city,
			@RequestParam(required = false) String minHeight,@RequestParam(required = false) String maxHeight,
	        @RequestParam(required = false) String minWeight,@RequestParam(required = false) String maxWeight,
			@RequestParam(required = false) String skinColor,@RequestParam(required = false) String eyeColor,
			@RequestParam(required = false) String religion,@RequestParam(required = false) String education,
			@RequestParam(required = false) String profession) {

		return donorService.getAllMatchingDonorList(page, size, admin, search, bloodGroup,city,
				minHeight, maxHeight, minWeight, maxWeight,
				skinColor,eyeColor,religion,education,profession);

	}
	
	
	@GetMapping("/getFamilyList")
    public ResponseEntity<?> getFamilyList(@ModelAttribute("admin") Admin admin ) {
        return  donorService.getFamilyList(admin);
    }

	
	// End donor api
	
	@PostMapping("/convertProposalToProforma/{proposalId}")
	public ResponseEntity<?> convertProposalToProforma(@PathVariable String proposalId){
		return salesService.convertProposalToProforma(proposalId);
	}
	

	@PostMapping("/createItem")
	public ResponseEntity<?> createItem(@ModelAttribute("admin") Admin admin,@RequestBody Items request) {
		request.setAdminId(admin.getAdminId());
	
		return salesService.createItem(request);
		
	}
	
	@PutMapping("/updateItem")
	public ResponseEntity<?> updateItem(@RequestBody Items request) {
		
		return salesService.updateItem(request);
		
	}
	
	
	@GetMapping("/getAllItems/{page}/{size}")
	public ResponseEntity<?> getAllItems(@ModelAttribute("admin") Admin admin, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search) {

		return salesService.getAllItems(admin,page ,size,search);

	}
	
	@GetMapping("/getItemByItemId/{itemId}")
	public ResponseEntity<?> getItemByItemId(@PathVariable String itemId) {
		
		return salesService.getItemByItemId(itemId);
		
	}

	@DeleteMapping("/deleteItem/{itemId}")
	public ResponseEntity<?> deleteItem(@PathVariable String itemId) {
		
		return salesService.deleteItemById(itemId);
		
	}
	
	
	@GetMapping("/getProformaInvoiceSummary")
	public ResponseEntity<?> getProformaInvoiceSummary(@ModelAttribute("admin") Admin admin,
			@RequestParam Date startDate, @RequestParam Date endDate) {

		return ResponseEntity.ok(salesService.getProformaInvoiceSummary(admin, startDate, endDate));
	}
	
	
	@GetMapping("/getInvoiceTaxSummary")
	public ResponseEntity<?> getInvoiceTaxSummary(@ModelAttribute("admin") Admin admin,
			@RequestParam Date startDate, @RequestParam Date endDate) {

		return ResponseEntity.ok(salesService.getInvoiceTaxSummary(admin,startDate,endDate));
	}
	
	@GetMapping("/getItemListWithNameAndId")
	public ResponseEntity<?> getItemListWithNameAndId(@ModelAttribute("admin") Admin admin) {

		return salesService.getItemListWithNameAndId(admin);

	}
	
	@GetMapping("/leadExcelExport")
    public void leadExcelExport(
    		HttpServletResponse response,
    		@ModelAttribute("admin") Admin admin,
			@RequestParam(required = false) String leadStatus,
			@RequestParam(required = false) String startDate,
	        @RequestParam(required = false) String endDate) throws Exception {
        leadService.generateLeadExcel(response, admin, leadStatus, startDate, endDate);
    }
	
	@PostMapping("/createTask")
	public ResponseEntity<?> createTask(@ModelAttribute("admin") Admin admin,@RequestBody TaskDto request){
		try {
			if (request.getTaskAttachments() != null && request.getTaskAttachments().size() > 4) {
	            return ResponseEntity
	                    .status(HttpStatus.BAD_REQUEST)
	                    .body("Error: Only 4 attachments are allowed at a time.");
	        }

			Task responce = taskService.createTask(admin,request);
			return ResponseEntity.ok(responce);
		} catch (Exception e) {
			 e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                             .body("Error: " + e.getMessage());
		}
	}
	
	
	@GetMapping("/getAllTaskList/{page}/{size}")
	public ResponseEntity<?> getAllTaskList(@ModelAttribute("admin") Admin admin, @PathVariable int page,@PathVariable int size,
			@RequestParam(required = false) String search,
			@RequestParam(required = false)TaskStatus status,
			@RequestParam(required = false) String listType
			) {

		return taskService.getAllTaskList(page ,size,admin,search,status,null);

	}
	
	@PutMapping("/updateTask")
	public ResponseEntity<?> updateTask(@ModelAttribute("admin") Admin admin,@RequestBody Task task){
		task.setAdminId(admin.getAdminId());
		try {
			Task responce = taskService.updateTask(task);
			return ResponseEntity.ok(responce);
		} catch (Exception e) {
			 e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                             .body("Error: " + e.getMessage());
		}
	}
	
	@GetMapping("/getTaskByItemId/{taskId}")
	public ResponseEntity<?> getTaskByItemId(@ModelAttribute("admin") Admin admin,@PathVariable String taskId) {
		return taskService.getTaskById(taskId,admin);	
	}
	
	@DeleteMapping("/deleteTask/{taskId}")
	public ResponseEntity<?> deleteTask(@PathVariable String taskId) {
		
		return taskService.deleteTaskById(taskId);
		
	}
	
	@GetMapping("/getLeadNameAndIdWithConverted")
	public ResponseEntity<?> getLeadNameAndIdWithConverted(@ModelAttribute("admin") Admin admin) {

	  return  leadService.getLeadNameAndIdWithConverted(admin);
	}
	
	@GetMapping("/getProposalNumberAndId")
	public ResponseEntity<?> getProposalNameAndId(@ModelAttribute("admin") Admin admin) {

	  return  salesService.proposalNumberAndIdByAdminId(admin);
	}
	
	@GetMapping("/getProformaNumberAndId")
	public ResponseEntity<?> getProformaNameAndId(@ModelAttribute("admin") Admin admin) {

	  return  salesService.proformaNumberAndIdByAdminId(admin);
	}
	
	@GetMapping("/getInvoiceNumberAndId")
	public ResponseEntity<?> getInvoiceNumberAndId(@ModelAttribute("admin") Admin admin) {

	  return  salesService.inoviceNumberAndIdByAdminId(admin);
	}
	
	@DeleteMapping("/deleteSemenReport/{semenReportId}")
    public ResponseEntity<?> deleteSemenReport(@PathVariable String semenReportId ) {
     
        return  donorService.deleteSemenReport(semenReportId);
    }
	
	@PostMapping("/assignVialsToFamily")
	public ResponseEntity<?> assignVialsToFamily(@RequestBody FamilyVialAllocation familyVialAllocation){
		
		try {
			FamilyVialAllocation responce = donorService.assignVialsToFamily(familyVialAllocation);
			return ResponseEntity.ok(responce);
		} catch (Exception e) {
			 e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                             .body("Error: " + e.getMessage());
		}
	}
	
	@GetMapping("/getFinalReport/{allocationId}")
    public ResponseEntity<AllocationDetailsDTO> getAllocationDetails(@PathVariable String allocationId) {
        AllocationDetailsDTO details = donorService.getAllocationDetails(allocationId);
        return ResponseEntity.ok(details);
    }
	
	@GetMapping("/getAllFinalReportByFamilyId/{familyId}")
	public ResponseEntity<?> getAllFinalReport(@PathVariable String familyId) {
		return donorService.getAllFinalReport(familyId);
	}
	
	@GetMapping("/getAllDonation/{donorId}")
	public ResponseEntity<?> getAllDonation(@PathVariable String donorId) {
		return donorService.getAllDonation(donorId);
	}
	
	@PostMapping("/addTaskAttachement")
	public ResponseEntity<?> addAttachmentToTask(@ModelAttribute("admin") Admin admin,@RequestBody List<TaskAttachment> request) {
		return taskService.addAttachmentToTask(admin,request);
	}
	
	@GetMapping("/getTaskAttachmentByTaskId/{taskId}")
	public ResponseEntity<?> getAttachmentByTaskId(@PathVariable String taskId) {
		return taskService.getAttachmentByTaskId(taskId);
	}
	
	@DeleteMapping("/deleteTaskAttachement/{taskAttachmentId}")
	public ResponseEntity<?> deleteTaskAttachement(@PathVariable String taskAttachmentId){
		return taskService.deleteTaskAttachement(taskAttachmentId);
	}
	
	@PostMapping("/addCommentOnTask")
	public ResponseEntity<?> addCommentOnTask(@ModelAttribute("admin") Admin admin,@RequestBody TaskComments taskComments) {
		return taskService.addCommentOnTask(admin,taskComments);
	}
	
	@GetMapping("/getAllCommentsByTaskId/{taskId}")
    public ResponseEntity<Page<TaskComments>> getCommentsForTask(
            @PathVariable String taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("commentedAt").descending());
            Page<TaskComments> commentsPage = taskService.getAllCommentByTaskIs(taskId, pageable);
            return ResponseEntity.ok(commentsPage);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	@DeleteMapping("/deleteTaskComment/{commentId}")
	public ResponseEntity<?> deleteTaskComment(@ModelAttribute("admin") Admin admin,@PathVariable String commentId) {
	    return taskService.deleteCommentOnTask(commentId,admin);
	}
//	
//	@DeleteMapping("/deleteCommentAttachment/{commentAttachmentId}")
//	public ResponseEntity<?> deleteAttachment(@PathVariable String commentAttachmentId) {
//	    return taskService.deleteTaskCommentAttachement(commentAttachmentId);
//	}
//	
//	@PutMapping("/updateCommentOfTask")
//	public ResponseEntity<?> updateCommentOnTask(@ModelAttribute("admin") Admin admin,@RequestBody TaskComments taskComments) {
//		return taskService.updateCommentOfTask(admin,taskComments);
//	}
	
	@GetMapping("/getAllTimerLogs/{taskId}")
	public ResponseEntity<?> getAllTimerLogs(@ModelAttribute("admin") Admin admin,@PathVariable("taskId") String taskId){
		return taskService.getAllTaskTimerList(admin,taskId);
	}
	
	@PutMapping("/addAssigneesToTask/{taskId}")
    public ResponseEntity<Task> addAssignees(@PathVariable String taskId, @RequestBody Set<String> employeeIds) {
        Task updatedTask = taskService.addAssignees(taskId, employeeIds);
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/addFollowersToTask/{taskId}")
    public ResponseEntity<Task> addFollowers(@PathVariable String taskId, @RequestBody Set<String> employeeIds) {
        Task updatedTask = taskService.addFollowers(taskId, employeeIds);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/removeTaskAssignee/{taskId}/{employeeId}")
    public ResponseEntity<?> removeAssignee(@PathVariable String taskId, @PathVariable String employeeId) {
        taskService.removeAssignee(taskId, employeeId);
        return ResponseEntity.ok("Removed Assignee with Id: "+employeeId);
    }

    @DeleteMapping("/removeTaskFollower/{taskId}/{employeeId}")
    public ResponseEntity<?> removeFollower(@PathVariable String taskId, @PathVariable String employeeId) {
        taskService.removeFollower(taskId, employeeId);
        return ResponseEntity.ok("Removed Follower with Id: "+employeeId);
    }
    

    @PutMapping("/updateTaskStatus/{taskId}/{status}")
    public ResponseEntity<Task> updateStatus(
            @PathVariable String taskId, 
            @PathVariable TaskStatus status) {
        
        Task updatedTask = taskService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok(updatedTask);
    }

    // AMC APIs
    

	
	@PostMapping("/createAMC")
	public ResponseEntity<?> createAMC(@ModelAttribute("admin") Admin admin ,@RequestBody CreateAMC createAMC){
		createAMC.getAmcInfo().setAdminId(admin.getAdminId());
		return amcService.createAMC(createAMC);
	}
	
	
    
	@GetMapping("/getAllAMC/{page}/{size}")
	public ResponseEntity<?> getAllAMC(@ModelAttribute("admin") Admin admin, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search,
			@RequestParam(required = false) String expiryFromDate,@RequestParam(required = false) String expirayToDate) {

		return amcService.getAllAMC(page,size,admin,search,expiryFromDate,expirayToDate);

	}
	
	@PutMapping("/updateAMC")
	public ResponseEntity<AMC> updateAMC(@ModelAttribute("admin") Admin admin ,@RequestBody AMC updateAMC){
		updateAMC.setAdminId(admin.getAdminId());
		return amcService.updateAMC(updateAMC);
	}
	
	
	@DeleteMapping("/deleteAMC/{amcId}")
	public ResponseEntity<String> deleteAMC(@PathVariable String amcId){
		return amcService.deleteAMC(amcId);
	}
	
	
	@GetMapping("/getAllAMCHistoy/{amcId}")
	public ResponseEntity<List<AMCHistory>> getAllAMCHistoy(@PathVariable String amcId){
		return amcService.getAllAMCHistoy(amcId);
	}
	
	@PutMapping("/updateAMCHistory")
	public ResponseEntity<AMCHistory> updateAMCHistory(@RequestBody AMCHistory updateAMCHistory){
		return amcService.updateAMCHistory(updateAMCHistory);
	}
	
	@DeleteMapping("/deleteAMCHistory/{amcHistoryId}")
	public ResponseEntity<String> deleteAMCHistory(@PathVariable String amcHistoryId){
		return amcService.deleteAMCHistory(amcHistoryId);
	}
	
	@GetMapping("/getAllAMCDomainHistoy/{amcId}")
	public ResponseEntity<List<AMCDomainHistory>> getAllAMCDomainHistoy(@PathVariable String amcId){
		return amcService.getAllAMCDomainHistoy(amcId);
	}
	
	@PutMapping("/updateAMCDomainHistoy")
	public ResponseEntity<AMCDomainHistory> updateAMCDomainHistoy(@RequestBody AMCDomainHistory updateAMCDomainHistory){
		return amcService.updateAMCDomainHistoy(updateAMCDomainHistory);
	}
	
	@DeleteMapping("/deleteAMCDomainHistory/{amcDomainHistoryId}")
	public ResponseEntity<String> deleteAMCDomainHistory(@PathVariable String amcDomainHistoryId){
		return amcService.deleteAMCDomainHistory(amcDomainHistoryId);
	}

	// amc API END
	
	@GetMapping("/getDonorInfo/{donorId}")
	public ResponseEntity<?> getDonorInfo(@PathVariable("donorId") String donorId){
		return donorService.getDonorInfo(donorId);
	}
	
	@GetMapping("/getTaskCount")
	public ResponseEntity<?> getTaskCount(@ModelAttribute("admin") Admin admin,
			@RequestParam(required = false) String listType){
		return taskService.getTaskCounts(admin, null);
	}
	
	@PostMapping("/startTimerOfTask")
	public ResponseEntity<?> startTimer(@ModelAttribute("admin") Admin admin,@RequestBody Map<String, Object> req) {
	    String taskId = (String) req.get("taskId");
	    return taskService.startTimer(taskId, admin);
	}
	
	@PostMapping("/stopTimerOfTask")
	public ResponseEntity<?> stopTimer(@ModelAttribute("admin") Admin admin,@RequestBody Map<String, Object> req) {
	    String taskId = (String) req.get("taskId");
	    String endNote = (String) req.get("endNote");
	    return taskService.stopTimer(taskId, admin, endNote);
	}
	
	@GetMapping("/activeTimer")
	public ResponseEntity<?> getActiveTimer(@ModelAttribute("admin") Admin admin) {
	    return taskService.getActiveTimer(admin);
	}
}
