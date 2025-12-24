package com.mahendratechnosoft.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Contacts;
import com.mahendratechnosoft.crm.entity.PF;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.helper.ResourceNotFoundException;
import com.mahendratechnosoft.crm.repository.ContactsRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;
import com.mahendratechnosoft.crm.service.ComplianceService;

@RestController
@RequestMapping("/contacts")
public class ContactsController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactsRepository contactsRepository;
	
	@Autowired
	private ComplianceService complianceService;
	
	@ModelAttribute("contacts")
    public Contacts getCurrentlyLoggedInContact(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("User not authenticated");
        }
        
        String loginEmail = authentication.getName();
        
        User user = userRepository.findByLoginEmail(loginEmail)
        		.orElseThrow(()-> new ResourceNotFoundException("User", "email", loginEmail));
        
        Contacts contacts = contactsRepository.findByUserId(user.getUserId())
        		.orElseThrow(()-> new ResourceNotFoundException("Contacts", "userId", user.getUserId()));
        
        return contacts;
    }
    
	
	@PutMapping("/updatePf")
	public ResponseEntity<PF> updatePf(@ModelAttribute Contacts contacts,@RequestBody PF pf){
		PF responce = complianceService.updatePF(contacts, pf);
		return ResponseEntity.ok(responce);
	}
}
