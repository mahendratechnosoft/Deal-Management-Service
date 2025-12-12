package com.mahendratechnosoft.crm.controller;

import java.sql.Date;
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

import com.mahendratechnosoft.crm.dto.CreateAMC;
import com.mahendratechnosoft.crm.dto.InvoiceDto;
import com.mahendratechnosoft.crm.dto.ProformaInvoiceDto;
import com.mahendratechnosoft.crm.dto.ProposalDto;
import com.mahendratechnosoft.crm.dto.TaskDto;
import com.mahendratechnosoft.crm.dto.Hospital.AllocationDetailsDTO;
import com.mahendratechnosoft.crm.entity.AMC;
import com.mahendratechnosoft.crm.entity.AMCDomainHistory;
import com.mahendratechnosoft.crm.entity.AMCHistory;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Attendance;
import com.mahendratechnosoft.crm.entity.Contacts;
import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.Deals;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.Items;
import com.mahendratechnosoft.crm.entity.Leads;
import com.mahendratechnosoft.crm.entity.Payments;
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
import com.mahendratechnosoft.crm.repository.EmployeeRepository;
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
import com.mahendratechnosoft.crm.service.TaskService;

import jakarta.servlet.http.HttpServletResponse;

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
	
	@GetMapping("/getLeadFollowUp")
	public ResponseEntity<?> getLeadFollowUp(@ModelAttribute("employee") Employee employee,@RequestParam(required = false) String followUpdate) {

	  return  leadService.getLeadFollowUp(employee,followUpdate);
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
		public ResponseEntity<?> getAllLeads(@ModelAttribute("employee") Employee employee,
				@PathVariable int page ,
				@PathVariable int size,
				@RequestParam(required = false) String search,
				@RequestParam(required = false) String leadStatus,
				@RequestParam(required = false) String startDate,
		        @RequestParam(required = false) String endDate,@RequestParam(required = false) String  followUpDate) {

		  return  leadService.getAllLeads(page, size,employee,leadStatus,search,startDate, endDate,followUpDate);
		}
		
		@GetMapping("/getLeadStatusAndCount")
		public ResponseEntity<?> getLeadStatusAndCount(@ModelAttribute("employee") Employee employee) {

		  return  leadService.getLeadStatusAndCount(employee);
		}
	    
	    
		@PutMapping("/updateLead")
		public ResponseEntity<?> updateLead( @ModelAttribute("employee") Employee employee,@RequestBody Leads lead) {
			lead.setAdminId(employee.getAdmin().getAdminId());
			lead.setAssignTo(employee.getName());
			return leadService.updateLead(lead,employee.getName());
		}
		
		@GetMapping("/getLeadById/{leadId}")
		public ResponseEntity<?> getLeadById(@ModelAttribute("employee") Employee employee,@PathVariable String leadId) {
			
			return leadService.getLeadById(leadId,employee);
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
		public ResponseEntity<?> getAllCustomerStatusAndCount(@ModelAttribute("employee") Employee employee) {

			return customerService.getAllCustomerStatusAndCount(employee);

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

			return salesService.getAllProformaAsInvoice(page ,size,employee,search);

		}
		
		@GetMapping("/getInvoiceByCustomerId/{customerId}")
		public ResponseEntity<?> getInvoiceByCustomerId( @PathVariable String customerId) {

			return salesService.getInvoiceByCustomerId(customerId);

		}
		
		@GetMapping("/checkCustomerIsExist")
		public ResponseEntity<?> getInvoiceByCustomerId(@ModelAttribute("employee") Employee employee,@RequestBody Map<String, String> request ) {

			String companyName = request.get("companyName");
	        return customerService.checkCustomerExist(employee,companyName);

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
		public ResponseEntity<?> getAllProformaInvoice(@ModelAttribute("employee") Employee employee, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search,@RequestParam(required = false) Date startDate,@RequestParam(required = false) Date endDate) {

			return salesService.getAllProformaInvoice(page ,size,employee,search,startDate,endDate);

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
		public ResponseEntity<?> createPayment(@ModelAttribute("employee") Employee employee,
				@RequestBody Payments request) {

			return salesService.createPayment(request, employee);

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
		public ResponseEntity<?> getAllPayments(@ModelAttribute("employee") Employee employee, @PathVariable int page,
				@PathVariable int size, @RequestParam(required = false) String search) {

			return salesService.getAllPayments(page, size, employee, search);

		}

		@GetMapping("/getPaymentsByProformaInvoice/{proformaInvoiceId}")
		public ResponseEntity<?> getPaymentByProformaInvoice(@PathVariable String proformaInvoiceId) {

			return salesService.getPaymentByProformaInvoice(proformaInvoiceId);

		}
		
		@GetMapping("/getNextProposalNumber")
	    public ResponseEntity<Integer> getNextProposalNumber(@ModelAttribute("employee") Employee employee) {
	        int nextNumber = salesService.getNextProposalNumberForAdmin(employee.getAdmin().getAdminId());
	        return ResponseEntity.ok(nextNumber);
	    }
		
		@GetMapping("/isProposalNumberUnique/{proposalNumber}")
	    public ResponseEntity<Boolean> checkProposalNumberUnique(
	    		@ModelAttribute Employee employee,
	            @PathVariable int proposalNumber) {

	        boolean isUnique = salesService.isProposalNumberUnique(employee.getAdmin().getAdminId(), proposalNumber);
	        return ResponseEntity.ok(isUnique);
	    }
		
		@GetMapping("/getAllPerforma")
	    public ResponseEntity<?> getAllPerforma(@ModelAttribute("employee") Employee employee) {
	     
	        return ResponseEntity.ok(salesService.getAllPerforma(employee.getAdmin().getAdminId()));
	    }
		
		
		@PostMapping("/addAttendance/{status}")
	    public ResponseEntity<?> addAttendance(@ModelAttribute("employee") Employee employee,@PathVariable boolean status) {
	     
	        return attendanceService.addAttendance(employee.getAdmin().getAdminId(),employee.getEmployeeId(),status);
	    }
		
		@GetMapping("/getLatestTime")
	    public ResponseEntity<?> getLatestTime(@ModelAttribute("employee") Employee employee) {
	     
	        return  attendanceService.getLatestTime(employee.getEmployeeId());
	    }
		
		@GetMapping("/getAttendanceBetween")
		public ResponseEntity<?> getAttendanceBetween( @ModelAttribute("employee") Employee employee,@RequestParam(required = false) String employeeId,
		        @RequestParam String fromDate, @RequestParam String toDate) {
			
			return  attendanceService.getAttendanceBetween(employee,fromDate,toDate,employeeId);
		}
		
		
		@GetMapping("/getAttendanceBetweenForParticalurEmployee")
		public ResponseEntity<?> getAttendanceBetweenForParticalurEmployee( @ModelAttribute("employee") Employee employee,
		        @RequestParam String fromDate, @RequestParam String toDate) {
			
			return  attendanceService.getAttendanceBetweenForParticalurEmployee(employee.getEmployeeId(),fromDate,toDate);
		}
		
		
		
		@PutMapping("/updateAttendance")
	    public ResponseEntity<?> updateAttendance(@ModelAttribute("employee") Employee employee,@RequestBody Attendance attendance) {
	     
	        return  attendanceService.updateAttendance(employee,attendance);
	    }
		
		@DeleteMapping("/deleteAttendance/{attendanceId}")
	    public ResponseEntity<?> updateAttendance(@PathVariable String attendanceId ) {
	     
	        return  attendanceService.deleteAttendance(attendanceId);
	    }
		
		
		@GetMapping("/getLoginStatusAllEmployee/{date}")
	    public ResponseEntity<?> getLoginStatusAllEmployee(@ModelAttribute Employee employee ,@PathVariable String date) {
	      
	        return attendanceService.getLoginStatusAllEmployee(employee.getAdmin().getAdminId(),date);
	    }
		
		@GetMapping("/getAttendanceExcelExport")
		public ResponseEntity<?> getAttendanceBetweenExcelExport(@ModelAttribute Employee employee, @RequestParam String fromDate,
				@RequestParam String toDate,@RequestParam(required = false) String employeeId,String attendanceType,String monthName) {
			return attendanceService.getAttendanceExcelExport(employee.getAdmin(), fromDate, toDate,employeeId,attendanceType,monthName);
		}
		
		@GetMapping("/getNextProformaNumber")
	    public ResponseEntity<Integer> getNextProformaNumber(@ModelAttribute("employee") Employee employee) {
	        int nextNumber = salesService.getNextProformaNumberForAdmin(employee.getAdmin().getAdminId());
	        return ResponseEntity.ok(nextNumber);
	    }
		
		@GetMapping("/isProformaNumberUnique/{proformaNumber}")
	    public ResponseEntity<Boolean> checkProformaNumberUnique(
	    		@ModelAttribute("employee") Employee employee,
	            @PathVariable int proformaNumber) {

	        boolean isUnique = salesService.isProformaNumberUnique(employee.getAdmin().getAdminId(), proformaNumber);
	        return ResponseEntity.ok(isUnique);
	    }
		
		@GetMapping("/leadTemplateExcel")
	    public void downloadExcel(HttpServletResponse response) throws Exception {
	        excelService.generateExcel(response);
	    }
		
		
		@PostMapping("/importLeads")
		public ResponseEntity<?> importLeads(@RequestParam("file") MultipartFile file,@ModelAttribute("employee") Employee employee) {
		    try {
		        List<Leads> imported = excelService.importLeadsFromExcel(file,employee);
		        return ResponseEntity.ok("Successfully imported " + imported.size() + " leads.");
		    } catch (Exception e) {
		        return ResponseEntity.status(500).body("Failed to import: " + e.getMessage());
		    }
		}
		
		@PostMapping("/createItem")
		public ResponseEntity<?> createItem(@ModelAttribute("employee") Employee employee,@RequestBody Items request) {
			request.setAdminId(employee.getAdmin().getAdminId());
			request.setEmployeeId(employee.getEmployeeId());
			return salesService.createItem(request);
			
		}
		
		@PutMapping("/updateItem")
		public ResponseEntity<?> updateItem(@RequestBody Items request) {
			
			return salesService.updateItem(request);
			
		}
		
		
		@GetMapping("/getAllItems/{page}/{size}")
		public ResponseEntity<?> getAllItems(@ModelAttribute("employee") Employee employee, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search) {

			return salesService.getAllItems(employee,page ,size,search);

		}
		
		@GetMapping("/getItemByItemId/{itemId}")
		public ResponseEntity<?> getItemByItemId(@PathVariable String itemId) {
			
			return salesService.getItemByItemId(itemId);
			
		}

		@DeleteMapping("/deleteItem/{itemId}")
		public ResponseEntity<?> deleteItem(@PathVariable String itemId) {
			
			return salesService.deleteItemById(itemId);
			
		}
		
		@PostMapping("/convertProposalToProforma/{proposalId}")
		public ResponseEntity<?> convertProposalToProforma(@PathVariable String proposalId){
			return salesService.convertProposalToProforma(proposalId);
		}
		
		@GetMapping("/getProformaInvoiceSummary")
		public ResponseEntity<?> getProformaInvoiceSummary(@ModelAttribute("employee") Employee employee,
				@RequestParam Date startDate, @RequestParam Date endDate) {

			return ResponseEntity.ok(salesService.getProformaInvoiceSummary(employee, startDate, endDate));
		}
		
		
		@GetMapping("/getInvoiceTaxSummary")
		public ResponseEntity<?> getInvoiceTaxSummary(@ModelAttribute("employee") Employee employee,
				@RequestParam Date startDate, @RequestParam Date endDate) {

			return ResponseEntity.ok(salesService.getInvoiceTaxSummary(employee,startDate,endDate));
		}
		
		@GetMapping("/getItemListWithNameAndId")
		public ResponseEntity<?> getItemListWithNameAndId(@ModelAttribute("employee") Employee employee) {

			return salesService.getItemListWithNameAndId(employee);

		}
		
		@GetMapping("/leadExcelExport")
	    public void leadExcelExport(
	    		HttpServletResponse response,
	    		@ModelAttribute("employee") Employee employee,
				@RequestParam(required = false) String leadStatus,
				@RequestParam(required = false) String startDate,
		        @RequestParam(required = false) String endDate) throws Exception {
	        leadService.generateLeadExcel(response, employee, leadStatus, startDate, endDate);
	    }
		
		
		// donor code start
		
		@PostMapping("/createDonor")
		public ResponseEntity<?> createDonar(@ModelAttribute("employee") Employee employee,@RequestBody Donors request) {
			 request.setAdminId(employee.getAdmin().getAdminId());
			 request.setEmployeeId(employee.getEmployeeId());
			 request.setCreatedBy(employee.getName());
			return donorService.createDonor(request);
			
		}
		
		
		
		@GetMapping("/getAllDonorList/{page}/{size}")
		public ResponseEntity<?> getAllDonorList(@ModelAttribute("employee") Employee employee, @PathVariable int page,@PathVariable int size,
				@RequestParam(required = false) String search,@RequestParam(required = false) String status) {

			return donorService.getAllDonorList(page ,size,employee,search,status);

		}
		
		@GetMapping("/getDonorStatusCount")
	    public ResponseEntity<?> getDonorStatusCount(@ModelAttribute("employee") Employee employee) {
	     
	        return  donorService.getDonorStatusCount(employee);
	    }

		@GetMapping("/getDonorById/{donorId}")
	    public ResponseEntity<?> getDonorById(@PathVariable String donorId ) {
	     
	        return  donorService.getDonarById(donorId);
	    }
		
		@PutMapping("/updateDonor")
		public ResponseEntity<?> updateeDonor(@ModelAttribute("employee") Employee employee,@RequestBody Donors request) {
			return donorService.updateDonor(employee,request);
		}
		
		
		@PutMapping("/updateDonorStatus")
		public ResponseEntity<?> updateDonorStatus(@RequestBody Map<String,String> request) {
			
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
		public ResponseEntity<?> createFamilyInfo(@ModelAttribute Employee employee, @RequestBody FamilyInfo request) {
			request.setAdminId(employee.getAdmin().getAdminId());
			request.setEmployeeId(employee.getEmployeeId());
			return donorService.createFamilyInfo(request);
		}
		
		
		
		@GetMapping("/getAllFamilyList/{page}/{size}")
		public ResponseEntity<?> getAllFamilyList(@ModelAttribute("employee") Employee employee, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search) {

			return donorService.getAllFamilyList(page ,size,employee,search);

		}
		
		
		
		@GetMapping("/getFamilyById/{familyInfoId}")
	    public ResponseEntity<?> getFamilyById(@PathVariable String familyInfoId ) {
	        return  donorService.getFamilyInfoById(familyInfoId);
	    }

		
		@PutMapping("/updateFamilyInfo")
		public ResponseEntity<?> updateFamilyInfo(@ModelAttribute("employee") Employee employee, @RequestBody FamilyInfo request) {
			return donorService.updateFamilyInfo(employee,request);
			
		}
		
		
		@GetMapping("/getAllMatchingDonorList/{page}/{size}")
		public ResponseEntity<?> getAllMatchingDonorList(@ModelAttribute("employee") Employee employee, @PathVariable int page,
				@PathVariable int size, @RequestParam(required = false) String search,
				@RequestParam(required = false) String bloodGroup,@RequestParam(required = false) String city,
				@RequestParam(required = false) String minHeight,@RequestParam(required = false) String maxHeight,
		        @RequestParam(required = false) String minWeight,@RequestParam(required = false) String maxWeight,
				@RequestParam(required = false) String skinColor,@RequestParam(required = false) String eyeColor,
				@RequestParam(required = false) String religion,@RequestParam(required = false) String education,
				@RequestParam(required = false) String profession) {

			return donorService.getAllMatchingDonorList(page, size, employee, search, bloodGroup,city,
					minHeight, maxHeight, minWeight, maxWeight,
					skinColor,eyeColor,religion,education,profession);

		}
		
		
		@GetMapping("/getFamilyList")
	    public ResponseEntity<?> getFamilyList(@ModelAttribute("employee") Employee employee) {
	        return  donorService.getFamilyList(employee);
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
		
		// End donor api
		
		@PostMapping("/createTask")
		public ResponseEntity<?> createTask(@ModelAttribute("employee") Employee employee,@RequestBody TaskDto request){
			
			try {
				Task responce = taskService.createTask(employee,request);
				return ResponseEntity.ok(responce);
			} catch (Exception e) {
				 e.printStackTrace();
			        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			                             .body("Error: " + e.getMessage());
			}
		}
		
		
		@GetMapping("/getAllTaskList/{page}/{size}")
		public ResponseEntity<?> getAllTaskList(@ModelAttribute("employee") Employee employee, @PathVariable int page,@PathVariable int size,
				@RequestParam(required = false) String search,
				@RequestParam(required = false) TaskStatus status,
				@RequestParam(required = false) String listType
				) {

			return taskService.getAllTaskList(page ,size,employee,search,status,listType);

		}
		
		@PutMapping("/updateTask")
		public ResponseEntity<?> updateTask(@ModelAttribute("employee") Employee employee,@RequestBody Task task){
			task.setAdminId(employee.getAdmin().getAdminId());
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
		public ResponseEntity<?> getTaskByItemId(@ModelAttribute("employee") Employee employee,@PathVariable String taskId) {
			return taskService.getTaskById(taskId,employee);	
		}
		
		@DeleteMapping("/deleteTask/{taskId}")
		public ResponseEntity<?> deleteTask(@PathVariable String taskId) {
			
			return taskService.deleteTaskById(taskId);
			
		}
		
		@PostMapping("/addTaskAttachement")
		public ResponseEntity<?> addAttachmentToTask(@ModelAttribute("employee") Employee employee,@RequestBody List<TaskAttachment> request) {
			return taskService.addAttachmentToTask(employee,request);
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
		public ResponseEntity<?> addCommentOnTask(@ModelAttribute("employee") Employee employee,@RequestBody TaskComments taskComments) {
			return taskService.addCommentOnTask(employee,taskComments);
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
		public ResponseEntity<?> deleteTaskComment(@ModelAttribute Employee employee,@PathVariable String commentId) {
		    return taskService.deleteCommentOnTask(commentId,employee);
		}
		
		@PostMapping("/startTimerOfTask")
		public ResponseEntity<?> startTimer(@ModelAttribute("employee") Employee employee,@RequestBody Map<String, Object> req) {
		    String taskId = (String) req.get("taskId");
		    return taskService.startTimer(taskId, employee);
		}
		
		@PostMapping("/stopTimerOfTask")
		public ResponseEntity<?> stopTimer(@ModelAttribute("employee") Employee employee,@RequestBody Map<String, Object> req) {
		    String taskId = (String) req.get("taskId");
		    String endNote = (String) req.get("endNote");
		    return taskService.stopTimer(taskId, employee, endNote);
		}
		
		@GetMapping("/getAllTimerLogs/{taskId}")
		public ResponseEntity<?> getAllTimerLogs(@ModelAttribute("employee") Employee employee,@PathVariable("taskId") String taskId){
			return taskService.getAllTaskTimerList(employee,taskId);
		}
		
		@GetMapping("/activeTimer")
		public ResponseEntity<?> getActiveTimer(@ModelAttribute("employee") Employee employee) {
		    return taskService.getActiveTimer(employee);
		}
		
		@GetMapping("/getActiveTimerForTask/{taskId}")
		public ResponseEntity<?> getActiveTimerForTask(@ModelAttribute("employee") Employee employee, @PathVariable("taskId") String taskId) {
		    return taskService.getActiveTimerForTask(taskId, employee);
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
		public ResponseEntity<?> createAMC(@ModelAttribute("employee") Employee employee ,@RequestBody CreateAMC createAMC){
			createAMC.getAmcInfo().setAdminId(employee.getAdmin().getAdminId());
			return amcService.createAMC(createAMC);
		}
		
		
	    
		@GetMapping("/getAllAMC/{page}/{size}")
		public ResponseEntity<?> getAllAMC(@ModelAttribute("employee") Employee employee, @PathVariable int page,@PathVariable int size,@RequestParam(required = false) String search,
				@RequestParam(required = false) String expiryFromDate,@RequestParam(required = false) String expirayToDate) {

			return amcService.getAllAMC(page ,size,employee,search,expiryFromDate,expirayToDate);

		}
		
		@PutMapping("/updateAMC")
		public ResponseEntity<AMC> updateAMC(@ModelAttribute("employee") Employee employee,@RequestBody AMC updateAMC){
			updateAMC.setAdminId(employee.getAdmin().getAdminId());
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
		public ResponseEntity<?> getTaskCount(@ModelAttribute("employee") Employee employee,
				@RequestParam(required = false) String listType){
			return taskService.getTaskCounts(employee, listType);
		}
		
		@GetMapping("/exportTasks")
		public void exportTasks(
				@ModelAttribute("employee") Employee employee,
		        HttpServletResponse response,
		        @RequestParam(required = false) String search,
		        @RequestParam(required = false) TaskStatus status,
		        @RequestParam(required = false) String listType
		        ) {
		    taskService.exportTaskExcel(response, employee, search, status, listType);
		}
}
