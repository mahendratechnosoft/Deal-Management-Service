package com.mahendratechnosoft.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Department;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.PaymentProfile;
import com.mahendratechnosoft.crm.entity.Role;
import com.mahendratechnosoft.crm.repository.DepartmentRepository;
import com.mahendratechnosoft.crm.repository.PaymentProfileRepository;
import com.mahendratechnosoft.crm.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class SettingServices {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PaymentProfileRepository paymentProfileRepository;
	
	public Department createDepartment(Department department) {
		Department save = departmentRepository.save(department);
		return save;
	}
	
	public List<Department> getAllDepartmentByAdmin(String adminId, String name) {
	    if (name != null && !name.trim().isEmpty()) {
	        return departmentRepository.findAllByAdminIdAndNameContainingIgnoreCase(adminId, name);
	    } else {
	        return departmentRepository.findAllByAdminId(adminId);
	    }
	}
	
	public Role createRole(Role role) {
		Role save = roleRepository.save(role);
		return save;
	}
	
	public List<Role> getAllRoleByDepartemntId(String departmentId){
		List<Role> allByDepartmentId = roleRepository.findAllByDepartmentId(departmentId);
		return allByDepartmentId;
	}
	
	@Transactional
    public void deleteDepartmentAndRoles(String departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new RuntimeException("Department not found with id: " + departmentId);
        }
        roleRepository.deleteByDepartmentId(departmentId);
        departmentRepository.deleteById(departmentId);
    }
	
    public void deleteRole(String roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new RuntimeException("Role not found with id: " + roleId);
        }
        roleRepository.deleteById(roleId);
    }
    
    
    public ResponseEntity<PaymentProfile> createPaymentProfile(Object logginUser,PaymentProfile paymentProfile){
    	String adminId = null;
    	if(logginUser instanceof Admin admin) {
    		adminId = admin.getAdminId();
    	}
    	paymentProfile.setAdminId(adminId);
    	PaymentProfile saved = paymentProfileRepository.save(paymentProfile);
    	return ResponseEntity.ok(saved);
    }
    
    public ResponseEntity<List<PaymentProfile>> getAllPaymentProfile(Object logginUser){
    	String adminId = null;
    	if(logginUser instanceof Admin admin) {
    		adminId = admin.getAdminId();
    	}
    	List<PaymentProfile> paymentProfiles= paymentProfileRepository.findByAdminId(adminId);
    	
    	return ResponseEntity.ok(paymentProfiles);
    }
    
    public ResponseEntity<PaymentProfile> getPaymentProfileById(String paymentProfileId){
    	PaymentProfile paymentProfile= paymentProfileRepository.findById(paymentProfileId)
    			.orElseThrow(()->new RuntimeException("Payment profile not found for id : "+ paymentProfileId));
    	return ResponseEntity.ok(paymentProfile);
    }
    
    public ResponseEntity<String> deletePaymentProfile(String paymentProfileId){
    	PaymentProfile paymentProfile= paymentProfileRepository.findById(paymentProfileId)
    			.orElseThrow(()->new RuntimeException("Payment profile not found for id : "+ paymentProfileId));
    	paymentProfileRepository.delete(paymentProfile);
    	return ResponseEntity.ok("Payment profile deleted successfully with id :"+ paymentProfileId);
    }
    
    public ResponseEntity<PaymentProfile> updatePaymentProfileStatus(String paymentProfileId, boolean isActive) {
        PaymentProfile profile = paymentProfileRepository.findById(paymentProfileId)
                .orElseThrow(() -> new RuntimeException("Payment profile not found"));

        profile.setActive(isActive);
        PaymentProfile save = paymentProfileRepository.save(profile);
        return ResponseEntity.ok(save);
    }
}
