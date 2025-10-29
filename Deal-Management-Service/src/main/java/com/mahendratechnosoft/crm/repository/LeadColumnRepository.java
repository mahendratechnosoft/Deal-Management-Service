package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.LeadColumn;
@Repository
public interface LeadColumnRepository extends JpaRepository<LeadColumn, String> {
 
	LeadColumn findByAdminId(String companyId);
}
