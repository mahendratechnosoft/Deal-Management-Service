package com.mahendratechnosoft.crm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.mahendratechnosoft.crm.dto.InvoiceDto;
import com.mahendratechnosoft.crm.dto.ProformaInvoiceDto;
import com.mahendratechnosoft.crm.dto.ProposalDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Contacts;
import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.Deals;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.Leads;
import com.mahendratechnosoft.crm.repository.EmployeeRepository;
import com.mahendratechnosoft.crm.service.ContactsService;
import com.mahendratechnosoft.crm.service.CustomerService;
import com.mahendratechnosoft.crm.service.DealsService;
import com.mahendratechnosoft.crm.service.EmployeeService;
import com.mahendratechnosoft.crm.service.LeadService;
import com.mahendratechnosoft.crm.service.SalesService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
    private EmployeeRepository employeeRepository; // Create this repository interface
	
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
	private SalesService salesService;

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
    
    
    @GetMapping("/getEmployeeNameAndId")
    public ResponseEntity<?> getEmployeeByIdByAdmin( @ModelAttribute("employee") Employee employee)   {
   
    	return employeeService.getEmployeeNameAndId(employee);
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

	// lead APis
	
		@PostMapping("/createLead")
		public ResponseEntity<?> createLead(@ModelAttribute("employee") Employee employee,@RequestBody Leads dto) {
			dto.setAdminId(employee.getAdmin().getAdminId());
			dto.setEmployeeId(employee.getEmployeeId());
			dto.setAssignTo(employee.getName());
			return leadService.createLead(dto,employee.getName());
		}
		
		
		
		@GetMapping("/getAllLeads/{page}/{size}")
		public ResponseEntity<?> getAllLeads(@ModelAttribute("employee") Employee employee,@PathVariable int page ,@PathVariable int size,@RequestParam(required = false) String search,@RequestParam(required = false) String leadStatus) {

		  return  leadService.getAllLeads(page, size,employee,leadStatus,search);
		}
	    
		@PutMapping("/updateLead")
		public ResponseEntity<?> updateLead( @ModelAttribute("employee") Employee employee,@RequestBody Leads lead) {
			lead.setAdminId(employee.getAdmin().getAdminId());
			lead.setAssignTo(employee.getName());
			return leadService.updateLead(lead,employee.getName());
		}
		
		@GetMapping("/getLeadById/{leadId}")
		public ResponseEntity<?> getLeadById(@PathVariable String leadId) {
			
			return leadService.getLeadById(leadId);
		}
		
		@DeleteMapping("/deleteLead/{id}")
		public String deleteLead(@PathVariable String id) {
			
			return leadService.deleteLead(id);
		}
		
		@GetMapping("/getLeadNameAndId")
		public ResponseEntity<?> getLeadNameAndId(@ModelAttribute("employee") Employee employee) {

		  return  leadService.getLeadNameAndId(employee);
		}
		
		@PutMapping("/updateLeadStatus")
		public ResponseEntity<?> updateLeadStatus(@ModelAttribute("employee") Employee employee,@RequestBody Map<String, String> request) {
			String leadId = request.get("leadId");
			String status = request.get("status");
			return leadService.updateLeadStatus(leadId,status,employee.getName());
		}
		
		@PostMapping("/createCustomer")
		public ResponseEntity<?> createCustomer(@ModelAttribute("employee") Employee employee, @RequestBody Customer customer) {

			try {
				customer.setAdminId(employee.getAdmin().getAdminId());
				customer.setEmployeeId(employee.getEmployeeId());
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
		public ResponseEntity<?> udpateCustomer(@ModelAttribute("employee") Employee employee, @RequestBody Customer customer) {

			try {
				customer.setAdminId(employee.getAdmin().getAdminId());
				return customerService.updateCustomer(customer);

			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
			}
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

		@GetMapping("/getAllCustomer/{page}/{size}")
		public ResponseEntity<?> getAllCustomer(@ModelAttribute("employee") Employee employee, @PathVariable int page,
				@PathVariable int size,@RequestParam(required = false) String search) {

			return customerService.getAllCustomer(page, size, employee,search);

		}
		
		@GetMapping("/getCustomerListWithNameAndId")
		public ResponseEntity<?> getCustomerListWithNameAndId(@ModelAttribute("employee") Employee employee) {

			return customerService.getCustomerListWithNameAndId(employee);

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
		
		
		@PostMapping("/createProposal")
		public ResponseEntity<?> createProposal(@ModelAttribute("employee") Employee employee,@RequestBody ProposalDto request) {
			
			return salesService.createProposal(request,employee);
			
		}
		
		@PutMapping("/updateProposal")
		public ResponseEntity<?> updateProposal(@ModelAttribute("employee") Employee employee,@RequestBody ProposalDto request) {
			
			return salesService.updateProposal(request,employee);
			
		}
		
		
		@GetMapping("/getProposalById/{proposalId}")
		public ResponseEntity<?> getProposalById(@PathVariable String proposalId) {
			
			return salesService.getProposalById(proposalId);
			
		}
		
		@GetMapping("/getAllProposal/{page}/{size}")
		public ResponseEntity<?> getAllProposal(@ModelAttribute("employee") Employee employee, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search) {

			return salesService.getAllProposal(page ,size,employee,search);

		}
		
		
		@GetMapping("/getProposalByModuleType/{moduleTypeId}/{moduleType}")
		public ResponseEntity<?> getProposalByLeadId(@PathVariable String moduleTypeId,@PathVariable String moduleType) {

			return salesService.getProposalByModuleType(moduleTypeId,moduleType);

		}
		
		
		//Invoice API
		@PostMapping("/createInvoice")
		public ResponseEntity<?> createInvoice(@ModelAttribute("employee") Employee employee,@RequestBody InvoiceDto request) {
			
			return salesService.createInvoice(request,employee);
			
		}
		
		@PutMapping("/updateInvoice")
		public ResponseEntity<?> updateInvoice(@ModelAttribute("employee") Employee employee,@RequestBody InvoiceDto request) {
			
			return salesService.updateInvoice(request,employee);
			
		}
		
		@GetMapping("/getInvoiceById/{invoiceId}")
		public ResponseEntity<?> getInvoiceById(@PathVariable String invoiceId) {
			
			return salesService.getInvoiceById(invoiceId);
			
		}
		
		
		@GetMapping("/getAllInvoice/{page}/{size}")
		public ResponseEntity<?> getAllInvoice(@ModelAttribute("employee") Employee employee, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search) {

			return salesService.getAllInvoice(page ,size,employee,search);

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
		
		@GetMapping("/getModuleActivity/{moduleId}/{moduleType}")
		public ResponseEntity<?> getModuleActivity(@PathVariable String moduleId,@PathVariable String moduleType) {

			return leadService.getModuleActivit(moduleId,moduleType);
		}
		
		
		// proforma-invoice api
		
		@PostMapping("/createProformaInvoice")
		public ResponseEntity<?> createProformaInvoice(@ModelAttribute("employee") Employee employee,@RequestBody ProformaInvoiceDto request) {
			 
			return salesService.createProformaInvoice(request,employee);
			
		}
		
		
		@PutMapping("/updateProformaInvoice")
		public ResponseEntity<?> updateProformaInvoice(@ModelAttribute("employee") Employee employee,@RequestBody ProformaInvoiceDto request) {
			
			return salesService.updateProformaInvoice(request,employee);
			
		}
		
		@GetMapping("/getProformaInvoiceById/{proformaInvoiceId}")
		public ResponseEntity<?> getProformaInvoiceById(@PathVariable String proformaInvoiceId) {
			
			return salesService.getProformaInvoiceById(proformaInvoiceId);
			
		}
		
		
		
		@GetMapping("/getAllProformaInvoice/{page}/{size}")
		public ResponseEntity<?> getAllProformaInvoice(@ModelAttribute("employee") Employee employee, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search) {

			return salesService.getAllProformaInvoice(page ,size,employee,search);

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
