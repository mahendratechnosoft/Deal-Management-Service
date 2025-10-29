package com.mahendratechnosoft.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Deals;
@Repository
public interface DealsRepository extends JpaRepository<Deals, String> {

	
	Page<Deals> findByAdminIdOrderByDealIdDesc(String adminId,Pageable pageable);
	
	Page<Deals> findByEmployeeIdOrderByDealIdDesc(String adminId,Pageable pageable);
}
