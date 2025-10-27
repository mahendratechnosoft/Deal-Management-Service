package com.mahendratechnosoft.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	Optional<Admin> findByUser_LoginEmail(String loginEmail);
}
