package com.mahendratechnosoft.crm.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
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
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
import com.mahendratechnosoft.crm.entity.Hospital.DonorBloodReport;
import com.mahendratechnosoft.crm.entity.Hospital.DonorFamilyInfo;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;
import com.mahendratechnosoft.crm.entity.Hospital.FamilyInfo;
import com.mahendratechnosoft.crm.entity.Hospital.SampleReport;
import com.mahendratechnosoft.crm.entity.Hospital.SemenReport;
import com.mahendratechnosoft.crm.repository.Hospital.DonorBloodReportRepositroy;
import com.mahendratechnosoft.crm.repository.Hospital.DonorFamilyInfoRepository;
import com.mahendratechnosoft.crm.repository.Hospital.DonorsRepository;
import com.mahendratechnosoft.crm.repository.Hospital.FamilyInfoRepository;
import com.mahendratechnosoft.crm.repository.Hospital.SampleReportRepository;
import com.mahendratechnosoft.crm.repository.Hospital.SemenReportRepository;

@Service
public class DonorService {
	
	@Autowired
	private DonorsRepository donorsRepository;
	
	@Autowired
	private SampleReportRepository donorSampleRepository;
	
	@Autowired
	private SemenReportRepository semenReportRepository;
	
	@Autowired
	private DonorFamilyInfoRepository donorFamilyInfoRepository;
	
	@Autowired
	private DonorBloodReportRepositroy donoBloodReportRepositroy;
	
	@Autowired
	private SampleReportRepository sampleReportRepository;
	
