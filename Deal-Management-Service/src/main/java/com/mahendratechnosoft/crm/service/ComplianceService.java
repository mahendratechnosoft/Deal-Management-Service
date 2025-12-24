package com.mahendratechnosoft.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.entity.Contacts;
import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.PF;
import com.mahendratechnosoft.crm.helper.ResourceNotFoundException;
import com.mahendratechnosoft.crm.repository.ContactsRepository;
import com.mahendratechnosoft.crm.repository.CustomerRepository;
import com.mahendratechnosoft.crm.repository.PFRepository;

@Service
public class ComplianceService {
	
	@Autowired
	private PFRepository pfRepository;
	
	@Autowired
	private ContactsRepository contactsRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	public PF createPF(PF pf) {
		String contactId = pf.getContactId();
		Contacts contacts = contactsRepository.findById(contactId)
		.orElseThrow(()->new ResourceNotFoundException("Contact", "contactID", contactId));
		
		Customer customer = customerRepository.findById(contacts.getCustomerId())
		.orElseThrow(()->new ResourceNotFoundException("Customer", "CustomerId", contacts.getCustomerId()));
		
		pf.setAdminId(customer.getAdminId());
		pf.setEmployeeId(customer.getEmployeeId());
		pf.setCustomerId(customer.getCustomerId());
		
		PF save = pfRepository.save(pf);
		
		return save;
	}
	
	public PF updatePF(Object loginUser,PF pf) {
		String contactId = null;
		String customerId = null;
		String adminId = null;
		String employeeId = null;
		
		if (loginUser instanceof Contacts contacts) {
			contactId = contacts.getId();
			customerId = contacts.getCustomerId();
			Customer customer = customerRepository.findById(customerId)
			.orElseThrow(()->new ResourceNotFoundException("Customer", "CustomerId", contacts.getCustomerId()));
			adminId = customer.getAdminId();
			employeeId = customer.getEmployeeId();
		}
		
		pf.setAdminId(adminId);
		pf.setEmployeeId(employeeId);
		pf.setCustomerId(customerId);
		pf.setContactId(contactId);
		
		PF save = pfRepository.save(pf);
		
		return save;
	}

}
