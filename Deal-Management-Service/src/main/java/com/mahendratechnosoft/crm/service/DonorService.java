package com.mahendratechnosoft.crm.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
import org.springframework.transaction.annotation.Transactional;

import com.mahendratechnosoft.crm.dto.Hospital.AllocationDetailsDTO;
import com.mahendratechnosoft.crm.dto.Hospital.DonorResponseDto;
import com.mahendratechnosoft.crm.dto.Hospital.FamilyInfoDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
import com.mahendratechnosoft.crm.entity.Hospital.DonorBloodReport;
import com.mahendratechnosoft.crm.entity.Hospital.DonorFamilyInfo;
import com.mahendratechnosoft.crm.entity.Hospital.Donors;
import com.mahendratechnosoft.crm.entity.Hospital.FamilyInfo;
import com.mahendratechnosoft.crm.entity.Hospital.FamilyVialAllocation;
import com.mahendratechnosoft.crm.entity.Hospital.SampleReport;
import com.mahendratechnosoft.crm.entity.Hospital.SemenReport;
import com.mahendratechnosoft.crm.repository.Hospital.DonorBloodReportRepositroy;
import com.mahendratechnosoft.crm.repository.Hospital.DonorFamilyInfoRepository;
import com.mahendratechnosoft.crm.repository.Hospital.DonorsRepository;
import com.mahendratechnosoft.crm.repository.Hospital.FamilyInfoRepository;
import com.mahendratechnosoft.crm.repository.Hospital.FamilyVialAllocationRepository;
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
	
	@Autowired
	private FamilyVialAllocationRepository familyVialAllocationRepository;
	
	
	public ResponseEntity<?> createDonor( Donors request) {

		try {
			request.setStatus("New Donor");
			
//			String base64SelfeImage = request.getSelfeImageData();
//            String base64fullLengthImage=request.getFullLengthImageData();
//			
//			if (base64SelfeImage != null && !base64SelfeImage.isEmpty()) {
//			    try {
//			        byte[] imageBytes = Base64.getDecoder().decode(base64SelfeImage);
//			        request.setSelfeImage(imageBytes);   // ✅ Correct setter
//			    } catch (IllegalArgumentException e) {
//			        throw new RuntimeException("Invalid Base64 image format", e);
//			    }
//			}
//			
//			if (base64fullLengthImage != null && !base64fullLengthImage.isEmpty()) {
//			    try {
//			        byte[] imageBytes = Base64.getDecoder().decode(base64SelfeImage);
//			        request.setFullLengthImage(imageBytes);   // ✅ Correct setter
//			    } catch (IllegalArgumentException e) {
//			        throw new RuntimeException("Invalid Base64 image format", e);
//			    }
//			}

			
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
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
				moduleAccess=employee.getModuleAccess();
			}

			// 2. Fetch paginated leads for company
			Pageable pageable = PageRequest.of(page, size);
			if (role.equals("ROLE_ADMIN")) {
				donorPage = donorsRepository.findByAdminId(adminId, search,status, pageable);

			}
			else if(moduleAccess.isDonorViewAll()) {
				donorPage = donorsRepository.findByAdminId(adminId, search, status,pageable);
				
			} 
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
	
	
	public ResponseEntity<?> updateDonor( Object loginUser,Donors request) {

		try {
			String adminId = null;
			String employeeId=request.getEmployeeId();
		    
			if (loginUser instanceof Admin admin) {
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId= employee.getAdmin().getAdminId();
				
			}
			request.setAdminId(adminId);
			request.setEmployeeId(employeeId);
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
	        
	        if ("Not Shortlisted".equalsIgnoreCase(donorStatus)) {
                donorsRepository.delete(donor);
                return ResponseEntity.ok("Donor removed from database successfully");
            }
	        
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
	
	
	public ResponseEntity<?> updateDonorSemenReport( List<SemenReport> request) {

		try {
			
			semenReportRepository.saveAll(request);
			
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
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
				moduleAccess=employee.getModuleAccess();
			}

			// 2. Fetch paginated leads for company
			Pageable pageable = PageRequest.of(page, size);
			if (role.equals("ROLE_ADMIN")) {
				familyInfoPage = familyInfoRepository.findByAdminId(adminId, search, pageable);
			} else if(moduleAccess.isDonorViewAll()){
				familyInfoPage = familyInfoRepository.findByAdminId(adminId, search, pageable);
			}
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
	

	public ResponseEntity<?> updateFamilyInfo(Object loginUser, FamilyInfo  request) {

		try {
			String adminId = null;
			String employeeId=request.getEmployeeId();
		    
			if (loginUser instanceof Admin admin) {
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId= employee.getAdmin().getAdminId();
				
			}
			request.setAdminId(adminId);
			request.setEmployeeId(employeeId);
			return ResponseEntity.ok(familyInfoRepository.save(request));

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getAllMatchingDonorList(int page, int size, Object loginUser, String search,String bloodGroup,String city,
			String minHeight, String maxHeight, String minWeight, String maxWeight,
			String skinColor,String eyeColor,String religion,String education,String profession) {

		try {
            ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			
			Double minHeightVal = (minHeight != null && !minHeight.isEmpty()) ? Double.parseDouble(minHeight) : null;
	        Double maxHeightVal = (maxHeight != null && !maxHeight.isEmpty()) ? Double.parseDouble(maxHeight) : null;
	        Double minWeightVal = (minWeight != null && !minWeight.isEmpty()) ? Double.parseDouble(minWeight) : null;
	        Double maxWeightVal = (maxWeight != null && !maxWeight.isEmpty()) ? Double.parseDouble(maxWeight) : null;
			
			Page<DonorResponseDto> donorPage = null;
			if (loginUser instanceof Admin admin) {
				role = admin.getUser().getRole();
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
				moduleAccess=employee.getModuleAccess();
			}

			// 2. Fetch paginated leads for company
			Pageable pageable = PageRequest.of(page, size);
			if (role.equals("ROLE_ADMIN") || (moduleAccess != null && moduleAccess.isDonorViewAll())) {
	            donorPage = donorsRepository.findByMatchingDonorAdminId(adminId, search, bloodGroup, city, 
	                    minHeightVal, maxHeightVal, minWeightVal, maxWeightVal, 
	                    skinColor, eyeColor, religion, profession, pageable);
	        } else {
	            donorPage = donorsRepository.findByMatchingDonorEmployeeId(employeeId, search, bloodGroup, city, 
	                    minHeightVal, maxHeightVal, minWeightVal, maxWeightVal, 
	                    skinColor, eyeColor, religion, profession, pageable);
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
	
	public ResponseEntity<?> getFamilyList(Object loginUser) {

		try {
			
			ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			
			if (loginUser instanceof Admin admin) {
				role = admin.getUser().getRole();
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
				moduleAccess=employee.getModuleAccess();
			}
			
			List<FamilyInfoDto> result = new LinkedList<>();
			
			if (role.equals("ROLE_ADMIN") || moduleAccess.isDonorViewAll()) {
				result = familyInfoRepository.findByAdminId(adminId);
			}
			else {
				result = familyInfoRepository.findByEmployeeId(employeeId);
			}
			
			return ResponseEntity.ok(result);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getDonorStatusCount(Object loginUser) {

		try {
			
			ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			if (loginUser instanceof Admin admin) {
				role = admin.getUser().getRole();
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
				moduleAccess=employee.getModuleAccess();
			}
			
			List<Object[]> results = new LinkedList<>();
			
			if (role.equals("ROLE_ADMIN")) {
				results = donorsRepository.getDonorCountByStatus(adminId);
			} else if(moduleAccess.isDonorViewAll()) {
				results = donorsRepository.getDonorCountByStatus(adminId);
			} 
			else {
				results = donorsRepository.getDonorCountByStatusWithEmployeeId(employeeId);
			}
			
	        
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
	
	public ResponseEntity<?> deleteSemenReport( String  semenReportId) {

		try {
			semenReportRepository.deleteById(semenReportId);
			return ResponseEntity.ok("Deleted Successfully");

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}

	@Transactional
	public FamilyVialAllocation assignVialsToFamily(FamilyVialAllocation familyVialAllocation) {
		
		Optional<Donors> donarOptional = donorsRepository.findById(familyVialAllocation.getDonorId());
	    if(donarOptional.isEmpty()) {
	    	throw new RuntimeException("Donar is not present for id = " 
                    + familyVialAllocation.getDonorId());
	    }

	    int requestedVials = familyVialAllocation.getVialsAssignedCount();
	    SampleReport sampleReport = sampleReportRepository.findByDonorId(familyVialAllocation.getDonorId());

	    if (sampleReport.getBalancedVials() < requestedVials) {
	        throw new RuntimeException("Not enough vials available. Balanced vials = " 
	                                   + sampleReport.getBalancedVials());
	    }

	    // Update balanced vials
	    sampleReport.setBalancedVials(sampleReport.getBalancedVials() - requestedVials);
	    sampleReportRepository.save(sampleReport);
	    
	    Donors donar = donarOptional.get();
	    donar.setStatus("Donor");
	    donorsRepository.save(donar);

	    // Save allocation
	    return familyVialAllocationRepository.save(familyVialAllocation);
	}

	@Transactional(readOnly = true)
    public AllocationDetailsDTO getAllocationDetails(String allocationId) {
        // 1. Get Allocation
        FamilyVialAllocation allocation = familyVialAllocationRepository.findById(allocationId)
                .orElseThrow(() -> new RuntimeException("Allocation not found with ID: " + allocationId));

        // 2. Initialize DTO
        AllocationDetailsDTO dto = new AllocationDetailsDTO();
        dto.setAllocationId(allocation.getAllocationId());
        dto.setVialsAssignedCount(allocation.getVialsAssignedCount());

        // 3. Fetch and Map Donor Details
        Donors donor = donorsRepository.findById(allocation.getDonorId())
                .orElseThrow(() -> new RuntimeException("Donor not found with ID: " + allocation.getDonorId()));
        
        dto.setDonorId(donor.getDonorId());
        dto.setDonorUin(donor.getUin());
        dto.setSkinColor(donor.getSkinColor());
        dto.setEyeColor(donor.getEyeColor());
        dto.setEducation(donor.getEducation());
        dto.setProfession(donor.getProfession());
        dto.setReligion(donor.getReligion());

        // 4. Fetch and Map Family Details
        FamilyInfo family = familyInfoRepository.findById(allocation.getFamilyInfoId())
                .orElseThrow(() -> new RuntimeException("Family Info not found with ID: " + allocation.getFamilyInfoId()));

        dto.setFamilyInfoId(family.getFamilyInfoId());
        dto.setFamilyUin(family.getUin());
        dto.setReferHospital(family.getReferHospital());
        dto.setReferHospitalAddress(family.getReferHospitalAddress());
        dto.setReferDoctor(family.getReferDoctor());
        dto.setHusbandName(family.getHusbandName());
        dto.setWifeName(family.getWifeName());

        // 5. Fetch Latest Blood Report (Handle null if no report exists)
        donoBloodReportRepositroy.findFirstByDonorIdOrderByReportDateTimeDesc(allocation.getDonorId())
                .ifPresent(report -> {
                    dto.setDonorBloodReportId(report.getDonorBloodReportId());
                    dto.setBloodGroup(report.getBloodGroup());
                    dto.setBsl(report.getBsl());
                    dto.setHIV(report.getHIV());
                    dto.setHBSAG(report.getHBSAG());
                    dto.setVDRL(report.getVDRL());
                    dto.setHCV(report.getHCV());
                    dto.setHBElectrophoresis(report.getHBElectrophoresis());
                    dto.setSRCreatinine(report.getSRCreatinine());
                    dto.setCMV(report.getCMV());
                });

        // 6. Fetch Latest Semen Report (Handle null if no report exists)
        semenReportRepository.findFirstByDonorIdOrderByDateAndTimeDesc(allocation.getDonorId())
                .ifPresent(report -> {
                    dto.setSampleReportId(report.getSampleReportId());
                    dto.setSemenReportDateAndTime(report.getDateAndTime());
                    dto.setMedia(report.getMedia());
                    dto.setVolumne(report.getVolumne());
                    dto.setSpermConcentration(report.getSpermConcentration());
                    dto.setProgressiveMotilityA(report.getProgressiveMotilityA());
                    dto.setProgressiveMotilityB(report.getProgressiveMotilityB());
                    dto.setProgressiveMotilityC(report.getProgressiveMotilityC());
                    dto.setMorphology(report.getMorphology());
                    dto.setAbnormality(report.getAbnormality());
                });

        return dto;
    }


	public ResponseEntity<?> getAllFinalReport(String familyId) {
		try {
			List<FamilyVialAllocation> responce = familyVialAllocationRepository.findByFamilyInfoIdOrderByAllocationDateDesc(familyId);
			return ResponseEntity.ok(responce);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}
	
	public ResponseEntity<?> getAllDonation(String donorId) {
		try {
			List<FamilyVialAllocation> responce = familyVialAllocationRepository.findByDonorIdOrderByAllocationDateDesc(donorId);
			return ResponseEntity.ok(responce);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}
	
	public ResponseEntity<?> createDonorPublic( Donors request) {

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
			        byte[] imageBytes = Base64.getDecoder().decode(base64fullLengthImage);
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
	
}
