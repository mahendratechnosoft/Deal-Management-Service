package com.mahendratechnosoft.crm.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
import com.mahendratechnosoft.crm.entity.Vendor;
import com.mahendratechnosoft.crm.entity.VendorContact;
import com.mahendratechnosoft.crm.repository.VendorContactRepository;
import com.mahendratechnosoft.crm.repository.VendorRepository;

@Service
public class ExpenceService {
	
	@Autowired
	private VendorRepository vendorRepository;
	
	@Autowired
	private VendorContactRepository vendorContactRepository;
	
	public Vendor createVendor(Object loginUser,Vendor request) {
		String adminId = null;
		String employeeId= null;
		String createdBy =null;
		
		if (loginUser instanceof Admin admin) {
            adminId = admin.getAdminId();
            createdBy = admin.getName();
        } else if (loginUser instanceof Employee employee) {
            adminId = employee.getAdmin().getAdminId();
            employeeId = employee.getEmployeeId();
            createdBy = employee.getName();
        }
		
		request.setAdminId(adminId);
		request.setEmployeeId(employeeId);
		request.setCreatedBy(createdBy);
        return vendorRepository.save(request);
    }
	
	public Vendor updateVendor(Object loginUser,Vendor request) {
		String adminId = null;
		
		if (loginUser instanceof Admin admin) {
            adminId = admin.getAdminId();
        } else if (loginUser instanceof Employee employee) {
            adminId = employee.getAdmin().getAdminId();
        }
		
		request.setAdminId(adminId);
        return vendorRepository.save(request);
    }
	
	public Vendor getVendorById(String vendorId) {
		Vendor vendor = vendorRepository.findById(vendorId)
		.orElseThrow(()->new RuntimeException("Vendor is not found with id : "+vendorId));
        return vendor;
    }

	public Page<Vendor> getVendors(Object loginUser, String vendorName, Pageable pageable) {
		
		String adminId = null;
		String employeeId= null;
		String role = "ROLE_EMPLOYEE";
		ModuleAccess moduleAccess = null;
		
		if (loginUser instanceof Admin admin) {
            adminId = admin.getAdminId();
            role = admin.getUser().getRole();
        } else if (loginUser instanceof Employee employee) {
            adminId = employee.getAdmin().getAdminId();
            employeeId = employee.getEmployeeId();
            moduleAccess = employee.getModuleAccess();
        }
		
		Page<Vendor> page = null;
		
		if (role.equals("ROLE_ADMIN")) {
			page = vendorRepository.findAllByAdminAndEmployeeAndName(adminId, null, vendorName, pageable);
        } else if (role.equals("ROLE_EMPLOYEE")&& moduleAccess.isVendorAccess()) {
        	page = vendorRepository.findAllByAdminAndEmployeeAndName(adminId, null, vendorName, pageable);
        }else {
        	page = vendorRepository.findAllByAdminAndEmployeeAndName(adminId, employeeId, vendorName, pageable);
        }
		
        return page;
    }
	
	public VendorContact createVendorContact(Object loginUser,VendorContact request) {
		String adminId = null;
		String employeeId= null;
		String createdBy =null;
		
		if (loginUser instanceof Admin admin) {
            adminId = admin.getAdminId();
            createdBy = admin.getName();
        } else if (loginUser instanceof Employee employee) {
            adminId = employee.getAdmin().getAdminId();
            employeeId = employee.getEmployeeId();
            createdBy = employee.getName();
        }
		
		request.setAdminId(adminId);
		request.setEmployeeId(employeeId);
		request.setCreatedBy(createdBy);
        return vendorContactRepository.save(request);
    }
	
	public VendorContact updateVendorContact(Object loginUser,VendorContact request) {
		String adminId = null;
		
		if (loginUser instanceof Admin admin) {
            adminId = admin.getAdminId();
        } else if (loginUser instanceof Employee employee) {
            adminId = employee.getAdmin().getAdminId();
        }
		
		request.setAdminId(adminId);
        return vendorContactRepository.save(request);
    }
	
	public VendorContact getVendorContactById(String vendorContactId) {
		VendorContact vendorContact = vendorContactRepository.findById(vendorContactId)
		.orElseThrow(()->new RuntimeException("Vendor contact is not found with id : "+vendorContactId));
        return vendorContact;
    }
	
	public Page<VendorContact> getVendorContacts(String vendorId, String search, Pageable pageable) {
		Page<VendorContact> page =vendorContactRepository.findAllByAdminAndEmployeeAndName(vendorId, search, pageable);
        return page;
    }

	public void deleteVendorContact(String vendorContactId) {

	    if (!vendorContactRepository.existsById(vendorContactId)) {
	        throw new RuntimeException(
	            "Vendor contact not found with id: " + vendorContactId
	        );
	    }

	    vendorContactRepository.deleteById(vendorContactId);
	}

}
