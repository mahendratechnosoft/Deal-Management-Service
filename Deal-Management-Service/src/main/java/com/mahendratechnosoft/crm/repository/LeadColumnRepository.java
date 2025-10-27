package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.LeadColumn;

public interface LeadColumnRepository extends JpaRepository<LeadColumn, String> {
 
	LeadColumn findByCompanyId(int companyId);
}
