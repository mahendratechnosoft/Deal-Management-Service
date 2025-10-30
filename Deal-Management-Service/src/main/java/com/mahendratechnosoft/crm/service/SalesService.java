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

import com.mahendratechnosoft.crm.dto.ProposalDto;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.Proposal;
import com.mahendratechnosoft.crm.entity.ProposalContent;
import com.mahendratechnosoft.crm.repository.ProposalContentRepository;
import com.mahendratechnosoft.crm.repository.ProposalRepository;

@Service
public class SalesService {

	@Autowired
	private ProposalRepository proposalRepository;

	@Autowired
	private ProposalContentRepository proposalContentRepository;

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

}
