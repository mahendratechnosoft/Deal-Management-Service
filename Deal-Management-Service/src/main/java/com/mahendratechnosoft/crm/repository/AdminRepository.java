package com.mahendratechnosoft.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	@Query("SELECT a FROM Admin a JOIN FETCH a.user WHERE a.loginEmail = :loginEmail")
    Optional<Admin> findByLoginEmail(String loginEmail);
}
