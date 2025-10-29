package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.LeadStatus;

@Repository
public interface LeadStatusRepository extends JpaRepository<LeadStatus, String>{
	
	List<LeadStatus> findByAdminId(String companyId);

}
