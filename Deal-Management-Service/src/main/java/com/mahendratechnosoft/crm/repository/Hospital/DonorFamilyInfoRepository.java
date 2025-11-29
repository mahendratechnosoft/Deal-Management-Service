package com.mahendratechnosoft.crm.repository.Hospital;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.Hospital.DonorFamilyInfo;

public interface DonorFamilyInfoRepository extends JpaRepository<DonorFamilyInfo, String>{
	
	List<DonorFamilyInfo> findByDonorId(String donorId);

}
