package com.mahendratechnosoft.crm.repository.Hospital;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahendratechnosoft.crm.entity.Hospital.SampleReport;

public interface SampleReportRepository extends JpaRepository<SampleReport, String>{
	
	SampleReport findByDonorId(String donorId);

}
