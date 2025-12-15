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
import com.mahendratechnosoft.crm.entity.AMCHistory;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Customer;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
import com.mahendratechnosoft.crm.repository.AMCDomainHistoryRepository;
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
	
	public ResponseEntity<CreateAMC> createAMC( CreateAMC createAMC) {

	AMC newAmc=	amcRepository.save(createAMC.getAmcInfo());
		
	AMCHistory  amcHistory=createAMC.getAmcHistoryInfo();
	amcHistory.setAmcId(newAmc.getAmcId());
	amcHistoryRepository.save(amcHistory);
	
	AMCDomainHistory amcDomainHistory=createAMC.getAmcDomainHistoryInfo();
	amcDomainHistory.setAmcId(newAmc.getAmcId());
	amcDomainHistoryRepository.save(amcDomainHistory);
		
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


	public ResponseEntity<List<AMCHistory>> getAllAMCHistoy(String amcId) {
		
		return ResponseEntity.ok(amcHistoryRepository.findByAmcId(amcId));
	}


	public ResponseEntity<AMCHistory> updateAMCHistory(AMCHistory updateAMCHistory) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(amcHistoryRepository.save(updateAMCHistory));
	}


	public ResponseEntity<String> deleteAMCHistory(String amcHistoryId) {
		amcHistoryRepository.deleteById(amcHistoryId);
		return ResponseEntity.ok("Deleted Successfully");
	}
	
	
	public ResponseEntity<List<AMCDomainHistory>> getAllAMCDomainHistoy(String amcId) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(amcDomainHistoryRepository.findByAmcId(amcId));
	}


	public ResponseEntity<AMCDomainHistory> updateAMCDomainHistoy(AMCDomainHistory updateAMCDomainHistory) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(amcDomainHistoryRepository.save(updateAMCDomainHistory));
	}


	public ResponseEntity<String> deleteAMCDomainHistory(String amcDomainHistoryId) {
		amcDomainHistoryRepository.deleteById(amcDomainHistoryId);
		return ResponseEntity.ok("Deleted Successfully");
	}


	public ResponseEntity<AMC> getAmcById(String amcId) {
		return ResponseEntity.ok(amcRepository.findById(amcId)
				.orElseThrow(()->new RuntimeException("Amc not Found with Id : "+amcId)));
	}



	
	
	

}
