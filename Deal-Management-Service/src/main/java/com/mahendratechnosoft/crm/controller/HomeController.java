package com.mahendratechnosoft.crm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mahendratechnosoft.crm.config.UserDetailServiceImp;
import com.mahendratechnosoft.crm.dto.AdminRegistrationDto;
import com.mahendratechnosoft.crm.dto.EsicDto;
import com.mahendratechnosoft.crm.dto.SignInRespoonceDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Contacts;
import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.Esic;
import com.mahendratechnosoft.crm.entity.Leads;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
import com.mahendratechnosoft.crm.entity.PF;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;
import com.mahendratechnosoft.crm.entity.Hospital.FamilyInfo;
import com.mahendratechnosoft.crm.entity.Hospital.SemenEnquiry;
import com.mahendratechnosoft.crm.helper.ResourceNotFoundException;
import com.mahendratechnosoft.crm.helper.SoftwareValidityExpiredException;
import com.mahendratechnosoft.crm.repository.AdminRepository;
import com.mahendratechnosoft.crm.repository.ContactsRepository;
import com.mahendratechnosoft.crm.repository.CustomerRepository;
import com.mahendratechnosoft.crm.repository.EmployeeRepository;
import com.mahendratechnosoft.crm.repository.ModuleAccessRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;
import com.mahendratechnosoft.crm.security.JwtUtil;
import com.mahendratechnosoft.crm.service.ComplianceService;
import com.mahendratechnosoft.crm.service.DonorService;
import com.mahendratechnosoft.crm.service.LeadService;
import com.mahendratechnosoft.crm.service.UserService;


@Controller
@RestController
public class HomeController {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
	private UserDetailServiceImp userDetailServiceImp;
    
    @Autowired
	private UserRepository userRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private LeadService leadService;
    
    @Autowired
    private DonorService donorService;
    
	@Autowired
	private ModuleAccessRepository moduleAccessRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ContactsRepository contactsRepository;
	
	@Autowired
	private ComplianceService complianceService;
    

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AdminRegistrationDto registrationDto) {
        try {
            userService.registerAdmin(registrationDto);
            return ResponseEntity.ok("Admin registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Registration failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/signin")
    public ResponseEntity<?> generateToken(@RequestBody AdminRegistrationDto adminRegistrationDto) {
        try {
        	
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(adminRegistrationDto.getUsername(), adminRegistrationDto.getPassword())
            );
            final UserDetails userDetails = userDetailServiceImp.loadUserByUsername(adminRegistrationDto.getUsername());
            User user = userRepository.findByLoginEmail(adminRegistrationDto.getUsername()).get();
            
            final String token = jwtUtil.generateToken(userDetails);
            String name=null;
            String employeeId=null;
            String adminId=null;
            String customerId = null;
            String contactId = null;
            byte[] logo = null;
            ModuleAccess moduleAccess=null;
            if (user.getRole().equals("ROLE_ADMIN")) {
            	 Optional<Admin> admin=adminRepository.findByLoginEmail(user.getLoginEmail());
            	 name=admin.get().getName();
            	 adminId=admin.get().getAdminId();
            	 logo = admin.get().getLogo();
            	 moduleAccess = moduleAccessRepository.findByAdminIdAndEmployeeIdAndCustomerId(adminId,null,null);
            }else if(user.getRole().equals("ROLE_EMPLOYEE")){
            	Optional<Employee> employee=employeeRepository.findByLoginEmail(user.getLoginEmail());
            	name=employee.get().getName();
            	adminId=employee.get().getAdmin().getAdminId();
            	employeeId=employee.get().getEmployeeId();
            	logo = employee.get().getAdmin().getLogo();
            	
            	moduleAccess = moduleAccessRepository.findByAdminIdAndEmployeeIdAndCustomerId(adminId,employeeId,null);
            }else if(user.getRole().equals("ROLE_CUSTOMER")) {
            	Customer customer = customerRepository.findByUserId(user.getUserId())
            	.orElseThrow(()->new RuntimeException("Customer not found for user id :"+user.getUserId()));
            	
            	Admin admin = adminRepository.findById(customer.getAdminId())
            	.orElseThrow(()->new ResourceNotFoundException("Admin","adminId",customer.getAdminId()));
            	
            	name = customer.getCompanyName();
            	adminId = customer.getAdminId();
            	employeeId = customer.getEmployeeId();
            	customerId = customer.getCustomerId();
            	logo = admin.getLogo();
            	moduleAccess = moduleAccessRepository.findByAdminIdAndEmployeeIdAndCustomerId(adminId,employeeId,customerId);
            } else if(user.getRole().equals("ROLE_CONTACT")) {
            	Contacts contacts = contactsRepository.findByUserId(user.getUserId())
            			.orElseThrow(()->new ResourceNotFoundException("Contact", "userId", user.getUserId()));
            	
            	Customer customer = customerRepository.findById(contacts.getCustomerId())
            	.orElseThrow(()->new ResourceNotFoundException("Contact", "contactId", contacts.getCustomerId()));
            	
            	Admin admin = adminRepository.findById(customer.getAdminId())
            	.orElseThrow(()->new ResourceNotFoundException("Admin","adminId",customer.getAdminId()));
            	
            	name = contacts.getName();
            	adminId = customer.getAdminId();
            	employeeId = customer.getEmployeeId();
            	customerId = customer.getCustomerId();
            	logo = admin.getLogo();
            	contactId = contacts.getId();
            }
            
            SignInRespoonceDto signInRespoonceDto = new SignInRespoonceDto(token, user.getUserId(), user.getLoginEmail(),
            		user.getRole(), user.getExpiryDate(),name,employeeId,adminId,moduleAccess,customerId,logo,contactId);
            
            return ResponseEntity.ok(signInRespoonceDto);

        } catch (SoftwareValidityExpiredException s) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(s.getMessage());
        } catch (UsernameNotFoundException u) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + u.getMessage());
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials: " + ex.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    
    @GetMapping("/checkEmail/{email}")
    public ResponseEntity<Boolean> checkEmailExists(
            @PathVariable String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }
    
    
    @PostMapping("/generateLeads")
    public ResponseEntity<?> genrateLeads(@RequestBody Leads dto ) {
        if(dto.getAdminId() == null || dto.getAdminId().isEmpty()) {
        	
        	return ResponseEntity.ok("Admin Is Missing (Admin Id)");
        }
        return leadService.createLead(dto, "Form Genrated");
    }
    
    
	@PostMapping("/createDonor")
	public ResponseEntity<?> createDonorPublic(@RequestBody Donors request) {
		
		return donorService.createDonorPublic(request);
		
	}
	
	@PostMapping("/createFamily")
	public ResponseEntity<?> createFamilyPublic(@RequestBody FamilyInfo request) {
		return donorService.createFamilyInfo(request);
	}
	
	@PostMapping("/createSemenEnquiry")
	public ResponseEntity<SemenEnquiry> createPublicSemenEnquiry(@RequestBody SemenEnquiry enquiry){
		SemenEnquiry semenEnquiry = donorService.createSemenEnquiryPublic(enquiry);
		return ResponseEntity.ok(semenEnquiry);
	}
	
	@PostMapping("/submitPfForm")
	public ResponseEntity<PF> submitPfForm(@RequestBody PF pf){
		PF responce = complianceService.createPF(pf);
		return ResponseEntity.ok(responce);
	}

	@PostMapping("/submitEsicForm")
	public ResponseEntity<Esic> submitEsicForm(@RequestBody EsicDto request){
		Esic responce = complianceService.createEsic(request);
		return ResponseEntity.ok(responce);
	}
}
