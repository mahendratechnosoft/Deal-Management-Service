package com.mahendratechnosoft.crm.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.mahendratechnosoft.crm.dto.CustomerDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.repository.CustomerRepository;
import com.mahendratechnosoft.crm.repository.ModuleAccessRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;

@Service
public class CustomerService {

    private final ModuleAccessRepository moduleAccessRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

    CustomerService(ModuleAccessRepository moduleAccessRepository) {
        this.moduleAccessRepository = moduleAccessRepository;
    }

	public ResponseEntity<?> createCustomer(Object loginUser, CustomerDto request) {
		try {
			
			String adminId=null;
			String employeeId = null;
			
			if(loginUser instanceof Admin admin) {
				adminId = admin.getAdminId();
			}else if(loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
			}
			
			ModuleAccess adminAccess = moduleAccessRepository.findByAdminIdAndEmployeeIdAndCustomerId(adminId, null,null);
			
			Customer customer = request.getCustomer();
			customer.setAdminId(adminId);
			customer.setEmployeeId(employeeId);
			
			if(request.getLoginEmail() != null && !request.getLoginEmail().isEmpty() &&
				request.getPassword() != null && !request.getPassword().isEmpty()) {
				
				if (userRepository.existsByLoginEmail(request.getLoginEmail())) {
		            throw new RuntimeException("Error: Email is already in use!");
		        }
				
				User newCustomerUser = new User();
				newCustomerUser.setLoginEmail(request.getLoginEmail());
				newCustomerUser.setPassword(passwordEncoder.encode(request.getPassword()));
				newCustomerUser.setRole("ROLE_CUSTOMER");
				newCustomerUser.setExpiryDate(LocalDateTime.now().plusYears(1));
				
				User savedUser = userRepository.save(newCustomerUser);
				customer.setUserId(savedUser.getUserId());
			}
			
			Customer savedCustomer = customerRepository.save(customer);
			if(savedCustomer.getUserId()!= null && !savedCustomer.getUserId().isBlank()) {
				ModuleAccess customerModuleAccess = new ModuleAccess();
		        customerModuleAccess.setAdminId(adminId);
		        customerModuleAccess.setCustomerId(savedCustomer.getCustomerId());
		        Field[] fields = ModuleAccess.class.getDeclaredFields();
		        for (Field field : fields) {
		            if (field.getName().startsWith("customer") && field.getType() == boolean.class) {
		                try {
		                    field.setAccessible(true);
		                    Object value = field.get(adminAccess);
		                    field.set(customerModuleAccess, value); 
		                } catch (IllegalAccessException e) {
		                    System.err.println("Failed to copy field: " + field.getName());
		                }
		            }
		        }
		        moduleAccessRepository.save(customerModuleAccess);
			}
			return ResponseEntity.ok(customer);

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	public ResponseEntity<?> getCustomerById(String  customerId) {

		try {

			Customer customer = customerRepository.findByCustomerId(customerId);
			CustomerDto customerDto = new CustomerDto();
			customerDto.setCustomer(customer);
			
			String userId = customer.getUserId();
			if(userId != null && !userId.isBlank()) {
				User userCustomer = userRepository.findById(userId)
						.orElseThrow(()-> new RuntimeException("user with not found with id :" + userId));
				customerDto.setLoginEmail(userCustomer.getLoginEmail());
				customerDto.setActive(userCustomer.isActive());
			}
			
			return ResponseEntity.ok(customerDto);

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}

	public ResponseEntity<?> updateCustomer(Object loginUser, CustomerDto request) {

		try {
			String adminId=null;
			
			if(loginUser instanceof Admin admin) {
				adminId = admin.getAdminId();
			}else if(loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
			}
			
			Customer customer = request.getCustomer();
			customer.setAdminId(adminId);
			ModuleAccess adminAccess = moduleAccessRepository.findByAdminIdAndEmployeeIdAndCustomerId(adminId, null,null);
			
			if(request.isActive()) {
				if(request.getLoginEmail() != null && !request.getLoginEmail().isEmpty()) {
					
					String userId = customer.getUserId();
					
					if(userId != null && !userId.isBlank()) {
						User userCustomer = userRepository.findById(userId)
						.orElseThrow(()-> new RuntimeException("user with not found with id :" + userId));
						
						if (userRepository.existsByLoginEmailAndUserIdNot(request.getLoginEmail(),userCustomer.getUserId())) {
				            throw new RuntimeException("Error: Email is already in use!");
				        }
						
						userCustomer.setLoginEmail(request.getLoginEmail());
						if(request.getPassword()!=null && !request.getPassword().isBlank()) {
							userCustomer.setPassword(passwordEncoder.encode(request.getPassword()));
						}
						userCustomer.setActive(true);
						userRepository.save(userCustomer);
					} else {
						if (userRepository.existsByLoginEmail(request.getLoginEmail())) {
				            throw new RuntimeException("Error: Email is already in use!");
				        }
						
						User newCustomerUser = new User();
						newCustomerUser.setLoginEmail(request.getLoginEmail());
						newCustomerUser.setPassword(passwordEncoder.encode(request.getPassword()));
						newCustomerUser.setRole("ROLE_CUSTOMER");
						newCustomerUser.setExpiryDate(LocalDateTime.now().plusYears(1));
						
						User savedUser = userRepository.save(newCustomerUser);
						customer.setUserId(savedUser.getUserId());
						
						if(customer.getUserId()!= null && !customer.getUserId().isBlank()) {
							ModuleAccess customerModuleAccess = new ModuleAccess();
					        customerModuleAccess.setAdminId(adminId);
					        customerModuleAccess.setCustomerId(customer.getCustomerId());
					        Field[] fields = ModuleAccess.class.getDeclaredFields();
					        for (Field field : fields) {
					            if (field.getName().startsWith("customer") && field.getType() == boolean.class) {
					                try {
					                    field.setAccessible(true);
					                    Object value = field.get(adminAccess);
					                    field.set(customerModuleAccess, value); 
					                } catch (IllegalAccessException e) {
					                    System.err.println("Failed to copy field: " + field.getName());
					                }
					            }
					        }
					        moduleAccessRepository.save(customerModuleAccess);
						}
					}
				}
			}else {
				String userId = customer.getUserId();
				if(userId != null && !userId.isBlank()) {
					User userCustomer = userRepository.findById(userId)
					.orElseThrow(()-> new RuntimeException("user with not found with id :" + userId));
					userCustomer.setActive(false);
					userRepository.save(userCustomer);
				}
			}
			Customer savedCustomer = customerRepository.save(customer);
			return ResponseEntity.ok(savedCustomer);

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	public ResponseEntity<?> updateCustomerStatus(String customerId, boolean status) {

		try {

			Customer customer = customerRepository.findByCustomerId(customerId);
			customer.setStatus(status);
			customerRepository.save(customer);
			return ResponseEntity.ok(customer);

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}

	public ResponseEntity<?> getAllCustomer(@PathVariable int page, @PathVariable int size, Object loginUser,String search) {

		try {
			ModuleAccess moduleAccess=null;
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
				moduleAccess=employee.getModuleAccess();
			}

			// 2. Fetch paginated leads for company
			Pageable pageable = PageRequest.of(page, size);
			if (role.equals("ROLE_ADMIN")) {
				customerPage = customerRepository.findByAdminId(adminId,search, pageable);

			

			}else if(moduleAccess.isCustomerViewAll()) {
				
				customerPage = customerRepository.findByAdminId(adminId,search, pageable);
				
			} 
			else if(moduleAccess.isCustomerViewAll()) {
				
				customerPage = customerRepository.findByAdminId(adminId,search, pageable);
				
			} 

			else {

				customerPage = customerRepository.findByEmployeeId(employeeId, search,pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new HashMap<>();

			response.put("customerList", customerPage.getContent());
			response.put("currentPage", customerPage.getNumber());
			response.put("totalCustomers", customerPage.getTotalElements());
			response.put("totalPages", customerPage.getTotalPages());

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}
	
	
	
	public ResponseEntity<?> getAllCustomerStatusAndCount( Object loginUser) {

		try {
			
			String adminId = null;
			if (loginUser instanceof Admin admin) {
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
			}

			List<Object[]> result = customerRepository.countLeadsByStatusAndAdminId(adminId);

		

			return ResponseEntity.ok(result);

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
	
	
	
	public ResponseEntity<?> checkCustomerExist(Object loginUser,String companyName) {
		try {
			String adminId = null;
			if (loginUser instanceof Admin admin) {
				
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {

				adminId = employee.getAdmin().getAdminId();
				
				
			}
			boolean exists = customerRepository.existsByCompanyNameAndAdminId(companyName,adminId);
          
			return ResponseEntity.ok(exists);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

}
