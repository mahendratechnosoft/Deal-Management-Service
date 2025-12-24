package com.mahendratechnosoft.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.PF;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.helper.ResourceNotFoundException;
import com.mahendratechnosoft.crm.repository.CustomerRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;
import com.mahendratechnosoft.crm.service.ComplianceService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ComplianceService complianceService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@ModelAttribute("customer")
    public Customer getCurrentlyLoggedInContact(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("User not authenticated");
        }
        
        String loginEmail = authentication.getName();
        
        User user = userRepository.findByLoginEmail(loginEmail)
        		.orElseThrow(()-> new ResourceNotFoundException("User", "email", loginEmail));
        
        Customer customer = customerRepository.findByUserId(user.getUserId())
        		.orElseThrow(()-> new ResourceNotFoundException("Customer", "userId", user.getUserId()));
        
        return customer;
    }
	
	@PutMapping("/updatePf")
	public ResponseEntity<PF> updatePf(@RequestBody PF pf){
		PF responce = complianceService.updatePF(pf);
		return ResponseEntity.ok(responce);
	}
	
	@GetMapping("/getPfById/{pfId}")
	public ResponseEntity<PF> getPfById(@PathVariable String pfId){
		PF responce = complianceService.getPfById(pfId);
		return ResponseEntity.ok(responce);
	}
	
	@DeleteMapping("/deletePfById/{pfId}")
	public ResponseEntity<?> deletePfById(@PathVariable String pfId){
		complianceService.deletePfById(pfId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/getAllPf")
	public ResponseEntity<Page<PF>> getAllPf(
			@ModelAttribute Customer customer,
			@RequestParam(required = false) String customerId,
		    @RequestParam(required = false) String contactId,
		    @RequestParam(required = false) String search,
		    Pageable pageable){
		
		Page<PF> result = complianceService.getAllPF(customer, customerId, contactId, search, pageable);
	    return ResponseEntity.ok(result);
	}
	
}
