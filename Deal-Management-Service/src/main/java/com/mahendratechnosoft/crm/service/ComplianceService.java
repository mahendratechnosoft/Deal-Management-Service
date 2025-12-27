package com.mahendratechnosoft.crm.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.dto.EsicDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Contacts;
import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.Esic;
import com.mahendratechnosoft.crm.entity.EsicContent;
import com.mahendratechnosoft.crm.entity.PF;
import com.mahendratechnosoft.crm.helper.ResourceNotFoundException;
import com.mahendratechnosoft.crm.repository.ContactsRepository;
import com.mahendratechnosoft.crm.repository.CustomerRepository;
import com.mahendratechnosoft.crm.repository.EsicContentRepository;
import com.mahendratechnosoft.crm.repository.EsicRepository;
import com.mahendratechnosoft.crm.repository.PFRepository;

import jakarta.transaction.Transactional;

@Service
public class ComplianceService {
	
	@Autowired
	private PFRepository pfRepository;
	
	@Autowired
	private ContactsRepository contactsRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private EsicRepository esicRepository;
	
	@Autowired
	private EsicContentRepository esicContentRepository;
	
	
	public PF createPF(PF pf) {
		String contactId = pf.getContactId();
		Contacts contacts = contactsRepository.findById(contactId)
		.orElseThrow(()->new ResourceNotFoundException("Contact", "contactID", contactId));
		
		Customer customer = customerRepository.findById(contacts.getCustomerId())
		.orElseThrow(()->new ResourceNotFoundException("Customer", "CustomerId", contacts.getCustomerId()));
		
		pf.setAdminId(customer.getAdminId());
		pf.setEmployeeId(customer.getEmployeeId());
		pf.setCustomerId(customer.getCustomerId());
		pf.setCustomerName(customer.getCompanyName());
		pf.setCreatedBy(contacts.getName());
		
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
	
	@Transactional
	public Esic createEsic(EsicDto request) {
		Esic esic = request.getEsic();
	    String contactId = esic.getContactId();

	    Contacts contacts = contactsRepository.findById(contactId)
	            .orElseThrow(() -> new ResourceNotFoundException("Contact", "contactID", contactId));

	    Customer customer = customerRepository.findById(contacts.getCustomerId())
	            .orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", contacts.getCustomerId()));

	    esic.setAdminId(customer.getAdminId());
	    esic.setEmployeeId(customer.getEmployeeId());
	    esic.setCustomerId(customer.getCustomerId());
	    esic.setCustomerName(customer.getCompanyName());
	    esic.setCreatedBy(contacts.getName());
	    Esic savedEsic = esicRepository.save(esic);

	    if (request.getEsicContents() != null) {
	    	request.getEsicContents().forEach(content -> content.setEsicId(savedEsic.getEsicId()));
	    	esicContentRepository.saveAll(request.getEsicContents());
	    }

	    return savedEsic;
	}
	
	
	@Transactional
	public Esic updateEsic(EsicDto request) {
		Esic esic = request.getEsic();
	    Esic savedEsic = esicRepository.save(esic);
	    if (request.getEsicContents() != null) {
	    	request.getEsicContents().forEach(content -> content.setEsicId(savedEsic.getEsicId()));
	    	esicContentRepository.saveAll(request.getEsicContents());
	    }

	    return savedEsic;
	}
	
	public void deleteEsicById(String esicId) {
		if(!esicRepository.existsById(esicId)) {
			throw new ResourceNotFoundException("ESIC","esicId",esicId);
		}
		esicRepository.deleteById(esicId);
	}
	
	public void deleteEsicContentById(String esicContentId) {
		if(!esicContentRepository.existsById(esicContentId)) {
			throw new ResourceNotFoundException("Esic Content","esicContentId",esicContentId);
		}
		esicContentRepository.deleteById(esicContentId);
	}
	
	public EsicDto getEsicById(String esicId) {
		Esic esic = esicRepository.findById(esicId)
		.orElseThrow(()->new ResourceNotFoundException("ESIC","esicId",esicId));
		
		List<EsicContent> esicContentList = esicContentRepository.findByEsicId(esic.getEsicId());
		
		EsicDto esicDto = new EsicDto();
		esicDto.setEsic(esic);
		esicDto.setEsicContents(esicContentList);
		return esicDto;
	}
	
	public Page<Esic> getAllEsics(
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
		
		
		return esicRepository.findEsicsByHierarchyAndName(
                adminId, 
                employeeId, 
                customerId, 
                contactId, 
                search, 
                pageable
        );
    }
	
	public PF updatePFVerificationStatus(String pfId, boolean status) {
        PF pf = pfRepository.findById(pfId)
                .orElseThrow(() -> new ResourceNotFoundException("PF","pfId", pfId));
        pf.setVerified(status);
        return pfRepository.save(pf);
    }
	
	public Esic updateEsicVerificationStatus(String esicId, boolean status) {
		Esic esic = esicRepository.findById(esicId)
                .orElseThrow(() -> new ResourceNotFoundException("Esic","esicId", esicId));
		esic.setVerified(status);
        return esicRepository.save(esic);
    }
}
