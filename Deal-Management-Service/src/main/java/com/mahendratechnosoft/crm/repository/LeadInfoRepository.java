package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Lead;

@Repository
public interface LeadInfoRepository extends JpaRepository<Lead, String>{

}
