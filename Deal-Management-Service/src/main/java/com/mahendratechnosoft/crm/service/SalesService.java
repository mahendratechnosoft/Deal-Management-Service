package com.mahendratechnosoft.crm.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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

import com.mahendratechnosoft.crm.dto.InvoiceDto;
import com.mahendratechnosoft.crm.dto.ProformaInvoiceDto;
import com.mahendratechnosoft.crm.dto.ProformaInvoiceSummaryDTO;
import com.mahendratechnosoft.crm.dto.ProposalDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.Invoice;
import com.mahendratechnosoft.crm.entity.InvoiceContent;
import com.mahendratechnosoft.crm.entity.ModuleAccess;
import com.mahendratechnosoft.crm.entity.Payments;
import com.mahendratechnosoft.crm.entity.ProformaInvoice;
import com.mahendratechnosoft.crm.entity.ProformaInvoiceContent;
import com.mahendratechnosoft.crm.entity.Proposal;
import com.mahendratechnosoft.crm.entity.ProposalContent;
import com.mahendratechnosoft.crm.repository.*;
import jakarta.transaction.Transactional;

@Service
public class SalesService {

    private final AttendanceRepository attendanceRepository;

	@Autowired
	private ProposalRepository proposalRepository;

	@Autowired
	private ProposalContentRepository proposalContentRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private InvoiceContentRepository invoiceContentRepository;
	
	@Autowired
	private ProformaInvoiceRepository proformaInvoiceRepository;
	
	@Autowired
	private ProformaInvoiceContentRepository proformaInvoiceContentRepository;
	
    @Autowired
	private PaymentsRepository paymentsRepository;


    SalesService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
	

