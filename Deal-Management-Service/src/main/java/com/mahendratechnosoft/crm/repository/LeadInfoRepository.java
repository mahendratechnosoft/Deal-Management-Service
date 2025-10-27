package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.Lead;


public interface LeadInfoRepository extends JpaRepository<Lead, String>{

}
