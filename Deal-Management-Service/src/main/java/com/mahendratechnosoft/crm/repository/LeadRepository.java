package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Leads;
@Repository
public interface LeadRepository extends JpaRepository<Leads, String> {

	Page<Leads> findByCompanyIdOrderByIdDesc(String companyId,Pageable pageable);
	
	List<Leads> findByCompanyId(String companyId);
}