	public ResponseEntity<?> createProposal(ProposalDto proposalDto, Object loginUser) {

		try {

			String adminId = null;
			String employeeId = null;

			if (loginUser instanceof Admin admin) {

				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
			}

			Proposal proposal = proposalDto.getProposalInfo();
			proposal.setAdminId(adminId);
			if(proposal.getEmployeeId()==null || proposal.getEmployeeId().isEmpty()) {
			proposal.setEmployeeId(employeeId);
			}
			proposalRepository.save(proposal);

			List<ProposalContent> proposalContentAll = new ArrayList<>();

			for (ProposalContent content : proposalDto.getProposalContent()) {

				content.setProposalId(proposal.getProposalId());

				proposalContentAll.add(content);
			}

			proposalContentRepository.saveAll(proposalContentAll);

			return ResponseEntity.ok(proposalDto);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}

	public ResponseEntity<?> updateProposal(ProposalDto proposalDto, Object loginUser) {

		try {

			String adminId = null;

			if (loginUser instanceof Admin admin) {
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
			}

			Proposal proposal = proposalDto.getProposalInfo();
			proposal.setAdminId(adminId);
			proposalRepository.save(proposal);

			List<ProposalContent> proposalContentAll = new ArrayList<>();

			for (ProposalContent content : proposalDto.getProposalContent()) {

				content.setProposalId(proposal.getProposalId());

				proposalContentAll.add(content);
			}

			proposalContentRepository.saveAll(proposalContentAll);

			return ResponseEntity.ok(proposalDto);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	public ResponseEntity<?> getProposalById(String proposalId) {

		try {

			ProposalDto response = new ProposalDto();
			response.setProposalInfo(proposalRepository.findByProposalId(proposalId));
			response.setProposalContent(proposalContentRepository.findByProposalIdOrderByCreatedAt(proposalId));

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	

	public ResponseEntity<?> getProposalByModuleType(String moduleTypeId,String moduleType) {

		try {
            if("lead".equalsIgnoreCase(moduleType)) {
			return ResponseEntity.ok(proposalRepository.findByRelatedToAndRelatedId("lead",moduleTypeId));
            }else {
            	return ResponseEntity.ok(proposalRepository.findByRelatedToAndRelatedId("customer",moduleTypeId));
            }

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	

	public ResponseEntity<?> getAllProposal(int page, int size, Object loginUser, String search) {

		try {
            ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<Proposal> proposalPage = null;
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
				proposalPage = proposalRepository.findByAdminId(adminId, search, pageable);

			}else if(moduleAccess.isProposalViewAll()) {
                
				proposalPage = proposalRepository.findByAdminId(adminId, search, pageable);
				
			} else {

				proposalPage = proposalRepository.findByEmployeeId(employeeId, search, pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new HashMap<>();

			response.put("proposalList", proposalPage.getContent());
			response.put("currentPage", proposalPage.getNumber());

			response.put("totalPages", proposalPage.getTotalPages());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	
	public ResponseEntity<?> createInvoice(InvoiceDto invoiceDto, Object loginUser) {

		try {

			String adminId = null;
			String employeeId = null;

			if (loginUser instanceof Admin admin) {

				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
			}

			Invoice invoice = invoiceDto.getInvoiceInfo();
			invoice.setAdminId(adminId);
			if(invoice.getEmployeeId()==null) {
				invoice.setEmployeeId(employeeId);
			}
			invoiceRepository.save(invoice);

			List<InvoiceContent> invoiceContentAll = new ArrayList<>();

			for (InvoiceContent content : invoiceDto.getInvoiceContent()) {

				content.setInvoiceId(invoice.getInvoiceId());

				invoiceContentAll.add(content);
			}

			invoiceContentRepository.saveAll(invoiceContentAll);

			return ResponseEntity.ok(invoiceDto);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}

	public ResponseEntity<?> updateInvoice(InvoiceDto  invoiceDto, Object loginUser) {

		try {

			String adminId = null;

			if (loginUser instanceof Admin admin) {
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
			}

			Invoice invoice = invoiceDto.getInvoiceInfo();
			invoice.setAdminId(adminId);
			invoiceRepository.save(invoice);

			List<InvoiceContent> invoiceContentAll = new ArrayList<>();

			for (InvoiceContent content : invoiceDto.getInvoiceContent()) {

				content.setInvoiceId(invoice.getInvoiceId());

				invoiceContentAll.add(content);
			}

			invoiceContentRepository.saveAll(invoiceContentAll);

			return ResponseEntity.ok(invoiceDto);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	public ResponseEntity<?> getInvoiceById(String  invoiceId) {

		try {
			
			InvoiceDto response=new InvoiceDto();
			response.setInvoiceInfo(invoiceRepository.findByInvoiceId(invoiceId));
            response.setInvoiceContent(invoiceContentRepository.findByInvoiceId(invoiceId));
			
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getAllInvoice(int page, int size, Object loginUser, String search) {

		try {
            ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<Invoice> invoicePage = null;
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
				invoicePage = invoiceRepository.findByAdminId(adminId, search, pageable);

			}else if(moduleAccess.isInvoiceViewAll()) {
				invoicePage = invoiceRepository.findByAdminId(adminId, search, pageable);
				
			} else {

				invoicePage = invoiceRepository.findByEmployeeId(employeeId, search, pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new HashMap<>();

			response.put("invoiceList", invoicePage.getContent());
			response.put("currentPage", invoicePage.getNumber());

			response.put("totalPages", invoicePage.getTotalPages());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getInvoiceByCustomerId(String customerId) {

		try {
			
			return ResponseEntity.ok(invoiceRepository.findByRelatedToAndRelatedId("customer",customerId));

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	@Transactional
	public void deleteProposalContent(List<String> proposalContentIds) {
        proposalContentRepository.deleteAllById(proposalContentIds);
    }
	

	public ResponseEntity<?> createProformaInvoice(ProformaInvoiceDto invoiceDto, Object loginUser) {

		try {

			String adminId = null;
			String employeeId = null;

			if (loginUser instanceof Admin admin) {

				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
			}

			ProformaInvoice invoice = invoiceDto.getProformaInvoiceInfo();
			invoice.setAdminId(adminId);
			if(invoice.getEmployeeId()==null || invoice.getEmployeeId().isEmpty()) {
				invoice.setEmployeeId(employeeId);
			}
			proformaInvoiceRepository.save(invoice);

			List<ProformaInvoiceContent> invoiceContentAll = new ArrayList<>();

			for (ProformaInvoiceContent content : invoiceDto.getProformaInvoiceContents()) {

				content.setProformaInvoiceId(invoice.getProformaInvoiceId());

				invoiceContentAll.add(content);
			}

			proformaInvoiceContentRepository.saveAll(invoiceContentAll);

			return ResponseEntity.ok(invoiceDto);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> updateProformaInvoice(ProformaInvoiceDto invoiceDto, Object loginUser) {

		try {

			String adminId = null;

			if (loginUser instanceof Admin admin) {
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
			}

			ProformaInvoice invoice = invoiceDto.getProformaInvoiceInfo();
			invoice.setAdminId(adminId);
			proformaInvoiceRepository.save(invoice);

			List<ProformaInvoiceContent> invoiceContentAll = new ArrayList<>();

			for (ProformaInvoiceContent content : invoiceDto.getProformaInvoiceContents()) {

				content.setProformaInvoiceId(invoice.getProformaInvoiceId());

				invoiceContentAll.add(content);
			}

			proformaInvoiceContentRepository.saveAll(invoiceContentAll);

			return ResponseEntity.ok(invoiceDto);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getProformaInvoiceById(String  proformaInvoiceId) {

		try {
			
			ProformaInvoiceDto response=new ProformaInvoiceDto();
			response.setProformaInvoiceInfo(proformaInvoiceRepository.findByProformaInvoiceId(proformaInvoiceId));
            response.setProformaInvoiceContents(proformaInvoiceContentRepository.findByProformaInvoiceIdOrderByCreatedAt(proformaInvoiceId));
			
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getAllProformaInvoice(int page, int size, Object loginUser, String search,Date startDate,Date endDate) {

		try {
            ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<ProformaInvoice> invoicePage = null;
			if (loginUser instanceof Admin admin) {
				role = admin.getUser().getRole();
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {

				employeeId = employee.getEmployeeId();
				moduleAccess=employee.getModuleAccess();
			}
			
			startDate = (startDate == null) ? java.sql.Date.valueOf(LocalDate.of(1990, 1, 1)): startDate;
			endDate = (endDate == null) ? java.sql.Date.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDate()) : endDate;


			// 2. Fetch paginated leads for company
			Pageable pageable = PageRequest.of(page, size);
			if (role.equals("ROLE_ADMIN")) {
				invoicePage = proformaInvoiceRepository.findByAdminId(adminId, search,startDate,endDate, pageable);

			}else if(moduleAccess.isProformaInvoiceEdit()) {
				
				invoicePage = proformaInvoiceRepository.findByAdminId(adminId, search,startDate,endDate, pageable);
				
			} else {

				invoicePage = proformaInvoiceRepository.findByEmployeeId(employeeId, search,startDate,endDate, pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new HashMap<>();

			response.put("ProformaInvoiceList", invoicePage.getContent());
			response.put("currentPage", invoicePage.getNumber());

			response.put("totalPages", invoicePage.getTotalPages());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	@Transactional
	public void deleteProformaInvoiceContent(List<String> proformaInvoiceContentIds) {
		proformaInvoiceContentRepository.deleteAllById(proformaInvoiceContentIds);
    }
	
	
	public ResponseEntity<?> createPayment(Payments payment, Object loginUser) {

		try {

			String adminId = null;
			String employeeId = null;
			String createdBy = null;
			if (loginUser instanceof Admin admin) {
				adminId = admin.getAdminId();
				createdBy = admin.getCompanyName();
			} else if (loginUser instanceof Employee employee) {
				adminId = employee.getAdmin().getAdminId();
				employeeId = employee.getEmployeeId();
				createdBy = employee.getName();
			}
  
			payment.setAdminId(adminId);
			payment.setEmployeeId(employeeId);
			payment.setCreatedBy(createdBy);
            payment.setCreatedDateTime(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime());
			paymentsRepository.save(payment);
			
			
			 ProformaInvoice invoice= proformaInvoiceRepository.findByProformaInvoiceId(payment.getProformaInvoiceId());
				
			 if (invoice.getTotalAmount() == (invoice.getPaidAmount() + payment.getAmount())) {
					invoice.setStatus("Paid");
					if(invoice.getInvoiceNumber() == 0) {
						invoice.setInvoiceNumber(generateInvoiceNumber(adminId));
						invoice.setInvoiceDate(payment.getPaymentDate());
					}
				} else if (invoice.getTotalAmount() > (invoice.getPaidAmount() + payment.getAmount())) {
					invoice.setStatus("Partially Paid");
				}
			 invoice.setPaidAmount(invoice.getPaidAmount()+payment.getAmount());
			
			 proformaInvoiceRepository.save(invoice);
			 
			 payment.setTotalProformaInvoicePaidAmount(invoice.getPaidAmount());

			return ResponseEntity.ok(payment);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> updatePayment(Payments payment) {

		try {

			paymentsRepository.save(payment);
			
		    ProformaInvoice invoice= proformaInvoiceRepository.findByProformaInvoiceId(payment.getProformaInvoiceId());
			
	        double totalAmount = invoice.getTotalAmount();
	        double paidAmount = payment.getTotalProformaInvoicePaidAmount();

	        if ( totalAmount == paidAmount ) {
	            invoice.setStatus("Paid");
	            if(invoice.getInvoiceNumber() == 0) {
					invoice.setInvoiceNumber(generateInvoiceNumber(invoice.getAdminId()));
				}
	            invoice.setInvoiceDate(payment.getPaymentDate());
	        } else if (paidAmount > 0 && totalAmount > paidAmount) {
	            invoice.setStatus("Partially Paid");
	        } else {
	            invoice.setStatus("Unpaid");
	        }
			 
			 invoice.setPaidAmount(payment.getTotalProformaInvoicePaidAmount());
			 proformaInvoiceRepository.save(invoice);

			return ResponseEntity.ok(payment);

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getPaymentById(String paymentId) {

		try {

			Optional<Payments> payment = paymentsRepository.findById(paymentId);

			ProformaInvoice invoice = proformaInvoiceRepository.findByProformaInvoiceId(payment.get().getProformaInvoiceId());

			payment.get().setTotalProformaInvoicePaidAmount(invoice.getPaidAmount());
			return ResponseEntity.ok(payment);

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getAllPayments(int page, int size, Object loginUser, String search) {

		try {
            ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<Payments> paymentPage = null;
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
				paymentPage  = paymentsRepository.findByAdminId(adminId, search, pageable);

			}else if(moduleAccess.isPaymentViewAll()) {
				
				paymentPage = paymentsRepository.findByAdminId(adminId, search, pageable);
				
			} else {

				paymentPage = paymentsRepository.findByEmployeeId(employeeId, search, pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new LinkedHashMap<>();

			response.put("paymentList", paymentPage.getContent());
			response.put("currentPage", paymentPage.getNumber());

			response.put("totalPages", paymentPage.getTotalPages());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	
	public ResponseEntity<?> getPaymentByProformaInvoice(String  proformaInvoiceId) {

		try {
			
			return ResponseEntity.ok(paymentsRepository.findByProformaInvoiceId(proformaInvoiceId));

		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
	
	public int getNextProposalNumberForAdmin(String adminId) {
	    int maxNumber = proposalRepository.findMaxProposalNumberByAdminId(adminId);
	    return maxNumber + 1; // Generate next proposal number
	}
	
	public boolean isProposalNumberUnique(String adminId, int proposalNumber) {
        return !proposalRepository.existsByAdminIdAndProposalNumber(adminId, proposalNumber);
    }
	
	public ResponseEntity<?> getAllPerforma(String adminId) {
		try {
		List<ProformaInvoiceSummaryDTO> data=proformaInvoiceRepository.getAllPerfromaByAdmin(adminId);
		
	    return ResponseEntity.ok(data);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}
	}
	
	public int getNextProformaNumberForAdmin(String adminId) {
	    int maxNumber = proformaInvoiceRepository.findMaxProposalNumberByAdminId(adminId);
	    return maxNumber + 1;
	}
	
	public boolean isProformaNumberUnique(String adminId, int proformaNumber) {
        return !proformaInvoiceRepository.existsByAdminIdAndProformaNumber(adminId, proformaNumber);
    }
	
	public int generateInvoiceNumber(String adminId) {
		int invoiceNumber = proformaInvoiceRepository.findMaxInvoiceNumberByAdminId(adminId);
		return invoiceNumber + 1;
	}
	
	public ResponseEntity<?> getAllProformaAsInvoice(int page, int size, Object loginUser, String search) {

		try {
            ModuleAccess moduleAccess=null;
			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<ProformaInvoice> invoicePage = null;
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
				invoicePage = proformaInvoiceRepository.findPaidInvoicesByAdminId(adminId, search, pageable);

			}else if(moduleAccess.isInvoiceViewAll()) {
				invoicePage = proformaInvoiceRepository.findPaidInvoicesByAdminId(adminId, search, pageable);
				
			} else {

				invoicePage = proformaInvoiceRepository.findPaidInvoicesByEmployee(employeeId, search, pageable);
			}
			// 3. Prepare response
			Map<String, Object> response = new HashMap<>();

			response.put("invoiceList", invoicePage.getContent());
			response.put("currentPage", invoicePage.getNumber());

			response.put("totalPages", invoicePage.getTotalPages());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error " + e.getMessage());
		}

	}
}
