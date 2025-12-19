package com.mahendratechnosoft.crm.service;

import java.time.LocalDate;
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

import com.mahendratechnosoft.crm.dto.AMCList;
import com.mahendratechnosoft.crm.dto.CreateAMC;
import com.mahendratechnosoft.crm.entity.AMC;
import com.mahendratechnosoft.crm.entity.AMCDomainHistory;
import com.mahendratechnosoft.crm.entity.AMCGsuitHistory;
import com.mahendratechnosoft.crm.entity.AMCHistory;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
import com.mahendratechnosoft.crm.repository.AMCDomainHistoryRepository;
import com.mahendratechnosoft.crm.repository.AMCGsuitHistoryRepository;
import com.mahendratechnosoft.crm.repository.AMCHistoryRepository;
import com.mahendratechnosoft.crm.repository.AMCRepository;
@Service
public class AMCService {

	@Autowired
	private AMCRepository amcRepository;
	@Autowired
	private AMCHistoryRepository amcHistoryRepository;
	@Autowired
	private AMCDomainHistoryRepository amcDomainHistoryRepository;
	
	@Autowired
	private AMCGsuitHistoryRepository amcGsuitHistoryRepository;
	
	public ResponseEntity<CreateAMC> createAMC( CreateAMC createAMC) {

		AMC newAmc=	amcRepository.save(createAMC.getAmcInfo());
		
		if(createAMC.getAmcHistoryInfo() != null) {
			AMCHistory  amcHistory=createAMC.getAmcHistoryInfo();
			amcHistory.setAmcId(newAmc.getAmcId());
			amcHistory.setPaid(false);
			amcHistoryRepository.save(amcHistory);
		}
			
		
		if(createAMC.getAmcDomainHistoryInfo()!= null) {
			AMCDomainHistory amcDomainHistory=createAMC.getAmcDomainHistoryInfo();
			amcDomainHistory.setAmcId(newAmc.getAmcId());
			amcDomainHistory.setPaid(false);
			amcDomainHistoryRepository.save(amcDomainHistory);
		}
		
		
		if (createAMC.getAmcGsuitHistory() != null) {
			AMCGsuitHistory amcGsuitHistory = createAMC.getAmcGsuitHistory();
			amcGsuitHistory.setAmcId(newAmc.getAmcId());
			amcGsuitHistory.setPaid(false);
			amcGsuitHistoryRepository.save(amcGsuitHistory);
		}
		
		return ResponseEntity.ok(createAMC);
	}
	
	
	public ResponseEntity<?> getAllAMC(@PathVariable int page, @PathVariable int size,  Object loginUser,String search,String expiryFromDate,String expiryToDate) {

		try {
			
			LocalDate from=null;
			LocalDate to=null;
			
			if (expiryFromDate != null && !expiryFromDate.trim().isEmpty()) {
			    from = LocalDate.parse(expiryFromDate.trim());
			}

			if (expiryToDate != null && !expiryToDate.trim().isEmpty()) {
			    to = LocalDate.parse(expiryToDate.trim());
			}
			
			
			ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<AMCList> amcPage = null;
			
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
				amcPage = amcRepository.findByAMC(adminId,null,search,from,to, pageable);

			}else if(moduleAccess.isAmcViewAll()) {
				amcPage = amcRepository.findByAMC(adminId,null,search,from,to, pageable);
			} 
			else {
				amcPage = amcRepository.findByAMC(adminId,employeeId,search,from,to, pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new HashMap<>();

			response.put("amcList", amcPage.getContent());
			response.put("currentPage", amcPage.getNumber());
			response.put("totalElements", amcPage.getTotalElements());
			response.put("totalPages", amcPage.getTotalPages());

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}


	public ResponseEntity<AMC> updateAMC(AMC updateAMC) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(amcRepository.save(updateAMC));
	}


	public ResponseEntity<String> deleteAMC(String amcId) {
		
		amcRepository.deleteById(amcId);
		
		amcHistoryRepository.deleteByAmcId(amcId);
		
		amcDomainHistoryRepository.deleteByAmcId(amcId);
		
	
		return ResponseEntity.ok("AMC Deleted");
	}


	public ResponseEntity<List<AMCHistory>> getAllAMCHistoy(Object loginUser,String amcId) {
		
		if(loginUser instanceof Employee employee) {
			return ResponseEntity.ok(amcHistoryRepository.findByAmcIdAndIsDeletedFalseOrderBySequenceDesc(amcId));
		}
		
		return ResponseEntity.ok(amcHistoryRepository.findByAmcIdOrderBySequenceDesc(amcId));
	}


	public ResponseEntity<AMCHistory> updateAMCHistory(AMCHistory updateAMCHistory) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(amcHistoryRepository.save(updateAMCHistory));
	}


