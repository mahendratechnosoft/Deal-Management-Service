package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Leads;

@Repository
public interface LeadInfoRepository extends JpaRepository<Leads, String>{

}
