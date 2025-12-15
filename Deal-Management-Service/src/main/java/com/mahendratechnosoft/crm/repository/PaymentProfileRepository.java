package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.entity.PaymentProfile;

@Repository
public interface PaymentProfileRepository extends JpaRepository<PaymentProfile, String> {

	List<PaymentProfile> findByAdminId(String adminId);
}
