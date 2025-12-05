package com.mahendratechnosoft.crm.repository.Hospital;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.Hospital.DonorBloodReport;

public interface DonorBloodReportRepositroy extends JpaRepository<DonorBloodReport, String>{

	List<DonorBloodReport> findByDonorId(String donorId);
	
	Optional<DonorBloodReport> findFirstByDonorIdOrderByReportDateTimeDesc(String donorId);
}
