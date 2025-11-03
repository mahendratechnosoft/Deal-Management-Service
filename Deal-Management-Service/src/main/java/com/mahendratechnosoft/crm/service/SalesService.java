package com.mahendratechnosoft.crm.service;

import java.util.ArrayList;
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

import com.mahendratechnosoft.crm.dto.InvoiceDto;
import com.mahendratechnosoft.crm.dto.ProposalDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.Invoice;
import com.mahendratechnosoft.crm.entity.InvoiceContent;
import com.mahendratechnosoft.crm.entity.Proposal;
import com.mahendratechnosoft.crm.entity.ProposalContent;
import com.mahendratechnosoft.crm.repository.InvoiceContentRepository;
import com.mahendratechnosoft.crm.repository.InvoiceRepository;
import com.mahendratechnosoft.crm.repository.ProposalContentRepository;
import com.mahendratechnosoft.crm.repository.ProposalRepository;

@Service
public class SalesService {

	@Autowired
	private ProposalRepository proposalRepository;

	@Autowired
	private ProposalContentRepository proposalContentRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private InvoiceContentRepository invoiceContentRepository;
	

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
			if(proposal.getEmployeeId()==null) {
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
			response.setProposalContent(proposalContentRepository.findByProposalId(proposalId));

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

			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<Proposal> proposalPage = null;
			if (loginUser instanceof Admin admin) {
				role = admin.getUser().getRole();
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {

				employeeId = employee.getEmployeeId();
			}

			// 2. Fetch paginated leads for company
			Pageable pageable = PageRequest.of(page, size);
			if (role.equals("ROLE_ADMIN")) {
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

			String role = "ROLE_EMPLOYEE";
			String adminId = null;
			String employeeId = null;
			Page<Invoice> invoicePage = null;
			if (loginUser instanceof Admin admin) {
				role = admin.getUser().getRole();
				adminId = admin.getAdminId();
			} else if (loginUser instanceof Employee employee) {

				employeeId = employee.getEmployeeId();
			}

			// 2. Fetch paginated leads for company
			Pageable pageable = PageRequest.of(page, size);
			if (role.equals("ROLE_ADMIN")) {
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

}
