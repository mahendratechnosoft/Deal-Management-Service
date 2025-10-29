package com.mahendratechnosoft.crm.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Deals;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.repository.DealsRepository;

@Service
public class DealsService {
	
	@Autowired
	DealsRepository dealsRepository;
	
	
	public ResponseEntity<?> createDeals(@RequestBody Deals deal) {

		try {
			deal.setCreatedDateTime(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime());
			dealsRepository.save(deal);
			return ResponseEntity.ok(deal);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	public ResponseEntity<?> updateDeals(@RequestBody Deals deal) {

		try {
			deal.setCreatedDateTime(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime());
			dealsRepository.save(deal);
			return ResponseEntity.ok(deal);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	
	public ResponseEntity<?> getAllDeals(@PathVariable int page ,@PathVariable int size,Object loginUser) {

	    try {
	    	  String role = "ROLE_EMPLOYEE";
	          String adminId = null;
	          String employeeId=null;
	          Page<Deals> dealPage=null;
	    	   if (loginUser instanceof Admin admin) {
	               role = admin.getUser().getRole();
	               adminId = admin.getAdminId();
	           } 
	           else if (loginUser instanceof Employee employee) {
	               
	        	   employeeId = employee.getEmployeeId();
	           }

	        // 2. Fetch paginated leads for company
	        Pageable pageable = PageRequest.of(page, size);
	        if(role.equals("ROLE_ADMIN")){
	        	dealPage = dealsRepository.findByAdminIdOrderByDealIdDesc(adminId, pageable);
 
	        }else {
	        	
	        	dealPage = dealsRepository.findByEmployeeIdOrderByDealIdDesc(employeeId, pageable);
	        }
	        // 3. Prepare response
	        Map<String, Object> response = new HashMap<>();
	        
	        response.put("dealList", dealPage.getContent());
	        response.put("currentPage", dealPage.getNumber());
	      
	        response.put("totalPages", dealPage.getTotalPages());

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error " + e.getMessage());
	    }
	}
	

}
