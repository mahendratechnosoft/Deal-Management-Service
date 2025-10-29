package com.mahendratechnosoft.crm.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mahendratechnosoft.crm.dto.LeadWithColumnsDTO;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Deals;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.LeadColumn;
import com.mahendratechnosoft.crm.entity.LeadStatus;
import com.mahendratechnosoft.crm.entity.Leads;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.repository.LeadColumnRepository;
import com.mahendratechnosoft.crm.repository.LeadInfoRepository;
import com.mahendratechnosoft.crm.repository.LeadRepository;
import com.mahendratechnosoft.crm.repository.LeadStatusRepository;

@Service
public class LeadService {
	
	@Autowired
	private LeadInfoRepository leadInfoRepository;

	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private LeadColumnRepository leadColumnRepository;
	

	
	@Autowired
	private LeadStatusRepository leadStatusRepository;

	User user;



	
	public ResponseEntity<?> createLead(@RequestBody Leads dto) {
		// 1. Save or update LeadColumn sequence
		try {
		LeadColumn leadColumn = leadColumnRepository.findByAdminId(dto.getAdminId());

		if (leadColumn == null) {
			leadColumn = new LeadColumn();
			leadColumn.setColumns(dto.getColumns());

		} else {
			leadColumn.setColumns(dto.getColumns());
		}
		
		leadColumn.setAdminId(dto.getAdminId());
		leadColumnRepository.save(leadColumn);

		dto.setCreatedDate(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime());
		dto.setUpdatedDate(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime());
		leadRepository.save(dto);

		// 3. Return updated columns and saved lead
		return ResponseEntity.ok(dto);
		
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error " + e.getMessage());
			
		}
	}

	
	public ResponseEntity<?> getAllLeads(@PathVariable int page ,@PathVariable int size,Object loginUser) {

	    try {
	    	
	    	  String role = "ROLE_EMPLOYEE";
	          String adminId = null;
	          String employeeId=null;
	          Page<Leads> leadPage=null;
	    	   if (loginUser instanceof Admin admin) {
	               role = admin.getUser().getRole();
	               adminId = admin.getAdminId();
	           } 
	           else if (loginUser instanceof Employee employee) {
	               
	        	   employeeId = employee.getEmployeeId();
	        	   adminId=employee.getAdmin().getAdminId();
	           }

	    	
	        // 1. Fetch column metadata for company
	        LeadColumn leadColumn = leadColumnRepository.findByAdminId(adminId);
	        List<LeadColumn.ColumnDefinition> sortedColumns=null ;
	        if(leadColumn!=null) {
	        	sortedColumns=  leadColumn.getColumns()
	                .stream()
	                .sorted(Comparator.comparingInt(LeadColumn.ColumnDefinition::getSequence))
	                .toList();
	        }
	        

	        // 2. Fetch paginated leads for company
	        Pageable pageable = PageRequest.of(page, size);
	        
	        if(role.equals("ROLE_ADMIN")){
	        	leadPage = leadRepository.findByAdminIdOrderByIdDesc(adminId, pageable);
 
	        }else {
	        	
	        	leadPage = leadRepository.findByEmployeeIdOrderByIdDesc(employeeId, pageable);
	        }
	        

	        // 3. Prepare response
	        Map<String, Object> response = new HashMap<>();
	        response.put("columnSequence", sortedColumns);
	        response.put("leadList", leadPage.getContent());
	        response.put("currentPage", leadPage.getNumber());
	      //  response.put("totalItems", leadPage.getTotalElements());
	        response.put("totalPages", leadPage.getTotalPages());

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error " + e.getMessage());
	    }
	}



	@PutMapping("/updateLead")
	public ResponseEntity<?> updateLead( @RequestBody Leads lead) {
		try {
			Map<String,Object> leadInfo=new HashMap<>();
			LeadColumn leadColumn = leadColumnRepository.findByAdminId(lead.getAdminId());
			

			if (leadColumn == null) {
				leadColumn = new LeadColumn();
				leadColumn.setColumns(lead.getColumns());

			} else {
				leadColumn.setColumns(lead.getColumns());
			}

			lead.setFields(lead.getFields());
			leadRepository.save(lead);
			
		//	leadColumn.setAdminId(lead.getAdminId());
		//	leadColumnRepository.save(leadColumn);
			
			leadInfo.put("lead",lead);
			leadInfo.put("leadColumn", leadColumn);
			return ResponseEntity.ok(lead);
		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}
	}
	