	@Autowired
	private FamilyInfoRepository familyInfoRepository;
	
	
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
	
	
	public ResponseEntity<?> getAllDonorList(int page, int size, Object loginUser, String search,String status) {

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
				donorPage = donorsRepository.findByAdminId(adminId, search,status, pageable);

			}
//			else if(moduleAccess.isProposalViewAll()) {
//                
//				proposalPage = proposalRepository.findByAdminId(adminId, search, pageable);
//				
//			} 
			else {

				donorPage = donorsRepository.findByEmployeeId(employeeId, search,status, pageable);
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
			donorsRepository.save(request);
			
			return ResponseEntity.ok(request);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	
	public ResponseEntity<?> getDonorFamilyInfo( String  donarId) {

		try {
			
			return ResponseEntity.ok(donorFamilyInfoRepository.findByDonorId(donarId));

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	
	
	public ResponseEntity<?> deleteDonorFamilyInfo( String  donarFamailyId) {

		try {
			donorFamilyInfoRepository.deleteById(donarFamailyId);
			return ResponseEntity.ok("Deleted Successfully");

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> deleteDonorBloodReport( String  bloodReportId) {

		try {
			donoBloodReportRepositroy.deleteById(bloodReportId);
			return ResponseEntity.ok("Deleted Successfully");

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	
	
	public ResponseEntity<?> updateDonorFamilyInfo(List<DonorFamilyInfo> request) {

		try {

			donorFamilyInfoRepository.saveAll(request);

			return ResponseEntity.ok(request);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> updateDonorBloodReport(List<DonorBloodReport> request) {

		try {

			donoBloodReportRepositroy.saveAll(request);

			return ResponseEntity.ok(request);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	
	public ResponseEntity<?> getDonorBloodReport( String  donarId) {

		try {
			
			return ResponseEntity.ok(donoBloodReportRepositroy.findByDonorId(donarId));

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> updateDonorStatus( Map<String,String> request) {

		try {
	
			String donorId=request.get("donorId");
	        String donorStatus=request.get("status");
	        
	        Optional<Donors> donorOpt=donorsRepository.findById(donorId);
			
	        Donors donor = donorOpt.get();
	        donor.setStatus(donorStatus);
	        
	        if (donor.getUin() == null || donor.getUin().isEmpty()) {

	            // Current date
	        	LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

	            String day = String.format("%02d", today.getDayOfMonth());
	            String month = String.format("%02d", today.getMonthValue());
	            String year = String.valueOf(today.getYear()).substring(2); // last 2 digits

	            // Prefix for counting: DN DD MM YY
	            String prefix = "DN " + day + " " + month + " " + year;

	            // Count already existing UINs for today
	            int count = donorsRepository.countUinByPrefix(prefix);

	            // Serial number (001, 002, 003...)
	            String serial = String.format("%03d", count + 1);

	            // Final UIN
	            String uin = prefix + " " + serial;

	            donor.setUin(uin);
	        }
	        
	        
	        donorsRepository.save(donor);
			
			return ResponseEntity.ok(donor);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	

	
	
	public ResponseEntity<?> updateDonorSample( SampleReport request) {

		try {
			
			donorSampleRepository.save(request);
			
			return ResponseEntity.ok(request);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> updateDonorSemenReport( SemenReport request) {

		try {
			
			semenReportRepository.save(request);
			
			return ResponseEntity.ok(request);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	public ResponseEntity<?> updateSampleReport( SampleReport request) {

		try {
			
			sampleReportRepository.save(request);
			
			return ResponseEntity.ok(request);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	
	public ResponseEntity<?> getSemenReportByDonorId( String  donarId) {

		try {
			
			return ResponseEntity.ok(semenReportRepository.findByDonorId(donarId));

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getSampleReportByDonorId( String  donarId) {

		try {
			
			return ResponseEntity.ok(donorSampleRepository.findByDonorId(donarId));

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> createFamilyInfo( FamilyInfo  request) {

		try {
			   // Current date
        	LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

            String day = String.format("%02d", today.getDayOfMonth());
            String month = String.format("%02d", today.getMonthValue());
            String year = String.valueOf(today.getYear()).substring(2); // last 2 digits

            // Prefix for counting: DN DD MM YY
            String prefix = "RC " + day + " " + month + " " + year;

            // Count already existing UINs for today
            int count = familyInfoRepository.countUinByPrefix(prefix);

            // Serial number (001, 002, 003...)
            String serial = String.format("%03d", count + 1);

            // Final UIN
            String uin = prefix + " " + serial;

            request.setUin(uin);
			
			
			return ResponseEntity.ok(familyInfoRepository.save(request));

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getAllFamilyList(int page, int size, Object loginUser, String search) {

		try {
            ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<FamilyInfo> familyInfoPage = null;
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
				familyInfoPage = familyInfoRepository.findByAdminId(adminId, search, pageable);

			}
//			else if(moduleAccess.isProposalViewAll()) {
//                
//				proposalPage = proposalRepository.findByAdminId(adminId, search, pageable);
//				
//			} 
			else {

				familyInfoPage = familyInfoRepository.findByEmployeeId(employeeId, search, pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new HashMap<>();
			response.put("familyList", familyInfoPage.getContent());
			response.put("currentPage", familyInfoPage.getNumber());
			response.put("totalPages", familyInfoPage.getTotalPages());
			response.put("totalElements", familyInfoPage.getTotalElements());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getFamilyById( String  familyInfoId) {

		try {
			
			return ResponseEntity.ok(familyInfoRepository.findById(familyInfoId));

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	public ResponseEntity<?> getFamilyInfoById( String  familyInfoId) {

		try {
			
			return ResponseEntity.ok(familyInfoRepository.findById(familyInfoId));

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	

	public ResponseEntity<?> updateFamilyInfo( FamilyInfo  request) {

		try {
			
			return ResponseEntity.ok(familyInfoRepository.save(request));

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getAllMatchingDonorList(int page, int size, Object loginUser, String search,String bloodGroup,String city,
			String  height,String weight,String skinColor,String eyeColor,String religion,String education,String profession) {

		try {
            ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			double heightDigital=0.0;
			double weightDigital=0.0;
			if(height!=null) {
				heightDigital = Double.parseDouble(height);
			}
			if(weight!=null) {
				  weightDigital = Double.parseDouble(weight);
			}
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
				donorPage = donorsRepository.findByMatchingDonorAdminId(adminId, search,bloodGroup,city,heightDigital,weightDigital,skinColor,
						                                               eyeColor,religion,profession, pageable);

			}
//			else if(moduleAccess.isProposalViewAll()) {
//                
//				proposalPage = proposalRepository.findByAdminId(adminId, search, pageable);
//				
//			} 
			else {

			//	donorPage = donorsRepository.findByEmployeeId(employeeId, search,status, pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new LinkedHashMap<>();

			response.put("donarList", donorPage.getContent());
			response.put("currentPage", donorPage.getNumber());
			response.put("totalPages", donorPage.getTotalPages());
			response.put("totalElements", donorPage.getTotalElements());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	public ResponseEntity<?> getFamilyList( String  adminId) {

		try {
			
			return ResponseEntity.ok(familyInfoRepository.findByAdminId(adminId));

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getDonorStatusCount() {

		try {

			List<Object[]> results = donorsRepository.getDonorCountByStatus();
	        
	        Map<String, Long> response = new HashMap<>();
	        
	        for (Object[] row : results) {
	            String status = (String) row[0];
	            Long count = (Long) row[1];
	            response.put(status, count);
	        }

	        return ResponseEntity.ok(response);


		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
}
