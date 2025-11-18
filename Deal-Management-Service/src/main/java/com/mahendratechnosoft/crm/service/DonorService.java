package com.mahendratechnosoft.crm.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
import com.mahendratechnosoft.crm.entity.Hospital.DonorSample;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;
import com.mahendratechnosoft.crm.repository.DonorSampleRepository;
import com.mahendratechnosoft.crm.repository.DonorsRepository;

@Service
public class DonorService {
	
	@Autowired
	private DonorsRepository donorsRepository;
	
	@Autowired
	private DonorSampleRepository donorSampleRepository;
	
	public ResponseEntity<?> createDonor( Donors request) {

		try {
			request.setStatus("New Donor");
			
			String base64SelfeImage = request.getSelfeImageData();
            String base64fullLengthImage=request.getFullLengthImageData();
			
			if (base64SelfeImage != null && !base64SelfeImage.isEmpty()) {
			    try {
			        byte[] imageBytes = Base64.getDecoder().decode(base64SelfeImage);
			        request.setSelfeImage(imageBytes);   // ✅ Correct setter
			    } catch (IllegalArgumentException e) {
			        throw new RuntimeException("Invalid Base64 image format", e);
			    }
			}
			
			if (base64fullLengthImage != null && !base64fullLengthImage.isEmpty()) {
			    try {
			        byte[] imageBytes = Base64.getDecoder().decode(base64SelfeImage);
			        request.setFullLengthImage(imageBytes);   // ✅ Correct setter
			    } catch (IllegalArgumentException e) {
			        throw new RuntimeException("Invalid Base64 image format", e);
			    }
			}

			
			donorsRepository.save(request);
			
			return ResponseEntity.ok(request);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getAllDonarList(int page, int size, Object loginUser, String search) {

		try {
            ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<Donors> donorPage = null;
			if (loginUser instanceof Admin admin) {
				role = admin.getUser().getRole();
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {

				employeeId = employee.getEmployeeId();
				moduleAccess=employee.getModuleAccess();
			}

			// 2. Fetch paginated leads for company
			Pageable pageable = PageRequest.of(page, size);
			if (role.equals("ROLE_ADMIN")) {
				donorPage = donorsRepository.findByAdminId(adminId, search, pageable);

			}
//			else if(moduleAccess.isProposalViewAll()) {
//                
//				proposalPage = proposalRepository.findByAdminId(adminId, search, pageable);
//				
//			} 
			else {

				donorPage = donorsRepository.findByEmployeeId(employeeId, search, pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new HashMap<>();

			response.put("donarList", donorPage.getContent());
			response.put("currentPage", donorPage.getNumber());

			response.put("totalPages", donorPage.getTotalPages());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getAllSelectedDonarList(int page, int size, Object loginUser, String search) {

		try {
            ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<Donors> donorPage = null;
			if (loginUser instanceof Admin admin) {
				role = admin.getUser().getRole();
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {

				employeeId = employee.getEmployeeId();
				moduleAccess=employee.getModuleAccess();
			}

			// 2. Fetch paginated leads for company
			Pageable pageable = PageRequest.of(page, size);
			if (role.equals("ROLE_ADMIN")) {
				donorPage = donorsRepository.findByAdminIdSelected(adminId, search, pageable);

			}
//			else if(moduleAccess.isProposalViewAll()) {
//                
//				proposalPage = proposalRepository.findByAdminId(adminId, search, pageable);
//				
//			} 
			else {

				donorPage = donorsRepository.findByEmployeeIdSelected(employeeId, search, pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new HashMap<>();

			response.put("donarList", donorPage.getContent());
			response.put("currentPage", donorPage.getNumber());

			response.put("totalPages", donorPage.getTotalPages());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getDonarById( String  donarId) {

		try {
			
			return ResponseEntity.ok(donorsRepository.findById(donarId));

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> updateDonor( Donors request) {

		try {
			request.setStatus("New Donor");
			donorsRepository.save(request);
			
			return ResponseEntity.ok(request);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> updateDonorStatus( Map<String,String> request) {

		try {
	
			String donorId=request.get("donorId");
	        String donarStatus=request.get("status");
	        
	        Optional<Donors> donar=donorsRepository.findById(donorId);
			
	        donar.get().setStatus(donarStatus);
	        
	        donorsRepository.save(donar.get());
			
			return ResponseEntity.ok(donar);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	
	public ResponseEntity<?> createDonorSample( DonorSample request) {

		try {
			
			donorSampleRepository.save(request);
			
			return ResponseEntity.ok(request);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	public ResponseEntity<?> getAllDonarSampleList(int page, int size, String search, String donorId) {

		try {
			Pageable pageable = PageRequest.of(page, size);
			Page<DonorSample> donorPage = null;

			donorPage = donorsRepository.findByDonorId(donorId, search, pageable);

		
			Map<String, Object> response = new HashMap<>();

			response.put("donarSampleList", donorPage.getContent());
			response.put("currentPage", donorPage.getNumber());

			response.put("totalPages", donorPage.getTotalPages());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}

}
