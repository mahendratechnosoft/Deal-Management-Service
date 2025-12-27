package com.mahendratechnosoft.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.dto.TempEmailOTP;

public interface TempEmailOTPRepository extends JpaRepository<TempEmailOTP, String> {

	TempEmailOTP findByEmail(String email);
	
	 void deleteByEmail(String email);

}
