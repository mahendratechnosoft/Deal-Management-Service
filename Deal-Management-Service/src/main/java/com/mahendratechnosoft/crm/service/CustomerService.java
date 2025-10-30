package com.mahendratechnosoft.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public ResponseEntity<?> createCustomer(Customer customer) {

		try {

			customerRepository.save(customer);
			return ResponseEntity.ok(customer);

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}

	public ResponseEntity<?> updateCustomer(Customer customer) {

		try {

			customerRepository.save(customer);
			return ResponseEntity.ok(customer);

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}

	public ResponseEntity<?> getAllCustomer(@PathVariable int page, @PathVariable int size, Object loginUser,String search) {

		try {
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<Customer> customerPage = null;
			if (loginUser instanceof Admin admin) {
				role = admin.getUser().getRole();
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {

				employeeId = employee.getEmployeeId();
				adminId = employee.getAdmin().getAdminId();
			}

			// 2. Fetch paginated leads for company
			Pageable pageable = PageRequest.of(page, size);
			if (role.equals("ROLE_ADMIN")) {
				customerPage = customerRepository.findByAdminId(adminId,search, pageable);

			} else {

				customerPage = customerRepository.findByEmployeeId(employeeId, search,pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new HashMap<>();

			response.put("customerList", customerPage.getContent());
			response.put("currentPage", customerPage.getNumber());
			response.put("totalPages", customerPage.getTotalPages());

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}
	
	
	public ResponseEntity<?> getCustomerListWithNameAndId(Object loginUser) {
	    try {
	    	
			String adminId = null;
			if (loginUser instanceof Admin admin) {
				
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {

				adminId = employee.getAdmin().getAdminId();
				
			}
	    	
	        List<Object[]> results = customerRepository.findCompanyNameAndIdsByCompanyId(adminId);

	        // Convert to List<Map<String, Object>> for cleaner JSON output
	        List<Map<String, Object>> list = results.stream().map(obj -> {
	            Map<String, Object> map = new HashMap<>();
	            map.put("id", obj[0]);
	            map.put("companyName", obj[1]);
	            return map;
	        }).toList();

	        return ResponseEntity.ok(list);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error: " + e.getMessage());
	    }
	}

}
