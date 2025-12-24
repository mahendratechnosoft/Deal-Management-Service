package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.PF;

@Repository
public interface PFRepository extends JpaRepository<PF, String>{

}
