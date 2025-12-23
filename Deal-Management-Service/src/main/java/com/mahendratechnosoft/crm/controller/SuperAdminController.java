package com.mahendratechnosoft.crm.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahendratechnosoft.crm.dto.AdminInfoDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.repository.AdminRepository;
import com.mahendratechnosoft.crm.repository.ModuleAccessRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/super")
public class SuperAdminController {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModuleAccessRepository moduleAccessRepository;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	
	@Transactional
	@PostMapping("/createAdmin")
	public ResponseEntity<?> createAdmin(@RequestBody AdminInfoDto admin){
		
		
		try {
			
			User user=new User();
			
			user.setExpiryDate(admin.getExpiryDate());
			user.setLoginEmail(admin.getLoginEmail());
			user.setRole("ROLE_ADMIN");
			user.setPassword(passwordEncoder.encode(admin.getPassword()));
			
			userRepository.save(user);
			
			
		    Admin adminNew = new Admin();
		    adminNew.setLoginEmail(admin.getLoginEmail());
		    adminNew.setUser(user);
		    adminNew.setCompanyEmail(admin.getCompanyEmail());
		    adminNew.setCompanyName(admin.getCompanyName());
		    adminNew.setPhone(admin.getPhone());
		    adminNew.setAddress(admin.getAddress());
		    adminNew.setGstNumber(admin.getGstNumber());
	        adminRepository.save(adminNew);
	        
	        ModuleAccess access=new ModuleAccess();
	       
	        access.setAdminId(adminNew.getAdminId());
	        
	        moduleAccessRepository.save(access);
	        
			
			return ResponseEntity.ok(adminNew);
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
			
		}
		
	}
	
	
	
	
	
	
	@GetMapping("/getAdminInfoAndModuleAccess/{adminId}")
	public ResponseEntity<?> getAdminInfoAndModuleAccess(@PathVariable String adminId) {

		try {

			Optional<Admin> adminInfo = adminRepository.findByAdmin(adminId);

			ModuleAccess access = moduleAccessRepository.findByAdminIdAndEmployeeIdAndCustomerId(adminId,null,null);

			Map<String, Object> response = new HashMap<String, Object>();

			response.put("adminInfo", adminInfo.get());
	     	response.put("moduleAccess", access);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());

		}

	}
	
	
	@PutMapping("/updateAdminInfoWithAccess")
	public ResponseEntity<?> updateAdminInfoWithAccess(@RequestBody AdminInfoDto admin) {

		try {
			
			Map<String, Object> response = new LinkedHashMap<String, Object>();


		   Optional<Admin> adminInfo=adminRepository.findByAdmin(admin.getAdminId());
		   
		   adminInfo.get().setCompanyEmail(admin.getCompanyEmail());
		   adminInfo.get().setName(admin.getName());
		   adminInfo.get().setCompanyName(admin.getCompanyName());
		   adminInfo.get().setPhone(admin.getPhone());
		   adminInfo.get().setAddress(admin.getAddress());
		   adminInfo.get().setGstNumber(admin.getGstNumber());
		   
		   adminRepository.save(adminInfo.get());
		   
		//   ModuleAccess access=moduleAccessRepository.findByAdminIdAndEmployeeId(admin.getAdminId(),null);
		   ModuleAccess savedAdminAccess = moduleAccessRepository.save(admin.getModuleAccess());
		   
		   List<ModuleAccess> customerAccessList = moduleAccessRepository.findByAdminIdAndCustomerIdIsNotNull(admin.getAdminId());

	        if (!customerAccessList.isEmpty()) {
	            Field[] fields = ModuleAccess.class.getDeclaredFields();
	            for (ModuleAccess customerAccess : customerAccessList) {
	                for (Field field : fields) {
	                    if (field.getName().startsWith("customer") && field.getType() == boolean.class) {
	                        try {
	                            field.setAccessible(true);
	                            Object newValue = field.get(savedAdminAccess);
	                            field.set(customerAccess, newValue);
	                            
	                        } catch (IllegalAccessException e) {
	                            System.err.println("Failed to sync field: " + field.getName());
	                        }
	                    }
	                }
	            }
	            moduleAccessRepository.saveAll(customerAccessList);
	        }
		   
			response.put("adminInfo",adminInfo);
	     	response.put("moduleAccess",  savedAdminAccess);


			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());

		}

	}
	
	
	
	

	@GetMapping("/getAllAdmin/{page}/{size}")
	public ResponseEntity<?> getAllCustomer(@PathVariable int page, @PathVariable int size, String search) {

		try {

			Page<AdminInfoDto> adminListPage = null;

			// 2. Fetch paginated leads for company
			Pageable pageable = PageRequest.of(page, size);

			adminListPage = adminRepository.findAllAdmins(search, pageable);


			// 3. Prepare response
			Map<String, Object> response = new HashMap<>();

			response.put("adminList", adminListPage.getContent());
			response.put("currentPage", adminListPage.getNumber());
			response.put("totalAdmin", adminListPage.getTotalElements());
			response.put("totalPages", adminListPage.getTotalPages());

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}

}