	@GetMapping("/getLeadById/{leadId}")
	public ResponseEntity<?> getLeadById(@PathVariable String leadId) {
		try {
			
			Map<String,Object> leadInfo=new HashMap<>();
			
			LeadColumn leadColumn = leadColumnRepository.findByAdminId("1");
			 List<LeadColumn.ColumnDefinition> sortedColumns = leadColumn.getColumns()
				        .stream()
				        .sorted(Comparator.comparingInt(LeadColumn.ColumnDefinition::getSequence))
				        .toList();
			Optional<Leads> lead = leadRepository.findById(leadId);
			
			leadInfo.put("lead",lead);
			leadInfo.put("leadColumn", sortedColumns);
			
			return ResponseEntity.ok(leadInfo);
		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());

		}
	}
	

	@DeleteMapping("/deleteLead/{id}")
	public String deleteLead(@PathVariable String id) {
		leadRepository.deleteById(id);
		return "Lead deleted: " + id;
	}
	
	// Lead Colums APi as follows
	
	@GetMapping("/getAllColumns")
	public ResponseEntity<?> getAllColumns() {
		try {
			return ResponseEntity.ok(leadColumnRepository.findByAdminId("1"));
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}
	}

	@PostMapping("/updateLeadColumn")
	public LeadColumn createColumn(@RequestBody LeadColumn leadColumn) {
        LeadColumn leadColumnInfo=leadColumnRepository.findByAdminId("1");
        if(leadColumnInfo==null) {
        	LeadColumn leadColumnNew=new LeadColumn();
        	
        	leadColumnNew.setAdminId("1");
        	leadColumnNew.setColumns(leadColumn.getColumns());
        	
        	return leadColumnRepository.save(leadColumnNew);
        	
        }else {
        	 leadColumnInfo.setColumns(leadColumn.getColumns());
     		return leadColumnRepository.save(leadColumnInfo);
        }
       
	}
	


	@PutMapping("/leadColumnRename")
	public ResponseEntity<?> renameColumn(@RequestBody Map<String, String> renameRequest) {

		try {
			String oldName = renameRequest.get("oldName");
			String newName = renameRequest.get("newName");

			LeadColumn column = leadColumnRepository.findByAdminId("1");

			column.getColumns().forEach(c -> {
				if (c.getName().equals(oldName)) {
					c.setName(newName);
				}
			});

			leadColumnRepository.save(column);
			
			
			// 3️⃣ Update all Leads JSON field keys for this company
	        List<Leads> leadsList = leadRepository.findByAdminId("1");
	        for (Leads lead : leadsList) {
	            Map<String, Object> fields = lead.getFields();
	            if (fields != null && fields.containsKey(oldName)) {
	                Object value = fields.remove(oldName);
	                fields.put(newName, value);
	                lead.setFields(fields);
	            }
	        }
	        leadRepository.saveAll(leadsList);

	        return ResponseEntity.ok("Column renamed successfully and leads updated.");
			
			
			

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}

	}

	@PutMapping("/leadColumnSequence")
	public LeadColumn updateColumnSequence(
			@RequestBody List<LeadColumn.ColumnDefinition> updatedColumns) {

		LeadColumn column = leadColumnRepository.findByAdminId("1");

		// Update only the columns provided in the request
		column.getColumns().forEach(existingColumn -> {
			updatedColumns.forEach(updated -> {
				if (existingColumn.getName().equals(updated.getName())) {
					existingColumn.setSequence(updated.getSequence());
				}
			});
		});

		// Sort the columns by the new sequence (optional)
		column.getColumns().sort((c1, c2) -> Integer.compare(c1.getSequence(), c2.getSequence()));

		return leadColumnRepository.save(column);
	}
	
	
	@DeleteMapping("/deleteColumn")
	public ResponseEntity<String> deleteColumn(@RequestBody Map<String, String> renameRequest) {
		
		String columnName = renameRequest.get("columnName");

	    // 1. Find and update column sequence
	    LeadColumn leadColumn = leadColumnRepository.findByAdminId("1");
	    if (leadColumn == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("No column metadata found for company " + "1");
	    }
	    boolean removed = leadColumn.getColumns().removeIf(col -> col.getName().equalsIgnoreCase(columnName));
	    if (!removed) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Column '" + columnName + "' not found");
	    }
	    leadColumnRepository.save(leadColumn);
	    
	    
	    // 2️⃣ Delete this column key from every lead’s JSON fields
        List<Leads> leadsList = leadRepository.findByAdminId("1");
        for (Leads lead : leadsList) {
            Map<String, Object> fields = lead.getFields();
            if (fields != null && fields.containsKey(columnName)) {
                fields.remove(columnName);
                lead.setFields(fields);
            }
        }
        leadRepository.saveAll(leadsList);



	    return ResponseEntity.ok("Column '" + columnName + "' deleted successfully");
	}

	
	// lead status APIs
	
	
	public ResponseEntity<?> addLeadStatus(@RequestBody LeadStatus leadStatus) {

		try {

			leadStatusRepository.save(leadStatus);

			return ResponseEntity.ok(leadStatus);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());

		}
	}
	
	
	public ResponseEntity<?> getLeadStatus(Object loginUser) {

		try {
			
			  String role = "ROLE_EMPLOYEE";
	          String adminId = null;
	          String employeeId=null;
	          Page<Leads> leadPage=null;
	    	   if (loginUser instanceof Admin admin) {
	               role = admin.getUser().getRole();
	               adminId = admin.getAdminId();
	           } 
	           else if (loginUser instanceof Employee employee) {
	               
	        	   employeeId = employee.getEmployeeId();
	        	   adminId=employee.getAdmin().getAdminId();
	           }

			List<LeadStatus> leadStatus = leadStatusRepository.findByAdminId(adminId);

			return ResponseEntity.ok(leadStatus);

		} catch (Exception e) {
            e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}
	}
	
	
	public ResponseEntity<?> deleteLeadStatus(String leadStatusId) {

		try {

			leadStatusRepository.deleteById(leadStatusId);

			return ResponseEntity.ok("Lead Status Deleted");

		} catch (Exception e) {
            e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}
	}
	
	

}
