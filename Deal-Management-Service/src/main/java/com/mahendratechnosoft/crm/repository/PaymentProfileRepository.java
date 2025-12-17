package com.mahendratechnosoft.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendratechnosoft.crm.dto.PaymentProfileDropdownDto;
import com.mahendratechnosoft.crm.entity.PaymentProfile;

@Repository
public interface PaymentProfileRepository extends JpaRepository<PaymentProfile, String> {

	List<PaymentProfile> findByAdminId(String adminId);
	
	@Query("SELECT new com.mahendratechnosoft.crm.dto.PaymentProfileDropdownDto(" +
	           "p.paymentProfileId, p.profileName, p.type, p.isDefault) " +
	           "FROM PaymentProfile p " +
	           "WHERE p.adminId = :adminId " +
	           "AND p.isActive = true " +
	           "AND p.forInvoice = true")
    List<PaymentProfileDropdownDto> findInvoiceDropdownByAdmin(@Param("adminId") String adminId);
	
}
