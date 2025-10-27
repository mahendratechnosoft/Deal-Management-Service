package com.mahendratechnosoft.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Lead;
@Repository
public interface LeadRepository extends JpaRepository<Lead, String> {

	Page<Lead> findByCompanyIdOrderByIdDesc(long companyId,Pageable pageable);
}