	public ResponseEntity<String> deleteAMCHistory(Object loginUser,String amcHistoryId) {
		
		if(loginUser instanceof Employee employee) {
			AMCHistory amcHistory = amcHistoryRepository.findById(amcHistoryId)
				.orElseThrow(()->new RuntimeException("No histosry found with id :"+ amcHistoryId));
				amcHistory.setDeleted(true);
				amcHistoryRepository.save(amcHistory);
			return ResponseEntity.ok("Deleted Successfully");
		}
		
		amcHistoryRepository.deleteById(amcHistoryId);
		return ResponseEntity.ok("Deleted Successfully");
	}
	
	
	public ResponseEntity<List<AMCDomainHistory>> getAllAMCDomainHistoy(Object loginUser,String amcId) {
		if(loginUser instanceof Employee employee) {
			return ResponseEntity.ok(amcDomainHistoryRepository.findByAmcIdAndIsDeletedFalseOrderBySequenceDesc(amcId));
		}
		return ResponseEntity.ok(amcDomainHistoryRepository.findByAmcIdOrderBySequenceDesc(amcId));
	}


	public ResponseEntity<AMCDomainHistory> updateAMCDomainHistoy(AMCDomainHistory updateAMCDomainHistory) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(amcDomainHistoryRepository.save(updateAMCDomainHistory));
	}


	public ResponseEntity<String> deleteAMCDomainHistory(Object loginUser,String amcDomainHistoryId) {
		if(loginUser instanceof Employee employee) {
			AMCDomainHistory domainHistory = amcDomainHistoryRepository.findById(amcDomainHistoryId)
				.orElseThrow(()->new RuntimeException("No histosry found with id :"+ amcDomainHistoryId));
			domainHistory.setDeleted(true);
			amcDomainHistoryRepository.save(domainHistory);
			return ResponseEntity.ok("Deleted Successfully");
		}
		
		amcDomainHistoryRepository.deleteById(amcDomainHistoryId);
		return ResponseEntity.ok("Deleted Successfully");
	}


	public ResponseEntity<AMC> getAmcById(String amcId) {
		return ResponseEntity.ok(amcRepository.findById(amcId)
				.orElseThrow(()->new RuntimeException("Amc not Found with Id : "+amcId)));
	}

	public ResponseEntity<List<AMCGsuitHistory>> getAllAMCGsuitHistory(Object loginUser,String amcId) {
		if(loginUser instanceof Employee employee) {
			return ResponseEntity.ok(amcGsuitHistoryRepository.findByAmcIdAndIsDeletedFalseOrderBySequenceDesc(amcId));
		}
		return ResponseEntity.ok(amcGsuitHistoryRepository.findByAmcIdOrderBySequenceDesc(amcId));
	}
	
	public ResponseEntity<AMCGsuitHistory> updateAMCGsuitHistory(AMCGsuitHistory amcGsuitHistory) {
		return ResponseEntity.ok(amcGsuitHistoryRepository.save(amcGsuitHistory));
	}
	
	public ResponseEntity<String> deleteAMCGsuitHistory(Object loginUser,String amcGsuitHistoryId) {
		
		if(loginUser instanceof Employee employee) {
			AMCGsuitHistory gsuitHistory = amcGsuitHistoryRepository.findById(amcGsuitHistoryId)
				.orElseThrow(()->new RuntimeException("No histosry found with id :"+ amcGsuitHistoryId));
			gsuitHistory.setDeleted(true);
			amcGsuitHistoryRepository.save(gsuitHistory);
			return ResponseEntity.ok("Deleted Successfully");
		}
		
		amcGsuitHistoryRepository.deleteById(amcGsuitHistoryId);
		return ResponseEntity.ok("Deleted Successfully");
	}
	
	public ResponseEntity<Boolean> isGsuitHistorySequenceUnique(String amcId, int sequence) {
        return ResponseEntity.ok(!amcGsuitHistoryRepository.existsByAmcIdAndSequence(amcId, sequence));
    }
	
	public ResponseEntity<Boolean> isDomainHistorySequenceUnique(String amcId, int sequence) {
        return ResponseEntity.ok(!amcDomainHistoryRepository.existsByAmcIdAndSequence(amcId, sequence));
    }
	
	public ResponseEntity<Boolean> isAmcHistorySequenceUnique(String amcId, int sequence) {
        return ResponseEntity.ok(!amcHistoryRepository.existsByAmcIdAndSequence(amcId, sequence));
    }
	
    public ResponseEntity<Map<String, Object>> getDashboardCounts(String adminId) {
        Map<String, Object> counts = amcRepository.getDashboardCounts(adminId);
        return ResponseEntity.ok(counts);
    }
	
}
