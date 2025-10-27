package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

}
