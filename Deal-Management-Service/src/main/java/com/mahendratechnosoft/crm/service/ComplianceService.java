package com.mahendratechnosoft.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Contacts;
import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.Employee;
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
	
	public PF updatePF(PF pf) {
		PF save = pfRepository.save(pf);
		return save;
	}
	
	public PF getPfById(String pfId) {
		PF pf = pfRepository.findById(pfId)
		.orElseThrow(()->new ResourceNotFoundException("PF","pfId", pfId));
		return pf;
	}
	
	public void deletePfById(String pfId) {
		if(!pfRepository.existsById(pfId)) {
			throw new ResourceNotFoundException("PF","pfId", pfId);
		}
		pfRepository.deleteById(pfId);
	}
	
	public Page<PF> getAllPF(
			Object loginUser,String reqCustomerId,
			String reqContactId,String search,
			Pageable pageable ) {
		String adminId = null;
		String employeeId = null;
		String customerId = null;
		String contactId = null;
		
		if(loginUser instanceof Admin admin) {
			adminId = admin.getAdminId();
			customerId = reqCustomerId;
			contactId = reqContactId;
		}else if(loginUser instanceof Employee employee) {
			adminId = employee.getAdmin().getAdminId();
			employeeId = employee.getEmployeeId();
			customerId = reqCustomerId;
			contactId = reqContactId;
		}else if(loginUser instanceof Customer customer) {
			adminId = customer.getAdminId();
			customerId = customer.getCustomerId();
			contactId = reqContactId;
			System.out.println("contact id : "+ contactId);
		}else if( loginUser instanceof Contacts contacts) {
			Customer contactCustomer = customerRepository.findById(contacts.getCustomerId())
					.orElseThrow(()->new ResourceNotFoundException("Customer", "CustomerId", contacts.getCustomerId()));
			adminId = contactCustomer.getAdminId();
			customerId = contactCustomer.getCustomerId();
			contactId = contacts.getId();
		}
		
		
		return pfRepository.findPFsByHierarchyAndName(
                adminId, 
                employeeId, 
                customerId, 
                contactId, 
                search, 
                pageable
        );
    }

}
